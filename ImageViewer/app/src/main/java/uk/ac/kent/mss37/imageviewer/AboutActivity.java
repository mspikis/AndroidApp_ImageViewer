package uk.ac.kent.mss37.imageviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Import layout
        setContentView(R.layout.activity_about);

        // Enable back to MainActivity Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
