package com.example.libin.yingyuebofangqi;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.libin.yingyuebofangqi.utils.FileUtil;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Handler.Callback, SeekBar.OnSeekBarChangeListener {
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private static final int progress=1;
    private TextView cuttent;
    private TextView total;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button paly = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        cuttent = (TextView) findViewById(R.id.player_cuttent_time);
        total = (TextView) findViewById(R.id.player_total_time);
        seekBar = (SeekBar) findViewById(R.id.player_progress);
        seekBar.setOnSeekBarChangeListener(this);
        paly.setOnClickListener(this);
        pause.setOnClickListener(this);
        handler=new Handler(this);
    }

    /**
     * 播放
     */
    private void play(String path){
        if (mediaPlayer==null){
            //创建一个mediaPlayer
            mediaPlayer=MediaPlayer.create(this, Uri.parse(path));
        }
        mediaPlayer.start();
        int duration = mediaPlayer.getDuration();//获取总时长
        seekBar.setMax(duration);
        total.setText(DateFormat.format("mm:ss",duration));
        int cuttentPositon=mediaPlayer.getCurrentPosition();
        Message message=Message.obtain();
        message.what=progress;
        message.arg1=cuttentPositon;
        handler.sendMessageDelayed(message,500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                //http://www.cfrb.cn/images/2007-05/10/350504101836109106262.mp3
                //http://7rflo2.com2.z0.glb.qiniucdn.com/5714b0b53c958.mp4
                play("http://www.cfrb.cn/images/2007-05/10/350504101836109106262.mp3");
                break;
            case R.id.pause:
                if (mediaPlayer!=null){
                    mediaPlayer.pause();
                    handler.removeMessages(progress);
                }
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case progress:
                cuttent.setText(DateFormat.format("mm:ss",msg.arg1));
                Message message=Message.obtain();
                message.what=progress;
                message.arg1=mediaPlayer.getCurrentPosition();
                handler.sendMessageDelayed(message,1000);
                break;
        }
        return true;
    }
//--------------------------seekBar的回掉函数--------------------------
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mediaPlayer.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.start();
    }
}
