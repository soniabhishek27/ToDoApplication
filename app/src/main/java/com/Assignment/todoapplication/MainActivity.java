package com.Assignment.todoapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List <String> insertdata = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final ListView listView = findViewById(R.id.list);
        final setText adapter= new setText();

        final ImageButton add;

        add=findViewById(R.id.add);

        readlist();
        adapter.setData(insertdata);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog dailog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete the task")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertdata.remove(position);
                                adapter.setData(insertdata);
                            }
                        })
                        .setNegativeButton("No",null)
                        .create();
                dailog.show();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText insert = new EditText(MainActivity.this);

                insert.setSingleLine();
                AlertDialog dailog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add new Task")
                        .setMessage("What is your new task")
                        .setView(insert)
                        .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertdata.add(insert.getText().toString());
                                adapter.setData(insertdata);
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dailog.show();
            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();

        savelist();


    }

    public void savelist()
    {
        try {
            File file = new File(this.getFilesDir(), "Saved");
            FileOutputStream fout = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout));

            for (int i = 0; i < insertdata.size(); i++)
            {
                bw.write(insertdata.get(i));
                bw.newLine();
            }
            bw.close();
            fout.close(); }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void readlist()
    {
        File file= new File(this.getFilesDir(),"Saved");

        if(!file.exists())
        {
            return;
        }

        try
        {
            FileInputStream fin = new FileInputStream(file);
            BufferedReader reader= new BufferedReader(new InputStreamReader(fin));
            String line = reader.readLine();
            while(line != null) {
                insertdata.add(line);
                line = reader.readLine();

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void later(View view)
    {
        Intent intent = new Intent(MainActivity.this,Later.class);
        startActivity(intent);
    }

    public class setText extends BaseAdapter
    {
        List <String> insertdata = new ArrayList<>();

        void setData(List<String>templist)
        {



            insertdata.clear();
            insertdata.addAll(templist);

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return insertdata.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View RowView = inflater.inflate(R.layout.items,parent,false);
            TextView textView = RowView.findViewById(R.id.textitem);
            textView.setText(insertdata.get(position));

            return RowView;
        }
    }
    
}
