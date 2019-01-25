package com.narges.sadeghi.recorder;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class RecorderFragment extends Fragment {

    private ImageView start;
    private ImageView stop;
    private TextView answer;

    private static MediaRecorder mediaRecorder;
    private String audioFile;

    public RecorderFragment() {

    }

    public static RecorderFragment newInstance() {
        RecorderFragment fragment = new RecorderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recorder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setup(view);
    }

    private void setup(View view) {

        start = view.findViewById(R.id.start);
        stop = view.findViewById(R.id.stop);
        stop.setVisibility(View.GONE);
        answer = view.findViewById(R.id.text);

        setupRecorder();

        start.setOnClickListener(new StartOnClickListener());
        stop.setOnClickListener(new StopOnClickListener());
    }

    private void setupRecorder() {

        // root of external storage
        audioFile =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setAudioSamplingRate(44100);
        mediaRecorder.setAudioChannels(AudioFormat.CHANNEL_OUT_STEREO); // stereo
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioFile);

    }

    private class StopOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            start.setVisibility(View.VISIBLE);
            stop.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Audio Recorder stopped", Toast.LENGTH_LONG).show();
        }
    }

    private class StartOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException ise) {
                // make something ...
            } catch (IOException ioe) {
                // make something
            }
            start.setVisibility(View.GONE);
            stop.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "Recording started", Toast.LENGTH_SHORT).show();
        }
    }
}
