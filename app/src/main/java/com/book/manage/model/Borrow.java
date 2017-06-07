package com.book.manage.model;

/**
 * Created by Fly Wu on 2016/7/20.
 */
public class Borrow  {
    private String studentNo;
    private String bookno;
    private String borrowDate;
    private String studentMobile;


    public Borrow() {
    }

    public Borrow(String studentNo, String bookno, String borrowDate, String studentMobile) {
        this.studentNo = studentNo;
        this.bookno = bookno;
        this.borrowDate = borrowDate;
        this.studentMobile = studentMobile;
    }

    public Borrow(String studentNo, String borrowDate, String bookno) {
        this.studentNo = studentNo;
        this.borrowDate = borrowDate;
        this.bookno = bookno;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }
}
