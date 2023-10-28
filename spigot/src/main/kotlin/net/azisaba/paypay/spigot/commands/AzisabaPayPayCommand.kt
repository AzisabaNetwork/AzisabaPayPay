package net.azisaba.paypay.spigot.commands

import net.azisaba.paypay.api.AzisabaPayPayAPIProvider
import net.azisaba.paypay.spigot.config.PluginConfig
import net.azisaba.paypay.spigot.gui.RankScreen
import net.azisaba.paypay.spigot.util.Util
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object AzisabaPayPayCommand : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) return true
        if (sender.hasPermission("azisabapaypay.admin")) {
            if (args.isNotEmpty()) {
                if (args[0] == "cancel") {
                    val paymentId = args.getOrNull(1) ?: run {
                        sender.sendMessage("${ChatColor.RED}/azisabapaypay cancel <payment id>")
                        return true
                    }
                    AzisabaPayPayAPIProvider.getAPI().getScheduler().schedule {
                        AzisabaPayPayAPIProvider.getAPI().cancelPayment(paymentId)
                        Util.sendDiscordWebhookAsync(
                            PluginConfig.instance.discordWebhookNotifyUrl,
                            null,
                            "`$paymentId`の決済が手動でキャンセル(返金)処理されました。",
                        )
                        sender.sendMessage("${ChatColor.GREEN}ﾖｼ!")
                    }
                    return true
                }
                if (args[0] == "refund") {
                    val paymentId = args.getOrNull(1) ?: run {
                        sender.sendMessage("${ChatColor.RED}/azisabapaypay refund <payment id> <reason>")
                        return true
                    }
                    val reason = args.getOrNull(2) ?: run {
                        sender.sendMessage("${ChatColor.RED}/azisabapaypay refund <payment id> <reason>")
                        return true
                    }
                    AzisabaPayPayAPIProvider.getAPI().getScheduler().schedule {
                        AzisabaPayPayAPIProvider.getAPI().refundPayment(paymentId, reason)
                        Util.sendDiscordWebhookAsync(
                            PluginConfig.instance.discordWebhookNotifyUrl,
                            null,
                            "`$paymentId`の決済が手動で返金処理されました。",
                        )
                        sender.sendMessage("${ChatColor.GREEN}ﾖｼ!")
                    }
                    return true
                }
            }
        }
        if (args.getOrNull(0) == "yes") {
            sender.openInventory(RankScreen(sender).inventory)
        } else {
            sender.sendMessage("§b§nhttps://link.azisaba.net/sct")
            sender.sendMessage("§e続行する前に上記のURLを確認してください。")
            sender.spigot().sendMessage(
                TextComponent("✔確認したので続行する").apply {
                    color = ChatColor.GREEN.asBungee()
                    isUnderlined = true
                    hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("購入画面を表示する"))
                    clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/azisabapaypay yes")
                }
            )
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> {
        if (sender.hasPermission("azisabapaypay.admin")) {
            if (args.size == 1) {
                return listOf("yes", "cancel", "refund").filter { it.startsWith(args[0]) }
            }
        } else {
            if (args.size == 1) {
                return listOf("yes")
            }
        }
        return emptyList()
    }
}
