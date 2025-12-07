plugins {
    id("java")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":afybroker:afybroker-core"))
}

tasks.test {
    useJUnitPlatform()
}