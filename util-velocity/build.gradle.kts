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
    implementation(project(":api"))
}

tasks.test {
    useJUnitPlatform()
}