/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package ru.pixnews.foundation.di.anvil.codegen

import com.google.auto.service.AutoService
import com.squareup.anvil.compiler.api.AnvilContext
import com.squareup.anvil.compiler.api.CodeGenerator
import com.squareup.anvil.compiler.api.GeneratedFile
import com.squareup.anvil.compiler.api.createGeneratedFile
import com.squareup.anvil.compiler.internal.asClassName
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.reference.ClassReference
import com.squareup.anvil.compiler.internal.reference.MemberFunctionReference
import com.squareup.anvil.compiler.internal.reference.allSuperTypeClassReferences
import com.squareup.anvil.compiler.internal.reference.asClassName
import com.squareup.anvil.compiler.internal.reference.asTypeName
import com.squareup.anvil.compiler.internal.reference.classAndInnerClassReferences
import com.squareup.anvil.compiler.internal.reference.generateClassName
import com.squareup.anvil.compiler.internal.safePackageString
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.psi.KtFile
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.FqNames
import ru.pixnews.foundation.di.anvil.codegen.util.contributesToAnnotation
import ru.pixnews.foundation.di.anvil.codegen.util.parseConstructorParameters
import java.io.File

@AutoService(CodeGenerator::class)
public class ContributesInitializerCodeGenerator : CodeGenerator {
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>,
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(FqNames.contributesInitializer) }
            .map { generateInitializerModule(it, codeGenDir) }
            .toList()
    }

    private fun generateInitializerModule(
        annotatedClass: ClassReference,
        codeGenDir: File,
    ): GeneratedFile {
        val boundType = checkNotNull(annotatedClass.getInitializerBoundType()) {
            "${annotatedClass.fqName} doesn't extend any of ${ClassNames.initializer} or ${ClassNames.asyncInitializer}"
        }

        val moduleClassId = annotatedClass.moduleNameForInitializer()
        val generatedPackage = moduleClassId.packageFqName.safePackageString()
        val moduleClassName = moduleClassId.relativeClassName.asString()

        val replaces: List<TypeName> = annotatedClass.annotations.first { it.fqName == FqNames.contributesInitializer }
            .replaces(parameterIndex = 0)
            .map { replacedClassRef ->
                if (replacedClassRef.isInitializer()) {
                    replacedClassRef.moduleNameForInitializer().asClassName()
                } else {
                    replacedClassRef.asTypeName()
                }
            }

        val moduleSpecBuilder = TypeSpec.objectBuilder(moduleClassName)
            .addAnnotation(ClassNames.Dagger.module)
            .addAnnotation(
                contributesToAnnotation(
                    className = ClassNames.appInitializersScope,
                    replaces = replaces,
                ),
            )
            .addFunction(generateProvideMethod(annotatedClass, boundType))

        val content = FileSpec.buildFile(generatedPackage, moduleClassName) {
            addType(moduleSpecBuilder.build())
        }

        return createGeneratedFile(codeGenDir, generatedPackage, moduleClassName, content)
    }

    private fun generateProvideMethod(
        annotatedClass: ClassReference,
        boundType: ClassName,
    ): FunSpec {
        val builder = FunSpec.builder("provide${annotatedClass.shortName}")
            .addAnnotation(ClassNames.Dagger.provides)
            .addAnnotation(ClassNames.Dagger.intoSet)
            .addAnnotation(ClassNames.Dagger.reusable)
            .returns(boundType)

        val primaryConstructor: MemberFunctionReference = annotatedClass.constructors.firstOrNull()
            ?: throw IllegalArgumentException("No primary constructor on $annotatedClass")
        val constructorParameters = primaryConstructor.parameters.parseConstructorParameters(annotatedClass)

        constructorParameters.forEach {
            builder.addParameter(it.name, it.resolvedType)
        }

        val initializerParams = constructorParameters.joinToString(", ") { "${it.name} = ${it.name}" }
        builder.addStatement("return %T(\n$initializerParams\n)", annotatedClass.asClassName())
        return builder.build()
    }

    private fun ClassReference.getInitializerBoundType(): ClassName? {
        return allSuperTypeClassReferences()
            .map(ClassReference::asClassName)
            .firstOrNull { it == ClassNames.initializer || it == ClassNames.asyncInitializer }
    }

    private fun ClassReference.isInitializer() = getInitializerBoundType() != null

    private fun ClassReference.moduleNameForInitializer(): ClassId = generateClassName(suffix = "_InitializerModule")
}
