package com.example.manda.Data;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CourseInfo {
    @Id(autoincrement = true)
    private Long courseId;

    @Generated(hash = 1075262965)
    public CourseInfo(Long courseId) {
        this.courseId = courseId;
    }

    @Generated(hash = 1849777725)
    public CourseInfo() {
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
