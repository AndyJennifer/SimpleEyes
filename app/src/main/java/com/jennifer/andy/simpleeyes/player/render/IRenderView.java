/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jennifer.andy.simpleeyes.player.render;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public interface IRenderView {
    int AR_ASPECT_FIT_PARENT = 0; // without clip
    int AR_ASPECT_FILL_PARENT = 1; // may clip
    int AR_ASPECT_WRAP_CONTENT = 2;//包裹布局
    int AR_MATCH_PARENT = 3;//填充父布局
    int AR_16_9_FIT_PARENT = 4;//16:9适配屏幕
    int AR_4_3_FIT_PARENT = 5;//4:3适配屏幕

    /**
     * 获取当前view
     */
    View getView();

    /**
     * 是否需要等待重新测量
     */
    boolean shouldWaitForResize();

    /**
     * 设置视屏的宽高
     */
    void setVideoSize(int videoWidth, int videoHeight);

    /**
     * 设置视频采样方向比例
     */
    void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen);

    /**
     * 设置视频旋转角度
     */
    void setVideoRotation(int degree);

    /**
     * 设置方向比例
     */
    void setAspectRatio(int aspectRatio);

    /**
     * 设置渲染回调
     */
    void addRenderCallback(@NonNull IRenderCallback callback);

    /**
     * 移除渲染回调
     */
    void removeRenderCallback(@NonNull IRenderCallback callback);

    /**
     * surfaceHolder管理相关接口
     */
    interface ISurfaceHolder {
        /**
         * 绑定当前媒体播放器
         */
        void bindToMediaPlayer(IMediaPlayer mp);

        /**
         * 获取当前渲染的view
         */
        @NonNull
        IRenderView getRenderView();

        /**
         * 获取当前surfaceHolder
         */
        @Nullable
        SurfaceHolder getSurfaceHolder();

        /**
         * 打开surface
         */
        @Nullable
        Surface openSurface();

        /**
         * 获取SurfaceTexture
         */
        @Nullable
        SurfaceTexture getSurfaceTexture();
    }

    interface IRenderCallback {
        /**
         * @param holder
         * @param width  could be 0
         * @param height could be 0
         */
        void onSurfaceCreated(@NonNull ISurfaceHolder holder, int width, int height);

        /**
         * @param holder
         * @param format could be 0
         * @param width
         * @param height
         */
        void onSurfaceChanged(@NonNull ISurfaceHolder holder, int format, int width, int height);

        void onSurfaceDestroyed(@NonNull ISurfaceHolder holder);
    }
}
