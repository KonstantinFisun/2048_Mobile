package com.example.game_2048;

import android.content.Context;
import android.view.Gravity;

class Cell extends androidx.appcompat.widget.AppCompatTextView {

    public Cell(Context context) {
        super(context);
        //Текст
        this.setText("");
        this.setTextSize(37);
        //Расположение текста
        this.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        this.setBackgroundColor(getResources().getColor(R.color.soft_amber));
    }

    public void draw(int number) {
        switch (number) {
            case 0: {
                this.setText("");
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.soft_amber));
                break;
            }
            case 2: {
                this.setText(String.valueOf(2));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b2));
                this.setTextColor(getResources().getColor(R.color.t1));
                break;
            }
            case 4: {
                setText(String.valueOf(4));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b4));
                this.setTextColor(getResources().getColor(R.color.t1));
                break;
            }
            case 8: {
                setText(String.valueOf(8));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b8));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 16: {
                setText(String.valueOf(16));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b16));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 32: {
                setText(String.valueOf(32));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b32));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 64: {
                setText(String.valueOf(64));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b64));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 128: {
                setText(String.valueOf(128));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b128));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 256: {
                setText(String.valueOf(256));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b256));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 512: {
                setText(String.valueOf(512));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b512));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 1024: {
                setText(String.valueOf(1024));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b1024));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 2048: {
                setText(String.valueOf(2048));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b2048));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 4096: {
                setText(String.valueOf(4096));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b4096));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 8192: {
                setText(String.valueOf(8192));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b8192));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
            case 16284: {
                setText(String.valueOf(16284));
                this.setTextSize(37);
                this.setBackgroundColor(getResources().getColor(R.color.b16284));
                this.setTextColor(getResources().getColor(R.color.t2));
                break;
            }
        }
    }
}
