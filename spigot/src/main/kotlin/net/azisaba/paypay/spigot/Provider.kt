package net.azisaba.paypay.spigot

import net.azisaba.paypay.api.AzisabaPayPayAPI
import net.azisaba.paypay.api.AzisabaPayPayAPIProvider
import org.bukkit.plugin.java.JavaPlugin

class Provider : AzisabaPayPayAPIProvider {
    override fun get(): AzisabaPayPayAPI = JavaPlugin.getPlugin(SpigotPlugin::class.java).api
}
