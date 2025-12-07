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
    api("com.google.guava:guava:33.0.0-jre")
    api("org.slf4j:slf4j-api:1.7.21")
    api("io.netty:netty-all:4.1.42.Final")
    api("com.google.code.gson:gson:2.8.9")
    api("org.yaml:snakeyaml:1.30")
    api("net.sf.trove4j:core:3.1.0")
    api("jline:jline:2.14.6")
    api("ch.qos.logback:logback-classic:1.5.16")
}

tasks.test {
    useJUnitPlatform()
}