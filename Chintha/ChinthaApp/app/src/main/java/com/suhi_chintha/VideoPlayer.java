package com.suhi_chintha;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.universalvideoview.UniversalVideoView.VideoViewCallback;

public class VideoPlayer extends AppCompatActivity {
    View mBottomLayout;
    UniversalMediaController mMediaController;
    View mVideoLayout;
    UniversalVideoView mVideoView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_video_player);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mMediaController.setOnLoadingView((int) R.layout.uvv_on_loading_layout);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(Static_Variable.videolinks);
        mVideoView.start();
        mVideoView.setVideoViewCallback(new VideoViewCallback() {
            private boolean isFullscreen;

            public void onScaleChange(boolean isFullscreen2) {
                isFullscreen = isFullscreen2;
                if (isFullscreen2) {
                    LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = -1;
                    layoutParams.height = -1;
                    mVideoLayout.setLayoutParams(layoutParams);
                    mBottomLayout. setVisibility(View.GONE);
                    return;
                }
                LayoutParams layoutParams2 = mVideoLayout.getLayoutParams();
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
