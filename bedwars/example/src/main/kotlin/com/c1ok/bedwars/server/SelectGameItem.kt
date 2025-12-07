package com.c1ok.bedwars.server

import com.c1ok.bedwars.inventory.GameInventory
import com.c1ok.yggdrasil.Clickable
import com.c1ok.yggdrasil.SpecialItem
import net.minestom.server.entity.Player

object SelectGameItem: SpecialItem, Clickable {
    override val tagValue: String = "selectGame"

    override fun onClick(player: Player) {
        player.openInventory(GameInventory.inventory)
    }
}