package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
                score++;
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
        String passStatus = "";
        int seconds = (int) (mTimeLeftInMillis ) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
        mCountDownTimer.cancel();
        if (seconds < totalQuestion * 3 && score>0.6*totalQuestion ) {
            passStatus = "Passed in "+seconds+" s " + milliseconds + " ms";
            score = 12*seconds + 6* milliseconds;
        } else {
            passStatus = "Failed "+seconds+" s " + milliseconds + " ms";
            score=0;
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();


    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
        startTimer();
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
            }
        }.start();

    }

}

