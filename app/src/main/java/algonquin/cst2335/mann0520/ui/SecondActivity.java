package algonquin.cst2335.mann0520.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.mann0520.R;
import algonquin.cst2335.mann0520.databinding.ActivityMainBinding;
import algonquin.cst2335.mann0520.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        variableBinding.textView3.setText("Welcome Back:" + emailAddress);

    }
}