package net.pmkjun.pyrofishinghelper.util;

public class Timer {
    private long currentTime;

    public void updateTime() {
        this.currentTime = System.currentTimeMillis();
    }

    public float getDifference(long time) {
        return (float)(this.currentTime - time) / 1000.0F;
    }

    public long getCurrentTime() {
        return this.currentTime;
    }
}
