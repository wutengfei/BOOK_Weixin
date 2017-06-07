package com.book.manage.model;

/**
 * Created by lsy on 2016/7/14.
 */
public class Student {
    //属性
    private String studentNo; //学号
    private String studentName;
    private String studentMajor;
    private String studentClass;
    private String studentMobile;
    //构造方法
    public Student(String studentNo, String studentName, String studentMajor, String studentClass, String studentMobile) {
        super();
        this.studentNo = studentNo;
        this.studentName = studentName;
        this.studentMajor = studentMajor;
        this.studentClass = studentClass;
        this.studentMobile = studentMobile;
    }
    public Student(){};
    //getter and setter
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }




}
