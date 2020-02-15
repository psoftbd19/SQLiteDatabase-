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

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private EditText signInUserIDEditText,signInPassEditText ;
    private Button signInButton;
    private TextView signUpTextView;
    private ProgressBar progressBar;
    MyDatabaseHelper  myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();//-----------for hide address bar--------------------
        this.setTitle("Sign In Activity");
        myDatabaseHelper= new MyDatabaseHelper(this);
        myDatabaseHelper.getWritableDatabase();
        SQLiteDatabase sqLiteDatabase= myDatabaseHelper.getWritableDatabase();

        signInUserIDEditText = findViewById(R.id.editTextUserID_id);
        signInPassEditText = findViewById(R.id.editTextSignInPass_id);
        signUpTextView = findViewById(R.id.signUpTextView_id);
        signInButton = findViewById(R.id.signInButton_id);
        progressBar = findViewById(R.id.progressbar_id);

        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.signInButton_id:

                String userID = signInUserIDEditText.getText().toString();
                String pass = signInPassEditText.getText().toString();
                Boolean result = myDatabaseHelper.findPassword(userID,pass);
   if(result==true){
       Intent intent = new Intent(getApplicationContext(),MainActivity.class);
       startActivity(intent);
       break;
   }else{
       Toast.makeText(getApplicationContext(),"Unsuccessfully",Toast.LENGTH_LONG).show();
       break;
   }

            case R.id.signUpTextView_id:
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }
}
