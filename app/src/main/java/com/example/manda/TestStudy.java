package com.example.manda;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.manda.Adapter.RecordingUtil;
import com.example.manda.Fragment.CircleProgressView;
import com.example.manda.Fragment.CircleRecordSurfaceView;
import com.example.manda.Fragment.SpellingTexiView;
import com.example.manda.Rating.DTW;
import com.example.manda.Rating.MFCC;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

public class TestStudy extends KJActivity {
    @BindView(id=R.id.showSpelling)
    private SpellingTexiView spelling;
    @BindView(id=R.id.Standard_play,click = true)
    private CircleProgressView play;
    @BindView(id=R.id.record,click = true)
    private CircleRecordSurfaceView record;
    @BindView(id=R.id.test_score)
    private TextView score;
    private boolean isPlay;
    private MediaPlayer mediaPlayer;   //播放器
    private boolean isComplete = true;
    String filename;
    private RecordingUtil mSoundRecorder;  //录音器
    private double calscore;


    @Override
    public void setRootView() {
        setContentView(R.layout.test);
    }
    @Override
    public void initData() {
        super.initData();
        filename="1";
        mSoundRecorder = new RecordingUtil(filename);
        spelling.setStringResource("  那是力争上游的一种树，笔直的干，笔直的枝。它的干通常是丈把高，像是加过人工似的，一丈以内绝无旁枝。它所有的丫枝一律向上，而且紧紧靠拢，也像是加过人工似的，成为一束，绝不旁逸斜出。");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        record.setDuration(180);
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
                        mSoundRecorder.startRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        record.reset();
                        record.stopDraw();
                        mSoundRecorder.stopRecord();
                        mSoundRecorder.convertWaveFile();
                        cal();
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
            case R.id.Standard_play:
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
                String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/assets/";
                //AssetFileDescriptor fileDescriptor = getAssets().openFd(basePath+filename+".wav");//"test1.mp3");
                //mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                //        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                File file=new File(basePath+filename+".wav");
                FileInputStream fis=new FileInputStream(file);
                mediaPlayer.setDataSource(fis.getFD());
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

    private void cal(){  //测分
        MFCC mfcc = new MFCC();
        MFCC mfcc2 = new MFCC();
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/assets/";
        double[][] result = mfcc.getMfcc(basePath+filename+".wav");
        double[][] result2= mfcc2.getMfcc(basePath+filename+".wav");
        DTW d1=new DTW(result,result2); //测试数据，参考数据

        calscore=d1.calscore();
        score.setText(String.valueOf(calscore));

    }
}
