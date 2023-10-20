package net.pmkjun.pyrofishinghelper.file;

import java.io.Serializable;
public class Data implements Serializable{
    public String userName;

    public boolean toggleTotemtime = true;

    public boolean toggleTotemtimeText = true;

    public boolean isTotemCooldown = false;
    public int valueTotemCooldown = 60;
    public int valueTotemActivetime = 5;
    public long valueCooldownReduction = 0;
    public long lastTotemTime=0;
    public long lastTotemCooldownTime = 0;

    public boolean toggleCustomTexture = true;
    public boolean toggleMuteotherfishingbobber = false;
    public boolean toggleChattinglog = false;
    public int Timer_xpos = 1;
    public int Timer_ypos = 1;
}
