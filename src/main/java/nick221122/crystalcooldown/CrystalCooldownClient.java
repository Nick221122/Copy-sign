package nick221122.crystalcooldown;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class CrystalCooldownClient implements ClientModInitializer {
    private static KeyBinding settingsKey;

    @Override
    public void onInitializeClient() {
        CooldownConfig.load();
        settingsKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.crystal_kill_click_cooldown.settings",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                KeyBinding.Category.MISC
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (settingsKey.wasPressed()) {
                if (client.player != null) client.setScreen(new CooldownSettingsScreen(client.currentScreen));
            }
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            long remaining = CooldownState.remainingMillis();
            if (client.player == null || remaining <= 0) return;
            String label = String.format("Clicks locked: %.1fs", remaining / 1000.0);
            drawContext.drawCenteredTextWithShadow(client.textRenderer, label,
                    client.getWindow().getScaledWidth() / 2,
                    client.getWindow().getScaledHeight() / 2 + 20, 0xFFFF5555);
        });
    }
}
