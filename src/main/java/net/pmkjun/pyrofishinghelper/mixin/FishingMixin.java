package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingMixin {
    private static final Logger LOGGER = LogManager.getLogger("FishingMixin");
    @Shadow
    private boolean caughtFish;


    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();

    @Inject(method = "onRemoved", at = @At("RETURN"))
    private void onRemovedMixin(CallbackInfo ci) {
        FishHelperClient client = FishHelperClient.getInstance();

        String bobberOwner;

        bobberOwner = getPlayerOwner().getName().getString();

        //LOGGER.info("Fishing bobber entity removed."+caughtFish+" "+bobberOwner);

        if(caughtFish && bobberOwner.equals(FishHelperClient.getInstance().getUsername()) && client.data.isTotemCooldown){
            //LOGGER.info("fish caught!"+FishHelperClient.getInstance().getUsername());
            client.data.lastTotemCooldownTime -= client.data.valueCooldownReduction;
            client.configManage.save();
        }
    }


}