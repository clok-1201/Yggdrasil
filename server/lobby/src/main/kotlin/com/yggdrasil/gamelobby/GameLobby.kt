package com.yggdrasil.gamelobby

import ServerInfo
import ServerInfo.minestomData
import com.redstone.beacon.internal.core.terminal.EasyTerminal
import com.yggdrasil.lobby.Lobby
import com.yggdrasil.util.velocity.StpCommand
import net.minestom.server.Auth
import net.minestom.server.MinecraftServer
import net.minestom.server.extras.lan.OpenToLAN

fun main() {
    ServerInfo.setData()
    if (minestomData.server.terminal) {
        EasyTerminal.start()
    }
    ServerInfo.displayServerInfo()
    setProperty()
    val auth = proxyHandle(minestomData.proxy, minestomData.network)
    val server = auth?.let {
        return@let MinecraftServer.init(it)
    } ?: MinecraftServer.init()

    LobbyInstance.lobby.apply()
    Lobby.registerListener()
    MinecraftServer.getSchedulerManager().buildShutdownTask {
        if (minestomData.server.terminal) {
            EasyTerminal.stop()
        }
    }

    val command = StpCommand("stp")
    command.init()
    MinecraftServer.getCommandManager().register(command)
    server.start(minestomData.network.ip, minestomData.network.port)

}

private fun proxyHandle(proxyData: MinestomData.Proxy, networkData: MinestomData.Network): Auth? {
    if (networkData.openToLan) {
        OpenToLAN.open()
    }
    if (proxyData.enable) {
        val proxyType: kotlin.String = proxyData.type

        if (proxyType.equals("velocity", ignoreCase = true)) {
            Auth.Velocity(proxyData.secret)
        } else if (proxyType.equals("bungeecord", ignoreCase = true)) {
            Auth.Bungee(setOf(proxyData.secret))
        }
    }
    return null
}

private fun setProperty() {
    //设置服务器每秒的tps
    System.setProperty("minestom.tps",
        minestomData.server.tickPreSecond.toString()
    )

    System.setProperty("minestom.chunk-view-distance",
        minestomData.server.chunkViewDistance.toString()
    )

    System.setProperty("minestom.entity-view-distance",
        minestomData.server.entityViewDistance.toString()
    )

    System.setProperty("minestom.dispatcher-threads", minestomData.server.threads.toString())

}