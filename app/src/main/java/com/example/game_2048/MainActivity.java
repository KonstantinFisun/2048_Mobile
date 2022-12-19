package com.example.game_2048;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start;
    Button exit;
    Button help;
    Button settings;


    // Вызывается при создании Активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Инициализация активность
        start = (Button) findViewById(R.id.start);
        exit = (Button) findViewById(R.id.exit);
        help = (Button) findViewById(R.id.help);
        settings = (Button) findViewById(R.id.settings);

        start.setOnClickListener(this);
        exit.setOnClickListener(this);
        help.setOnClickListener(this);
        settings.setOnClickListener(this);


    }

    // Вызывается после завершения метода onCreate
    // Используется для восстановления состояния UI
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Восстановить состояние UI из объекта savedInstanceState.
        // Данный объект также был передан методу onCreate.
    }

    // Вызывается перед тем, как Активность снова становится видимой
    @Override
    public void onRestart(){ super.onRestart();
// Восстановить состояние UI с учетом того,
// что данная Активность уже была видимой.
    }

    // Вызывается, когда Активность стала видимой
    @Override
    public void onStart(){ super.onStart();
        // Проделать необходимые действия для Активности, видимой на экране
    }

    // Должен вызываться в начале видимого состояния.
    // На самом деле Android вызывает данный обработчик только
    // для Активностей, восстановленных из неактивного состояния
    @Override
    public void onResume(){
        super.onResume();
        // Восстановить приостановленные обновления UI, потоки и процессы,
        // «замороженные, когда Активность была в неактивном состоянии
    }

    // Вызывается перед выходом из активного состояния,
    // позволяя сохранить состояние в объекте savedInstanceState
    @Override
    public void onSaveInstanceState(@NonNull Bundle  savedInstanceState) {
        // Объект savedInstanceState будет в последующем  передан
        // методам onCreate и onRestoreInstanceState super.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }
    // Вызывается перед выходом из активного состояния
    @Override
    public void onPause() {
        // «Заморозить» обновления UI, потоки или «трудоемкие» процессы,
        // ненужные, когда Активность не на переднем плане super.onPause();
        super.onPause();
    }

    // Вызывается перед выходом из видимого состояния
    @Override
    public void onStop() {
        // «Заморозить» обновления UI, потоки или «трудоемкие» процессы,
        // ненужные, когда Активность не на переднем плане
        // Сохранить все данные и изменения в UI, так как процесс может быть
        // в любой момент убит системой  без объявления войны super.onStop();
        super.onStop();
    }

    // Вызывается перед уничтожением активности
    @Override
    public void onDestroy() {
        // Освободить все ресурсы, включая работающие потоки,
        // соединения с БД и т. д. super.onDestroy();
        super.onDestroy();
    }

    //Слушатель на кнопки меню
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) {
            Intent start = new Intent(this, Game.class);
            start.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(start);
        }
        if (view.getId() == R.id.settings) {
            Intent settings = new Intent(this, Settings.class);
            settings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(settings);
        }
        if (view.getId() == R.id.help) {
            Intent help = new Intent(this, Help.class);
            help.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(help);
        }
        if (view.getId() == R.id.exit) {
            Intent help = new Intent(this, Help.class);
            help.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(help);
        }
    }




}