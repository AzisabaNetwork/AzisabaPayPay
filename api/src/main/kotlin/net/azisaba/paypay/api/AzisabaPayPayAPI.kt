package net.azisaba.paypay.api

import net.azisaba.paypay.api.scheduler.Scheduler

interface AzisabaPayPayAPI {
    fun getLogger(): Logger

    /**
     * Create QR code for payment.
     * @return URL
     */
    fun createQRCode(amount: Int, currency: Currency, description: String, purchased: () -> Unit): String

    fun getScheduler(): Scheduler
}
