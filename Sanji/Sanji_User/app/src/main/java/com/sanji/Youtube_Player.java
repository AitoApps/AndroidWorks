package com.sanji;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.PlaylistEventListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube_Player extends YouTubeBaseActivity implements OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 4;
    final DatabaseHandler db = new DatabaseHandler(this);
    private YouTubePlayerView youTubeView;

    private class FullScreenListener implements OnFullscreenListener {
        private FullScreenListener() {
        }

        public void onFullscreen(boolean isFullscreen) {
        }
    }

    private class PlayListListener implements PlaylistEventListener {
        private PlayListListener() {
        }

        public void onNext() {
        }

        public void onPlaylistEnded() {
        }

        public void onPrevious() {
        }
    }

    private class PlaybackListener implements PlaybackEventListener {
        private PlaybackListener() {
        }

        public void onBuffering(boolean isBuffering) {
        }

        public void onPaused() {
        }

        public void onPlaying() {
        }

        public void onSeekTo(int newPositionMillis) {
        }

        public void onStopped() {
        }
    }

    private class PlayerStateListener implements PlayerStateChangeListener {
        private PlayerStateListener() {
        }

        public void onAdStarted() {
        }

        public void onError(ErrorReason reason) {
        }

        public void onLoaded(String arg0) {
        }

        public void onLoading() {
        }

        public void onVideoEnded() {
        }

        public void onVideoStarted() {
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_youtube__player);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Temp.youtubeapilink, this);
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, 4).show();
            return;
        }
        String.format("YouTube Error (%1$s)", new Object[]{errorReason.toString()});
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(db.getyoutubelink());
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        if (requestCode == 4) {
            getYouTubePlayerProvider().initialize(Temp.youtubeapilink, this);
        }
    }
    public Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}
