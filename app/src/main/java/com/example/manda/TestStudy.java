package com.example.manda;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.manda.Fragment.CircleProgressView;
import com.example.manda.Fragment.CircleRecordSurfaceView;
import com.example.manda.Fragment.SpellingTexiView;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class TestStudy extends KJActivity {
    @BindView(id=R.id.showSpelling,click = true)
    private SpellingTexiView spelling;
    @BindView(id=R.id.play,click = true)
    private CircleProgressView play;
    @BindView(id=R.id.record,click = true)
    private CircleRecordSurfaceView record;
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
        record.setDuration(6);
        record.setStartBitmap(R.drawable.audiorecord_star);
        record.setStopBitmap(R.drawable.audiorecord_in);
        record.setArcColor(ContextCompat.getColor(this, R.color.colorPrimary));
        record.setSmallCircleColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        record.setDefaultRadius(50);
        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        record.startDraw();
                        break;
                    case MotionEvent.ACTION_UP:
                        record.reset();
                        record.stopDraw();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
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
                AssetFileDescriptor fileDescriptor = getAssets().openFd("test1.mp3");
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
