package com.jennifer.andy.simpleeyes.player.event;

/**
 * Author:  andy.xwt
 * Date:    2018/3/13 18:10
 * Description:
 */


public class VideoProgressEvent {

    private int progress;
    private int secondaryProgress;

    public VideoProgressEvent(int progress, int secondaryProgress) {
        this.progress = progress;
        this.secondaryProgress = secondaryProgress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getSecondaryProgress() {
        return secondaryProgress;
    }

    public void setSecondaryProgress(int secondaryProgress) {
        this.secondaryProgress = secondaryProgress;
    }
}
