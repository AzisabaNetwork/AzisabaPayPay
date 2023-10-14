package net.azisaba.paypay.spigot

import net.azisaba.paypay.api.Logger
import net.azisaba.paypay.api.scheduler.Scheduler
import net.azisaba.paypay.common.AbstractAzisabaPayPayAPI
import net.azisaba.paypay.spigot.scheduler.SpigotScheduler
import org.bukkit.plugin.java.JavaPlugin

class AzisabaPayPayAPIImpl(plugin: JavaPlugin) : AbstractAzisabaPayPayAPI() {
    private val logger = Logger.createFromJavaLogger(plugin.logger)
    private val scheduler = SpigotScheduler(plugin)

    override fun getLogger(): Logger = logger

    override fun getScheduler(): Scheduler = scheduler
}
