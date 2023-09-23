package net.pmkjun.pyrofishinghelper.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class FishingBobberSilentMixin extends ClientCommonNetworkHandler implements TickablePacketListener, ClientPlayPacketListener {
    protected FishingBobberSilentMixin(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onPlaySound(PlaySoundS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if(FishHelperClient.getInstance().data.toggleMuteotherfishingbobber
                && packet.getSound().getKey().get().getValue().getPath().equals("entity.fishing_bobber.splash")){
            System.out.println("sound ignored");
            return;
        }
        this.client.world.playSound(this.client.player, packet.getX(), packet.getY(), packet.getZ(), packet.getSound(), packet.getCategory(), packet.getVolume(), packet.getPitch(), packet.getSeed());
    }

}
