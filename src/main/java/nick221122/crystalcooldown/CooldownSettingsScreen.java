package nick221122.crystalcooldown;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public final class CooldownSettingsScreen extends Screen {
    private final Screen parent;
    private SecondsSlider secondsSlider;

    public CooldownSettingsScreen(Screen parent) {
        super(Text.literal("Crystal Kill Click Cooldown"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;
        secondsSlider = new SecondsSlider(cx - 100, cy - 8, 200, 20, CooldownConfig.getSeconds());
        this.addDrawableChild(secondsSlider);
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(cx - 50, cy + 28, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 52, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                "Cooldown length: " + CooldownConfig.getSeconds() + " seconds",
                this.width / 2, this.height / 2 - 28, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                "Only left-click attacks are blocked; right-click still works.",
                this.width / 2, this.height / 2 + 58, 0xAAAAAA);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(parent);
    }

    private static final class SecondsSlider extends SliderWidget {
        private SecondsSlider(int x, int y, int width, int height, int seconds) {
            super(x, y, width, height, Text.empty(), (Math.max(1, Math.min(60, seconds)) - 1) / 59.0);
            updateMessage();
        }

        @Override
        protected void updateMessage() {
            int seconds = getSeconds();
            setMessage(Text.literal(seconds + (seconds == 1 ? " second" : " seconds")));
        }

        @Override
        protected void applyValue() {
            CooldownConfig.setSeconds(getSeconds());
        }

        private int getSeconds() {
            return 1 + (int) Math.round(this.value * 59.0);
        }
    }
}
