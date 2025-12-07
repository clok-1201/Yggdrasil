package com.yggdrasil.gamelobby

import net.minestom.server.inventory.Inventory
import net.minestom.server.inventory.InventoryType

object GameInventory {
    val inventory: Inventory = Inventory(InventoryType.CHEST_3_ROW, "game inventory")
}