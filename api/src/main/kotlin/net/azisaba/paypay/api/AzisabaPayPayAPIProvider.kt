package net.azisaba.paypay.api

import java.util.ServiceLoader

interface AzisabaPayPayAPIProvider {
    fun get(): AzisabaPayPayAPI

    companion object {
        @JvmStatic
        fun getProvider(): AzisabaPayPayAPIProvider =
            ServiceLoader.load(AzisabaPayPayAPIProvider::class.java, AzisabaPayPayAPIProvider::class.java.classLoader).first()

        @JvmStatic
        fun getAPI() = getProvider().get()
    }
}
