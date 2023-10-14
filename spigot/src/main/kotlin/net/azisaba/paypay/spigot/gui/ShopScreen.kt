package net.azisaba.paypay.spigot.gui

import net.azisaba.paypay.spigot.config.PluginConfig
import net.azisaba.paypay.spigot.util.ItemUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

open class ShopScreen(type: ShopType, title: String) : InventoryHolder {
    @Suppress("LeakingThis")
    private val inventory = Bukkit.createInventory(this, 54, title)

    override fun getInventory(): Inventory = inventory

    init {
        inventory.setItem(
            0,
            ItemUtil.itemOf(Material.EMERALD, 1, 0, "§aランク")
        )
        PluginConfig.instance.categories.forEachIndexed { index, categoryInfo ->
            if (index < 8) {
                inventory.setItem(
                    index + 1,
                    ItemUtil.itemOf(categoryInfo.getActualMaterial(), 1, 0, "§a" + ChatColor.translateAlternateColorCodes('&', categoryInfo.name))
                )
            }
        }
        inventory.setItem(
            9,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Rank) 4 else 15, " ")
        )
        inventory.setItem(
            10,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category2) 4 else 15, " ")
        )
        inventory.setItem(
            11,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category3) 4 else 15, " ")
        )
        inventory.setItem(
            12,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category4) 4 else 15, " ")
        )
        inventory.setItem(
            13,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category5) 4 else 15, " ")
        )
        inventory.setItem(
            14,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category6) 4 else 15, " ")
        )
        inventory.setItem(
            15,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category7) 4 else 15, " ")
        )
        inventory.setItem(
            16,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category8) 4 else 15, " ")
        )
        inventory.setItem(
            17,
            ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Category9) 4 else 15, " ")
        )
    }

    fun handle(e: InventoryClickEvent): Boolean {
        if (e.slot == 0) {
            e.whoClicked.openInventory(RankScreen(e.whoClicked as Player).inventory)
            return true
        }
        if (e.slot <= 8) {
            PluginConfig.instance.categories.getOrNull(e.slot - 1)?.also { category ->
                e.whoClicked.openInventory(CustomCategoryScreen(category, e.slot - 1).inventory)
                return true
            }
        }
        return false
    }

    enum class ShopType {
        Rank,
        Category2,
        Category3,
        Category4,
        Category5,
        Category6,
        Category7,
        Category8,
        Category9,
    }
}
