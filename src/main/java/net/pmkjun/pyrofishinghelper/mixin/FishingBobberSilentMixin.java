package net.pmkjun.pyrofishinghelper.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class FishingBobberSilentMixin implements TickablePacketListener, ClientPlayPacketListener {

    @Shadow
    @Final
    private MinecraftClient client;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onPlaySound(PlaySoundS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if(packet.getSound().getKey().get().getValue().getPath().equals("entity.fishing_bobber.splash")){
            System.out.println("sound ignored");
            return;
        }
        this.client.world.playSound(this.client.player, packet.getX(), packet.getY(), packet.getZ(), packet.getSound(), packet.getCategory(), packet.getVolume(), packet.getPitch(), packet.getSeed());
    }



}
