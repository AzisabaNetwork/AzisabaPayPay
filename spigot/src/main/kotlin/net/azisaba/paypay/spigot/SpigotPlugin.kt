package net.azisaba.paypay.spigot

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.encodeToString
import net.azisaba.paypay.api.config.PayPayCredentials
import net.azisaba.paypay.spigot.commands.AzisabaPayPayCommand
import net.azisaba.paypay.spigot.config.PluginConfig
import net.azisaba.paypay.spigot.gui.CustomCategoryScreen
import net.azisaba.paypay.spigot.gui.RankScreen
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SpigotPlugin : JavaPlugin() {
    val api = AzisabaPayPayAPIImpl(this)

    override fun onEnable() {
        PayPayCredentials.load(File(dataFolder, "credentials.yml"), PayPayCredentials())
        PluginConfig.load(File(dataFolder, "config.yml"), PluginConfig())
        if (PluginConfig.instance.overwrite) {
            File(dataFolder, "config.yml").writeText(Yaml.default.encodeToString(PluginConfig.instance))
        }

        Bukkit.getPluginManager().registerEvents(RankScreen.EventListener, this)
        Bukkit.getPluginManager().registerEvents(CustomCategoryScreen.EventListener, this)
        getCommand("azisabapaypay")?.executor = AzisabaPayPayCommand
    }
}
