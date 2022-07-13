package org.acornmc.drsleep;

import org.acornmc.drsleep.configuration.Config;

public class Debug {
    public static void log(String s) {
        if (!Config.DEBUG) return;
        DrSleep.plugin.getLogger().info(s);
    }
}
