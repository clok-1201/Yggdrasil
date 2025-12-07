plugins {
    kotlin("jvm") version "2.0.0"
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":api"))
    api(project(":bukkit-configuration"))
    api(project(":tiny-logger"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}