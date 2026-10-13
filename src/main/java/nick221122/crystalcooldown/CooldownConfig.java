package nick221122.crystalcooldown;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class CooldownConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("crystal-kill-click-cooldown.properties");
    private static int seconds = 3;
    private static boolean enabled = true;
    private CooldownConfig() {}

    public static int getSeconds() { return seconds; }
    public static boolean isEnabled() { return enabled; }

    public static void setEnabled(boolean value) {
        enabled = value;
        save();
    }

    public static void setSeconds(int value) {
        seconds = Math.max(1, Math.min(60, value));
        save();
    }

    public static void load() {
        if (!Files.exists(FILE)) { save(); return; }
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(FILE)) {
            p.load(in);
            seconds = Math.max(1, Math.min(60, Integer.parseInt(p.getProperty("seconds", "3"))));
            enabled = Boolean.parseBoolean(p.getProperty("enabled", "true"));
        } catch (IOException | NumberFormatException ex) {
            seconds = 3;
            enabled = true;
        }
    }

    private static void save() {
        Properties p = new Properties();
        p.setProperty("seconds", Integer.toString(seconds));
        p.setProperty("enabled", Boolean.toString(enabled));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream out = Files.newOutputStream(FILE)) { p.store(out, "Crystal kill click cooldown"); }
        } catch (IOException ignored) {}
    }
}
