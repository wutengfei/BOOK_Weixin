package com.book.manage.model;

import android.content.Context;
import com.book.manage.R;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Fly Wu on 2016/7/19.
 */
public class BookSet extends ArrayList<Book> {
    private static BookSet bookList = null;//定义存储唯一学生集合类的引用变量

    private BookSet() {//封装构造函数
    }
//静态成员方法，单例模式，

    public static BookSet getBookList() {//用静态函数生成集合对象
        if(bookList==null) bookList = new BookSet();
        return bookList;
    }

    //从文件读取信息，并将读取的信息转换成学生类的对象，存储到容器arraylist中
    public static boolean readFile(Context io) {
        // File file1=new File("d:/javatest/stu1.txt");
        try {
            InputStream in = io.getResources().openRawResource(R.raw.book1);

            //把字节流转换成字符流并设置编码为国标码
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
            String temp;
            while ((temp = br.readLine()) != null) {
                //读取txt文件里的内容
                String[] s = temp.split(" ");
                String bookno = s[0];
                String bookname = s[1];
                String Author = s[2];
                String publisher = s[3];
                String totalnum = s[4];
                String borrownum = s[5];
                String pubdayYear = s[6];
                String pubdayMonth = s[7];
                String pubdayDay = s[8];
                String pubday = pubdayYear + "-" + pubdayMonth + "-" + pubdayDay;

                Book book = new Book(bookno, bookname, Author, publisher, totalnum, borrownum, pubday);
                bookList.add(book);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("读取成功！");
        return true;
    }
    //选择文件读取数据
    public  boolean insertFile(File file) throws IOException {

        try {
            bookList.clear();
            FileInputStream fis = new FileInputStream(file);

            //把字节流转换成字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"gb2312"));

            while (br != null) {
                //读取txt文件里的内容
                String text = br.readLine();

                String[] s = text.split(" ");
                String bookno = s[0];
                String bookname = s[1];
                String author = s[2];
                String publisher = s[3];
                String totalnum = s[4];
                String borrownum = s[5];
                String pubdayYear = s[6];
                String pubdayMonth = s[7];
                String pubdayDay = s[8];
                String pubday = pubdayYear + "-" + pubdayMonth + "-" + pubdayDay;
                Book student = new Book(bookno, bookname, author, publisher, totalnum, borrownum, pubday);
                bookList.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //从文件读取学生信息，并将读取的信息转换成学术类的对象，存储到容器arraylist中
    public boolean writeFile() {
        Book s;
        String str;
        boolean b = false;
        File file1 = new File(".res/book1.txt");
        try {
            FileInputStream fis = new FileInputStream(file1);

            //把字节流转换成字符流
            // BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            //  BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"gb2312"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file1)), true);
            for (int i = 0; i < this.size(); i++) {
                s = (Book) this.get(i);
                str = s.getBookno() + " " + s.getBookname() + " " + s.getAuthor() + " " + s.getPublisher() + " "
                        + s.getTotalnum() + " " + s.getBorrownum() + " " + s.getPubday();
                pw.println(str);
            }
            pw.close();
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("保存成功！");
        return b;
    }


    public void ListInsert_S(Book e)   //向学生表中插入对象
    {
        this.add(e);
    }

    public void ListDelete_S(String info)  //删除学生表元素
    {
        Book s1;
        boolean b = false;
        for (int i = 0; i < this.size(); i++) {
            s1 = (Book) this.get(i);
            if ((s1.getBookno().equals(info)) || (s1.getBookname().equals(info)) || (s1.getAuthor().equals(info)) ||
                    (s1.getPublisher().equals(info)) || (s1.getTotalnum().equals(info)) || (s1.getBorrownum().equals(info))
                    || (s1.getPubday().equals(info))) {
                this.remove(i);
            }

        }
    }

    public int ListLength_S()   //获取容器大小
    {
        return this.size();
    }

    public ArrayList<Book> stu_search_char(String info) //查询学生信息
    {
        Book s1;
        ArrayList<Book> s = new ArrayList<Book>();
        int i = 0;
        for (int j = 0; j < this.size(); j++) {
            s1 = (Book) this.get(j);
            if ((s1.getBookno().equals(info)) || (s1.getBookname().equals(info)) || (s1.getAuthor().equals(info)) ||
                    (s1.getPublisher().equals(info)) || (s1.getTotalnum().equals(info)) || (s1.getBorrownum().equals(info))
                    || (s1.getPubday().equals(info))) {
                s.add(s1);
                i++;
            }
        }

        if (i != 0) {
            System.out.println("共找到" + i + "个符合条件的人员");
            return s;
        } else {
            System.out.println("没有找到符合条件的学生!");
            return null;
        }
    }

}
