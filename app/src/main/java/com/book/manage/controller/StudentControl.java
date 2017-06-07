package com.book.manage.controller;

import android.content.Context;

import com.book.manage.model.DBAdapter;
import com.book.manage.model.Student;
import com.book.manage.model.StudentSet;

/**
 * Created by Administrator on 2016/7/16.
 */
public class StudentControl {
    private static DBAdapter dbAdapter;
    private static StudentSet studentSet;
    Context context;

    public StudentControl(Context context) {
        this.context = context;
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();

    }

    public boolean addStudent(Student s1) {
        return dbAdapter.insert(s1) > 0;
    }

    public void saveAll() {
        studentSet = StudentSet.getStudentList();
        studentSet.readFile(context);
        //使用事务可以极大地加快插入速度，不再是一条一条插入，而是暂存在缓存区最后一起插入数据库
        dbAdapter.db.beginTransaction();//开启事务
        for (int i = 0; i < studentSet.size(); i++) {
            dbAdapter.insert(studentSet.get(i));
        }
        dbAdapter.db.setTransactionSuccessful();// 设置事务标志为成功，当结束事务时就会提交事务
        dbAdapter.db.endTransaction();// 结束事务

    }

    public long deleteAll() {
       return dbAdapter.deleteAllData();
    }

    public boolean deleteStudentByNo(String no) {
        Student s[] = dbAdapter.getOneByNo(no);
        if (s != null) {
            dbAdapter.deleteOneDataByNo(no);

            return true;
        }
        return false;
    }

    public void updataByNo(Student student) {
        String no = student.getStudentNo();
        if (QueryOnByNo(no) != null) {
            dbAdapter.updateOneDataByNo(no, student);
        }
    }

    public Student[] QueryOnByNo(String no) {
        return dbAdapter.getOneByNo(no);
    }

    public Student[] getAllStudent() {
        return dbAdapter.getAllStu();
    }
}
