/**
 * Convention plugin that adds additional directory to the gradle-dsl source directory list
 * Workaround for https://github.com/gradle/gradle/issues/21052
 */

plugins {
    `java-gradle-plugin`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java.srcDir("convention")
    }
}

// apply kotlin-dsl plugin last, because it erroneously fetches source dirs eagerly.
apply(plugin = "org.gradle.kotlin.kotlin-dsl")

