plugins {
    val kotlinVersion = "1.4.30"
    kotlin("jvm") version kotlinVersion
    `java-library`
    application
}

repositories {
    jcenter()
}

dependencies {
    val kotlinTestVersion: String by project
    val junitJupiterVersion: String by project

    testImplementation("io.kotest:kotest-assertions-core:$kotlinTestVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "1.8"
    }

    getByName<JavaExec>("run").standardInput = System.`in`
}

application {
    mainClass.set("com.github.mkrawetko.utils.ssl.MainKt")
}
