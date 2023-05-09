/*
 * Copyright 2023 Alexey Illarionov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.WildcardTypeName
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.FqNames
import ru.pixnews.foundation.di.anvil.codegen.util.checkClassExtendsType
import ru.pixnews.foundation.di.anvil.codegen.util.contributesToAnnotation
import java.io.File

@AutoService(CodeGenerator::class)
public class ContributesActivityCodeGenerator : CodeGenerator {
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>,
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(FqNames.contributesActivity) }
            .map { generateActivityModule(it, codeGenDir) }
            .toList()
    }

    private fun generateActivityModule(
        annotatedClass: ClassReference,
        codeGenDir: File,
    ): GeneratedFile {
        annotatedClass.checkClassExtendsType(FqNames.Android.activity)

        val moduleClassId = annotatedClass.generateClassName(suffix = "_ActivityModule")
        val generatedPackage = moduleClassId.packageFqName.safePackageString()
        val moduleClassName = moduleClassId.relativeClassName.asString()

        val moduleInterfaceSpec = TypeSpec.interfaceBuilder(moduleClassName)
            .addAnnotation(ClassNames.Dagger.module)
            .addAnnotation(contributesToAnnotation(ClassNames.activityScope))
            .addFunction(generateBindMethod(annotatedClass))
            .build()

        val content = FileSpec.buildFile(generatedPackage, moduleClassName) {
            addType(moduleInterfaceSpec)
        }
        return createGeneratedFile(codeGenDir, generatedPackage, moduleClassName, content)
    }

    private fun generateBindMethod(
        annotatedClass: ClassReference,
    ): FunSpec {
        val activityClass = annotatedClass.asClassName()

        // MembersInjector<out Activity>
        val returnType = ClassNames.Dagger.membersInjector
            .parameterizedBy(WildcardTypeName.producerOf(ClassNames.Android.activity))

        return FunSpec.builder("binds${annotatedClass.shortName}Injector")
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(ClassNames.Dagger.binds)
            .addAnnotation(ClassNames.Dagger.intoMap)
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.activityMapKey)
                    .addMember("activityClass = %T::class", activityClass)
                    .build(),
            )
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.singleIn)
                    .addMember("clazz = %T::class", ClassNames.activityScope)
                    .build(),
            )
            .addParameter("target", ClassNames.Dagger.membersInjector.parameterizedBy(activityClass))
            .returns(returnType)
            .build()
    }
}
