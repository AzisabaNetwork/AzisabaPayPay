package net.azisaba.paypay.spigot.config

import kotlinx.serialization.Serializable
import net.azisaba.paypay.api.config.ConfigLoader

@Serializable
data class PluginConfig(
    val overwrite: Boolean = false,
    val discordWebhookNotifyUrl: String = "",
    val actOnSpecifiedCommercialTransactionsUrl: String = "",
    val categories: List<CategoryInfo> = listOf(
        CategoryInfo(
            "Test",
            "DIRT",
            listOf(
                ProductInfo(Integer.MAX_VALUE, "STONE", 0, "&aテスト1", listOf("テスト商品です"), commands = listOf("give <player> stone 1")),
                ProductInfo(Integer.MAX_VALUE, "DIRT", 0, "&bテスト2", listOf("テスト商品です"), commands = listOf("give <player> dirt 1")),
                ProductInfo(Integer.MAX_VALUE, "STONE", 0, "&cテスト3", listOf("テスト商品です"), commands = listOf("give <player> stone 64")),
            )
        )
    ),
) {
    companion object : ConfigLoader<PluginConfig>()
}
