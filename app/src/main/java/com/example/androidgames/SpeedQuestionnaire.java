package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeedQuestionnaire extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestionsTextView;
    TextView timerTextView;
    TextView questionTextView;
    Button ansT, ansF;
    Button submitBtn;

    int score = 0;
    int questioncorrect=0;
    private String passStatus = "Failed";
    int totalQuestion = SpeedQA.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";


    private static final long START_TIME_IN_MILLIS = 10000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_questionnaire);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        timerTextView = findViewById(R.id.timer);
        ansT = findViewById(R.id.ans_A);
        ansF = findViewById(R.id.ans_B);
        submitBtn = findViewById(R.id.submit_btn);


        ansT.setOnClickListener(this);
        ansF.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        totalQuestionsTextView.setText("Total questions : " + totalQuestion);

        startTimer();
        timerTextView.setText("Timer : " + (int) (mTimeLeftInMillis ) / 1000 + "s "+ (int) (mTimeLeftInMillis ) % 1000+"ms" );

        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {

        ansT.setBackgroundColor(Color.WHITE);
        ansF.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_btn) {
            if (selectedAnswer.equals(SpeedQA.correctAnswers[currentQuestionIndex])) {
                questioncorrect++;
            }
            currentQuestionIndex++;
            loadNewQuestion();
            timerTextView.setText("Timer : " + (int) (mTimeLeftInMillis ) / 1000 + "s "+ (int) (mTimeLeftInMillis ) % 1000+"ms" );



        } else {
            //choices button clicked
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);

        }

    }

    void loadNewQuestion() {

        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(SpeedQA.question[currentQuestionIndex]);
        ansT.setText(SpeedQA.choices[currentQuestionIndex][0]);
        ansF.setText(SpeedQA.choices[currentQuestionIndex][1]);

    }

    void finishQuiz() {
        int seconds = (int) (mTimeLeftInMillis ) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
        mCountDownTimer.cancel();
        if (seconds < totalQuestion * 3 && questioncorrect>0.6*totalQuestion ) {
            passStatus = "Passed";
            score = 12*seconds + 6* milliseconds + questioncorrect*100;
        } else {
            passStatus = "Failed";
        }

        showEndScreen("Your score is : " + score + "\n(in "+ seconds+ " s "+milliseconds + "ms and " + questioncorrect +"/"+totalQuestion+")");
    }

    void actionSuite(int score) {
        Intent intent = new Intent();
        intent.putExtra("key_score", score);

        setResult(RESULT_OK, intent);

        finish();
    }


    private void startTimer(){
        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis=millisUntilFinished;
                timerTextView.setText("Timer : " + (int) (mTimeLeftInMillis / 1000) + "s "+ (int) (mTimeLeftInMillis % 1000)+"ms" );
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                String timeLeftFormatted = "Your score is : "+score;
                showEndScreen(timeLeftFormatted);
            }
        }.start();

    }

    private void showEndScreen(String message) {
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("OK",(dialogInterface, i) ->actionSuite(score))
                .setCancelable(false)
                .show();
    }

}

