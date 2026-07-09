package gg.steve.mc.ap.model.id;

/**
 * Opaque handle to an item instance.
 * The domain never inspects the item directly - it passes this handle to
 * the NbtPort for tag reads/writes. Adapters wrap platform item types
 * (e.g. Bukkit ItemStack) behind this identity.
 */
public final class ItemHandle extends TypedString {
    private ItemHandle(String value) { super(value); }

    public static ItemHandle of(String value) {
        if (value == null) throw new NullPointerException("value must not be null");
        if (value.isEmpty()) throw new IllegalArgumentException("value must not be empty");
        return new ItemHandle(value);
    }
}
