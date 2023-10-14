package net.azisaba.paypay.spigot.config

import kotlinx.serialization.Serializable
import net.azisaba.paypay.spigot.util.Util

@Serializable
data class CategoryInfo(
    val name: String,
    val material: String,
    val products: List<ProductInfo>,
) {
    fun getActualMaterial() = Util.getMaterial(material.uppercase())

    init {
        getActualMaterial()
    }
}
