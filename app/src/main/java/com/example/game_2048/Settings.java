package com.example.game_2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper; // БД

    String[] sizeBoard = {"4 X 4", "6 X 6", "8 X 8"};
    String[] chance2 = {"90","80","70","60"};


    GridView sizeBoardView;
    GridView twoView;

    private int selectedPosition = -1;

    Button apply;
    Button reset_bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Находим список
        sizeBoardView = findViewById(R.id.levels);
        twoView = findViewById(R.id.levels2);

        apply = findViewById(R.id.apply);
        reset_bd = findViewById(R.id.reset_bd);

        sizeBoardView.setNumColumns(sizeBoard.length);
        twoView.setNumColumns(chance2.length);

        apply.setOnClickListener(this);
        reset_bd.setOnClickListener(this);

        // Создание БД
        dbHelper = new DBHelper(this);

        // создаем адаптеры
        ArrayAdapter<String> adapterSize = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sizeBoard);

        ArrayAdapter<String> adapterChance2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, chance2);


        // присваиваем адаптер списку
        sizeBoardView.setAdapter(adapterSize);
        twoView.setAdapter(adapterChance2);


        sizeBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selectedPosition = position;
            }
        });

        twoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                selectedPosition = position;
            }
        });



    }

    //Слушатель на кнопки меню
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.apply) {
            Intent start = new Intent(this, Game.class);
            start.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(start);
        }
        if (view.getId() == R.id.reset_bd) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Рекорд сброшен", Toast.LENGTH_SHORT);
            toast.show();

            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.delete(DBHelper.TABLE_CONTACTS,null,null);
        }
    }

}