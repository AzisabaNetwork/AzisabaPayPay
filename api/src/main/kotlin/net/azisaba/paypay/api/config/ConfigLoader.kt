package net.azisaba.paypay.api.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File

open class ConfigLoader<T : Any> {
    companion object {
        inline fun <reified T> loadConfig(file: File, default: T? = null) =
            file.let {
                if (!it.parentFile.exists()) it.parentFile.mkdirs()
                if (!it.exists()) {
                    it.writeText(Yaml.default.encodeToString(default))
                }
                Yaml.default.decodeFromString<T>(it.readText())
            }
    }

    lateinit var instance: T

    inline fun <reified U : T> load(file: File, default: U? = null) : T =
        loadConfig(file, default).also { instance = it }
}
