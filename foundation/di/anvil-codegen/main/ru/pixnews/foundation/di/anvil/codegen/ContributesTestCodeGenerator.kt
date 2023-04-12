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
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.FqNames
import ru.pixnews.foundation.di.anvil.codegen.util.contributesToAnnotation
import java.io.File

@AutoService(CodeGenerator::class)
public class ContributesTestCodeGenerator : CodeGenerator {
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>,
    ): Collection<GeneratedFile> {
        return projectFiles
            .classAndInnerClassReferences(module)
            .filter { it.isAnnotatedWith(FqNames.contributesTest) }
            .map { generateTestModule(it, codeGenDir) }
            .toList()
    }

    private fun generateTestModule(
        annotatedClass: ClassReference,
        codeGenDir: File,
    ): GeneratedFile {
        val moduleClassId = annotatedClass.generateClassName(suffix = "_TestModule")
        val generatedPackage = moduleClassId.packageFqName.safePackageString()
        val moduleClassName = moduleClassId.relativeClassName.asString()

        val moduleSpecBuilder = TypeSpec.objectBuilder(moduleClassName)
            .addAnnotation(ClassNames.Dagger.module)
            .addAnnotation(contributesToAnnotation(ClassNames.appScope))
            .addFunction(generateProvideMethod(annotatedClass))

        val content = FileSpec.buildFile(generatedPackage, moduleClassName) {
            addType(moduleSpecBuilder.build())
        }
        return createGeneratedFile(codeGenDir, generatedPackage, moduleClassName, content)
    }

    private fun generateProvideMethod(
        annotatedClass: ClassReference,
    ): FunSpec {
        val testClass = annotatedClass.asClassName()
        return FunSpec.builder("provide${annotatedClass.shortName}Injector")
            .addAnnotation(ClassNames.Dagger.provides)
            .addAnnotation(ClassNames.Dagger.intoMap)
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.Dagger.classKey)
                    .addMember("%T::class", testClass)
                    .build(),
            )
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.singleIn)
                    .addMember("%T::class", ClassNames.appScope)
                    .build(),
            )
            .addParameter("injector", ClassNames.Dagger.membersInjector.parameterizedBy(testClass))
            .returns(ClassNames.singleInstrumentedTestInjector)
            .addStatement("return %T(injector)", ClassNames.singleInstrumentedTestInjector)
            .build()
    }
}
