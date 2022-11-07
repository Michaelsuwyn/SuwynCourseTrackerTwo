package com.suwyn.suwyncoursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.suwyn.suwyncoursetracker.Database.Repository;
import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.R;

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
}