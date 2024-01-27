package net.pmkjun.pyrofishinghelper.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.gui.widget.Slider;
import net.pmkjun.pyrofishinghelper.util.ConvertActivateTime;
import net.pmkjun.pyrofishinghelper.util.ConvertCooldown;


public class ConfigScreen extends Screen {

    private MinecraftClient mc;
    private FishHelperClient client;
    private final Screen parentScreen;
    private TextFieldWidget CooldownReduction_TextField;

    private ButtonWidget toggleTotemButton;
    private ButtonWidget toggleMuteotherfishingbobberButton;

    private Slider activateTimeSlider;
    private Slider cooldownSlider;

    private ButtonWidget toggleCustomTextureButton;
    private ButtonWidget toggleChattinglogButton;
    private Slider timerXSlider;
    private Slider timerYSlider;

    public ConfigScreen(Screen parentScreen){
        super(Text.translatable("fishhelper.config.title"));
        this.parentScreen = parentScreen;
        this.mc = MinecraftClient.getInstance();
        this.client = FishHelperClient.getInstance();

    }

    @Override
    protected void init() {
        String toggleTotem;
        String toggleTexture;
        String toggleMuter;
        String toggleLog;

        if(client.data.toggleTotemtime){
            toggleTotem = "fishhelper.config.enable";
        }
        else{
            toggleTotem = "fishhelper.config.disable";
        }

        if(client.data.toggleMuteotherfishingbobber){
            toggleMuter = "fishhelper.config.muter_enable";
        }
        else{
            toggleMuter = "fishhelper.config.muter_disable";
        }

        if(client.data.toggleCustomTexture){
            toggleTexture = "fishhelper.config.customtexture_enable";
        }
        else{
            toggleTexture = "fishhelper.config.customtexture_disable";
        }

        if(client.data.toggleChattinglog){
            toggleLog = "fishhelper.config.chattinglog_enable";
        }
        else {
            toggleLog = "fishhelper.config.chattinglog_disable";
        }
        activateTimeSlider = new Slider(10, 10, 150, 20, Text.literal(""),0,20,ConvertActivateTime.asLevel(this.client.data.valueTotemActivetime)){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Text.literal(Text.translatable("fishhelper.config.activatefield").getString()
                        +" : "+ level +"lv ("+
                        ConvertActivateTime.asMinute(level)+
                        Text.translatable("fishhelper.config.minute").getString()+")"));
            }
        };
        cooldownSlider = new Slider(10, 35, 150, 20, Text.literal(""),0,10,ConvertCooldown.asLevel(this.client.data.valueTotemCooldown)){
            @Override
            protected void updateMessage() {
                int level = getValueInt();
                this.setMessage(Text.literal(Text.translatable("fishhelper.config.cooldownfield").getString()
                        +" : "+ level +"lv ("+ ConvertCooldown.asMinute(level)+
                        Text.translatable("fishhelper.config.minute").getString()+")"));
            }
        };

        this.addDrawableChild(activateTimeSlider);
        this.addDrawableChild(cooldownSlider);

        //토템 쿨감시간
        this.CooldownReduction_TextField = new TextFieldWidget(this.textRenderer,100,60,35,10,this.CooldownReduction_TextField,Text.translatable("fishhelper.config.cooldownreductionfield"));
        this.CooldownReduction_TextField.setText(Double.toString(this.client.data.valueCooldownReduction/(double)1000));
        this.addSelectableChild(this.CooldownReduction_TextField);

        toggleTotemButton = ButtonWidget.builder(Text.translatable(toggleTotem),button -> {
            toggleTotemtime();
        }).dimensions(10,75, 150,20).build();
        this.addDrawableChild(toggleTotemButton);

        toggleCustomTextureButton = ButtonWidget.builder(Text.translatable(toggleTexture),button -> {
            onCustomTexturePress();
        }).dimensions(10,100,150,20).build();
        this.addDrawableChild(toggleCustomTextureButton);

        /*toggleMuteotherfishingbobberButton = ButtonWidget.builder(Text.translatable(toggleMuter),button -> {
            toggleMuter();
        }).dimensions(10,125, 150, 20).build();
        this.addDrawableChild(toggleMuteotherfishingbobberButton);*/

        toggleChattinglogButton = ButtonWidget.builder(Text.translatable(toggleLog),button -> {
            onChattinglogPress();
        }).dimensions(10,125, 150, 20).build();
        this.addDrawableChild(toggleChattinglogButton);

        timerXSlider = new Slider(10,165,150,20,Text.literal("X : "),1,1000,this.client.data.Timer_xpos){
            @Override
            protected void applyValue() {
                client.data.Timer_xpos = getValueInt();
                client.configManage.save();
            }
        };
        this.addDrawableChild(timerXSlider);
        timerYSlider = new Slider(10,190,150,20,Text.literal("Y : "),1,1000,this.client.data.Timer_ypos){
            @Override
            protected void applyValue() {
                client.data.Timer_ypos = getValueInt();
                client.configManage.save();
            }
        };
        this.addDrawableChild(timerYSlider);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.backbutton"),button -> {
            mc.setScreen(parentScreen);
        }).dimensions(10,this.height-30, 50,20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fishhelper.config.savebutton"),button -> {
            changeSetting();
            mc.setScreen(parentScreen);
        }).dimensions(this.width-60,this.height-30, 50,20).build());
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers)
                || this.CooldownReduction_TextField.keyPressed(keyCode, scanCode, modifiers);
    }
    @Override
    public boolean charTyped(char chr, int keyCode) {
        return this.CooldownReduction_TextField.charTyped(chr, keyCode);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //this.renderBackground(context,mouseX,mouseY,delta);
        context.drawTextWithShadow(this.textRenderer, Text.translatable("fishhelper.config.cooldownreductionfield"), 10, 60, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer,Text.translatable("fishhelper.config.changepos"),10,155,0xFFFFFF);

        this.activateTimeSlider.render(context, mouseX, mouseY, delta);
        this.cooldownSlider.render(context, mouseX, mouseY, delta);

        this.timerXSlider.render(context, mouseX, mouseY, delta);
        this.timerYSlider.render(context, mouseX, mouseY, delta);

        this.CooldownReduction_TextField.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }
    private void changeSetting(){
        try{
            client.data.valueTotemActivetime = ConvertActivateTime.asMinute(activateTimeSlider.getValueInt());
            client.data.valueTotemCooldown = ConvertCooldown.asMinute(cooldownSlider.getValueInt());
            client.data.valueCooldownReduction = (long)(Double.parseDouble(CooldownReduction_TextField.getText())*1000);

            client.configManage.save();
        }
        catch (NumberFormatException e){
            System.out.println("NumberFormatException!");
        }

    }

    private void toggleTotemtime(){
        if(client.data.toggleTotemtime){
            toggleTotemButton.setMessage(Text.translatable("fishhelper.config.disable"));
            client.data.toggleTotemtime = false;
        }
        else{
            toggleTotemButton.setMessage(Text.translatable("fishhelper.config.enable"));
            client.data.toggleTotemtime = true;
        }
        client.configManage.save();
    }
    private void toggleMuter(){
        if(client.data.toggleMuteotherfishingbobber){
            toggleMuteotherfishingbobberButton.setMessage(Text.translatable("fishhelper.config.muter_disable"));
            client.data.toggleMuteotherfishingbobber = false;
        }
        else{
            toggleMuteotherfishingbobberButton.setMessage(Text.translatable("fishhelper.config.muter_enable"));
            client.data.toggleMuteotherfishingbobber = true;
        }
        client.configManage.save();
    }
    private void onCustomTexturePress(){
        if(client.data.toggleCustomTexture){
            toggleCustomTextureButton.setMessage(Text.translatable("fishhelper.config.customtexture_disable"));
            client.data.toggleCustomTexture = false;
        }
        else{
            toggleCustomTextureButton.setMessage(Text.translatable("fishhelper.config.customtexture_enable"));
            client.data.toggleCustomTexture = true;
        }
        client.configManage.save();
    }
    private void onChattinglogPress(){
        if(client.data.toggleChattinglog){
            toggleChattinglogButton.setMessage(Text.translatable("fishhelper.config.chattinglog_disable"));
            client.data.toggleChattinglog = false;
        }
        else{
            toggleChattinglogButton.setMessage(Text.translatable("fishhelper.config.chattinglog_enable"));
            client.data.toggleChattinglog = true;
        }
        client.configManage.save();
    }


}
