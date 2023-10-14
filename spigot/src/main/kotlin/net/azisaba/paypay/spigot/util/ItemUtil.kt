package net.azisaba.paypay.spigot.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

object ItemUtil {
    fun itemOf(type: Material, amount: Int = 1, durability: Short = 0, displayName: String? = null, lore: List<String> = emptyList()) =
        ItemStack(type, amount).also { stack ->
            stack.durability = durability
            stack.itemMeta = stack.itemMeta!!.also { meta ->
                if (displayName != null) {
                    meta.displayName = displayName
                }
                if (lore.isNotEmpty()) {
                    meta.lore = lore
                }
            }
        }

    fun ItemMeta.setCustomModelData(data: Int?) {
        try {
            ItemMeta::class.java.getMethod("setCustomModelData", Integer::class.java).invoke(this, data)
        } catch (ignored: ReflectiveOperationException) {}
    }
}
