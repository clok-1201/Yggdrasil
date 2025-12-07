package com.yggdrasil.lobby

import net.minestom.server.event.entity.EntityAttackEvent
import net.minestom.server.event.player.PlayerEntityInteractEvent

object NpcListener {

    fun register() {
        val node = Lobby.instance.eventNode

        node.addListener(PlayerEntityInteractEvent::class.java) {
            val entity = it.target
            if (entity is NPC) {
                entity.handle(it)
            }
        }

        node.addListener(EntityAttackEvent::class.java) {
            val entity = it.target
            if (entity is NPC) {
                entity.handle(it)
            }
        }

    }

}