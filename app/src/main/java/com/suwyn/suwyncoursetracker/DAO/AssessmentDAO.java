package com.suwyn.suwyncoursetracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.Entity.Course;
import com.suwyn.suwyncoursetracker.Entity.Term;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseID =:id ORDER BY assessmentID ASC")
    List<Assessment> getAssessmentByCourseID(int id);

    @Query("SELECT * FROM assessments WHERE assessmentID = :id")
    Assessment getSingleAssessment(int id);
}
