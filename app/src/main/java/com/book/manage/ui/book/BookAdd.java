package com.book.manage.ui.book;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.book.manage.R;
import com.book.manage.controller.BookControl;
import com.book.manage.model.Book;

import java.util.Calendar;

public class BookAdd extends Activity {
    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_add);


        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);

        editText7 = (EditText) findViewById(R.id.editText7);
        final Calendar c = Calendar.getInstance();
        editText7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(BookAdd.this, new DatePickerDialog.OnDateSetListener() {

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

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new ButtonListener());
    }

    class ButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            String bookno = editText.getText().toString().trim();
            String bookname = editText2.getText().toString().trim();
            String author = editText3.getText().toString().trim();
            String publisher = editText4.getText().toString().trim();
            String totalnum = editText5.getText().toString().trim();
            String borrownum = editText6.getText().toString().trim();
            String pubday = editText7.getText().toString().trim();
            Book book = new Book(bookno, bookname, author, publisher, totalnum, borrownum, pubday);
            BookControl controlBook = new BookControl(BookAdd.this);

            if (bookno.equals("") || bookname.equals("") || author.equals("") || publisher.equals("") ||
                    totalnum.equals("") || borrownum.equals("") || bookname.equals("")) {
                new android.app.AlertDialog.Builder(BookAdd.this).setMessage("请填写完整").show();
            } else {
                if (controlBook.QueryOnByNo(bookno) != null) {//检测学生是否已存在
                    Toast.makeText(BookAdd.this, "该图书已存在", Toast.LENGTH_SHORT).show();
                } else {
                    if (controlBook.addBook(book)) {
                        editText.setText("");
                        editText2.setText("");
                        editText3.setText("");
                        editText4.setText("");
                        editText5.setText("");
                        editText6.setText("");
                        editText7.setText("");

                        buildDialog();
                    }
                }
            }
        }

        private void buildDialog() {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BookAdd.this);
            builder.setTitle("插入成功,是否继续插入");
            builder.setNegativeButton("返回上一页",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            finish();
                        }

                    });
            builder.setPositiveButton("继续插入", null);
            builder.show();
        }

    }

}
