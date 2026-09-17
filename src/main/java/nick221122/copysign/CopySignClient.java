package nick221122.copysign;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public class CopySignClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.getBlockEntity(hitResult.getBlockPos()) instanceof SignBlockEntity sign) {
                boolean front = sign.isPlayerFacingFront(player);
                MinecraftClient.getInstance().setScreen(new CopySignScreen(sign, front));
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });
    }
}
