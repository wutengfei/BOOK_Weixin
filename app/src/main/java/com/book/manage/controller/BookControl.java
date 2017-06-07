package com.book.manage.controller;

import android.content.Context;

import com.book.manage.model.Book;
import com.book.manage.model.BookSet;
import com.book.manage.model.DBAdapter;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fly Wu on 2016/7/19.
 */
public class BookControl {
    private static DBAdapter dbAdapter;
    private static BookSet set;
    Context context;

    public BookControl(Context context) {
        this.context = context;
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();

    }

    //添加单本书
    public boolean addBook(Book s1) {
        dbAdapter.insertBook(s1);

        return true;
    }

    //保存所有初始图书信息
    public void saveAll() {
        set = BookSet.getBookList();
        set.clear();
        set.readFile(context);
        //使用事务可以极大地加快插入速度，不再是一条一条插入，而是暂存在缓存区最后一起插入数据库
        dbAdapter.db.beginTransaction();//开启事务
        for (int i = 0; i < set.size(); i++) {
            dbAdapter.insertBook(set.get(i));
        }
        dbAdapter.db.setTransactionSuccessful();//设置事务标志为成功，当结束事务时就会提交事务
        dbAdapter.db.endTransaction();//结束事务

    }

    //读文件，批量插入
    public boolean insertFile(File file) throws IOException {

        set = BookSet.getBookList();
        set.insertFile(file);
        dbAdapter.db.beginTransaction();//开启事务
        for (int i = 0; i < set.size(); i++) {
            dbAdapter.insertBook(set.get(i));
        }
        dbAdapter.db.setTransactionSuccessful();//设置事务标志为成功，当结束事务时就会提交事务
        dbAdapter.db.endTransaction();//结束事务

        return true;

    }

    //删除所有图书
    public long deleteAll() {
       return dbAdapter.deleteAllDataBook();
    }

    //删除单本书
    public boolean deleteBookByNo(String no) {
        Book s[] = dbAdapter.getOneByNoBook(no);
        if (s != null) {
            dbAdapter.deleteBookDatabyNo(no);

            return true;
        }
        return false;
    }

    //修改图书信息
    public boolean updataByNo(Book e) {
        String no = e.getBookno();
        if (QueryOnByNo(no) != null) {
            dbAdapter.updateByBookno(no, e);
            return true;
        } else
            return false;
    }

    //查询书号
    public Book[] QueryOnByNo(String no) {
        Book[] books = dbAdapter.getOneByNoBook(no);
        return books;

    }

    //查询（书名，作者，出版社，库存等）
    public Book[] getAttrBook(String attr, String book_attr) {//参数分别是bookinfo表中的字段名，用户输入的book的属性
        return dbAdapter.getAttrBook(attr, book_attr);
    }

    //查询所有图书
    public Book[] getAllBook() {
        return dbAdapter.getAllBook();
    }
}
