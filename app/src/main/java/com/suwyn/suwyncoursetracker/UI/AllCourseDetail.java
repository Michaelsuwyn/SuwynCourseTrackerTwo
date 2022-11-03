package com.suwyn.suwyncoursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.suwyn.suwyncoursetracker.Database.Repository;
import com.suwyn.suwyncoursetracker.Entity.Course;
import com.suwyn.suwyncoursetracker.R;

public class AllCourseDetail extends AppCompatActivity {
    EditText editCourseName;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editStatus;
    EditText editInsName;
    EditText editInsEmail;
    EditText editInsPhone;
    EditText editNote;
    EditText editTermID;
    String name;
    String startDate;
    String endDate;
    int courseId;
    String status;
    String instructorEmail;
    String instructorName;
    String instructorPhone;
    String note;
    int termID;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course_detail);
        editCourseName = findViewById(R.id.editCourseName);
        editCourseStart=findViewById(R.id.editCourseStart);
        editCourseEnd=findViewById(R.id.editCourseEnd);
        editStatus=findViewById(R.id.editCourseStatus);
        editInsName=findViewById(R.id.editInsName);
        editInsEmail=findViewById(R.id.editInsEmail);
        editInsPhone=findViewById(R.id.editInsPhone);
        editNote=findViewById(R.id.editNote);
        editTermID=findViewById(R.id.editTermID);
        courseId=getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("Start Date");
        endDate=getIntent().getStringExtra("End Date");
        status=getIntent().getStringExtra("status");
        instructorName=getIntent().getStringExtra("insName");
        instructorEmail=getIntent().getStringExtra("insEmail");
        instructorPhone=getIntent().getStringExtra("insPhone");
        note=getIntent().getStringExtra("note");
        termID=getIntent().getIntExtra("termID", -1);
        editCourseName.setText(name);
        editCourseStart.setText(startDate);
        editCourseEnd.setText(endDate);
        editStatus.setText(status);
        editInsEmail.setText(instructorEmail);
        editInsName.setText(instructorName);
        editInsPhone.setText(instructorPhone);
        editNote.setText(note);
        editTermID.setText(Integer.toString(termID));
        repository = new Repository(getApplication());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveCourse(View view) {
        Course course;
        if(courseId==-1){
            int newID = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseID() + 1;
            course = new Course(newID, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editStatus.getText().toString(), editInsName.getText().toString(), editInsEmail.getText().toString(), editInsPhone.getText().toString(), editNote.getText().toString(), Integer.parseInt(editTermID.getText().toString()));
            repository.insert(course);
        }
        else {
            course = new Course(courseId, editCourseName.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editStatus.getText().toString(), editInsName.getText().toString(), editInsEmail.getText().toString(), editInsPhone.getText().toString(), editNote.getText().toString(), Integer.parseInt(editTermID.getText().toString()));
            repository.update(course);
        }
        Intent intent = new Intent(AllCourseDetail.this, AllCourseList.class);
        startActivity(intent);
    }
}