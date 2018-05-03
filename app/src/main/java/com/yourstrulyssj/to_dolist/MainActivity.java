package com.yourstrulyssj.to_dolist;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> arrayAdapter;
    ListView tasks;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        tasks = (ListView) findViewById(R.id.taskList);
        addBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);


        loadTasks();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add New Task")
                        .setMessage("Whats on your mind ?")
                        .setView(editText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task = String.valueOf(editText.getText());
                                dbHelper.newTask(task);
                                loadTasks();
                            }
                        }).setNegativeButton("Cancel",null).create();
                dialog.show();

            }



        });
    }



    private void loadTasks(){

        ArrayList<String> taskList = dbHelper.getTaskList();
        if (arrayAdapter == null){
            arrayAdapter = new ArrayAdapter<String>(this,R.layout.tasklayout,R.id.taskTitle,taskList);
            tasks.setAdapter(arrayAdapter);


        }else {

            arrayAdapter.clear();
            arrayAdapter.addAll(taskList);
            arrayAdapter.notifyDataSetChanged();

        }

    }

    public void deleteTask (View view){
        View parent = (View) view.getParent();
        TextView textView = (TextView)findViewById(R.id.taskTitle);
        String task = String.valueOf(textView.getText());
        dbHelper.removeTask(task);
        loadTasks();

    }
}
