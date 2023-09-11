package net.pmkjun.pyrofishinghelper.mixin;


import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class FishingBobberSilentMixin {

    @Inject(method = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;onPlaySound(Lnet/minecraft/network/packet/s2c/play/PlaySoundS2CPacket;)V",at=@At("RETURN"))
    public void onPlaySoundFromEntity(PlaySoundS2CPacket packet, CallbackInfo cir){
        System.out.println(packet.getSound().getKey().get().getValue().getPath());
    }

}
