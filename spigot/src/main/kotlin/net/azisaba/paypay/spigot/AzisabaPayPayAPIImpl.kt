package net.azisaba.paypay.spigot

import net.azisaba.paypay.api.Currency
import net.azisaba.paypay.api.Logger
import net.azisaba.paypay.api.scheduler.Scheduler
import net.azisaba.paypay.common.AbstractAzisabaPayPayAPI
import net.azisaba.paypay.spigot.config.PluginConfig
import net.azisaba.paypay.spigot.scheduler.SpigotScheduler
import net.azisaba.paypay.spigot.util.Util
import org.bukkit.plugin.java.JavaPlugin

class AzisabaPayPayAPIImpl(plugin: JavaPlugin) : AbstractAzisabaPayPayAPI() {
    private val logger = Logger.createFromJavaLogger(plugin.logger)
    private val scheduler = SpigotScheduler(plugin)

    override fun getLogger(): Logger = logger

    override fun getScheduler(): Scheduler = scheduler

    override fun onPaymentCreated(paymentId: String, amount: Int, currency: Currency, description: String) {
        Util.sendDiscordWebhookAsync(
            PluginConfig.instance.discordWebhookNotifyUrl,
            null,
            "`$paymentId`の決済が作成されました(決済は完了していません)。\n金額: $amount $currency\n説明: $description",
        )
    }

    override fun onPaymentCompleted(paymentId: String, amount: Int, currency: Currency, description: String) {
        Util.sendDiscordWebhookAsync(
            PluginConfig.instance.discordWebhookNotifyUrl,
            null,
            "`$paymentId`の決済が完了しました。\n金額: $amount $currency\n説明: $description",
        )
    }

    override fun onPaymentCancelled(paymentId: String, amount: Int, currency: Currency, description: String) {
        Util.sendDiscordWebhookAsync(
            PluginConfig.instance.discordWebhookNotifyUrl,
            null,
            "`$paymentId`の決済がエラーのため取り消されました。\n金額: $amount $currency\n説明: $description",
        )
    }
}
