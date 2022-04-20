import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.whisk"
version = "1.0-SNAPSHOT"

plugins {
    java
    kotlin("jvm") version "1.6.20-RC2"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.jayway.jsonpath:json-path:2.7.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.testcontainers:testcontainers:1.17.1")
    implementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
