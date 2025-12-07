package com.c1ok.bedwars

import net.minestom.server.MinecraftServer
import net.minestom.server.ServerFlag
import net.minestom.server.entity.Player
import net.minestom.server.entity.attribute.Attribute
import net.minestom.server.event.EventListener
import net.minestom.server.event.player.PlayerPacketEvent
import net.minestom.server.event.player.PlayerStartFlyingEvent
import net.minestom.server.network.packet.client.play.ClientInputPacket
import net.minestom.server.potion.PotionEffect
import net.minestom.server.potion.TimedPotion
import net.minestom.server.tag.Tag
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin


object Jump {
    val tag = Tag.Boolean("double_jump_available")

    fun enable() {
        val node = MinecraftServer.getGlobalEventHandler()
        node.addListener(
            EventListener.builder(PlayerPacketEvent::class.java)
            .handler { event ->
                if (event.packet is ClientInputPacket) {
                    val packet = event.packet as ClientInputPacket
                    handlePlayerMovement(event.player, packet)
                }
            }.build()
        )

        node.addListener(EventListener.builder(PlayerStartFlyingEvent::class.java)
            .handler { event ->
                val p: Player = event.player
                p.setTag(tag, false)
            }.build()
        )
    }

    private fun handlePlayerMovement(player: Player, packet: ClientInputPacket) {
        if (packet.jump()) {
            handleJumpInput(player)
        }
    }


    private fun handleJumpInput(player: Player) {
        if (player.isOnGround) {
            player.setTag(tag, true)
            return
        }

        val canDoubleJump: Boolean = player.getTag(tag)
        if (canDoubleJump) {
            jump(player)
            player.setTag(tag, false)
        }
    }

    private fun getJumpVelocity(player: Player): Double {
        return player.getAttribute(Attribute.JUMP_STRENGTH).value
    }

    private fun getJumpBoostVelocityModifier(player: Player): Double {
        val effect: TimedPotion? = player.getEffect(PotionEffect.JUMP_BOOST)
        return if (effect != null) (0.1 * (effect.potion().amplifier() + 1)) else 0.0
    }

    private fun jump(player: Player) {
        val tps = ServerFlag.SERVER_TICKS_PER_SECOND
        val yVel: Double = getJumpVelocity(player) + getJumpBoostVelocityModifier(player)
        player.velocity = player.velocity.withY(max(player.velocity.y(), yVel * tps))
        if (player.isSprinting) {
            val angle: Double = player.position.yaw() * (Math.PI / 180)
            player.velocity = player.velocity.add(-sin(angle) * 0.2 * tps, 0.0, cos(angle) * 0.2 * tps)
        }
    }

}