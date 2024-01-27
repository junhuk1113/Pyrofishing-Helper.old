package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.sound.SoundEvents;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.MinecraftClient;


@Mixin(FishingBobberEntity.class)
public abstract class FishingMixin {
    private static final Logger LOGGER = LogManager.getLogger("FishingMixin");
    FishHelperClient client = FishHelperClient.getInstance();

    private boolean previouscaughtFish = false;

    @Shadow
    private boolean caughtFish;


    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();

    @Shadow public abstract boolean canUsePortals();

    @Inject(method = "onRemoved", at = @At("RETURN"))
    private void onRemovedMixin(CallbackInfo ci) {
        String bobberOwner;
        try{
            bobberOwner = getPlayerOwner().getName().getString();
        }
        catch (NullPointerException e){
            //System.out.println("null1!");
            return;
        }

        LOGGER.info("Fishing bobber entity removed."+caughtFish+" "+bobberOwner);

        if(caughtFish && bobberOwner.equals(FishHelperClient.getInstance().getUsername()) && client.data.isTotemCooldown){
            //LOGGER.info("fish caught!"+FishHelperClient.getInstance().getUsername());
            client.data.lastTotemCooldownTime -= client.data.valueCooldownReduction;
            client.configManage.save();
        }
    }

    @Inject(method = "tick",at = @At("HEAD"))
    private void ontickMixin(CallbackInfo ci){
        String bobberOwner;
        try{
            bobberOwner = getPlayerOwner().getName().getString();
        }
        catch (NullPointerException e){
            //System.out.println("null2!");
            return;
        }

        /*if(client.data.toggleMuteotherfishingbobber && bobberOwner.equals(client.getUsername())){
            if(!previouscaughtFish && caughtFish){
                System.out.println("fishBobber splash!");
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH,1.0F,0.2F),0);

                previouscaughtFish = true;
            }
            if(previouscaughtFish && !caughtFish){
                previouscaughtFish = false;
            }

        }*/

    }


}