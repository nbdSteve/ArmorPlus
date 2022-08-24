package gg.steve.mc.ap.nbt.utils.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import gg.steve.mc.ap.nbt.utils.MinecraftVersion;

@Retention(RUNTIME)
@Target({ METHOD })
public @interface AvailableSince {

	MinecraftVersion version();

}