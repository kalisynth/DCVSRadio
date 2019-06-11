package org.nac.kalisynth.dcvsradio;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.MediaPlayer;

import co.mobiwise.library.RadioListener;
import co.mobiwise.library.RadioManager;

public class DCVSRadio extends AppCompatActivity implements RadioListener {

    RadioManager mRadioManager = RadioManager.with(this);
    TextView mTextViewControl;
    String radioplaying, radiopaused, radioloading, radiosong;
    Button radiodcvs, radionac;
    String radiourl = "http://thassos.cdnstream.com:5049/stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcvsradio);
        mRadioManager.registerListener(this);
        mRadioManager.enableNotification(true);
        mTextViewControl = (TextView)findViewById(R.id.mTextViewControl);
        radioplaying = getString(R.string.radioplaying);
        radiopaused = getString(R.string.radiostopped);
        radioloading = getString(R.string.radioloadin);
        radiodcvs = (Button)findViewById(R.id.dcvsbtn);
        radionac = (Button)findViewById(R.id.nac);
    }

    /*private void launchVLC(String url)
    {
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setComponent(new ComponentName("org.videolan.vlc", "org.videolan.vlc.gui.video.VideoPlayerActivity"));
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=org.videolan.vlc");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }*/

    public void onClickPlay(View v){
            /*launchVLC("http://bit.ly/nacradio");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_play);
        mBuilder.setContentTitle("NAC Radio");

        mBuilder.setContentText("Radio is Playing");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(3, mBuilder.build());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(radio.this, radio.class)); finish();
            }
        },5000);*/
        mRadioManager.startRadio(radiourl);
    }

    public void onClickPauseBad(View v){
        /*launchVLC("file:///android_asset/1sec.mp3");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_stat_pause);
        mBuilder.setContentTitle("DCVS Radio");
        mBuilder.setContentText("Radio is Paused");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(3, mBuilder.build());*/
        mRadioManager.stopRadio();
    }

    @Override
    public void onStart(){
        super.onStart();
        mRadioManager.connect();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRadioManager.disconnect();
    }

    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                mTextViewControl.setText(radioloading);
            }
        });
    }

    @Override
    public void onRadioConnected() {

    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here.
                mTextViewControl.setText(radioplaying);
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                mTextViewControl.setText(radiopaused);
            }
        });
    }

    @Override
    public void onMetaDataReceived(String s, String s1) {
        //TODO Check metadata values. Singer name, song name or whatever you have.

    }

    /*public void onRadioClick(View v){
        if(v.getId() == R.id.dcvs){
            mRadioManager.stopRadio();
            radiourl = "http://thassos.cdnstream.com:5046/stream";
            mRadioManager.startRadio(radiourl);
        } else if (v.getId() == R.id.nac){
            mRadioManager.stopRadio();
            radiourl = "http://thassos.cdnstream.com:5049/stream";
            mRadioManager.startRadio(radiourl);
        }
    }*/
}

//todo: complete transfer to libvlc add play/pause - option for other streams
