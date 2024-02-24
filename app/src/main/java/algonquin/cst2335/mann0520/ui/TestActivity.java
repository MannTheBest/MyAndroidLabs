package algonquin.cst2335.mann0520.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import algonquin.cst2335.mann0520.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
        Button btn1 = findViewById(R.id.testButton);
        
        btn1.setOnClickListener(e -> {
            Toast.makeText(this, "Testing", Toast.LENGTH_SHORT).show();
        });
    }
}