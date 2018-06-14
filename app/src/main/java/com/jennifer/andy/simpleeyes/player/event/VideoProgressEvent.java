package com.jennifer.andy.simpleeyes.player.event;

/**
 * Author:  andy.xwt
 * Date:    2018/3/13 18:10
 * Description:
 */


public class VideoProgressEvent {

    private int duration;
    private int currentPosition;
    private int progress;
    private int secondaryProgress;

    public VideoProgressEvent(int duration, int currentPosition, int progress, int secondaryProgress) {
        this.duration = duration;
        this.currentPosition = currentPosition;
        this.progress = progress;
        this.secondaryProgress = secondaryProgress;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
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
