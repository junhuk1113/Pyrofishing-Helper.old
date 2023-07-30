package net.pmkjun.pyrofishinghelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.pmkjun.pyrofishinghelper.config.ConfigManage;
import net.pmkjun.pyrofishinghelper.file.Data;
import net.pmkjun.pyrofishinghelper.gui.totemCooltimeGui;
import net.pmkjun.pyrofishinghelper.util.Timer;

public class FishHelperClient {
    private final MinecraftClient mc;
    private static FishHelperClient instance;
    public Data data;
    public ConfigManage configManage;

    private final totemCooltimeGui totemcooltimeGui;
    private final Timer timer = new Timer();
    public FishHelperClient(){
        this.mc = MinecraftClient.getInstance();
        instance = this;
        this.configManage = new ConfigManage();
        this.data = this.configManage.load();
        if(this.data == null){
            this.data = new Data();
            this.configManage.save();
        }
        this.totemcooltimeGui = new totemCooltimeGui();
    }
    public void init(){

    }
    public void renderEvent(DrawContext context) {
        this.totemcooltimeGui.renderTick(context,this.timer);
        this.timer.updateTime();
    }
    public void updateLastTotemtime(){
        this.data.lastTotemTime = this.timer.getCurrentTime();
        this.data.lastTotemCooldownTime = this.timer.getCurrentTime()+(long)this.data.valueTotemActivetime * 60 * 1000;
    }

    public String getUsername(){
        return this.mc.getSession().getUsername();
    }

    public static  FishHelperClient getInstance(){
        return instance;
    }
}
