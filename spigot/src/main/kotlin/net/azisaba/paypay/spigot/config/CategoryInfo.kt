package net.azisaba.paypay.spigot.config

import kotlinx.serialization.Serializable
import org.bukkit.Material

@Serializable
data class CategoryInfo(
    val name: String,
    val material: String,
    val products: List<ProductInfo>,
) {
    fun getActualMaterial() = Material.valueOf(material.uppercase())

    init {
        getActualMaterial()
    }
}
