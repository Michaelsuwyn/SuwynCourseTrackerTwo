package com.suwyn.suwyncoursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.suwyn.suwyncoursetracker.Database.Repository;
import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {

    EditText editAssessmentType;
    EditText editAssessmentName;
    EditText editAssessmentDescription;
    EditText editCourseID;
    EditText editStartDate;
    EditText editEndDate;
    String type;
    String name;
    String description;
    String startDate;
    String endDate;
    int assessmentID;
    int courseID;
    Repository repository;
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        editAssessmentType = findViewById(R.id.editType);
        editAssessmentName = findViewById(R.id.editName);
        editAssessmentDescription = findViewById(R.id.editDescription);
        editCourseID = findViewById(R.id.editCourseID);
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        assessmentID = getIntent().getIntExtra("id", -1);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        courseID = getIntent().getIntExtra("courseID", -1);
        editAssessmentType.setText(type);
        editAssessmentName.setText(name);
        editAssessmentDescription.setText(description);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        editCourseID.setText(Integer.toString(courseID));
        repository = new Repository(getApplication());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveAssessment(View view) {
        Assessment assessment;
        if(assessmentID == -1){
            int newID = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentID() + 1;
            assessment = new Assessment(newID, editAssessmentType.getText().toString(), editAssessmentName.getText().toString(), editAssessmentDescription.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), Integer.parseInt(editCourseID.getText().toString()));
            repository.insert(assessment);
        }
        else{
            assessment = new Assessment(assessmentID, editAssessmentType.getText().toString(), editAssessmentName.getText().toString(), editAssessmentDescription.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), Integer.parseInt(editCourseID.getText().toString()));
            repository.update(assessment);
        }
        Intent intent = new Intent(AssessmentDetail.this, AssessmentList.class);
        startActivity(intent);
    }

    public void deleteAssessment(View view) {
        Assessment toDelete = repository.getSingleAssessment(assessmentID);
        repository.delete(toDelete);
        Intent intent = new Intent(AssessmentDetail.this, AssessmentList.class);
        startActivity(intent);
    }

    public void setAlert(View view) {
        Intent intent = new Intent(AssessmentDetail.this, AlertAndNotify.class);
        startActivity(intent);
    }

    public boolean alertStart(View view) {
        String dateFromScreen = editStartDate.getText().toString();
        Date myDate = null;
        try {
            myDate=sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(AssessmentDetail.this, MyReceiver.class);
        intent.putExtra("key", "Start Date for Assessment: " + editAssessmentName.getText().toString());
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        return true;
    }

    public boolean alertEnd(View view) {
        String dateFromScreen = editEndDate.getText().toString();
        Date myDate = null;
        try {
            myDate=sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = myDate.getTime();
        Intent intent = new Intent(AssessmentDetail.this, MyReceiver.class);
        intent.putExtra("key", "End Date for Assessment: " + editAssessmentName.getText().toString());
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        return true;
    }
}