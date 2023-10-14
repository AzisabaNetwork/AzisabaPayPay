package net.azisaba.paypay.spigot.gui

import net.azisaba.paypay.spigot.util.ItemUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

open class ShopScreen(val type: ShopType) : InventoryHolder {
    @Suppress("LeakingThis")
    private val inventory = Bukkit.createInventory(this, 54)

    override fun getInventory(): Inventory = inventory

    init {
        inventory.setItem(0, ItemUtil.itemOf(Material.EMERALD, 1, 0, "§aランク"))
        inventory.setItem(9, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, if (type == ShopType.Rank) 4 else 15, " "))
        inventory.setItem(10, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(11, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(12, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(13, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(14, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(15, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(16, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
        inventory.setItem(17, ItemUtil.itemOf(Material.STAINED_GLASS_PANE, 1, 15, " "))
    }

    fun handle(e: InventoryClickEvent): Boolean {
        if (e.slot == 0) {
            e.whoClicked.openInventory(RankScreen(e.whoClicked as Player).inventory)
            return true
        }
        return false
    }

    enum class ShopType {
        Rank,
    }
}
