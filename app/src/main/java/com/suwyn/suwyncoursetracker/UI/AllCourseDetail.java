package com.suwyn.suwyncoursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.suwyn.suwyncoursetracker.Database.Repository;
import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.Entity.Course;
import com.suwyn.suwyncoursetracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


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

    public void relatedAssessments(View view) {
        MainActivity.mainCourseID = courseId;
        Intent intent = new Intent(AllCourseDetail.this, AssessmentList.class);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        Course toDelete = repository.getSingleCourse(courseId);
        repository.delete(toDelete);
        Intent intent = new Intent(AllCourseDetail.this, AllCourseList.class);
        startActivity(intent);
    }


    public boolean alertStart(View view) {
        String dateFromScreen = editCourseStart.getText().toString();
        Date myDate = null;
        try {
            myDate=sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(AllCourseDetail.this, MyReceiver.class);
        intent.putExtra("key", "Start Date for Course: " + editCourseName.getText().toString());
        PendingIntent sender = PendingIntent.getBroadcast(AllCourseDetail.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        return true;

    }

    public boolean alertEnd(View view) {
        String dateFromScreen = editCourseEnd.getText().toString();
        Date myDate = null;
        try {
            myDate=sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(AllCourseDetail.this, MyReceiver.class);
        intent.putExtra("key", "End Date for Course: " + editCourseName.getText().toString());
        PendingIntent sender = PendingIntent.getBroadcast(AllCourseDetail.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        return true;
    }

    public boolean shareNote(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Note from " + editCourseName.getText().toString());
        sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
        return true;
    }
}