package gg.steve.mc.ap.nbt;

import org.bukkit.Chunk;

import de.tr7zw.annotations.FAUtil;
import gg.steve.mc.ap.nbt.utils.MinecraftVersion;
import gg.steve.mc.ap.nbt.utils.annotations.AvailableSince;
import gg.steve.mc.ap.nbt.utils.annotations.CheckUtil;

public class NBTChunk {

    private final Chunk chunk;

    public NBTChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Gets the NBTCompound used by spigots PersistentDataAPI. This method is only
     * available for 1.16.4+!
     * 
     * @return NBTCompound containing the data of the PersistentDataAPI
     */
    @AvailableSince(version = MinecraftVersion.MC1_16_R3)
    public NBTCompound getPersistentDataContainer() {
        FAUtil.check(this::getPersistentDataContainer, CheckUtil::isAvaliable);
        return new NBTPersistentDataContainer(chunk.getPersistentDataContainer());
    }

}
