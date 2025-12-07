plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "Yggdrasil"
include("api")
include("bedwars")
include("bedwars:example")
findProject(":bedwars:example")?.name = "example"
include("util-InvUI")
include("lobby")
include("server")
include("server:lobby")
findProject(":server:lobby")?.name = "serverlobby"
include("plugin-eco")
include("util-config")
include("bukkit-configuration")
include("tiny-logger")
include("util-terminal")
include("util-velocity")

include("afybroker")
include("afybroker:afybroker-core")
findProject(":afybroker:afybroker-core")?.name = "afybroker-core"
include("afybroker:afybroker-client")
findProject(":afybroker:afybroker-client")?.name = "afybroker-client"
include("afybroker:afybroker-server")
findProject(":afybroker:afybroker-server")?.name = "afybroker-server"
include("afybroker:afybroker-boostrap")
findProject(":afybroker:afybroker-boostrap")?.name = "afybroker-boostrap"
include("afybroker:afybroker-minestom")
findProject(":afybroker:afybroker-minestom")?.name = "afybroker-minestom"
include("afybroker:afybroker-velocity")
findProject(":afybroker:afybroker-velocity")?.name = "afybroker-velocity"
