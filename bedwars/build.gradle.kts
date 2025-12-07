plugins {
    kotlin("jvm") version "2.0.0"
    id("java")
    id("java-library")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api("net.kyori:adventure-text-minimessage:4.23.0")
    api(project(":api"))
    api(project(":lobby"))
    api(project(":util-InvUI"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}