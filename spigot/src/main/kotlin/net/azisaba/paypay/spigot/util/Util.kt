package net.azisaba.paypay.spigot.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.azisaba.paypay.api.AzisabaPayPayAPIProvider
import net.azisaba.paypay.api.Logger
import net.azisaba.paypay.spigot.SpigotPlugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletableFuture
import javax.net.ssl.HttpsURLConnection

object Util {
    fun getMaterial(type: String): Material = java.lang.Enum.valueOf(Material::class.java, type)

    fun <R> runSync(action: () -> R) : CompletableFuture<R> {
        val future = CompletableFuture<R>()
        Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(SpigotPlugin::class.java)) {
            try {
                future.complete(action())
            } catch (t: Throwable) {
                future.completeExceptionally(t)
            }
        }
        return future
    }

    private val GSON = Gson()

    fun sendDiscordWebhookAsync(url: String, username: String?, content: String) {
        if (url.isBlank()) return
        AzisabaPayPayAPIProvider.getAPI().getScheduler().schedule {
            try {
                val con = URL(url).openConnection() as HttpsURLConnection
                con.setRequestMethod("POST")
                con.setRequestProperty("Content-Type", "application/json")
                con.setRequestProperty("User-Agent", "AzisabaPayPay - https://github.com/AzisabaNetwork/AzisabaPayPay")
                con.setRequestProperty("Accept", "application/json")
                con.setDoOutput(true)
                con.setConnectTimeout(5000)
                con.setReadTimeout(5000)
                val stream = con.outputStream
                val json = JsonObject()
                if (username != null) json.addProperty("username", username)
                json.addProperty("content", content)
                stream.write(GSON.toJson(json).toByteArray(StandardCharsets.UTF_8))
                stream.flush()
                stream.close()
                con.connect()
                val errorStream = con.errorStream
                if (errorStream != null) {
                    val err = String(errorStream.readBytes(), StandardCharsets.UTF_8)
                    Logger.currentLogger.warn("Discord webhook returned " + con.getResponseCode() + ": " + err)
                }
                con.inputStream.close()
                con.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
