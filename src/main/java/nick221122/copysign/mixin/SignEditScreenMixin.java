package nick221122.copysign.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin extends Screen {
    @Shadow
    @Final
    protected SignBlockEntity blockEntity;

    protected SignEditScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void copySign$addCopyButton(CallbackInfo ci) {
        int x = this.width / 2 - 180;
        int y = this.height / 2 - 10;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Copy"), button -> copySign$copyText())
                .dimensions(x, y, 70, 20)
                .build());
    }

    private void copySign$copyText() {
        boolean front = ((AbstractSignEditScreenAccessor) this).copySign$isFront();
        SignText signText = this.blockEntity.getText(front);
        Text[] messages = signText.getMessages(false);

        StringBuilder copied = new StringBuilder();
        for (int i = 0; i < messages.length; i++) {
            if (i > 0) {
                copied.append('\n');
            }
            copied.append(messages[i].getString());
        }

        MinecraftClient.getInstance().keyboard.setClipboard(copied.toString());
    }
}
