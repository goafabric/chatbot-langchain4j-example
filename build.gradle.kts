val group: String by project
val version: String by project
java.sourceCompatibility = JavaVersion.VERSION_21

val dockerRegistry = "goafabric"
val nativeBuilder = "paketobuildpacks/java-native-image:10.1.0"
val baseImage = "ibm-semeru-runtimes:open-21.0.4.1_7-jre-focal@sha256:8b94f8b14fd1d4660f9dc777b1ad3630f847b8e3dc371203bcb857a5e74d6c39"

plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    //id("org.graalvm.buildtools.native") version "0.10.3"

    id("com.google.cloud.tools.jib") version "3.4.4"
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

val langchain4JVersion = "0.35.0"

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
    //implementation("org.apache.opennlp:opennlp-tools:2.3.1")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
    exclude("**/*NRIT*")
    finalizedBy("jacocoTestReport")
}


