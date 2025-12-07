plugins {
    id("java")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    api("com.caucho:hessian:4.0.66")
    compileOnlyApi("org.slf4j:slf4j-api:1.7.21")
    compileOnlyApi("org.jetbrains:annotations:22.0.0")
    compileOnly("io.netty:netty-all:4.1.42.Final")
    compileOnly("com.google.guava:guava:33.0.0-jre")
}

tasks.test {
    useJUnitPlatform()
}