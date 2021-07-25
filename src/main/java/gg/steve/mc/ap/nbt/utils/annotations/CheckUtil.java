package gg.steve.mc.dazzer.mt.nbt.utils.annotations;

import gg.steve.mc.dazzer.mt.nbt.NbtApiException;
import gg.steve.mc.dazzer.mt.nbt.utils.MinecraftVersion;

import java.lang.reflect.Method;

public class CheckUtil {

	public static boolean isAvaliable(Method method) {
		if(MinecraftVersion.getVersion().getVersionId() < method.getAnnotation(AvailableSince.class).version().getVersionId())
			throw new NbtApiException("The Method '" + method.getName() + "' is only avaliable for the Versions " + method.getAnnotation(AvailableSince.class).version() + "+, but still got called!");
		return true;
	}
	
}
