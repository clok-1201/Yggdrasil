package com.yggdrasil.util.velocity

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player

open class StpCommand(command: String, vararg commands: String): Command(command, *commands) {
    fun init() {
        val arg = ArgumentType.String("server")
        addSyntax({ sender, contexts ->
            val player = sender as Player
            val server = contexts.get(arg)
            player.stp(server)
        }, arg)
    }
}