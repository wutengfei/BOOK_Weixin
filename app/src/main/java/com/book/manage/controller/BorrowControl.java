package com.book.manage.controller;

import android.content.Context;
import com.book.manage.model.Book;
import com.book.manage.model.Borrow;
import com.book.manage.model.DBAdapter;

/**
 * Created by Fly Wu on 2016/7/20.
 */
public class BorrowControl {
    private static DBAdapter stuDB;
    Context context;

    public BorrowControl(Context context) {
        this.context = context;
        stuDB = new DBAdapter(context);
        stuDB.open();
    }

    //借书
    public boolean addBorrow(Borrow borrow) {
        stuDB.insertBorrow(borrow);

        return true;
    }

    //还书
    public boolean deleteBorrow(String studentNo, String bookno) {
        String KEY_NO = "no";
        String KEY_BOOKNO = "bookno";
        Borrow borrows[] = stuDB.getNoOrBookno(KEY_NO, studentNo);//查询学生是否存在
        if (borrows != null) {
            Borrow borrow[] = stuDB.getNoOrBookno(KEY_BOOKNO, bookno);//查询图书是否存在
            if (borrow != null) {
                stuDB.deleteOneDataBorrow(studentNo, bookno);
                return true;
            } else return false;
        } else return false;
    }

    //查看所有借阅信息
    public Borrow[] getAllBorrow() {
        return stuDB.getAllBorrow();
    }


    //按图书编号查找借阅的学生学号和借阅日期 或 按学号查找图书编号和借阅日期
    public Borrow[] getBorrowMessage(String KEY_NoOrBookno, String NoOrBookno) {
        return stuDB.getNoOrBookno(KEY_NoOrBookno, NoOrBookno);
    }

    //同一本书是否被该学生借阅过
    public int isBorrowed(String studentNo,String bookno) {
        Borrow borrow[] = stuDB.isBorrowed(studentNo, bookno);
        if (borrow == null)
            return 0;
        else
            return borrow.length;
    }
  //修改图书剩余量
    public void updateBorrow(Book book){

        String no = book.getBookno();
       stuDB.updateByBookno(no,book);
    }
}
