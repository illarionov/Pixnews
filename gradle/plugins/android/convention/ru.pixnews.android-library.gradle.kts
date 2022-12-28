import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import ru.pixnews.configureCommonAndroid

/**
 * Convention plugin for use in android lbirary modules
 */
plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    configureCommonAndroid(this)

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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