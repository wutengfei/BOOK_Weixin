package com.book.manage.ui.book;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.book.manage.R;
import com.book.manage.controller.BookControl;
import com.book.manage.model.Book;

import java.util.Calendar;

public class BookUpdate extends Activity {
    private TextView textView1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_update);
        //从StudentFragment跳转而来,获取上个界面传递的信息
        Intent intent = getIntent();
        String bookno = intent.getStringExtra("bookno");
        String bookname = intent.getStringExtra("bookname");
        String author = intent.getStringExtra("author");
        String publisher = intent.getStringExtra("publisher");
        String totalnum = intent.getStringExtra("totalnum");
        String borrownum = intent.getStringExtra("borrownum");
        String pubday = intent.getStringExtra("pubday");
        //绑定textView控件
        textView1 = (TextView) findViewById(R.id.textView1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);

        //给控件设置内容
        textView1.setText(bookno);
        editText2.setText(bookname);
        editText3.setText(author);
        editText4.setText(publisher);
        editText5.setText(totalnum);
        editText6.setText(borrownum);
        editText7.setText(pubday);

        final Calendar c = Calendar.getInstance();
        editText7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(BookUpdate.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        editText7.setText(DateFormat.format("yyyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        Button insert = (Button) findViewById(R.id.update);
        insert.setOnClickListener(new ButtonListener());
    }


    class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String bookno = textView1.getText().toString().trim();
            String bookname = editText2.getText().toString().trim();
            String author = editText3.getText().toString().trim();
            String publisher = editText4.getText().toString().trim();
            String totalnum = editText5.getText().toString().trim();
            String borrownum = editText6.getText().toString().trim();
            String pubday = editText7.getText().toString().trim();
            Book book = new Book(bookno, bookname, author, publisher, totalnum, borrownum, pubday);
            BookControl controlBook = new BookControl(BookUpdate.this);
            controlBook.updataByNo(book);
            finish();
        }
    }
}
