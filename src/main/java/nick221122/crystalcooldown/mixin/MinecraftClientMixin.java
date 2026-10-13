package nick221122.crystalcooldown.mixin;

import nick221122.crystalcooldown.CooldownState;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    // Only block left-click attacks during the cooldown. Right-click item use is untouched.
    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void crystalCooldown$blockAttack(CallbackInfoReturnable<Boolean> cir) {
        if (CooldownState.isLocked()) cir.setReturnValue(false);
    }
}
