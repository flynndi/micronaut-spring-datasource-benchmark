plugins {
    id("java")
    id("java-library")
    alias(libs.plugins.micronaut.application)
    alias(libs.plugins.micronaut.aot)
}

dependencies {
    api(platform(libs.micronaut))
    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("com.h2database:h2")
    api("io.micronaut.data:micronaut-data-connection")
    api("io.micronaut.data:micronaut-data-tx-jdbc")
    api("io.micronaut.sql:micronaut-jdbc-hikari")
    api("com.zaxxer:HikariCP:6.3.0")
    api("io.micronaut:micronaut-aop")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass = "micronaut.benchmark.Application"
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.micronaut.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = false
        replaceLogbackXml = true
    }
}
