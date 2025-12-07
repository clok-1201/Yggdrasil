package com.yggdrasil.lobby

import net.kyori.adventure.sound.Sound
import net.minestom.server.entity.EntityCreature
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.Player
import net.minestom.server.entity.PlayerHand
import net.minestom.server.event.entity.EntityAttackEvent
import net.minestom.server.event.player.PlayerEntityInteractEvent
import net.minestom.server.sound.SoundEvent
import java.util.function.Consumer


open class NPC(
    entityType: EntityType,
    private val onClick: Consumer<Player>
): EntityCreature(entityType) {

    fun handle(event: EntityAttackEvent) {
        if (event.target !== this) return
        if (event.entity !is Player) return

        val player = event.entity as Player
        player.playSound(
            Sound.sound()
                .type(SoundEvent.BLOCK_NOTE_BLOCK_PLING)
                .pitch(2f)
                .build(), event.target
        )
        onClick.accept(player)
    }

    fun handle(event: PlayerEntityInteractEvent) {
        if (event.target !== this) return
        if (event.hand != PlayerHand.MAIN) return

        event.entity.playSound(
            Sound.sound()
                .type(SoundEvent.BLOCK_NOTE_BLOCK_PLING)
                .pitch(2f)
                .build(), event.target
        )
        onClick.accept(event.entity)
    }

}