package net.azisaba.paypay.common

import jp.ne.paypay.ApiClient
import jp.ne.paypay.ApiException
import jp.ne.paypay.Configuration
import jp.ne.paypay.api.PaymentApi
import jp.ne.paypay.model.MoneyAmount
import jp.ne.paypay.model.PaymentState
import jp.ne.paypay.model.QRCode
import jp.ne.paypay.model.Refund
import net.azisaba.paypay.api.AzisabaPayPayAPI
import net.azisaba.paypay.api.Currency
import net.azisaba.paypay.api.Logger
import net.azisaba.paypay.api.config.PayPayCredentials
import java.util.*

abstract class AbstractAzisabaPayPayAPI : AzisabaPayPayAPI {
    private val apiClient: ApiClient by lazy {
        Configuration().defaultApiClient.apply {
            isProductionMode = PayPayCredentials.instance.production
            setApiKey(PayPayCredentials.instance.apiKey)
            setApiSecretKey(PayPayCredentials.instance.apiSecretKey)
            setAssumeMerchant(PayPayCredentials.instance.merchantId)
        }
    }
    private val paymentApi by lazy { PaymentApi(apiClient) }

    override fun createQRCode(amount: Int, currency: Currency, description: String, purchased: () -> Unit): String {
        val qrCode = QRCode()
        val paymentId = UUID.randomUUID().toString()
        Logger.currentLogger.info("Attempting to create QR code with id: {}, amount: {}, currency: {}, description: {}", paymentId, amount, currency, description)
        qrCode.amount = MoneyAmount().amount(amount).currency(MoneyAmount.CurrencyEnum.valueOf(currency.name))
        qrCode.merchantPaymentId = paymentId
        qrCode.codeType = "ORDER_QR"
        qrCode.orderDescription = description
        qrCode.isAuthorization(false)
        val details = try {
            paymentApi.createQRCode(qrCode)
        } catch (e: ApiException) {
            error("Error returned from PayPay API, response body: ${e.responseBody}")
        }
        if (details.resultInfo.code != "SUCCESS") {
            error("Something went wrong (code: ${details.resultInfo.code})")
        }
        onPaymentCreated(paymentId, amount, currency, description)
        val start = System.currentTimeMillis()
        getScheduler().scheduleRepeatingTask(1000 * 30, 1000 * 30) {
            try {
                if (System.currentTimeMillis() - start > 1000 * 60 * 6) error("Timeout")
                val response = paymentApi.getCodesPaymentDetails(paymentId)
                if (response.resultInfo.code != "SUCCESS") {
                    error("Response code was ${response.resultInfo.code}")
                }
                if (response.data.status != PaymentState.StatusEnum.CREATED) {
                    cancel()
                }
                if (response.data.status == PaymentState.StatusEnum.COMPLETED) {
                    Logger.currentLogger.info("Payment {} was success, executing callback", paymentId)
                    onPaymentCompleted(paymentId, amount, currency, description)
                    purchased()
                }
            } catch (e: Throwable) {
                cancel()
                Logger.currentLogger.info("Cancelling the payment {} because of an error", paymentId, e)
                paymentApi.cancelPayment(paymentId)
                onPaymentCancelled(paymentId, amount, currency, description)
                throw e
            }
        }
        Logger.currentLogger.info("URL returned from PayPay API: ${details.data.url}")
        return details.data.url
    }

    override fun cancelPayment(paymentId: String) {
        paymentApi.cancelPayment(paymentId)
    }

    override fun refundPayment(paymentId: String, reason: String) {
        paymentApi.refundPayment(Refund().paymentId(paymentId).reason(reason))
    }
}
