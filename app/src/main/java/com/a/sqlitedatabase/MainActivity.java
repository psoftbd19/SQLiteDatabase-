package com.a.sqlitedatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MyDatabaseHelper  myDatabaseHelper;
    static final int REQUEST_IMAGE_CAPTURE = 1;
   private EditText nameEditText,ageEditText,genderEditText,contactEditText,idEditText;
   private Button addButton,showButton,updateButton,deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabaseHelper= new MyDatabaseHelper(this);
        myDatabaseHelper.getWritableDatabase();

        SQLiteDatabase sqLiteDatabase= myDatabaseHelper.getWritableDatabase();
        nameEditText = (EditText) findViewById(R.id.nameEditText_id);
        ageEditText = (EditText) findViewById(R.id.ageEditText_id);
        genderEditText =(EditText) findViewById(R.id.genderEditText_id);
        contactEditText =(EditText) findViewById(R.id.contactEditText_id);
        idEditText =(EditText) findViewById(R.id.idEditText_id);

        addButton =(Button) findViewById(R.id.addDataButton_id);
        showButton =(Button) findViewById(R.id.showDataButton_id);
        updateButton =(Button) findViewById(R.id.updateDataButton_id);
        deleteButton =(Button) findViewById(R.id.deleteDataButton_id);

        addButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String contact = contactEditText.getText().toString();
        String id = idEditText.getText().toString();

    if(view.getId()==R.id.addDataButton_id)
        {
            if(name.isEmpty()) {nameEditText.setError("Enter a Name !");
                nameEditText.requestFocus();
                return;
            }
            if(age.isEmpty()) {ageEditText.setError("Enter age !");
                ageEditText.requestFocus();
                return;
            }
            if(gender.isEmpty()) {genderEditText.setError("Enter Gender !");
                genderEditText.requestFocus();
                return;
            }
            if(contact.isEmpty()) {contactEditText.setError("Enter Contact No !");
                contactEditText.requestFocus();
                return;
            }
//======================Add data in Table=======================================================23-01-20===
            long rowId=  myDatabaseHelper.insertData(name,age,gender,contact);
             if(rowId>0)
             {
                 Toast.makeText(getApplicationContext(),"Data row "+rowId+" save Successfully",Toast.LENGTH_LONG).show();
                 nameEditText.setText("");//-------for clear editText--------
                 ageEditText.setText("");
                 genderEditText.setText("");
                 contactEditText.setText("");
             }else{
                 Toast.makeText(getApplicationContext(),"Unsuccessfully",Toast.LENGTH_LONG).show();
             }
        }

// ================== Show data =============================== 23-01-20 ========
    if(view.getId()==R.id.showDataButton_id)
        {
            SQLiteDatabase sqLiteDatabase= myDatabaseHelper.getWritableDatabase();
            Cursor cursor = myDatabaseHelper.showAllData();
            if(cursor.getCount()==0)
            {
                //
                showData("Error !!","No data found");
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext())
            {
                stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                stringBuffer.append("Name : "+cursor.getString(1)+"\n");
                stringBuffer.append("Age : "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender : "+cursor.getString(3)+"\n");
                stringBuffer.append("Contact No : "+cursor.getString(4)+"\n\n");
            }
            showData("Information",stringBuffer.toString());
        }
    //======== Update Data ===================================================================
        if(view.getId()==R.id.updateDataButton_id)
        {
            Boolean isUpdate=  myDatabaseHelper.updateData(id,name,age,gender,contact);
            if(isUpdate==true)
            {
                Toast.makeText(getApplicationContext(),"Successfully Data Updated..",Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(getApplicationContext(),"Data not Update !",Toast.LENGTH_LONG).show();
            }
        }

//======== Delete Data ========================================= 23-01-20==========================
        if(view.getId()==R.id.deleteDataButton_id)
        {
            if(id.isEmpty()) {idEditText.setError("Enter a Valid ID");
                idEditText.requestFocus();
                return;
            }
        int value = myDatabaseHelper.deleteData(id);
        if(value>0){
            Toast.makeText(getApplicationContext(),"Successfully Data Deleted !",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Unsuccessfully data delete",Toast.LENGTH_LONG).show();
        }
        }
    }

    //-----show data method-----
    public void showData(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
    public void  AddImage(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
}
