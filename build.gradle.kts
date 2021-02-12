import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version("1.4.30")
    id("com.github.johnrengelman.shadow").version("6.1.0")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.15.80"))
    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testRuntimeOnly(platform("org.junit:junit-bom:5.7.1"))

    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.7.0")
    implementation("software.amazon.awssdk:dynamodb")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("by.dev.madhead.aws-junit5:dynamo-v2:5.1.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
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

    test {
        useJUnitPlatform {
            excludeTags("db")
        }
    }

    val dbTest by registering(Test::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        description = "Runs the DB tests."
        shouldRunAfter("test")
        outputs.upToDateWhen { false }
        useJUnitPlatform {
            includeTags("db")
        }
    }
}
