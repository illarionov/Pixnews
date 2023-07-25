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
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import ru.pixnews.foundation.di.anvil.codegen.util.ClassNames
import ru.pixnews.foundation.di.anvil.codegen.util.FqNames
import ru.pixnews.foundation.di.anvil.codegen.util.checkClassExtendsType
import ru.pixnews.foundation.di.anvil.codegen.util.contributesToAnnotation
import java.io.File

@AutoService(CodeGenerator::class)
public class ContributesExperimentCodeGenerator : CodeGenerator {
    override fun isApplicable(context: AnvilContext): Boolean = true

    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>,
    ): Collection<GeneratedFile> {
        val experimentAnnotatedClass = projectFiles.classAndInnerClassReferences(module)
            .filter { classRef ->
                classRef.annotations.any { annotationRef ->
                    annotationRef.fqName == FqNames.contributesExperiment ||
                            annotationRef.fqName == FqNames.contributesVariantSerializer
                }
            }
            .toSortedSet()

        return buildList {
            if (experimentAnnotatedClass.isNotEmpty()) {
                add(generateExperimentModule(experimentAnnotatedClass, codeGenDir))
            }
        }
    }

    private fun generateExperimentModule(
        annotatedClasses: Collection<ClassReference.Psi>,
        codeGenDir: File,
    ): GeneratedFile {
        val moduleClassId = annotatedClasses.first().generateClassName(suffix = "_Experiments_Module")
        val generatedPackage = moduleClassId.packageFqName.safePackageString()
        val moduleClassName = moduleClassId.relativeClassName.asString()

        val moduleTypeSpecBuilder = TypeSpec.objectBuilder(moduleClassName)
            .addAnnotation(ClassNames.Dagger.module)
            .addAnnotation(contributesToAnnotation(ClassNames.experimentScope))

        annotatedClasses.forEach {
            moduleTypeSpecBuilder.addFunction(generateProvidesMethod(it))
        }

        val content = FileSpec.buildFile(generatedPackage, moduleClassName) {
            addType(moduleTypeSpecBuilder.build())
        }
        return createGeneratedFile(codeGenDir, generatedPackage, moduleClassName, content)
    }

    private fun generateProvidesMethod(annotatedClass: ClassReference): FunSpec {
        val experimentAnnotations = annotatedClass.annotations.filter { annotationRef ->
            annotationRef.fqName == FqNames.contributesExperiment ||
                    annotationRef.fqName == FqNames.contributesVariantSerializer
        }

        require(experimentAnnotations.size == 1) {
            "$annotatedClass has an incorrect combination of annotations"
        }

        val annotation = experimentAnnotations.single()
        return when (annotation.fqName) {
            FqNames.contributesExperiment -> providesExperimentFunction(annotatedClass)
            FqNames.contributesVariantSerializer -> {
                val experimentKeyAnnotation = annotation.arguments.singleOrNull()
                    ?: throw IllegalArgumentException("experimentKey on ContributesExperimentVariant not defined")

                providesExperimentVariantSerializerFunction(
                    annotatedSerializer = annotatedClass,
                    experimentVariantKey = experimentKeyAnnotation.value(),
                )
            }

            else -> throw IllegalArgumentException("Unknown annotation $annotation")
        }
    }

    /**
     * `@Provides @IntoSet abstract fun provideMainExperiment(experiment: MainExperiment): Experiment`
     */
    private fun providesExperimentFunction(annotatedExperiment: ClassReference): FunSpec {
        annotatedExperiment.checkClassExtendsType(FqNames.experiment)

        return FunSpec.builder("provide${annotatedExperiment.shortName}")
            .addAnnotation(ClassNames.Dagger.provides)
            .addAnnotation(ClassNames.Dagger.intoSet)
            .returns(ClassNames.experiment)
            .addCode("return %T", annotatedExperiment.asClassName())
            .build()
    }

    /**
     * `@Provides @IntoSet abstract fun provideMainExperiment(experiment: MainExperiment): Experiment`
     */
    private fun providesExperimentVariantSerializerFunction(
        annotatedSerializer: ClassReference,
        experimentVariantKey: String,
    ): FunSpec {
        annotatedSerializer.checkClassExtendsType(FqNames.experimentVariantSerializer)

        return FunSpec.builder("provide${annotatedSerializer.shortName}")
            .addAnnotation(ClassNames.Dagger.provides)
            .addAnnotation(ClassNames.Dagger.intoMap)
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassNames.experimentVariantMapKey)
                    .addMember("key = %S", experimentVariantKey)
                    .build(),
            )
            .returns(ClassNames.experimentVariantSerializer)
            .addCode("return %T", annotatedSerializer.asClassName())
            .build()
    }
}
