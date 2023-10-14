package net.azisaba.paypay.spigot.scheduler

import net.azisaba.paypay.api.scheduler.Scheduler
import net.azisaba.paypay.api.scheduler.Task
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

class SpigotScheduler(private val plugin: Plugin) : Scheduler {
    override fun scheduleRepeatingTask(delay: Long, period: Long, action: (Task) -> Unit) {
        object : BukkitRunnable() {
            override fun run() {
                action(object : Task {
                    override fun cancel() {
                        Bukkit.getScheduler().cancelTask(taskId)
                    }
                })
            }
        }.runTaskTimerAsynchronously(plugin, delay / 50, period / 50)
    }

    override fun schedule(action: () -> Unit) {
        Bukkit.getScheduler().runTask(plugin, action)
    }
}
