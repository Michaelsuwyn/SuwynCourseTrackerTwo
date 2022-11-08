package com.suwyn.suwyncoursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.suwyn.suwyncoursetracker.Database.Repository;
import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.Entity.Course;
import com.suwyn.suwyncoursetracker.Entity.Term;
import com.suwyn.suwyncoursetracker.R;

public class MainActivity extends AppCompatActivity {

    public static int mainTermID;
    public static int mainCourseID;
    public static int numAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateData();

    }

    public void enterHere(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
    }

    public void viewCourses(View view) {
        Intent intent = new Intent(MainActivity.this, AllCourseList.class);
        startActivity(intent);
    }

    public void viewAssessments(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentList.class);
        startActivity(intent);
    }

    public void toAlertNotify(View view) {
        Intent intent = new Intent(MainActivity.this, AlertAndNotify.class);
        startActivity(intent);
    }

    public void populateData(){
        Repository repo = new Repository(getApplication());
        Term term = new Term(1, "Test Term One", "10/31/22", "04/31/22");
        Term termTwo = new Term(2, "Test Term Two", "10/31/22", "04/31/22");
        Course course = new Course(1, "Course One", "10/31/22", "12/31/22","In Progress","Test Instructor", "test@test.com", "4809999122", "Optional note", 1);
        Course course2 = new Course(2, "Course two", "10/31/22", "12/31/22","In Progress","Test Instructor", "test@test.com", "4809999122", "Optional note", 2);
        Course course3 = new Course(3, "Course three", "10/31/22", "12/31/22","In Progress","Test Instructor", "test@test.com", "4809999122", "Optional note", 1);
        Assessment assessment1 = new Assessment(1, "Performance", "PA1", "test", "11/01/22", "02/01/23", 1);
        Assessment assessment2 = new Assessment(2, "Performance", "PA2", "test", "11/01/22", "02/01/23", 2);
        repo.insert(term);
        repo.insert(termTwo);
        repo.insert(course);
        repo.insert(course2);
        repo.insert(course3);
        repo.insert(assessment1);
        repo.insert(assessment2);
    }
}