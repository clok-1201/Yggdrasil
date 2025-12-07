package com.yggdrasil.lobby

import com.c1ok.yggdrasil.Clickable
import com.c1ok.yggdrasil.SpecialItemManager
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.event.player.PlayerUseItemEvent
import net.minestom.server.instance.Instance
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import net.minestom.server.utils.validate.Check

class Lobby(
    val eventNode: EventNode<Event>,
    val specialItemManager: SpecialItemManager,
    val world: Instance,
    val respawnPos: Pos
) {

    companion object {
        lateinit var instance: Lobby
            private set

        private fun getIsInit(): Boolean {
            return ::instance.isInitialized
        }

        fun registerListener() {
            Check.isTrue(getIsInit(), "无法调用，lobbyinstance还没初始化")
            instance.eventNode.addListener(PlayerUseItemEvent::class.java) {
                if (it.player.instance != instance.world) {
                    return@addListener
                }
                if (instance.specialItemManager.isSpecial(it.itemStack)) {
                    val special = instance.specialItemManager.getSpecialHandler(it.itemStack) as? Clickable ?: return@addListener
                    special.onClick(it.player)
                }
            }

            instance.eventNode.addListener(AsyncPlayerConfigurationEvent::class.java) {
                it.spawningInstance = instance.world
            }

            instance.eventNode.addListener(PlayerSpawnEvent::class.java) {
                if(it.instance == instance.world) {
                    it.player.gameMode = GameMode.SURVIVAL
                    it.player.teleport(instance.respawnPos)
                }
            }
        }

    }

    fun apply() {
        if (MinecraftServer.getGlobalEventHandler().findChildren(eventNode.name).isEmpty()) {
            MinecraftServer.process().eventHandler().addChild(eventNode)
        }
        Check.isTrue(!getIsInit(), "无法再次应用，该Lobby已经初始化过一次了")
        instance = this
        NpcListener.register()
    }

    class Builder {

        lateinit var eventNode: EventNode<Event>
        lateinit var specialItemManager: SpecialItemManager
        lateinit var instance: Instance
        lateinit var respawnPos: Pos

        fun setEventNode(eventNode: EventNode<Event>): Builder {
            this.eventNode = eventNode
            return this
        }

        fun setSpecialItemManager(specialItemManager: SpecialItemManager): Builder {
            this.specialItemManager = specialItemManager
            return this
        }

        fun setInstance(instance: Instance): Builder {
            this.instance = instance
            return this
        }

        fun setRespawnPos(pos: Pos): Builder {
            this.respawnPos = pos
            return this
        }

        fun build(): Lobby {
            return Lobby(eventNode, specialItemManager, instance, respawnPos)
        }

    }

}