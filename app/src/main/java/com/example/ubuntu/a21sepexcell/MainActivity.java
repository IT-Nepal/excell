package com.example.ubuntu.a21sepexcell;

//url: http://www.worldbestlearningcenter.com/tips/Android-CSV-file.htm

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//         you can read data from a Java InputStream as an ordered sequence of bytes (character).
        InputStream inputStream = getResources().openRawResource(R.raw.foodlist);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> foodList = csvFile.read(); //get 'return resultList'
        MyListAdapter adapter = new MyListAdapter(this, R.layout.listrow, R.id.txtid, foodList);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }

    private class CSVFile {
        InputStream inputStream;

        public CSVFile(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public List<String[]> read() {  //method name is read
            //Here making reference List, so that it accepts all it's child class wheather it is arraylist
//            or other..
            List<String[]> resultList = new ArrayList<String[]>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    resultList.add(row);
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("TAG", e.getMessage());
                }
            }
            return resultList;
        }
    }
}
