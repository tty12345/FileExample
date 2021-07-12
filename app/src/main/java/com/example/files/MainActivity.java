package com.example.files;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final int APP_SPEC_INT=1;
    private static final int APP_SPEC_EXT=2;
    private static final int PUB_EXT=3;

    EditText mInputTxt;
    Button mSaveBtn;
    Button mReadBtn;

    File mTargetFile;

    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputTxt = (EditText) findViewById(R.id.inputTxt);

        mSaveBtn = (Button) findViewById(R.id.btnSave);

        mSaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                writeToFile();
            }
        });

        mReadBtn = (Button) findViewById(R.id.btnRead);

        mReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                readFromFile();
            }
        });

        String filePath = "SampleFolder";
        String fileName = "SampleFile.txt";
        mTargetFile = new File(getFilesDir(), filePath +"/" + fileName);
    }

    protected void writeToFile(){
        try{
            File parent = mTargetFile.getParentFile();
            if (!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create dir:"+parent);
            }

            FileOutputStream fos = new FileOutputStream(mTargetFile);
            fos.write(mInputTxt.getText().toString().getBytes());
            fos.close();

            mInputTxt.setText("");

            Toast.makeText(this,"Write file ok!", Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void readFromFile(){
        String data = " ";
        try{
            FileInputStream fis = new FileInputStream(mTargetFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            //read each line word in the android app. If the next line is empty, stop reading
            while((strLine = br.readLine())!=null){
                data = data+strLine;
            }

            in.close();

            //save the text into the file
            mInputTxt.setText(data);

            Toast.makeText(this, "Read file ok!", Toast.LENGTH_LONG).show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}