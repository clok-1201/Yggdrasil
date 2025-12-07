import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api(project(":util-terminal"))
    api(project(":bedwars"))
    implementation(project(":util-config"))
    testImplementation("net.minestom:testing:2025.09.13-1.21.8")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    manifest {
        attributes(
            "Main-Class" to "com.c1ok.bedwars.server.RunKt",
            "Multi-Release" to true
        )
    }
    mergeServiceFiles()
}

kotlin {
    jvmToolchain(21)
}