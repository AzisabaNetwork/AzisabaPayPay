package net.azisaba.paypay.api.scheduler

interface Scheduler {
    fun scheduleRepeatingTask(delay: Long, period: Long, action: Task.() -> Unit)
    fun schedule(action: () -> Unit)
}
