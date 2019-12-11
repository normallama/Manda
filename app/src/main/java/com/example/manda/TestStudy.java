package com.example.manda;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.manda.Fragment.CircleProgressView;
import com.example.manda.Fragment.SpellingTexiView;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class TestStudy extends KJActivity {
    @BindView(id=R.id.showSpelling)
    private SpellingTexiView spelling;
    @BindView(id=R.id.play)
    private CircleProgressView play;
    private boolean isPlay;
    private MediaPlayer mediaPlayer;
    private boolean isComplete = true;


    @Override
    public void setRootView() {
        setContentView(R.layout.test);
    }
    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.play:
                if (!isPlay) {
                    playAudio();
                } else {
                    pauseAudio();
                }
                break;
        }
    }
    @Override

    protected void onDestroy() {
        //释放资源
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    private void playAudio() {
        try {
            if (isComplete) {
                mediaPlayer.reset();
                //从asset文件夹下读取MP3文件
                AssetFileDescriptor fileDescriptor = getAssets().openFd("lalalademaxiya.mp3");
                mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopAudio();
                    }
                });
                play.clearDuration();
                isComplete = false;
            }
            mediaPlayer.start();
            play.setDuration(mediaPlayer.getDuration());
            play.play();
            isPlay = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            play.pause();
            isPlay = false;
        }
    }



    private void stopAudio() {
        play.stop();
        isPlay = false;
        isComplete = true;
    }
}
