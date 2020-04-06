package com.example.manda.greenDao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.manda.Data.CourseInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COURSE_INFO".
*/
public class CourseInfoDao extends AbstractDao<CourseInfo, Long> {

    public static final String TABLENAME = "COURSE_INFO";

    /**
     * Properties of entity CourseInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CourseId = new Property(0, Long.class, "courseId", true, "_id");
    }


    public CourseInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CourseInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COURSE_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT );"); // 0: courseId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COURSE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CourseInfo entity) {
        stmt.clearBindings();
 
        Long courseId = entity.getCourseId();
        if (courseId != null) {
            stmt.bindLong(1, courseId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CourseInfo entity) {
        stmt.clearBindings();
 
        Long courseId = entity.getCourseId();
        if (courseId != null) {
            stmt.bindLong(1, courseId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CourseInfo readEntity(Cursor cursor, int offset) {
        CourseInfo entity = new CourseInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0) // courseId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CourseInfo entity, int offset) {
        entity.setCourseId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CourseInfo entity, long rowId) {
        entity.setCourseId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CourseInfo entity) {
        if(entity != null) {
            return entity.getCourseId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CourseInfo entity) {
        return entity.getCourseId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
