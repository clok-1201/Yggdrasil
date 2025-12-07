package com.c1ok.bedwars.server

import ServerInfo.minestomData
import com.c1ok.bedwars.Bedwars
import com.c1ok.bedwars.Jump
import com.c1ok.bedwars.commands.Test
import com.c1ok.bedwars.commands.TestStart
import com.c1ok.bedwars.games.train.Train
import com.c1ok.bedwars.simple.BedwarsManager
import com.c1ok.bedwars.simple.SimpleMiniPlayerManager
import com.c1ok.bedwars.simple.SimpleSpecialManager
import com.c1ok.bedwars.utils.setSpecial
import com.redstone.beacon.internal.core.terminal.EasyTerminal
import com.yggdrasil.lobby.Lobby
import net.kyori.adventure.text.Component
import net.minestom.server.Auth
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.GameMode
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.event.player.PlayerTickEvent
import net.minestom.server.extras.lan.OpenToLAN
import net.minestom.server.instance.anvil.AnvilLoader
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material


val eventNode by lazy {
    EventNode.all("BedwarsGame").setPriority(-8)
}

fun main() {
    ServerInfo.setData()
    if (minestomData.server.terminal) {
        EasyTerminal.start()
    }
    ServerInfo.displayServerInfo()
    setProperty()
    EasyTerminal.start()
    val auth = proxyHandle(minestomData.proxy, minestomData.network)
    val server = auth?.let {
        return@let MinecraftServer.init(it)
    } ?: MinecraftServer.init()

    Jump.enable()
    registerLobby()
    registerBedwars()
    listenerSetter()
    val game1 = Train()
    game1.init()
    Bedwars.instance.gameManager.addGameToManager(game1)
    MinecraftServer.getCommandManager().register(Test, TestStart)
    server.start(minestomData.network.ip, minestomData.network.port)
    MinecraftServer.getGlobalEventHandler().addListener(PlayerTickEvent::class.java) {
        val event = it
        val velocity: Vec = event.player.velocity
        event.player.sendActionBar(
            Component.text(
                String.format("x=%.3f  ,  y=%.3f  ,  z=%.3f", velocity.x(), velocity.y(), velocity.z())
            )
        )
    }
    MinecraftServer.getSchedulerManager().buildShutdownTask {
        EasyTerminal.stop()
    }
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
fun registerLobby() {
    val instance = MinecraftServer.getInstanceManager().createInstanceContainer()
    instance.chunkLoader = AnvilLoader("lobby")
    val lobby = Lobby.Builder()
        .setInstance(instance)
        .setRespawnPos(Pos(-2.5, 26.0, -40.0))
        .setEventNode(eventNode)
        .setSpecialItemManager(SimpleSpecialManager())
        .build()
    lobby.apply()
    lobby.specialItemManager.addSpecial(SelectGameItem)
    Lobby.registerListener()
}

fun registerBedwars() {
    val bedwars = Bedwars.Builder(MinecraftServer.process())
        .setLobby(Lobby.instance)
        .setEventNode(eventNode)
        .setGameManager(BedwarsManager())
        .setPlayerManager(SimpleMiniPlayerManager())
        .build()
    bedwars.apply()
}

private fun listenerSetter() {


    MinecraftServer.getGlobalEventHandler().addListener(PlayerSpawnEvent::class.java) {
        if(it.instance == Bedwars.instance.lobby.world) {
            if (it.isFirstSpawn) {
                it.player.inventory.addItemStack(
                    ItemStack.builder(Material.COMPASS).customName(Component.text("测试游戏选择器")).setSpecial("selectGame").build())
            }
        }
    }

}