package algonquin.cst2335.mann0520.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.mann0520.R;
import algonquin.cst2335.mann0520.data.MainViewModel;
import algonquin.cst2335.mann0520.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;

    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        setContentView(R.layout.activity_main);

        model = new ViewModelProvider(this).get(MainViewModel.class);
//        model.editString

        TextView mytext = findViewById(R.id.textview);

        Button button = variableBinding.button;

        EditText myedit = findViewById(R.id.myedittext);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                mytext.setText( "Your edit text has: " + editString);
            }


        });

    }



}
