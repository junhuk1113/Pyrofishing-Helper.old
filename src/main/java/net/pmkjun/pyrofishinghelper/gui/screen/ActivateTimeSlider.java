package net.pmkjun.pyrofishinghelper.gui.screen;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.pmkjun.pyrofishinghelper.util.ConvertActivateTime;

class ActivateTimeSlider extends SliderWidget {
    private final double min;
    private final double max;


    public ActivateTimeSlider(int x, int y, int width, int value, float min, float max) {
        super(x, y, width, 20, ScreenTexts.EMPTY, 0.0);
        this.min = (double)min;
        this.max = (double)max;
        this.value = (double)((MathHelper.clamp((float)value, min, max) - min) / (max - min));
        this.updateMessage();
    }

    protected void updateMessage() {
        int level;
        level = (int)MathHelper.lerp(MathHelper.clamp(this.value, 0.0, 1.0), this.min, this.max);

        this.setMessage(Text.literal(Text.translatable("fishhelper.config.activatefield").getString()
                +" : "+ level +"lv ("+
                ConvertActivateTime.asMinute(level)+
                Text.translatable("fishhelper.config.minute").getString()+")"));
    }

    @Override
    protected void applyValue() {

    }

    public int getValue(){
        return (int)MathHelper.lerp(MathHelper.clamp(this.value, 0.0, 1.0), this.min, this.max);
    }

    public void onClick(double mouseX, double mouseY) {
    }

    public void onRelease(double mouseX, double mouseY) {
    }
}