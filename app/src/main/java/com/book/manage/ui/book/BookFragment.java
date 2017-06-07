package com.book.manage.ui.book;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.book.manage.R;
import com.book.manage.controller.BookControl;
import com.book.manage.model.Book;

import java.util.ArrayList;
import java.util.HashMap;

public class BookFragment extends Fragment implements SearchView.OnQueryTextListener {

    public ListView mListView;
    BookControl bookControl;
    SimpleAdapter adapter;
    SearchView searchView;
    Book[] book;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bookControl = new BookControl(getActivity());
        if (bookControl.getAllBook() == null) {//初始时保存学生信息
            bookControl.saveAll();
        }
        View v = inflater.inflate(R.layout.fragment_book, null);
        mListView = (ListView) v.findViewById(R.id.lv);
        searchView = (SearchView) v.findViewById(R.id.searchView);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(true);
        // 为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(this);


        final ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();//用来存放学生对象
        book = bookControl.getAllBook();
        if (book != null)
            for (int i = 0; i < book.length; i++) {
                HashMap<String, Object> item = new HashMap<String, Object>();//用来存放学生属性
                item.put("bookno", book[i].getBookno());
                item.put("bookname", book[i].getBookname());
                item.put("author", book[i].getAuthor());
                item.put("publisher", book[i].getPublisher());
                item.put("totalnum", book[i].getTotalnum());
                item.put("borrownum", book[i].getBorrownum());
                item.put("pubday", book[i].getPubday());
                data.add(item);
            }
        //适配器
        adapter = new SimpleAdapter(getActivity(), data, R.layout.book_listview,
                new String[]{"bookno", "bookname", "author", "publisher", "totalnum", "borrownum", "pubday"},
                new int[]{R.id.tv_no, R.id.tv_name, R.id.tv_author, R.id.tv_publisher, R.id.tv_totalnum,
                        R.id.tv_borrownum, R.id.tv_pubday});
        //绑定适配器
        mListView.setAdapter(adapter);

        //设置ListView的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("--------点击的是-----" + parent.getAdapter().getItem(position).toString());
                ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
                dataList.add((HashMap<String, Object>) parent.getAdapter().getItem(position));//把搜索结果放到一个list中（配合搜索使用）
                //获取点击条目的内容
                String bookno = dataList.get(0).get("bookno") + "";
                String bookname = dataList.get(0).get("bookname") + "";
                String author = dataList.get(0).get("author") + "";
                String publisher = dataList.get(0).get("publisher") + "";
                String totalnum = dataList.get(0).get("totalnum") + "";
                String borrownum = dataList.get(0).get("borrownum") + "";
                String pubday = dataList.get(0).get("pubday") + "";
                //把信息带到下一个界面
                Intent intent = new Intent(getActivity(), BookUpdate.class);

                intent.putExtra("bookno", bookno);//把no传递到下一个界面
                intent.putExtra("bookname", bookname);
                intent.putExtra("author", author);
                intent.putExtra("publisher", publisher);
                intent.putExtra("totalnum", totalnum);
                intent.putExtra("borrownum", borrownum);
                intent.putExtra("pubday", pubday);

                startActivity(intent);

            }
        });
        //listView长按事件
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String no = data.get(position).get("bookno") + "";
                        if (data.remove(position) != null) {

                            bookControl.deleteBookByNo(no);
                            System.out.println("success");
                        } else {
                            System.out.println("failed");
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.create().show();
                return true;
            }
        });

    }


    /**
     * 点击输入法的搜索时触发
     *
     * @param query
     * @return true-点击输入法上的搜索无变化   false-点击搜索时输入法消失
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * 输入文字时实时刷新列表
     *
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<HashMap<String, Object>> obj = searchItem(newText);
        updateLayout(obj);
        return false;
    }

    /**
     * 搜索主要逻辑。数据库中book的属性和输入框中输入的文字一致就存放到新ArrayList
     *
     * @param name 输入框中输入的文字
     * @return dataList  搜索结果存放的ArrayList
     */
    public ArrayList<HashMap<String, Object>> searchItem(String name) {
        ArrayList dataList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < book.length; i++) {
            int bookno = book[i].getBookno().indexOf(name); //搜索框内输入的内容在ListView各条目中的位置 ，内容不匹配就返回-1
            int bookname = book[i].getBookname().indexOf(name);
            int author = book[i].getAuthor().indexOf(name);
            int publisher = book[i].getPublisher().indexOf(name);
            int totalnum = book[i].getTotalnum().indexOf(name);
            int borrownum = book[i].getBorrownum().indexOf(name);
            int pubday = book[i].getPubday().indexOf(name);
            System.out.println("bookno=" + bookno + "bookname=" + bookname + "author=" + author);
            // 存在匹配的数据
            if (bookno != -1 || bookname != -1 || author != -1 || publisher != -1 || totalnum != -1 ||
                    borrownum != -1 || pubday != -1) {

                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("bookno", book[i].getBookno());
                item.put("bookname", book[i].getBookname());
                item.put("author", book[i].getAuthor());
                item.put("publisher", book[i].getPublisher());
                item.put("totalnum", book[i].getTotalnum());
                item.put("borrownum", book[i].getBorrownum());
                item.put("pubday", book[i].getPubday());
                dataList.add(item);

            }
        }
        return dataList;
    }

    /**
     * 更新适配器
     *
     * @param obj
     */
    public void updateLayout(ArrayList<HashMap<String, Object>> obj) {
        mListView.setAdapter(new SimpleAdapter(getActivity(), obj, R.layout.book_listview,
                new String[]{"bookno", "bookname", "author", "publisher", "totalnum", "borrownum", "pubday"},
                new int[]{R.id.tv_no, R.id.tv_name, R.id.tv_author, R.id.tv_publisher, R.id.tv_totalnum,
                        R.id.tv_borrownum, R.id.tv_pubday}));
    }

}

