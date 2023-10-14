package net.azisaba.paypay.spigot

import net.azisaba.paypay.api.config.PayPayCredentials
import net.azisaba.paypay.spigot.commands.AzisabaPayPayCommand
import net.azisaba.paypay.spigot.gui.RankScreen
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SpigotPlugin : JavaPlugin() {
    val api = AzisabaPayPayAPIImpl(this)

    override fun onLoad() {
        PayPayCredentials.load(File(dataFolder, "credentials.yml"), PayPayCredentials())
    }

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(RankScreen.EventListener, this)
        getCommand("azisabapaypay")?.executor = AzisabaPayPayCommand
    }
}
