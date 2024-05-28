val group: String by project
val version: String by project
java.sourceCompatibility = JavaVersion.VERSION_21

val dockerRegistry = "goafabric"
val nativeBuilder = "dashaun/builder:20240403"
val baseImage = "ibm-semeru-runtimes:open-21.0.1_12-jre-focal@sha256:24d43669156684f7bc28536b22537a7533ab100bf0a5a89702b987ebb53215be"

plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    //id("org.graalvm.buildtools.native") version "0.10.2"

    id("com.google.cloud.tools.jib") version "3.4.2"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

val langchain4JVersion = "0.24.0"
dependencies {
    constraints {
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
        implementation("org.mapstruct:mapstruct:1.5.5.Final")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
        implementation("io.github.resilience4j:resilience4j-spring-boot3:2.1.0")
    }
}

dependencies {
    //web
    implementation("org.springframework.boot:spring-boot-starter")
    
    implementation("dev.langchain4j:langchain4j:${langchain4JVersion}")
    implementation("dev.langchain4j:langchain4j-open-ai:${langchain4JVersion}")
    implementation("dev.langchain4j:langchain4j-ollama:${langchain4JVersion}")

    //persistence
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {exclude("org.glassfish.jaxb", "jaxb-runtime")}
    implementation("com.h2database:h2")

    //tools
    implementation("org.apache.opennlp:opennlp-tools:2.3.1")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
    exclude("**/*NRIT*")
    finalizedBy("jacocoTestReport")
}


