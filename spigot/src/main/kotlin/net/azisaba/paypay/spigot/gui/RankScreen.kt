package net.azisaba.paypay.spigot.gui

import net.azisaba.azipluginmessaging.api.AziPluginMessagingProvider
import net.azisaba.azipluginmessaging.api.protocol.Protocol
import net.azisaba.azipluginmessaging.api.protocol.message.PlayerMessage
import net.azisaba.azipluginmessaging.api.protocol.message.ProxyboundGiveNitroSaraMessage
import net.azisaba.azipluginmessaging.api.protocol.message.ProxyboundGiveSaraMessage
import net.azisaba.paypay.api.AzisabaPayPayAPIProvider
import net.azisaba.paypay.api.Currency
import net.azisaba.paypay.spigot.config.PluginConfig
import net.azisaba.paypay.spigot.util.Util
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.ItemStack
import java.util.concurrent.TimeUnit

class RankScreen(private val player: Player) : ShopScreen(ShopType.Rank, "ランク") {
    companion object {
        fun getSaraPriceReduction(player: Player): Int {
            if (player.hasPermission("group.50000yen")) {
                return 50000
            } else if (player.hasPermission("group.10000yen")) {
                return 10000
            } else if (player.hasPermission("group.5000yen")) {
                return 5000
            } else if (player.hasPermission("group.2000yen")) {
                return 2000
            } else if (player.hasPermission("group.1000yen")) {
                return 1000
            } else if (player.hasPermission("group.500yen")) {
                return 500
            } else if (player.hasPermission("group.100yen")) {
                return 100
            }
            return 0
        }
    }

    private val benefits = mutableMapOf(
        "100yen" to listOf(
            "§7アジ鯖に100円寄付したものに送られる称号。",
            "",
            "§6§n全鯖共通",
            "§f- 名前の最初に§8[§1100円皿§8]§eがつき、名前も同じ色になる",
            "",
            "§6§nLGW",
            "§f- §1100円HANABI§eがもらえる",
            "§f- §d寄付ガチャチケット§eが1枚もらえる",
            "§f- §b/h§eコマンドで100円皿専用のパーティクルがアクセス可能になる",
        ),
        "500yen" to listOf(
            "§7アジ鯖に500円寄付したものに送られる称号。",
            "",
            "§6§n全鯖共通",
            "§f- 名前の最初に§8[§b500円皿§8]§eがつき、名前も同じ色になる",
            "",
            "§6§nLGW",
            "§f- §b500円HANABI§eがもらえる",
            "§f- §d寄付ガチャチケット§eが3枚もらえる",
            "§f- §b/h§eコマンドで500円皿専用のパーティクルがアクセス可能になる",
        ),
        "50000yen" to listOf(
            "§7アジ鯖に50000円寄付したものに送られる称号。",
            "",
            "§6§n全鯖共通",
            "§f- §e名前の最初に§8[§25§a0§20§a0§20§a円§2皿§8]§eがつき、名前も同じ色になる",
            "§f- §eチャットでカラーコードと装飾コードが使えるようになる",
            "",
            "§6§nLGW",
            "§f- §d寄付ガチャチケット§eが128枚もらえる",
            "§f- §b/h§eコマンドで50000円皿専用のパーティクルがアクセス可能になる",
            "§f- §b/hat§eコマンドが使用可能になる(アイテムを頭にかぶることができる)",
        ),
    )
    private val slots = mutableMapOf(
        28 to SlotData(Material.DIAMOND, 100, "§1100円皿", "100yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(100, player))) {
                error("Failed to send packet")
            }
        },
        29 to SlotData(Material.DIAMOND, 500, "§b500円皿", "500yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(500, player))) {
                error("Failed to send packet")
            }
        },
        30 to SlotData(Material.DIAMOND, 1000, "§a1000円皿", "1000yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(1000, player))) {
                error("Failed to send packet")
            }
        },
        31 to SlotData(Material.DIAMOND, 2000, "§d2000円皿", "2000yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(2000, player))) {
                error("Failed to send packet")
            }
        },
        32 to SlotData(Material.DIAMOND, 5000, "§55000円皿", "5000yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(5000, player))) {
                error("Failed to send packet")
            }
        },
        33 to SlotData(Material.DIAMOND, 10000, "§610000円皿", "10000yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(10000, player))) {
                error("Failed to send packet")
            }
        },
        34 to SlotData(Material.DIAMOND, 50000, "§25§a0§20§a0§20§a円§2皿", "50000yen") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveSaraMessage(50000, player))) {
                error("Failed to send packet")
            }
        },
        38 to SlotData(Material.EMERALD, 3000, "§9§lゲ§2§lー§a§lミ§e§lン§4§lグ", "changegamingsara") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_GAMING_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, PlayerMessage(player))) {
                error("Failed to send packet")
            }
        },
        42 to SlotData(Material.EMERALD, 500, "§3Nitro§6§l⚡ §e(30日間)", "changenitro") {
            val player = AziPluginMessagingProvider.get().getPlayerAdapter(Player::class.java).get(it)
            if (!Protocol.P_GIVE_NITRO_SARA.sendPacket(AziPluginMessagingProvider.get().server.packetSender, ProxyboundGiveNitroSaraMessage(player, 30, TimeUnit.DAYS))) {
                error("Failed to send packet")
            }
        },
    )

    init {
        slots.forEach { (slot, data) ->
            val benefit = benefits[data.groupName] ?: emptyList()
            val actualPrice = if (data.type == Material.DIAMOND) {
                data.price - getSaraPriceReduction(player)
            } else {
                data.price
            }
            val lorePrice = if (player.hasPermission("group.${data.groupName}")) {
                "§c購入済み"
            } else if (data.price == actualPrice) {
                "§6価格: §a${actualPrice}円"
            } else if (actualPrice <= 0) {
                "§c購入済み"
            } else {
                "§6価格: §8§m${data.price}円§a ${actualPrice}円"
            }
            ItemStack(data.type).apply {
                itemMeta = itemMeta.also { meta ->
                    meta.displayName = data.name
                    meta.lore = listOf(
                        *benefit.toTypedArray(),
                        "",
                        lorePrice,
                    )
                }
            }.also { stack -> inventory.setItem(slot, stack) }
        }
    }

    object EventListener : Listener {
        @EventHandler
        fun onInventoryDrag(e: InventoryDragEvent) {
            if (e.inventory.holder is RankScreen) e.isCancelled = true
        }

        @EventHandler
        fun onInventoryClick(e: InventoryClickEvent) {
            if (e.inventory.holder !is RankScreen) return
            e.isCancelled = true
            if (e.clickedInventory?.holder !is RankScreen) return
            val screen = e.inventory.holder as RankScreen
            if (screen.handle(e)) return
            val data = screen.slots[e.slot] ?: return
            val actualPrice = data.getActualPrice(e.whoClicked as Player)
            if (actualPrice > data.price) error("actualPrice ($actualPrice) is higher than package price (${data.price})")
            if (actualPrice <= 0 || e.whoClicked.hasPermission("group.${data.groupName}")) {
                return e.whoClicked.sendMessage("§cこの商品は既に購入済みです！")
            }
            e.whoClicked.closeInventory()
            val url = AzisabaPayPayAPIProvider.getAPI().createQRCode(actualPrice, Currency.JPY, ChatColor.stripColor(data.name)) {
                if (!(e.whoClicked as Player).isOnline) {
                    error("Player is offline")
                }
                Util.sendDiscordWebhookAsync(
                    PluginConfig.instance.discordWebhookNotifyUrl,
                    null,
                    "Minecraft ID `${e.whoClicked.name}`の決済が完了しました。\n金額: ~~${data.price} JPY~~ $actualPrice JPY\n説明: ${ChatColor.stripColor(data.name)}",
                )
                data.action(e.whoClicked as Player)
                Bukkit.broadcastMessage("§a§l${e.whoClicked.name}さんがPayPayで§d§l${actualPrice}円§a§l寄付しました！")
                Bukkit.broadcastMessage("§a§l${e.whoClicked.name}さんありがとうございます！")
                val text = TextComponent("§6§l${e.whoClicked.name}さんのように寄付するにはこのメッセージをクリック！")
                text.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/azisabapaypay")
                Bukkit.spigot().broadcast(text)
            }
            e.whoClicked.sendMessage("${ChatColor.AQUA}${ChatColor.UNDERLINE}$url${ChatColor.GREEN}を開いて、支払いを行ってください。")
            e.whoClicked.sendMessage("${ChatColor.GREEN}支払いが完了するまで切断しないでください。支払い完了後、1分以内に反映されます。")
        }
    }

    data class SlotData(val type: Material, val price: Int, val name: String, val groupName: String, val action: (player: Player) -> Unit) {
        fun getActualPrice(player: Player) =
            if (type == Material.DIAMOND) {
                price - getSaraPriceReduction(player)
            } else {
                price
            }
    }
}
