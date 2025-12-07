package com.yggdrasil.util.velocity

import net.minestom.server.entity.Player
import net.minestom.server.network.NetworkBuffer

fun Player.stp(server: String) {
    sendPluginMessage("bungeecord:main", NetworkBuffer.makeArray {
        it.write(NetworkBuffer.STRING_IO_UTF8, "Connect")
        it.write(NetworkBuffer.STRING_IO_UTF8, server)
    })
}