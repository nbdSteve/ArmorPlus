package gg.steve.mc.ap.model.id;

/**
 * Opaque handle to a scheduled task.
 * The domain uses this to cancel previously scheduled repeating tasks
 * via the SchedulerPort. Adapters wrap platform task IDs behind this.
 */
public final class TaskHandle extends StringId {
    private TaskHandle(String value) { super(value); }

    public static TaskHandle of(String value) { return new TaskHandle(value); }
}
