package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.FishHelperMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatMixin {
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void addMessageMixin(Text message, CallbackInfo ci) {
        if(FishHelperClient.getInstance().data.toggleChattinglog)
            FishHelperMod.LOGGER.info(message.getString());
    }
}