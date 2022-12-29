/**
 * Convention plugin that configures android application
 */
import ru.pixnews.configureCommonAndroid
import ru.pixnews.configureCompose
import java.io.StringReader
import java.util.Properties

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    configureCommonAndroid(this)
    defaultConfig {
        targetSdk = 33
    }

    val releaseKeystorePropertiesFilePath = providers.gradleProperty("PIXNEWS_RELEASE_KEYSTORE_PROPERTIES_FILE")
        .orElse("config/signing/release.properties")
    val releaseKeystorePropertiesContent = providers.fileContents(
        rootProject.layout.projectDirectory.file(releaseKeystorePropertiesFilePath)
    ).asText
    val useReleaseKeystore = releaseKeystorePropertiesContent.isPresent

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("config/signing/debug.jks")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        if (useReleaseKeystore) {
            val releaseProperties = Properties().apply {
                load(StringReader(releaseKeystorePropertiesContent.get()))
            }
            create("release") {
                storeFile = rootProject.file(releaseProperties["storeFile"] as String)
                keyAlias = releaseProperties["keyAlias"] as String
                storePassword =  releaseProperties["storePassword"] as String
                keyPassword = releaseProperties["keyPassword"] as String
            }
        } else {
            create("release")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(if (useReleaseKeystore) "release" else "debug")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions.resources.excludes += listOf(
        "**/*.properties",
        "*.properties",
        "kotlin/**",
        "LICENSE.txt",
        "LICENSE_OFL",
        "LICENSE_UNICODE",
        "META-INF/*.kotlin_module",
        "META-INF/*.version",
        "META-INF/androidx.*",
        "META-INF/CHANGES",
        "META-INF/com.uber.crumb/**",
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt",
        "META-INF/NOTICE",
        "META-INF/NOTICE.txt",
    )
    configureCompose(this)
}

internal val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(kotlin("stdlib"))
    versionCatalog.findLibrary("kotlinx.coroutines.bom").orElseThrow().also {
        implementation(platform(it))
        testImplementation(platform(it))
        androidTestImplementation(platform(it))
    }
}