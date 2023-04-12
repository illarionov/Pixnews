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
import com.squareup.anvil.compiler.internal.reference.MemberFunctionReference
import com.squareup.anvil.compiler.internal.reference.asClassName
import com.squareup.anvil.compiler.internal.reference.classAndInnerClassReferences
import com.squareup.anvil.compiler.internal.reference.generateClassName
import com.squareup.anvil.compiler.internal.safePackageString
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.FqNames
import ru.pixnews.foundation.di.anvil.codegen.util.checkClassExtendsBoundType
import ru.pixnews.foundation.di.anvil.codegen.util.contributesToAnnotation
import ru.pixnews.foundation.di.anvil.codegen.util.isSavedStateHandle
import ru.pixnews.foundation.di.anvil.codegen.util.parseConstructorParameters
import java.io.File

@AutoService(CodeGenerator::class)
public class ContributesViewModelCodeGenerator : CodeGenerator {
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>,
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(FqNames.contributesViewModel) }
            .map { generateViewModelModule(it, codeGenDir) }
            .toList()
    }

    private fun generateViewModelModule(
        annotatedClass: ClassReference,
        codeGenDir: File,
    ): GeneratedFile {
        annotatedClass.checkClassExtendsBoundType(FqNames.Android.viewModel)

        val moduleClassId = annotatedClass.generateClassName(suffix = "_FactoryModule")
        val generatedPackage = moduleClassId.packageFqName.safePackageString()
        val moduleClassName = moduleClassId.relativeClassName.asString()

        val moduleInterfaceSpecBuilder = TypeSpec.objectBuilder(moduleClassName)
            .addAnnotation(ClassNames.Dagger.module)
            .addAnnotation(contributesToAnnotation(ClassNames.viewModelScope))
            .addFunction(generateProvidesFactoryMethod(annotatedClass))

        val content = FileSpec.buildFile(generatedPackage, moduleClassName) {
            addType(moduleInterfaceSpecBuilder.build())
        }
        return createGeneratedFile(codeGenDir, generatedPackage, moduleClassName, content)
    }

    @Suppress("SpreadOperator")
    private fun generateProvidesFactoryMethod(
        annotatedClass: ClassReference,
    ): FunSpec {
        val viewModelClass = annotatedClass.asClassName()

        val primaryConstructor: MemberFunctionReference = annotatedClass.constructors.firstOrNull()
            ?: throw IllegalArgumentException("No primary constructor on $annotatedClass")
        val primaryConstructorParams = primaryConstructor.parameters.parseConstructorParameters(annotatedClass)

        val builder = FunSpec.builder("provides${annotatedClass.shortName}ViewModelFactory")
            .addAnnotation(ClassNames.Dagger.provides)
            .addAnnotation(ClassNames.Dagger.intoMap)
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.viewModelMapKey)
                    .addMember("%T::class", viewModelClass)
                    .build(),
            )
            .returns(ClassNames.viewModelFactory)

        primaryConstructorParams
            .filter { !it.isSavedStateHandle() }
            .forEach { builder.addParameter(it.name, it.resolvedType) }

        val viewModelConstructorParameters = primaryConstructorParams.joinToString(separator = "\n") {
            if (!it.isSavedStateHandle()) {
                "${it.name} = ${it.name},"
            } else {
                "${it.name} = it.%M()"
            }
        }
        val createViewModeStatementArgs: Array<Any> = primaryConstructorParams.mapNotNull {
            if (it.isSavedStateHandle()) createSavedStateHandleMember else null
        }.toTypedArray()

        builder.beginControlFlow("return %T", ClassNames.viewModelFactory)
        builder.addStatement("%T(\n$viewModelConstructorParameters\n)", viewModelClass, *createViewModeStatementArgs)
        builder.endControlFlow()
        return builder.build()
    }

    private companion object {
        private val createSavedStateHandleMember = MemberName(
            packageName = "androidx.lifecycle",
            simpleName = "createSavedStateHandle",
            isExtension = true,
        )
    }
}
