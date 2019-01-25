package com.narges.sadeghi.recorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToRecorderFragment();
    }

    private void goToRecorderFragment() {
        RecorderFragment recorderFragment = RecorderFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, recorderFragment)
                .commit();
    }
}
