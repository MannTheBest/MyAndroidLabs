package algonquin.cst2335.mann0520.ui;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.mann0520.R;
import algonquin.cst2335.mann0520.databinding.ActivitySecondBinding;


public class SecondActivity extends AppCompatActivity {

//    ActivitySecondBinding variableBinding;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityResultLauncher<Intent> cameraResult;

    @Override
    protected void onPause() {
        super.onPause();

    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        EditText editText=(EditText) findViewById(R.id.editTextPhone);

        editor.putString("phoneNumber", editText.getText().toString());
        editor.apply();

        Log.w( "TAG", "In onPause() - The application has suspended and currently paused." );
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Log.w("Mann", "Result2....>");
                    }
                });

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView txtWelcome=(TextView) findViewById(R.id.textView3);
        txtWelcome.setText("Welcome Back: " + emailAddress);


        Button buttonPic = (Button) findViewById(R.id.button3);
        Button buttonCall = (Button) findViewById(R.id.button2);

        EditText editText=(EditText) findViewById(R.id.editTextPhone);
        ImageView img=(ImageView) findViewById(R.id.imageView);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);


        String phone = prefs.getString("phoneNumber", "");

        editText.setText(phone);

        buttonCall.setOnClickListener(view -> {

            String phoneNumber = editText.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        File file = new File( getFilesDir(), "Picture.png");
        if(file.exists()){
            Bitmap theImage = BitmapFactory.decodeFile("Picture.png");
            img.setImageBitmap( theImage );

        }


        buttonPic.setOnClickListener(view -> {
            Intent pic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bitmap image = data.getParcelableExtra("data");
                            img.setImageBitmap(image);
                        }
                    });
            cameraResult.launch(pic);

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("Mann","Done pic1");
        Bitmap thumbnail=data.getParcelableExtra("data");
        ImageView img= findViewById(R.id.imageView);
        img.setImageBitmap(thumbnail);

        FileOutputStream fOut = null;
        try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
            assert thumbnail != null;
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        }
        catch (FileNotFoundException e)
        { e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}