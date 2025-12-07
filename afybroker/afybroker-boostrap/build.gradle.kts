plugins {
    id("java")
}

group = "com.c1ok.yggdrasil"
version = "1.0-Alpha"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":afybroker:afybroker-server"))
}

val mainClazz = "net.afyer.afybroker.server.BootStrap"

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to mainClazz,
        )
    }
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}