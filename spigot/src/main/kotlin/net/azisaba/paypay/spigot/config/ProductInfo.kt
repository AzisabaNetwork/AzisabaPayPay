package net.azisaba.paypay.spigot.config

import kotlinx.serialization.Serializable
import net.azisaba.paypay.spigot.util.Util
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

@Serializable
data class ProductInfo(
    val price: Int,
    val dummyPrice: Int = -1,
    val material: String,
    val durability: Short = 0,
    val name: String = "§c(設定されていません)",
    val lore: List<String> = emptyList(),
    val customModelData: Int? = null,
    val commands: List<String> = emptyList(),
    val repeatable: Boolean = true,
) {
    fun getColoredName(): String = ChatColor.translateAlternateColorCodes('&', name)
    fun getNameWithoutColor(): String = ChatColor.stripColor(getColoredName())
    fun getColoredLore(): List<String> = lore.map { ChatColor.translateAlternateColorCodes('&', it) }

    fun getActualMaterial() = Util.getMaterial(material.uppercase())

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
