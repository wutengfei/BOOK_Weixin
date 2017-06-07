package com.book.manage.ui.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.book.manage.R;
import com.book.manage.controller.StudentControl;
import com.book.manage.model.Student;

/**
 * Created by dell on 2017/6/5.
 */
public class StudentUpdate extends Activity {
    private TextView studentNo;
    private TextView studentName;
    private TextView studentMajor;
    private TextView studentClass;
    private TextView studentMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_update);
        //从StudentFragment跳转而来,获取上个界面传递的信息
        Intent intent = getIntent();
        String no = intent.getStringExtra("no");
        String name = intent.getStringExtra("name");
        String major = intent.getStringExtra("major");
        String _class = intent.getStringExtra("class");
        String phone = intent.getStringExtra("phone");
        //绑定textView控件
        studentNo = (TextView) findViewById(R.id.textView3);
        studentName = (TextView) findViewById(R.id.editText2);
        studentMajor = (TextView) findViewById(R.id.editText3);
        studentClass = (TextView) findViewById(R.id.editText4);
        studentMobile = (TextView) findViewById(R.id.editText5);
        //给控件设置内容
        studentNo.setText(no);
        studentName.setText(name);
        studentMajor.setText(major);
        studentClass.setText(_class);
        studentMobile.setText(phone);
    }

    public void sure(View view) {
        String no = studentNo.getText().toString().trim();
        String name = studentName.getText().toString().trim();
        String major = studentMajor.getText().toString().trim();
        String _class = studentClass.getText().toString().trim();
        String phone = studentMobile.getText().toString().trim();
        Student student = new Student(no, name, major, _class, phone);
        StudentControl studentControl = new StudentControl(this);
        studentControl.updataByNo(student);
        finish();
    }
}