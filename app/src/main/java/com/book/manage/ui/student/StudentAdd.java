package com.book.manage.ui.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.book.manage.R;
import com.book.manage.controller.StudentControl;
import com.book.manage.model.Student;

/**
 * Created by dell on 2017/6/6.
 */
public class StudentAdd extends Activity {
    private EditText studentNo;
    private EditText studentName;
    private EditText studentMajor;
    private EditText studentClass;
    private EditText studentMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_add);
        studentNo = (EditText) findViewById(R.id.editText);
        studentName = (EditText) findViewById(R.id.editText6);
        studentMajor = (EditText) findViewById(R.id.editText7);
        studentClass = (EditText) findViewById(R.id.editText8);
        studentMobile = (EditText) findViewById(R.id.editText9);
    }

    public void sure(View view) {
        String no = studentNo.getText().toString().trim();
        String name = studentName.getText().toString().trim();
        String major = studentMajor.getText().toString().trim();
        String _class = studentClass.getText().toString().trim();
        String phone = studentMobile.getText().toString().trim();
        Student student = new Student(no, name, major, _class, phone);
        StudentControl studentControl = new StudentControl(this);


        if (no.equals("") || name.equals("") || major.equals("") || _class.equals("") || phone.equals("")) {
            new AlertDialog.Builder(this).setMessage("不能有空行").show();
        } else {
            if (studentControl.QueryOnByNo(no) != null) {
                Toast.makeText(this, "该学生已经存在", Toast.LENGTH_SHORT).show();
            } else {
                if (studentControl.addStudent(student)) {
                    studentNo.setText("");
                    studentName.setText("");
                    studentMajor.setText("");
                    studentClass.setText("");
                    studentMobile.setText("");
                    buildDialog();
                } else
                    Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("插入成功，是否继续插入");
        builder.setNegativeButton("返回上一页", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        builder.setPositiveButton("继续插入", null);
        builder.show();
    }
}