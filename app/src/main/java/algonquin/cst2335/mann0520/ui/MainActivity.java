package algonquin.cst2335.mann0520.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.mann0520.R;
import algonquin.cst2335.mann0520.data.MainViewModel;
import algonquin.cst2335.mann0520.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;

    private MainViewModel model;

    private static String TAG = "MainActivity";

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.w( TAG, "In onDestroy() - The application has been forced to shut down" );
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.w( TAG, "In onPause() - The application has suspended and currently paused." );
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.w( TAG, "In onStop() - The application has disappeared and stop working." );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "In onStart() - The application is now visible on screen" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.w( TAG, "In onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w( TAG, "In onCreate() - Loading Widgets" );

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model = new ViewModelProvider(this).get(MainViewModel.class);

        Button btn1 = variableBinding.button;

        btn1.setOnClickListener(v -> {

            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);

            nextPage.putExtra( "EmailAddress", variableBinding.edittext.getText().toString() );
            startActivity(nextPage);

        });










    }



}
