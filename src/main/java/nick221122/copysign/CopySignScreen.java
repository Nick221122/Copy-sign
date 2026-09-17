package nick221122.copysign;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CopySignScreen extends Screen {
    private final SignBlockEntity sign;
    private final boolean front;
    private String copiedMessage = "";

    public CopySignScreen(SignBlockEntity sign, boolean front) {
        super(Text.literal("Copy Sign"));
        this.sign = sign;
        this.front = front;
    }

    @Override
    protected void init() {
        int panelX = this.width / 2 - 120;
        int panelY = this.height / 2 - 70;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Copy"), button -> copyText())
                .dimensions(panelX + 10, panelY + 38, 80, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), button -> close())
                .dimensions(panelX + 10, panelY + 92, 80, 20)
                .build());
    }

    private void copyText() {
        SignText signText = sign.getText(front);
        Text[] messages = signText.getMessages(false);
        StringBuilder copied = new StringBuilder();

        for (int i = 0; i < messages.length; i++) {
            if (i > 0) {
                copied.append('\n');
            }
            copied.append(messages[i].getString());
        }

        MinecraftClient.getInstance().keyboard.setClipboard(copied.toString());
        copiedMessage = "Copied!";
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int panelX = this.width / 2 - 120;
        int panelY = this.height / 2 - 70;

        context.fill(panelX, panelY, panelX + 240, panelY + 140, 0xCC202020);
        context.drawTextWithShadow(this.textRenderer, this.title, panelX + 10, panelY + 12, 0xFFFFFF);

        Text[] messages = sign.getText(front).getMessages(false);
        int textX = panelX + 105;
        int textY = panelY + 36;
        for (Text message : messages) {
            context.drawTextWithShadow(this.textRenderer, message, textX, textY, 0xFFFFFF);
            textY += 16;
        }

        if (!copiedMessage.isEmpty()) {
            context.drawTextWithShadow(this.textRenderer, Text.literal(copiedMessage), panelX + 105, panelY + 96, 0x55FF55);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
