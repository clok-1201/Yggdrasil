plugins {
    id("java")
    kotlin("jvm") version "2.0.0"
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    api("org.slf4j:slf4j-api:2.0.16")
    api(project(":tiny-logger"))
    implementation("net.minestom:minestom:2025.07.11-1.21.7")
    implementation("org.jline:jline-reader:3.25.0")
    implementation("org.jline:jline-terminal:3.25.0")
    implementation("org.jline:jline-terminal-jna:3.25.0")
    implementation("org.fusesource.jansi:jansi:2.4.1")
}

tasks.test {
    useJUnitPlatform()
}