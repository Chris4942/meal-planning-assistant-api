import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
    java
    id("org.springframework.boot") version "2.5.5"
}
apply(plugin = "io.spring.dependency-management")

group = "me.chriswest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("org.mockito:mockito-core:4.0.0")
    testImplementation(kotlin("test"))
    implementation("org.mongodb:mongo-java-driver:3.12.10")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.data:spring-data-mongodb:3.2.5")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-all:1.10.19")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

