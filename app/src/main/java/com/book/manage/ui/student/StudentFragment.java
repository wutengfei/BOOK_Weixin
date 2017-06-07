package com.book.manage.ui.student;

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
import com.book.manage.controller.StudentControl;
import com.book.manage.model.Student;

import java.util.*;

public class StudentFragment extends Fragment implements SearchView.OnQueryTextListener {

    public ListView mListView;
    StudentControl studentControl;
    SimpleAdapter adapter;
    SearchView searchView;
    Student[] student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        studentControl = new StudentControl(getActivity());
        if (studentControl.getAllStudent() == null) {//初始时保存学生信息
            studentControl.saveAll();
        }
        View v;

        v = inflater.inflate(R.layout.fragment_student, null);
        mListView = (ListView) v.findViewById(R.id.listView1);
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
        student = studentControl.getAllStudent();
        if (student != null)
            for (int i = 0; i < student.length; i++) {
                HashMap<String, Object> item = new HashMap<String, Object>();//用来存放学生属性
                item.put("no", student[i].getStudentNo());
                item.put("name", student[i].getStudentName());
                item.put("phone", student[i].getStudentMobile());
                item.put("class", student[i].getStudentClass());
                item.put("major", student[i].getStudentMajor());
                data.add(item);
            }
        //适配器
        adapter = new SimpleAdapter(getActivity(), data, R.layout.student_listview,
                new String[]{"no", "name", "class", "major", "phone"},
                new int[]{R.id.tv_No, R.id.tv_Name, R.id.tv_Class, R.id.tv_Major, R.id.tv_Mobile});
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
                String no = dataList.get(0).get("no") + "";
                String name = dataList.get(0).get("name") + "";
                String major = dataList.get(0).get("major") + "";
                String _class = dataList.get(0).get("class") + "";
                String phone = dataList.get(0).get("phone") + "";
                //把信息带到下一个界面
                Intent intent = new Intent(getActivity(), StudentUpdate.class);

                intent.putExtra("no", no);//把no传递到下一个界面
                intent.putExtra("name", name);
                intent.putExtra("major", major);
                intent.putExtra("class", _class);
                intent.putExtra("phone", phone);

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
                        String no = data.get(position).get("no") + "";
                        if (data.remove(position) != null) {

                            studentControl.deleteStudentByNo(no);
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
     * @param query
     * @return true-点击输入法上的搜索无变化   false-点击搜索时输入法消失
     */
    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
    }

    /**
     * 输入文字时实时刷新列表
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
     * 搜索主要逻辑。数据库中student的属性和输入框中输入的文字一致就存放到新ArrayList
     *
     * @param name 输入框中输入的文字
     * @return dataList  搜索结果存放的ArrayList
     */
    public ArrayList<HashMap<String, Object>> searchItem(String name) {
        ArrayList dataList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < student.length; i++) {
            int no = student[i].getStudentNo().indexOf(name);//搜索框内输入的内容在ListView各条目中的位置 ，内容不匹配就返回-1
            int _name = student[i].getStudentName().indexOf(name);
            int major = student[i].getStudentMajor().indexOf(name);
            int _class = student[i].getStudentClass().indexOf(name);
            int phone = student[i].getStudentMobile().indexOf(name);
            System.out.println("no=" + no + "_name=" + _name + "major=" + major);
            // 存在匹配的数据
            if (no != -1 || _name != -1 || major != -1 || _class != -1 || phone != -1) {

                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("no", student[i].getStudentNo());
                item.put("name", student[i].getStudentName());
                item.put("phone", student[i].getStudentMobile());
                item.put("class", student[i].getStudentClass());
                item.put("major", student[i].getStudentMajor());
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
        mListView.setAdapter(new SimpleAdapter(getActivity(), obj, R.layout.student_listview,
                new String[]{"no", "name", "class", "major", "phone"},
                new int[]{R.id.tv_No, R.id.tv_Name, R.id.tv_Class, R.id.tv_Major, R.id.tv_Mobile}));
    }

}
