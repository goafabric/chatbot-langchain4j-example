group = "org.goafabric"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val dockerRegistry = "goafabric"
val graalvmBuilderImage = "ghcr.io/graalvm/native-image-community:21.0.0"
val baseImage = "ibm-semeru-runtimes:open-20.0.1_9-jre-focal@sha256:f1a10da50d02f51e79e3c9604ed078a39c19cd2711789cab7aa5d11071482a7e"
jacoco.toolVersion = "0.8.9"

plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    //id("org.graalvm.buildtools.native") version "0.9.28"
    id("com.google.cloud.tools.jib") version "3.4.0"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

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
    
    implementation("dev.langchain4j:langchain4j:0.24.0")
    implementation("dev.langchain4j:langchain4j-open-ai:0.24.0")
    implementation("dev.langchain4j:langchain4j-ollama:0.24.0")
    implementation("dev.langchain4j:langchain4j-local-ai:0.24.0")

    implementation("org.mapdb:mapdb:3.0.9")
    implementation("dev.langchain4j:langchain4j-embeddings-all-minilm-l6-v2:0.24.0")

    //persistence
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {exclude("org.glassfish.jaxb", "jaxb-runtime")}
    implementation("com.h2database:h2")

    //tools
    implementation("org.apache.opennlp:opennlp-tools:2.3.1")
    //implementation("commons-codec:commons-codec:1.16.0")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
    exclude("**/*NRIT*")
    finalizedBy("jacocoTestReport")
}


