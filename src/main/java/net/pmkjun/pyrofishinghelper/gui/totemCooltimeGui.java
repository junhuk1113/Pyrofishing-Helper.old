package net.pmkjun.pyrofishinghelper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.util.Timer;



public class totemCooltimeGui {
    private MinecraftClient mc;
    private FishHelperClient client;
    private TextRenderer font;

    private static final Identifier TOTEM_ICON = new Identifier("pyrofishinghelper","totem.png");
    private static final Identifier TOTEM_SLEEP_ICON = new Identifier("pyrofishinghelper","sleepingtotem3.png");

    private Text lastTitle = null;

    public totemCooltimeGui(){
        this.mc = MinecraftClient.getInstance();
        this.client = FishHelperClient.getInstance();
    }

    public void renderTick(DrawContext context, Timer timer){
        int activesecond,cooldownsecond;

        activesecond = this.client.data.valueTotemActivetime * 60 - (int)timer.getDifference(this.client.data.lastTotemTime);
        cooldownsecond = this.client.data.valueTotemCooldown * 60 - (int)timer.getDifference(this.client.data.lastTotemCooldownTime);

        if (activesecond < 0)
            activesecond = 0;


        if (cooldownsecond>0&&cooldownsecond<this.client.data.valueTotemCooldown*60)
            this.client.data.isTotemCooldown = true;
        else
            this.client.data.isTotemCooldown = false;

        if(this.client.data.toggleTotemtime&&this.client.data.isTotemCooldown){
            render(context, TOTEM_SLEEP_ICON, cooldownsecond);
        }
        else if(this.client.data.toggleTotemtime){
            render(context, TOTEM_ICON, activesecond);
        }
    }

    private void render(DrawContext context,Identifier texture, int second){
        MatrixStack poseStack = context.getMatrices();
        int Timer_xpos, Timer_ypos;
        Timer_xpos = getXpos();
        Timer_ypos = getYpos();

        poseStack.push();
        poseStack.translate(Timer_xpos,Timer_ypos,0.0D);
        poseStack.scale(0.0625F, 0.0625F, 0.0625F);

        RenderSystem.setShaderTexture(0,texture);
        context.drawTexture(texture, 0, 0, 0, 0, 256, 256);
        poseStack.scale(16.0F, 16.0F, 16.0F);
        poseStack.pop();

        if (this.client.data.toggleTotemtimeText) {
            this.font = this.mc.textRenderer;
            int minute = second / 60;
            second -= minute * 60;
            poseStack.push();
            poseStack.translate((Timer_xpos + 16 + 2), Timer_ypos+4, 0.0D);
            poseStack.scale(0.9090909F, 0.9090909F, 0.9090909F);
            context.drawTextWithShadow(this.font, (Text)Text.literal(String.format("%02d:%02d", new Object[] { Integer.valueOf(minute), Integer.valueOf(second) })), 0, 0, 16777215);
            poseStack.scale(1.1F, 1.1F, 1.1F);

            poseStack.pop();
        }
    }
    private int getXpos(){
        return 2 + (this.mc.getWindow().getScaledWidth()-43-2) * this.client.data.Timer_xpos / 1000;
    }
    private int getYpos(){
        return 2 + (this.mc.getWindow().getScaledHeight()-18-2) * this.client.data.Timer_ypos / 1000;
    }

}
