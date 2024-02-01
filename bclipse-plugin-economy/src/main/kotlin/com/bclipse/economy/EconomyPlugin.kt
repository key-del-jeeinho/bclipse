package com.bclipse.economy

import com.bclipse.core.command.SpigotCommandExtension.command
import com.bclipse.economy.adapter.inbound.command.AddBalanceCommand
import com.bclipse.economy.adapter.inbound.command.BalanceCommand
import com.bclipse.economy.adapter.inbound.command.QueryBalanceConfigCommand
import com.bclipse.economy.adapter.inbound.command.QueryMyBalanceCommand
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin

class EconomyPlugin: JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        saveDefaultConfig()
        initializeModules()
        command("balance", "돈") {
            BalanceCommand(
                QueryMyBalanceCommand("my"),
                AddBalanceCommand("add"),
                QueryBalanceConfigCommand("config"),
            )
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
        saveConfig()
    }
}

fun initializeModules() {
    startKoin {
        modules(appModule)
    }
}
