package com.jennifer.andy.simpleeyes.player.controllerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import com.jennifer.andy.simpleeyes.R;
import com.jennifer.andy.simpleeyes.player.IjkMediaController;

/**
 * Author:  andy.xwt
 * Date:    2018/3/13 17:19
 * Description:
 */


public class FullScreenControllerView extends ControllerView implements View.OnClickListener {

    private ImageView mMinScreen;

    public FullScreenControllerView(MediaController.MediaPlayerControl player, IjkMediaController controller, Context context) {
        super(player, controller, context);
    }

    @Override
    public void initView(View rootView) {
        mMinScreen = rootView.findViewById(R.id.iv_min_screen);
        //添加标题等操作
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
    }

    @Override
    public void initControllerListener() {

        mMinScreen.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_min_screen://返回小界面
                mController.changeControllerView(new TinyControllerView(mPlayer, mController, mContext));
                break;
        }

    }


    @Override
    public void updateProgress(int progress, int secondaryProgress) {

    }

    @Override
    public void updateTime(String currentTime, String endTime) {

    }

    @Override
    public void updateTogglePauseUI(boolean isPlaying) {

    }

    @Override
    public int setControllerLayoutId() {
        return R.layout.layout_media_controller_full_screen;
    }

    @Override
    public void hideNextButton() {

    }
}
