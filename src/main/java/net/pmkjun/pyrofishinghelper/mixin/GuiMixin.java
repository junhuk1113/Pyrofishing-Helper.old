package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameHud.class})
public class GuiMixin {
    @Inject(method = {"render(Lnet/minecraft/client/gui/DrawContext;F)V"}, at = {@At("RETURN")} ,cancellable = false)
        private void renderMixin(DrawContext context, float tickDelta, CallbackInfo info) {
            FishHelperClient.getInstance().renderEvent(context);
        }
}

