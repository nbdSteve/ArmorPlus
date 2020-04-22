package gg.steve.mc.ap.utils;

import gg.steve.mc.ap.ArmorPlus;

public class LogUtil {

    public static void info(String message) {
        ArmorPlus.get().getLogger().info(message);
    }

    public static void warning(String message) {
        ArmorPlus.get().getLogger().warning(message);
    }
}
