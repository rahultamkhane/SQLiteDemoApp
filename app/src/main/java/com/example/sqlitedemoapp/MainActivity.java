package com.example.sqlitedemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addNewStudent(View view) {
        EditText etRollNo = findViewById(R.id.etRollNo);
        EditText etName = findViewById(R.id.etName);

        int rn = Integer.parseInt(etRollNo.getText().toString());
        String n = etName.getText().toString();

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.insertStudent(new Student(rn, n));

        etRollNo.setText("");
        etName.setText("");
        Toast.makeText(getApplicationContext(), "New student record added !!!", Toast.LENGTH_SHORT).show();
    }

    public void showStudents(View view) {
        TextView tvShowStudents = findViewById(R.id.tvStudents);

        DBHelper db = new DBHelper(this);
        List<Student> studentsList = db.getAllStudents();

        String data = "RNo.\tName\n------------------\n";

        for(Student s : studentsList) {

            data += s.getRollNo() + "\t" + s.getName() + "\n";
        }
        tvShowStudents.setText(data);
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
    }

    public void updateStudentRecord(View view) {

        EditText etRollNo = findViewById(R.id.etRollNo);
        EditText etName = findViewById(R.id.etName);

        int rn = Integer.parseInt(etRollNo.getText().toString());
        String n = etName.getText().toString();

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updateStudent(new Student(rn, n));

        etRollNo.setText("");
        etName.setText("");
        Toast.makeText(getApplicationContext(), "Student record updated !!!", Toast.LENGTH_SHORT).show();
    }

    public void deleteStudentRecord(View view) {
        EditText etRollNo = findViewById(R.id.etRollNo);
        EditText etName = findViewById(R.id.etName);

        int rn = Integer.parseInt(etRollNo.getText().toString());

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.deleteStudent(rn);

        etRollNo.setText("");
        etName.setText("");
        Toast.makeText(getApplicationContext(), "Student record deleted !!!", Toast.LENGTH_SHORT).show();
    }
}