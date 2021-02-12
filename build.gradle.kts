import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version("1.4.30")
    id("com.github.johnrengelman.shadow").version("6.1.0")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.15.80"))

    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.7.0")
    implementation("software.amazon.awssdk:dynamodb")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
        }
    }
}
