package nick221122.crystalcooldown.mixin;

import nick221122.crystalcooldown.CooldownState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityKillMixin {
    @Unique private boolean crystalCooldown$handledDeath;

    @Inject(method = "tick", at = @At("TAIL"))
    private void crystalCooldown$detectNearbyPlayerDeath(CallbackInfo ci) {
        LivingEntity victim = (LivingEntity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        if (!victim.isDead()) {
            crystalCooldown$handledDeath = false;
            return;
        }

        if (crystalCooldown$handledDeath || client.player == null || !(victim instanceof PlayerEntity)) return;

        // Any player death within 10 blocks triggers the cooldown, regardless of who caused it.
        if (victim.squaredDistanceTo(client.player) <= 100.0) {
            crystalCooldown$handledDeath = true;
            CooldownState.trigger();
        }
    }
}
