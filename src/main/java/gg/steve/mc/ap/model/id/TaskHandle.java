package gg.steve.mc.ap.model.id;

/**
 * Opaque handle to a scheduled task.
 * The domain uses this to cancel previously scheduled repeating tasks
 * via the SchedulerPort. Adapters wrap platform task IDs behind this.
 */
public final class TaskHandle extends TypedString {
    private TaskHandle(String value) { super(value); }

    public static TaskHandle of(String value) {
        if (value == null) throw new NullPointerException("value must not be null");
        if (value.isEmpty()) throw new IllegalArgumentException("value must not be empty");
        return new TaskHandle(value);
    }
}
