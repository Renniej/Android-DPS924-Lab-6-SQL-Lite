package com.example.lab6sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private DatabaseHelper mDatabaseHelper;
    private EditText txt_ID;
    private EditText txt_Name;
    private EditText txt_Marks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);

        txt_ID = (EditText) findViewById(R.id.studID);
        txt_Name = (EditText) findViewById(R.id.studName);
        txt_Marks = (EditText) findViewById(R.id.studMark);

        Button btnAddStud = (Button) findViewById(R.id.btn_addStudent);
        Button btnViewAll = (Button) findViewById(R.id.btn_getAllStudents);
        Button btnViewID = (Button) findViewById(R.id.btn_getStudent);








        btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button but=  (Button) v;

                String[] studData = createStudent();


                if (studData != null){
                    AddData(studData);
                }

            }
        });


        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("all");
            }
        });


        btnViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = String.valueOf(txt_ID.getText());;

                if(ID != null || ID.length() > 0){
                    getData(ID);
                }else{
                    getData(null);
                }


            }
        });

    }


    public String[] createStudent(){


        String ID = txt_ID.getText().toString();
        String Name = txt_Name.getText().toString();
        String Marks = txt_Marks.getText().toString();
        String[] studentData = null;

        if (Name.length() <= 0 || ID.length() <= 0 || Marks.length() <= 0){
            toastMessage("Incomplete");
        }
        else{
            studentData = new String[3];
            studentData[0] = ID;
            studentData[1] = Name;
            studentData[2] = Marks;
        }


        return studentData;

    }

    public void getData(String query){

         String Title = null;
         String StrData = "";
         Cursor Data = mDatabaseHelper.getData(query);

        if (query.equals("all")){
            Title = "The following students have been added";
        }
        else if(Data.getCount() == 0){
            Title = "This student does not exist";
        }
        else{
            Title = "Student details are as follows";
        }


        if (Data != null) {
            while (Data.moveToNext()) {
                StrData += ("ID: " + Data.getString(0) + " Name: " + Data.getString(1) + " Marks: " + Data.getString(2) + "\n");
            }
        }

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(Title)
                .setMessage(StrData).show();

    }


    public void AddData(String[] newEntry){

        boolean dataInserted = mDatabaseHelper.addData(newEntry);

        if (dataInserted) {
            toastMessage("Data sucessfully Inserted!");
        } else {
            toastMessage("Something went wrong ");
        }

    }


    public void toastMessage(String msg){
        Toast.makeText(MainActivity.this   , msg, Toast.LENGTH_SHORT).show();
    }
}
