package net.azisaba.paypay.spigot.gui

import net.azisaba.paypay.api.AzisabaPayPayAPIProvider
import net.azisaba.paypay.api.Currency
import net.azisaba.paypay.spigot.config.CategoryInfo
import net.azisaba.paypay.spigot.config.PluginConfig
import net.azisaba.paypay.spigot.util.ItemUtil.setCustomModelData
import net.azisaba.paypay.spigot.util.Util
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.ItemStack

class CustomCategoryScreen(val categoryInfo: CategoryInfo, index: Int) : ShopScreen(ShopType.valueOf("Category${index + 2}"), categoryInfo.name) {
    init {
        categoryInfo.products.forEachIndexed { i, product ->
            ItemStack(product.getActualMaterial()).apply {
                itemMeta = itemMeta.also { meta ->
                    meta.displayName = product.getColoredName()
                    meta.lore = listOf(
                        *product.getColoredLore().toTypedArray(),
                        "",
                        "§6価格: §a${product.price}円",
                    )
                    meta.setCustomModelData(product.customModelData)
                }
            }.also { stack -> inventory.setItem(i + 18, stack) }
        }
    }

    object EventListener : Listener {
        @EventHandler
        fun onInventoryDrag(e: InventoryDragEvent) {
            if (e.inventory.holder is CustomCategoryScreen) e.isCancelled = true
        }

        @EventHandler
        fun onInventoryClick(e: InventoryClickEvent) {
            if (e.inventory.holder !is CustomCategoryScreen) return
            e.isCancelled = true
            if (e.clickedInventory?.holder !is CustomCategoryScreen) return
            val screen = e.inventory.holder as CustomCategoryScreen
            if (screen.handle(e)) return
            val data = screen.categoryInfo.products.getOrNull(e.slot - 18) ?: return
            e.whoClicked.closeInventory()
            val url = AzisabaPayPayAPIProvider.getAPI().createQRCode(data.price, Currency.JPY, data.getNameWithoutColor()) {
                if (!(e.whoClicked as Player).isOnline) {
                    error("Player is offline")
                }
                Util.sendDiscordWebhookAsync(
                    PluginConfig.instance.discordWebhookNotifyUrl,
                    null,
                    "Minecraft ID `${e.whoClicked.name}`の決済が完了しました。\n金額: ${data.price} JPY\n説明: ${screen.categoryInfo.name} -> ${data.getNameWithoutColor()}",
                )
                Util.runSync {
                    data.execute(e.whoClicked as Player)
                }.join()
                Bukkit.broadcastMessage("§a§l${e.whoClicked.name}さんがPayPayで§d§l${data.price}円§a§l寄付しました！")
                Bukkit.broadcastMessage("§a§l${e.whoClicked.name}さんありがとうございます！")
                val text = TextComponent("§6§l${e.whoClicked.name}さんのように寄付するにはこのメッセージをクリック！")
                text.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/azisabapaypay")
                Bukkit.spigot().broadcast(text)
            }
            e.whoClicked.sendMessage("${ChatColor.AQUA}${ChatColor.UNDERLINE}$url${ChatColor.GREEN}を開いて、支払いを行ってください。")
            e.whoClicked.sendMessage("${ChatColor.GREEN}支払いが完了するまで切断しないでください。支払い完了後、1分以内に反映されます。")
        }
    }
}
