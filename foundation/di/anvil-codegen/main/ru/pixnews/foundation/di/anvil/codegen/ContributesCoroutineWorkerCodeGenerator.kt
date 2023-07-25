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
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.reference.ClassReference
import com.squareup.anvil.compiler.internal.reference.asClassName
import com.squareup.anvil.compiler.internal.reference.classAndInnerClassReferences
import com.squareup.anvil.compiler.internal.reference.generateClassName
import com.squareup.anvil.compiler.internal.safePackageString
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.ABSTRACT
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.KModifier.PUBLIC
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec.Companion
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.FqNames
import ru.pixnews.foundation.di.anvil.codegen.util.checkClassExtendsType
import ru.pixnews.foundation.di.anvil.codegen.util.contributesMultibindingAnnotation
import java.io.File

@AutoService(CodeGenerator::class)
public class ContributesCoroutineWorkerCodeGenerator : CodeGenerator {
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>,
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(FqNames.contributesCoroutineWorker) }
            .map { generateWorkManagerFactory(it, codeGenDir) }
            .toList()
    }

    private fun generateWorkManagerFactory(
        annotatedClass: ClassReference,
        codeGenDir: File,
    ): GeneratedFile {
        annotatedClass.checkClassExtendsType(FqNames.Android.coroutineWorker)

        val workerClassName = annotatedClass.asClassName()
        val factoryClassId = annotatedClass.generateClassName(suffix = "_AssistedFactory")
        val generatedPackage = factoryClassId.packageFqName.safePackageString()
        val factoryClassName = factoryClassId.relativeClassName.asString()

        val factoryInterfaceSpec = Companion.interfaceBuilder(factoryClassName)
            .addAnnotation(ClassNames.Dagger.assistedFactory)
            .addAnnotation(contributesMultibindingAnnotation(ClassNames.workManagerScope))
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.coroutineWorkerMapKey)
                    .addMember("%T::class", workerClassName)
                    .build(),
            )
            .addSuperinterface(ClassNames.coroutineWorkerFactory)
            .addFunction(createWorkerFunction(workerClassName))
            .build()
        val content = FileSpec.buildFile(generatedPackage, factoryClassName) {
            addType(factoryInterfaceSpec)
        }
        return createGeneratedFile(codeGenDir, generatedPackage, factoryClassName, content)
    }

    /**
     * ```
     * override fun create(@ApplicationContext context: Context, workerParameters: WorkerParameters): <annotatedClass>
     * ```
     */
    private fun createWorkerFunction(workerClass: ClassName): FunSpec {
        return FunSpec.builder("create")
            .addModifiers(ABSTRACT, OVERRIDE, PUBLIC)
            .addParameter(
                ParameterSpec.builder("context", ClassNames.Android.context)
                    .addAnnotation(ClassNames.applicationContext)
                    .build(),
            )
            .addParameter("workerParameters", ClassNames.Android.workerParameters)
            .returns(workerClass)
            .build()
    }
}
