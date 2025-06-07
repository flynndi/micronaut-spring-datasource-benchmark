plugins {
    id("application")
    id("java-library")
//    alias(libs.plugins.micronaut.application)
//    alias(libs.plugins.micronaut.aot)
}

dependencies {
    api(platform(libs.micronaut))
    api("io.micronaut.data:micronaut-data-connection-jdbc")
    api("io.micronaut.sql:micronaut-jdbc-hikari")

    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-inject")

    runtimeOnly("com.h2database:h2")
}

application {
    mainClass = "micronaut.benchmark.Application"
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

/*
micronaut {
    version(libs.versions.micronaut.get())
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
*/
