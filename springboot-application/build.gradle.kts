plugins {
    id("java")
    id("java-library")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    api(libs.spring.boot.starter)
    api(libs.spring.jdbc)
    runtimeOnly(libs.h2)
}

tasks.test {
    useJUnitPlatform()
}
