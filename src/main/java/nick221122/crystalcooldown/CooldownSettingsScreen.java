package nick221122.crystalcooldown;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class CooldownSettingsScreen extends Screen {
    private final Screen parent;

    public CooldownSettingsScreen(Screen parent) {
        super(Text.literal("Crystal Kill Click Cooldown"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("- 1 second"), b -> {
            CooldownConfig.setSeconds(CooldownConfig.getSeconds() - 1);
            this.clearAndInit();
        }).dimensions(cx - 105, cy - 5, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("+ 1 second"), b -> {
            CooldownConfig.setSeconds(CooldownConfig.getSeconds() + 1);
            this.clearAndInit();
        }).dimensions(cx + 5, cy - 5, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(cx - 50, cy + 30, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                "Cooldown: " + CooldownConfig.getSeconds() + " second(s) (1-60)",
                this.width / 2, this.height / 2 - 25, 0xFFFFFF);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(parent);
    }
}
