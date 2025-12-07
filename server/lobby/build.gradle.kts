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
    api(project(":api"))
    implementation(project(":lobby"))
    implementation(project(":util-config"))
    implementation(project(":util-velocity"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    manifest {
        attributes(
            "Main-Class" to "com.yggdrasil.gamelobby.GameLobbyKt",
            "Multi-Release" to true
        )
    }
}

kotlin {
    jvmToolchain(21)
}