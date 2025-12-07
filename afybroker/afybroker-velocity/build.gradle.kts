plugins {
    id("java")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":afybroker:afybroker-client"))
    implementation("com.velocitypowered:velocity-api:3.1.0")
    implementation("org.bstats:bstats-velocity:3.0.2")
}

tasks.test {
    useJUnitPlatform()
}