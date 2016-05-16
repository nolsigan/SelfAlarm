package net.teamsv.selfalarm;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.teamsv.selfalarm.database.Record;

import java.io.File;

import io.realm.Realm;

public class NewRecordActivity extends Activity {

    final private static String RECORD_FILE = "/temp.mp4";

    private Realm realm;
    MediaPlayer mPlayer;
    MediaRecorder mRecorder;

    TextView playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);


        /* set realm instance */
        realm = Realm.getInstance(this);


        /* button onclick listeners */
        TextView startButton = (TextView) findViewById(R.id.btn_start_record);
        TextView stopButton = (TextView) findViewById(R.id.btn_stop_record);
        TextView pushButton = (TextView) findViewById(R.id.btn_push_record);
        TextView cancelButton = (TextView) findViewById(R.id.btn_cancel_record);
        playButton = (TextView) findViewById(R.id.btn_play_record);


        startButton.setOnClickListener(mOnClickListener);
        stopButton.setOnClickListener(mOnClickListener);
        playButton.setOnClickListener(mOnClickListener);
        pushButton.setOnClickListener(mOnClickListener);
        cancelButton.setOnClickListener(mOnClickListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_start_record: {

                    Toast.makeText(getApplicationContext(), "Start recording", Toast.LENGTH_SHORT).show();

                    new MediaPrepareTask().execute(null, null, null);
                    break;
                }
                case R.id.btn_stop_record: {

                    stopRecord();
                    break;
                }
                case R.id.btn_play_record: {

                    // if not playing, play
                    // ow, stop playing
                    if (playButton.getText().equals("재생")) {
                        playRecord();
                        playButton.setText("재생 중지");
                    }
                    else {
                        stopPlayRecord();
                        playButton.setText("재생");
                    }

                    break;
                }
                case R.id.btn_push_record: {

                    pushRecord();
                    finish();

                    break;
                }
                case R.id.btn_cancel_record: {

                    File temp = new File(getFilesDir().getAbsolutePath() + RECORD_FILE);
                    temp.delete();

                    finish();

                    break;
                }
            }
        }
    };


    /* start record

       handled by async
     */
    class MediaPrepareTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d("Recorder", "Source dir : " + getFilesDir().getAbsolutePath() + RECORD_FILE);

            // initialize recorder

            if ( mRecorder != null ) {

                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mRecorder.setOutputFile(getFilesDir().getAbsolutePath() + RECORD_FILE);

            try {

                mRecorder.prepare();
                mRecorder.start();

                return true;
            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }
        }

    }


    /* stop record */
    private void stopRecord() {

        if ( mRecorder == null ) return;

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        Toast.makeText(this, "Stop recording", Toast.LENGTH_SHORT).show();
    }


    /* play record */

    private void playRecord() {

        if ( mPlayer != null ) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }

        Toast.makeText(this, "Play record", Toast.LENGTH_SHORT).show();

        try {

            mPlayer = new MediaPlayer();

            // set onCompletionListener

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    playButton.setText("재생");
                }
            });

            mPlayer.setDataSource(getFilesDir().getAbsolutePath() + RECORD_FILE);
            mPlayer.prepare();
            mPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* stop playing record */
    private void stopPlayRecord() {

        if ( mPlayer == null ) return;

        Toast.makeText(this, "Stop playing record", Toast.LENGTH_SHORT).show();

        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }


    /* push record */
    private void pushRecord() {

        String newName = String.valueOf(System.currentTimeMillis());

        // push to record db
        realm.beginTransaction();
        Record record = new Record(newName);
        realm.commitTransaction();


        // change file name
        File dir = new File(getFilesDir().getAbsolutePath() + RECORD_FILE);
        File to = new File(getFilesDir().getAbsolutePath() + "/" + newName + ".mp4");
        dir.renameTo(to);
    }
}
