package com.book.manage.model;

/**
 * Created by Fly Wu on 2016/7/5.
 */
public class Book {
    private String bookno;//书号
    private String bookname;//书名
    private String author;//作者
    private String publisher;//出版社
    private String totalnum;//总存货量
    private String borrownum; //借出数量
    private String pubday; //出版日期

    public Book(String bookno, String bookname, String author, String publisher, String totalnum, String borrownum, String pubday) {
        this.bookno = bookno;
        this.bookname = bookname;
        this.author = author;
        this.publisher = publisher;
        this.totalnum = totalnum;
        this.borrownum = borrownum;
        this.pubday = pubday;
    }

    public Book() {
    }

    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getBorrownum() {
        return borrownum;
    }

    public void setBorrownum(String borrownum) {
        this.borrownum = borrownum;
    }

    public String getPubday() {
        return pubday;
    }

    public void setPubday(String pubday) {
        this.pubday = pubday;
    }
}
