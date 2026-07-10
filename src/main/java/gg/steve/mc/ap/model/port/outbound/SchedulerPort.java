package gg.steve.mc.ap.model.port.outbound;

import gg.steve.mc.ap.model.id.TaskHandle;

/**
 * Outbound port for scheduling repeating tasks.
 * The domain calls this for abilities that need periodic execution
 * (lightning strikes, fairy particles, color-way cycling, engineer TNT).
 * Adapters implement via the platform scheduler.
 * Implementations live outside the model package.
 */
public interface SchedulerPort {

    TaskHandle runRepeating(Runnable task, long initialDelayTicks, long periodTicks);

    void cancel(TaskHandle handle);
}
