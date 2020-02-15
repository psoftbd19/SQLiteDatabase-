package com.a.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signUpNameEditText,signUpEmailEditText,signUpUserIDEditText,signUpPassEditText ;
    private Button signUpButton;
    private TextView signInTextView;
    private ProgressBar progressBar;
    MyDatabaseHelper  myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myDatabaseHelper= new MyDatabaseHelper(this);
        myDatabaseHelper.getWritableDatabase();
        SQLiteDatabase sqLiteDatabase= myDatabaseHelper.getWritableDatabase();

        this.setTitle("Sign Up Activity");
        signUpNameEditText = findViewById(R.id.editTextName_id);
        signUpEmailEditText = findViewById(R.id.editTextEmail_id);
        signUpUserIDEditText = findViewById(R.id.editTextUserID_id);
        signUpPassEditText = findViewById(R.id.editTextPass_id);

        signInTextView = findViewById(R.id.signInTextView_id);
        signUpButton = findViewById(R.id.signUpButton_id);

        progressBar = findViewById(R.id.progressbar_id);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = signUpNameEditText.getText().toString();
        String email = signUpEmailEditText.getText().toString();
        String userID = signUpUserIDEditText.getText().toString();
        String pass = signUpPassEditText.getText().toString();

        switch (v.getId())
        {
            case R.id.signUpButton_id:
                //======================Add data in Table=======================================================23-01-20===
                long rowId = myDatabaseHelper.insertDataLogin(name,email,userID,pass);
                if(rowId>0)
                {
                    Toast.makeText(getApplicationContext(),"Data row "+rowId+" save Successfully",Toast.LENGTH_LONG).show();
                    signUpNameEditText.setText("");//-------for clear editText--------
                    signUpEmailEditText.setText("");
                    signUpUserIDEditText.setText("");
                    signUpPassEditText.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Unsuccessfully",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.signInTextView_id:
                Intent intent = new Intent(getApplicationContext(),SignIn.class);
                startActivity(intent);
                break;
        }
    }
}
