package net.azisaba.paypay.api

import net.azisaba.paypay.api.scheduler.Scheduler

interface AzisabaPayPayAPI {
    fun getLogger(): Logger

    /**
     * Create QR code for payment.
     * @return URL
     */
    fun createQRCode(amount: Int, currency: Currency, description: String, purchased: () -> Unit): String

    fun cancelPayment(paymentId: String)

    fun refundPayment(paymentId: String, reason: String)

    fun getScheduler(): Scheduler

    fun onPaymentCreated(paymentId: String, amount: Int, currency: Currency, description: String) {}
    fun onPaymentCompleted(paymentId: String, amount: Int, currency: Currency, description: String) {}
    fun onPaymentCancelled(paymentId: String, amount: Int, currency: Currency, description: String) {}
}
