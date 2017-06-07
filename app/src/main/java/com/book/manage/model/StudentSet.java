package com.book.manage.model;

/**
 * Created by lsy on 2016/7/14.
 */

import android.content.Context;
import com.book.manage.R;

import java.io.*;
import java.util.ArrayList;

public class StudentSet extends ArrayList<Student> {
    private static StudentSet studentList = null;//声明静态的单例对象的变量

    private StudentSet() {
    }//封装构造函数

    //静态成员方法，外部通过此方法可以获取对象
    public static StudentSet getStudentList() {//用静态函数生成集合对象
        if (studentList == null) {
            synchronized (StudentSet.class) { //保证了同一时间只能只能有一个对象访问此同步块
                if (studentList == null)
                    studentList = new StudentSet();
            }
        }
        return studentList;
    }

    //从文件读取学生信息，并将读取的信息转换成学生类的对象，存储到容器arraylist中
    public static boolean readFile(Context io) {
        // File file1=new File("d:/javatest/stu1.txt");
        try {
            studentList.clear();
            InputStream in = io.getResources().openRawResource(R.raw.student1);

            //把字节流转换成字符流并设置编码为国标码
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
            String temp;
            while ((temp = br.readLine()) != null) {
                //读取txt文件里的内容
                String[] s = temp.split(" ");
                String studentNo = s[0];
                String studentName = s[1];
                String studentMajor = s[2];
                String studentClass = s[3];
                String studentMobile = s[4];
                Student student = new Student(studentNo, studentName, studentMajor, studentClass, studentMobile);
                studentList.add(student);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("读取成功！");
        return true;
    }


    //从文件读取学生信息，并将读取的信息转换成学术类的对象，存储到容器arraylist中
    public boolean writeFile() {
        Student s;
        String str;
        boolean b = false;
        File file1 = new File(".res/stu1.txt");
        try {
            FileInputStream fis = new FileInputStream(file1);

            //把字节流转换成字符流
            // BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            //  BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file1),"gb2312"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file1)), true);
            for (int i = 0; i < this.size(); i++) {
                s = (Student) this.get(i);
                str = s.getStudentNo() + " " + s.getStudentName() + " " + s.getStudentMajor() + " " + s.getStudentClass() + " " + s.getStudentMobile();
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


    public void ListInsert_S(Student e)   //向学生表中插入对象
    {
        this.add(e);
    }

    public void ListDelete_S(String info)  //删除学生表元素
    {
        Student s1;
        boolean b = false;
        for (int i = 0; i < this.size(); i++) {
            s1 = (Student) this.get(i);
            if ((s1.getStudentMajor().equals(info)) || (s1.getStudentMobile().equals(info)) ||
                    (s1.getStudentName().equals(info)) || (s1.getStudentNo().equals(info))) {
                this.remove(i);
            }

        }
    }

    public int ListLength_S()   //获取容器大小
    {
        return this.size();
    }

    public ArrayList<Student> stu_search_char(String info) //查询学生信息
    {
        Student s1;
        ArrayList<Student> s = new ArrayList<Student>();
        int i = 0;
        for (int j = 0; j < this.size(); j++) {
            s1 = (Student) this.get(j);
            if ((s1.getStudentMajor().equals(info)) || (s1.getStudentMobile().equals(info)) ||
                    (s1.getStudentName().equals(info)) || (s1.getStudentNo().equals(info))) {
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

    public void outputAll() {
        Student s;
        for (int i = 0; i < this.size(); i++) {
            s = (Student) this.get(i);
            System.out.println("姓名：" + s.getStudentName() + "   学号：" + s.getStudentNo());
        }
    }

}
