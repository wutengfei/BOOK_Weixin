package com.book.manage.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DBAdapter {
    //学生
    private static final String DB_NAME = "student.db";
    private static final String DB_TABLE = "studentinfo";
    private static final int DB_version = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NO = "no";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAJOR = "major";
    public static final String KEY_CLASS = "class";
    public static final String KEY_MOBILE = "mobile";

    //图书

    private static final String DB_TABLE_BOOK = "bookinfo";

    public static final String KEY_BOOKNO = "bookno";
    public static final String KEY_BOOKNAME = "bookname";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_PUBLISHER = "publisher";
    public static final String KEY_TOTALNUM = "totalnum";
    public static final String KEY_BORROWNUM = "borrownum";
    public static final String KEY_PUBDAY = "pubday";

    //借阅表
    private static final String DB_TABLE_BORROW = "borrow";

    public static final String KEY_BORROW_DATE = "borrowDate";


    public SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //学生表
        private static final String DB_CREATE = "create table " +
                DB_TABLE + "(" + KEY_ID + " integer primary key autoincrement," +
                KEY_NO + " varchar(20)," + KEY_NAME + " varchar(20)," + KEY_CLASS + " varchar(20)," +
                KEY_MAJOR + " varchar(20)," + KEY_MOBILE + " varchar(20))";
        //图书表
        private static final String DB_CREATE_BOOK = "create table " +
                DB_TABLE_BOOK + "(" + KEY_ID + " integer primary key autoincrement," + KEY_BOOKNO + " varchar(20)," +
                KEY_BOOKNAME + " varchar(20)," + KEY_AUTHOR + " varchar(20)," + KEY_PUBLISHER + " varchar(20),"
                + KEY_TOTALNUM + " varchar(20)," + KEY_BORROWNUM + " varchar(20)," + KEY_PUBDAY + " varchar(20))";
        //借阅表
        private static final String DB_CREATE_BORROW = "create table " +
                DB_TABLE_BORROW + "(" + KEY_ID + " integer primary key autoincrement, " + KEY_NO + " varchar(20)," +
                KEY_BOOKNO + " varchar(20)," + KEY_BORROW_DATE + " varchar(20)," + KEY_MOBILE + " varchar(20))";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
            _db.execSQL(DB_CREATE_BOOK);
            _db.execSQL(DB_CREATE_BORROW);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE_BOOK);
            _db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE_BORROW);
            onCreate(_db);
        }

    }

    public DBAdapter(Context _context) {
        context = _context;
    }

    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_version);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /////////////////////////////////////学生
    public long insert(Student s1) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NO, s1.getStudentNo());
        newValues.put(KEY_NAME, s1.getStudentName());
        newValues.put(KEY_CLASS, s1.getStudentClass());
        newValues.put(KEY_MAJOR, s1.getStudentMajor());
        newValues.put(KEY_MOBILE, s1.getStudentMobile());
        return db.insert(DB_TABLE, null, newValues);
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneDataByNo(String no) {
        return db.delete(DB_TABLE, KEY_NO + " like ? ", new String[]{no});
    }

    public long updateOneDataByNo(String no, Student student) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NO, student.getStudentNo());
        updateValues.put(KEY_NAME, student.getStudentName());
        updateValues.put(KEY_CLASS, student.getStudentClass());
        updateValues.put(KEY_MAJOR, student.getStudentMajor());
        updateValues.put(KEY_MOBILE, student.getStudentMobile());

        return db.update(DB_TABLE, updateValues, KEY_NO + " like ? ", new String[]{no});

    }

    public Student[] getOneByNo(String no) {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NO, KEY_NAME, KEY_CLASS, KEY_MAJOR, KEY_MOBILE},
                KEY_NO + " like ? ", new String[]{no}, null, null, null, null);
        return ConvertToStudent(cursor);
    }

    public Student[] getAllStu() {
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NO, KEY_NAME, KEY_CLASS, KEY_MAJOR, KEY_MOBILE},
                null, null, null, null, KEY_NO+" asc");
        return ConvertToStudent(cursor);
    }

    private Student[] ConvertToStudent(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) return null;
        Student[] peoples = new Student[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            peoples[i] = new Student();
            peoples[i].setStudentNo(cursor.getString(cursor.getColumnIndex(KEY_NO)));
            peoples[i].setStudentName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            peoples[i].setStudentMajor(cursor.getString(cursor.getColumnIndex(KEY_MAJOR)));
            peoples[i].setStudentClass(cursor.getString(cursor.getColumnIndex(KEY_CLASS)));
            peoples[i].setStudentMobile(cursor.getString(cursor.getColumnIndex(KEY_MOBILE)));
            cursor.moveToNext();
        }
        return peoples;
    }


    //////////////////////////////////////图书
    public void insertBook(Book book) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_BOOKNO, book.getBookno());
        newValues.put(KEY_BOOKNAME, book.getBookname());
        newValues.put(KEY_AUTHOR, book.getAuthor());
        newValues.put(KEY_PUBLISHER, book.getPublisher());
        newValues.put(KEY_TOTALNUM, book.getTotalnum());
        newValues.put(KEY_BORROWNUM, book.getBorrownum());
        newValues.put(KEY_PUBDAY, book.getPubday());
        db.insert(DB_TABLE_BOOK, null, newValues);
    }

    public long deleteAllDataBook() {
        return (db.delete(DB_TABLE_BOOK, null, null));
    }

    public long deleteBookDatabyNo(String no) {
        return (db.delete(DB_TABLE_BOOK, KEY_BOOKNO + " like ? ", new String[]{no}));
    }

    //更新图书信息
    public long updateByBookno(String no, Book book) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_BOOKNO, book.getBookno());
        updateValues.put(KEY_BOOKNAME, book.getBookname());
        updateValues.put(KEY_AUTHOR, book.getAuthor());
        updateValues.put(KEY_PUBLISHER, book.getPublisher());
        updateValues.put(KEY_TOTALNUM, book.getTotalnum());
        updateValues.put(KEY_BORROWNUM, book.getBorrownum());
        updateValues.put(KEY_PUBDAY, book.getPubday());

        return db.update(DB_TABLE_BOOK, updateValues, KEY_BOOKNO + " like ? ", new String[]{no});

    }

    //查找学号
    public Book[] getOneByNoBook(String no) {
        Cursor cursor = db.query(DB_TABLE_BOOK, new String[]{KEY_BOOKNO, KEY_BOOKNAME,
                        KEY_AUTHOR, KEY_PUBLISHER, KEY_TOTALNUM, KEY_BORROWNUM, KEY_PUBDAY},
                KEY_BOOKNO + " like ? ", new String[]{no}, null, null, null, null);
        return ConvertToBook(cursor);
    }

    //查询书名，作者，日期等
    public Book[] getAttrBook(String attr, String book_attr) {//参数分别是bookinfo表中的字段名，用户输入的book的属性
        Cursor cursor = db.query(DB_TABLE_BOOK, new String[]{KEY_BOOKNO, KEY_BOOKNAME,
                        KEY_AUTHOR, KEY_PUBLISHER, KEY_TOTALNUM, KEY_BORROWNUM, KEY_PUBDAY},
                attr + " like ? ", new String[]{"%"+book_attr+"%"}, null, null, null, null);
        return ConvertToBook(cursor);
    }

    public Book[] getAllBook() {
        Cursor cursor = db.query(DB_TABLE_BOOK, new String[]{KEY_BOOKNO, KEY_BOOKNAME,
                KEY_AUTHOR, KEY_PUBLISHER, KEY_TOTALNUM, KEY_BORROWNUM, KEY_PUBDAY}, null, null, null, null,KEY_BOOKNO+ " asc");
        return ConvertToBook(cursor);
    }

    private Book[] ConvertToBook(Cursor cursor) {
        int resultCouunts = cursor.getCount();
        if (resultCouunts == 0 || !cursor.moveToFirst()) return null;
        Book[] books = new Book[resultCouunts];
        for (int i = 0; i < resultCouunts; i++) {
            books[i] = new Book();
            books[i].setBookno(cursor.getString(cursor.getColumnIndex(KEY_BOOKNO)));
            books[i].setBookname(cursor.getString(cursor.getColumnIndex(KEY_BOOKNAME)));
            books[i].setAuthor(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
            books[i].setPublisher(cursor.getString(cursor.getColumnIndex(KEY_PUBLISHER)));
            books[i].setTotalnum(cursor.getString(cursor.getColumnIndex(KEY_TOTALNUM)));
            books[i].setBorrownum(cursor.getString(cursor.getColumnIndex(KEY_BORROWNUM)));
            books[i].setPubday(cursor.getString(cursor.getColumnIndex(KEY_PUBDAY)));
            cursor.moveToNext();
        }
        return books;
    }

    ///////////////////////////////////借阅

    //借书
    public long insertBorrow(Borrow borrow) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NO, borrow.getStudentNo());
        newValues.put(KEY_BOOKNO, borrow.getBookno());
        newValues.put(KEY_BORROW_DATE, borrow.getBorrowDate());
        newValues.put(KEY_MOBILE, borrow.getStudentMobile());

        return db.insert(DB_TABLE_BORROW, null, newValues);
    }

    //还书
    public long deleteOneDataBorrow(String studentNo, String bookno) {
        return (db.delete(DB_TABLE_BORROW, KEY_NO + " like ? " + " and " + KEY_BOOKNO
                + " like ? ", new String[]{studentNo, bookno}));
    }

    //查找学号或图书编号
    public Borrow[] getNoOrBookno(String KEY_NO_OR_BOOKNO, String no) {
        Cursor cursor = db.query(DB_TABLE_BORROW, new String[]{KEY_NO, KEY_BOOKNO, KEY_BORROW_DATE,KEY_MOBILE},
                KEY_NO_OR_BOOKNO + " like ? ", new String[]{no}, null, null, null, null);
        return ConvertToBorrow(cursor);
    }

    //查找学号或图书编号
    public Borrow[] isBorrowed(String no, String bookno) {
        Cursor cursor = db.query(DB_TABLE_BORROW, new String[]{KEY_NO, KEY_BOOKNO, KEY_BORROW_DATE,KEY_MOBILE},
                KEY_NO + " like ? " + " and " + KEY_BOOKNO + " like ? ", new String[]{no, bookno}, null, null, null, null);
        return ConvertToBorrow(cursor);
    }

    //把cursor中的内容封装成Borrow的数组
    private Borrow[] ConvertToBorrow(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) return null;
        Borrow[] borrows = new Borrow[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            borrows[i] = new Borrow();
            borrows[i].setStudentNo(cursor.getString(cursor.getColumnIndex(KEY_NO)));
            borrows[i].setBookno(cursor.getString(cursor.getColumnIndex(KEY_BOOKNO)));
            borrows[i].setBorrowDate(cursor.getString(cursor.getColumnIndex(KEY_BORROW_DATE)));
            borrows[i].setStudentMobile(cursor.getString(cursor.getColumnIndex(KEY_MOBILE)));
            cursor.moveToNext();
        }
        return borrows;
    }

    // 查询所有借阅信息
    public Borrow[] getAllBorrow() {
        Cursor cursor = db.query(DB_TABLE_BORROW, new String[]{KEY_NO, KEY_BOOKNO, KEY_BORROW_DATE,KEY_MOBILE},
                null, null, null, null, null);
        return ConvertToBorrow(cursor);
    }

}