package com.yggdrasil.gamelobby

import com.yggdrasil.lobby.Lobby
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.instance.Instance
import net.minestom.server.instance.anvil.AnvilLoader

object LobbyInstance {

    private val eventNode: EventNode<Event> by lazy {
        return@lazy EventNode.all("Lobby").setPriority(-8)
    }

    private val instance: Instance by lazy {
        val instance = MinecraftServer.getInstanceManager().createInstanceContainer()
        instance.chunkLoader = AnvilLoader("lobby")
        return@lazy instance
    }

    val lobby: Lobby by lazy {
        return@lazy Lobby.Builder().setEventNode(eventNode).setInstance(instance)
            .setRespawnPos(Pos(-635.428, 7.0, -237.486))
            .setSpecialItemManager(SimpleSpecialManager())
            .build()
    }

}