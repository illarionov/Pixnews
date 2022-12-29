/**
 * Convention plugin for use in android library modules
 */
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import ru.pixnews.configureCommonAndroid

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    configureCommonAndroid(this)

    defaultConfig {
        consumerProguardFiles("lib-proguard-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

kotlin {
    explicitApi = ExplicitApiMode.Warning
}

internal val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    versionCatalog.findLibrary("kotlinx.coroutines.bom").orElseThrow().also {
        implementation(platform(it))
        testImplementation(platform(it))
        androidTestImplementation(platform(it))
    }

    androidTestImplementation(kotlin("test"))
    testImplementation(kotlin("test"))
}