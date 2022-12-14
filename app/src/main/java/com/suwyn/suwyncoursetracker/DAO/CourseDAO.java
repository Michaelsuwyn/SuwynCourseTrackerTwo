package com.suwyn.suwyncoursetracker.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.suwyn.suwyncoursetracker.Entity.Course;
import com.suwyn.suwyncoursetracker.Entity.Term;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE termID =:id ORDER BY courseID ASC")
    List<Course> getCourseByTermID(int id);

    @Query("SELECT * FROM courses WHERE courseID = :id")
    Course getSingleCourse(int id);
}
