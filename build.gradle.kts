import org.gradle.internal.os.OperatingSystem

val version: String by project
val javaVersion = "25"
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)

val dockerRegistry = "goafabric"
val baseImage = "ibm-semeru-runtimes:open-jdk-25.0.0_36-jre@sha256:8ae073345116cfd51ec37b26c3a1c25de9336d436354e0be4271bda1463e119c"

plugins {
    java
    jacoco
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.11.4"

    id("com.google.cloud.tools.jib") version "3.5.2"

    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    kotlin("plugin.jpa") version "2.3.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    constraints {
        annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
        implementation("org.mapstruct:mapstruct:1.6.2")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
        implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
        implementation("net.ttddyy.observation:datasource-micrometer-spring-boot:1.0.5")
        testImplementation("com.tngtech.archunit:archunit-junit5:1.3.0")
    }
}

val langchain4JVersion = "1.10.0"
val langchain4JBetaVersion = "1.10.0-beta18"

dependencies {
    //web
    implementation("org.springframework.boot:spring-boot-starter")

    //langchain
    implementation("dev.langchain4j:langchain4j:${langchain4JVersion}")
    implementation("dev.langchain4j:langchain4j-open-ai:${langchain4JVersion}")
    implementation("dev.langchain4j:langchain4j-ollama:${langchain4JVersion}")
    implementation("dev.langchain4j:langchain4j-mcp:${langchain4JBetaVersion}")

    //rag
    implementation("dev.langchain4j:langchain4j-embeddings-bge-small-en-v15-q:${langchain4JBetaVersion}")
    implementation("dev.langchain4j:langchain4j-document-parser-apache-tika:${langchain4JBetaVersion}")
    implementation("dev.langchain4j:langchain4j-pgvector:${langchain4JBetaVersion}")

    implementation("dev.langchain4j:langchain4j-agentic:${langchain4JBetaVersion}")
    //persistence
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {exclude("org.glassfish.jaxb", "jaxb-runtime")}
    implementation("com.h2database:h2")
    implementation("org.postgresql:postgresql")


    //tools
    //implementation("org.apache.opennlp:opennlp-tools:2.3.1")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("tools.jackson.module:jackson-module-kotlin")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
    exclude("**/*NRIT*")
    finalizedBy("jacocoTestReport")
}



graalvmNative {
    binaries.named("main") {
        quickBuild.set(true)
        buildArgs.add("--initialize-at-build-time=org.slf4j.helpers.Reporter")
    }
}
