package net.azisaba.paypay.api.config

import kotlinx.serialization.Serializable

@Serializable
data class PayPayCredentials(
    val production: Boolean = false,
    val apiKey: String = "",
    val apiSecretKey: String = "",
    val merchantId: String = "",
) {
    companion object : ConfigLoader<PayPayCredentials>()
}
