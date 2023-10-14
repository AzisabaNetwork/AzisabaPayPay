package net.azisaba.paypay.spigot.config

import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

@Serializable
data class ProductInfo(
    val price: Int,
    val material: String,
    val durability: Short = 0,
    val name: String = "§c(設定されていません)",
    val lore: List<String> = emptyList(),
    val customModelData: Int? = null,
    val commands: List<String> = emptyList(),
    val repeatable: Boolean = true,
) {
    val coloredName: String = ChatColor.translateAlternateColorCodes('&', name)
    val nameWithoutColor: String = ChatColor.stripColor(coloredName)
    val coloredLore: List<String> = lore.map { ChatColor.translateAlternateColorCodes('&', it) }

    fun getActualMaterial() = Material.valueOf(material.uppercase())

    fun execute(player: Player) {
        commands.forEach { command ->
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                command
                    .replace("<player>", player.name)
                    .replace("<uuid>", player.uniqueId.toString())
            )
        }
    }

    init {
        if (!repeatable) error("repeatable = false is not supported")
        getActualMaterial()
    }
}
