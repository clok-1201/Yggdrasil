plugins {
    id("java")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    api("org.tinylog:tinylog-api:2.7.0")
    api("org.tinylog:tinylog-impl:2.7.0")
    api("org.tinylog:slf4j-tinylog:2.7.0")
}

tasks.test {
    useJUnitPlatform()
}