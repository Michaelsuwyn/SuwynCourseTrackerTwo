package com.suwyn.suwyncoursetracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.suwyn.suwyncoursetracker.DAO.AssessmentDAO;
import com.suwyn.suwyncoursetracker.DAO.CourseDAO;
import com.suwyn.suwyncoursetracker.DAO.TermDAO;
import com.suwyn.suwyncoursetracker.Entity.Assessment;
import com.suwyn.suwyncoursetracker.Entity.Course;
import com.suwyn.suwyncoursetracker.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 5, exportSchema = false)
public abstract class CourseTrackerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile CourseTrackerDatabaseBuilder INSTANCE;

    static CourseTrackerDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE==null){
        synchronized (CourseTrackerDatabaseBuilder.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CourseTrackerDatabaseBuilder.class, "myCourseTrackerDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
