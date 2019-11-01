package com.suhi_chintha;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import es.dmoral.toasty.Toasty;

public class Player extends YouTubeBaseActivity implements OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 4;
    private YouTubePlayerView youTubeView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.videoplayer_actvty);
        youTubeView = (YouTubePlayerView) findViewById(R.id.playerview);
        youTubeView.initialize(Static_Variable.ytyoutubelink, this);
    }

    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, 4).show();
            return;
        }
        Toasty.info((Context) this, (CharSequence) String.format("YouTube Error (%1$s)", new Object[]{errorReason.toString()}), Toast.LENGTH_LONG).show();
    }

    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(Static_Variable.videolinks);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 4) {
            getYouTubePlayerProvider().initialize(Static_Variable.ytyoutubelink, this);
        }
    }
    public Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.playerview);
    }
}
