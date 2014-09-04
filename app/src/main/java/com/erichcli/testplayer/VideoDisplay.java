package com.erichcli.testplayer;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;


public class VideoDisplay extends Activity implements Callback, OnCompletionListener {

    private static boolean isPlayer;

    private MediaPlayer mMediaPlayer;

    private int width = 0;
    private int height = 0;

    private static Uri mVideoUri;

    private SurfaceView mSurfaceView;
    private SurfaceHolder holder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);

        Bundle bundle = getIntent().getExtras();
        mVideoUri = Uri.parse(bundle.getString("video_address"));

        mSurfaceView=(SurfaceView) this.findViewById(R.id.preview);

        holder = mSurfaceView.getHolder();
        holder.addCallback(this);

        isPlayer = true;

        mSurfaceView.setKeepScreenOn(true);

        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPlayer){
                    Pause();
                }else{
                    Play();
                }

            }
        });

    }


    public void prepareVideo() {

        try {

            mMediaPlayer = new MediaPlayer();

            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDisplay(holder);


            mMediaPlayer.setDataSource(this,mVideoUri);
            mMediaPlayer.prepare();

            Log.e("TAG-Duration", mMediaPlayer.getDuration() + "");
            mMediaPlayer.setOnCompletionListener(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

        width = mMediaPlayer.getVideoWidth();
        height = mMediaPlayer.getVideoHeight();
        if (width != 0 && height != 0)
        {
            holder.setFixedSize(width, height);
            mMediaPlayer.start();
            Log.i("TAG-Duration2", mMediaPlayer.getDuration() + "");
        }

    }


    public void onCompletion(MediaPlayer mMediaPlayer)
    {
        // TODO Auto-generated method stub
        Log.i("TAG-onCompletion", "Completion");

        Stop();

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        // TODO Auto-generated method stub

    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        // TODO Auto-generated method stub

        prepareVideo();

    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        // TODO Auto-generated method stub
        Log.i("TAG-surfaceDestroyed", "surfaceDestroyed");
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer != null)
        {
            if (mMediaPlayer.isPlaying())
            {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        isPlayer = false;
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            isPlayer = false;
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.video_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.Play:
                Play();
                break;
            case R.id.Stop:
                Stop();
                break;
            case R.id.Pause:
                Pause();
                break;
            default:
                break;
        }

        return true;

    }

    private void Pause(){

        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            isPlayer = false;
            Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
        }

    }

    private void Stop(){

        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;

        isPlayer = false;

        Intent intent = new Intent();
        intent.setClass(VideoDisplay.this,MyActivity.class);
        startActivity(intent);

        Toast.makeText(this, "End", Toast.LENGTH_SHORT).show();

        this.finish();
    }

    private void Play(){

        if (!mMediaPlayer.isPlaying()){

            mMediaPlayer.start();
            isPlayer = true;
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }

    }

}
