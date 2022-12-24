plugins {
    `java-gradle-plugin`
    `kotlin-dsl` apply false
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
    main {
        java.srcDir("src")
    }
}

// Workaround for https://github.com/gradle/gradle/issues/21052 -
// apply kotlin-dsl plugin last, because it erroneously fetches source dirs eagerly.
apply(plugin = "org.gradle.kotlin.kotlin-dsl")

dependencies {
    implementation("com.gradle:gradle-enterprise-gradle-plugin:3.12.1")
}
