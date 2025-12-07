package com.c1ok.bedwars.simple.specialitems

import com.c1ok.bedwars.Bedwars
import com.c1ok.bedwars.BedwarsGame
import com.c1ok.yggdrasil.Clickable
import com.c1ok.yggdrasil.SpecialItem
import net.minestom.server.entity.Player

class ExitTeam(val game: BedwarsGame): SpecialItem, Clickable {

    override val tagValue: String = "exitTeam"

    override fun onClick(player: Player) {
        val mp = Bedwars.instance.playerManager.getPlayerFromUUID(player.uuid) ?: return
        game.removePlayer(mp)
    }

}