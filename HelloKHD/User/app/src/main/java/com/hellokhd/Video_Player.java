package com.hellokhd;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

public class Video_Player extends AppCompatActivity {
    View mBottomLayout;
    UniversalMediaController mMediaController;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__player);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mMediaController.setOnLoadingView((int) R.layout.videoloadinglayout);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(Temp.videolinks);
        mVideoView.start();
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            private boolean isFullscreen;

            public void onScaleChange(boolean isFullscreen2) {
                isFullscreen = isFullscreen2;
                if (isFullscreen2) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = -1;
                    layoutParams.height = -1;
                    mVideoLayout.setLayoutParams(layoutParams);
                    mBottomLayout. setVisibility(View.GONE);
                    return;
                }
                ViewGroup.LayoutParams layoutParams2 = mVideoLayout.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -1;
                mVideoLayout.setLayoutParams(layoutParams2);
                mBottomLayout.setVisibility(View.VISIBLE);
            }

            public void onPause(MediaPlayer mediaPlayer) {
            }

            public void onStart(MediaPlayer mediaPlayer) {
            }

            public void onBufferingStart(MediaPlayer mediaPlayer) {
            }

            public void onBufferingEnd(MediaPlayer mediaPlayer) {
            }
        });
    }

    public void onBackPressed() {
        finish();
    }
}
