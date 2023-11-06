package com.example.kr2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView mBall;
    ImageView mTarget;
    TextView textViewScore;
    int mBallX;
    int mBallY;
    int score;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScore = findViewById(R.id.textViewScore);
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();

        init();
        toStart();
    }

    public void init() {
        mBall = findViewById(R.id.mBall);
        mTarget = findViewById(R.id.mTarget);
        mBall.setOnTouchListener(this);
        score = User.activeUser.getScore();
    }

    public void toStart() {
        mBall.setX(mTarget.getX()-20);
        mBall.setY(mTarget.getY()*25);
    }

    public void hitTarget() {
        toStart();
        score++;
        textViewScore.setText(Integer.toString(score));
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Victory!")
                .setMessage("You've hit the target!")
                .setCancelable(false)
                .setPositiveButton("WOW!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                mBallX = X - lParams.leftMargin;
                mBallY = Y - lParams.topMargin;
                break;

            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - mBallX;
                layoutParams.topMargin = Y - mBallY;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;

                view.setLayoutParams(layoutParams);

                if (mBall.getX() >= mTarget.getX() - 40 && mBall.getX() <= mTarget.getX() + 40 &&
                        mBall.getY() >= mTarget.getY() - 40 && mBall.getY() <= mTarget.getY() + 40) {
                    hitTarget();
                }
        }
        return true;
    }

    public void buttonBackToMainClick(View view) {
        databaseHelper.updateDatabase(User.activeUser.getId(), User.activeUser.getUsername(), score);
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.updateDatabase(User.activeUser.getId(), User.activeUser.getUsername(), score);
        databaseHelper.closeDatabase();
    }
}