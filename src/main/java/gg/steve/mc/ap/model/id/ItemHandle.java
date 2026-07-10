package gg.steve.mc.ap.model.id;

/**
 * Opaque handle to an item instance.
 * The domain never inspects the item directly - it passes this handle to
 * the NbtPort for tag reads/writes. Adapters wrap platform item types
 * (e.g. Bukkit ItemStack) behind this identity.
 */
public final class ItemHandle extends StringId {
    private ItemHandle(String value) { super(value); }

    public static ItemHandle of(String value) { return new ItemHandle(value); }
}
