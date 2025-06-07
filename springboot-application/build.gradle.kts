plugins {
    id("java")
    id("java-library")
    alias(libs.plugins.spring.boot)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    api(libs.spring.boot.starter) {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
    api(libs.spring.jdbc)
    runtimeOnly(libs.h2)
}

tasks.test {
    useJUnitPlatform()
}
