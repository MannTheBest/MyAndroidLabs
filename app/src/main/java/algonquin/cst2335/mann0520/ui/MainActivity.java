package algonquin.cst2335.mann0520.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model = new ViewModelProvider(this).get(MainViewModel.class);

        TextView mytext = variableBinding.textview;

        Button button = variableBinding.button;

        EditText myedit = variableBinding.myedittext;

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                String editString = variableBinding.myedittext.getText().toString();
                variableBinding.textview.setText( "Your edit text has: " + editString);
            }
        });

        model.isSelected.observe(this,selected->{
            variableBinding.checkbox1.setChecked(selected);
            variableBinding.radio1.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
        });



        variableBinding.checkbox1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (Boolean.TRUE.equals(model.isSelected.getValue())) {
                Toast.makeText(MainActivity.this, "Checkbox Checked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Checkbox Unchecked", Toast.LENGTH_SHORT).show();
            }


        });

        variableBinding.radio1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (Boolean.TRUE.equals(model.isSelected.getValue())) {
                Toast.makeText(MainActivity.this, "Radio Checked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Radio Unchecked", Toast.LENGTH_SHORT).show();
            }


        });
        variableBinding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (Boolean.TRUE.equals(model.isSelected.getValue())) {
                Toast.makeText(MainActivity.this, "Switch Checked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Switch Unchecked", Toast.LENGTH_SHORT).show();
            }

        });


        variableBinding.aclogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variableBinding.myedittext.setText("Algonquin");
            }
        });

        variableBinding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "The width = " + variableBinding.imageButton.getWidth() +
                        "and height = " + variableBinding.imageButton.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });








    }



}
