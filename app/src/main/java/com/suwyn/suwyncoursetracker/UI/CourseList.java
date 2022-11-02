package com.suwyn.suwyncoursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.suwyn.suwyncoursetracker.Database.Repository;
import com.suwyn.suwyncoursetracker.Entity.Term;
import com.suwyn.suwyncoursetracker.R;

public class CourseList extends AppCompatActivity {

    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;
    String name;
    String startDate;
    String endDate;
    int termId;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        editTermName=findViewById(R.id.editTermName);
        editTermStart=findViewById(R.id.editTermStart);
        editTermEnd=findViewById(R.id.editTermEnd);
        termId=getIntent().getIntExtra("id", -1);
        name=getIntent().getStringExtra("Name");
        startDate=getIntent().getStringExtra("Start Date");
        endDate=getIntent().getStringExtra("End Date");
        editTermName.setText(name);
        editTermStart.setText(startDate);
        editTermEnd.setText(endDate);
        repository= new Repository(getApplication());
    }

    public void saveButton(View view) {
        Term term;
        if(termId == -1){
            int newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newID, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.insert(term);
        }
        else {
            term = new Term(termId, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.update(term);
        }
    }
}