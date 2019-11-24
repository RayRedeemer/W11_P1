package com.example.w11_p1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Tab3Fragment extends Fragment {

    private int question_num = 0;
    private int correct = 0;

    Random random = new Random();
    TextView num1;
    TextView num2;
    TextView question;
    EditText answer;
    Button submit_btn;
    Button restart_btn;
    private FirebaseAuth mAuth;
    String displayName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        num1 = (TextView) view.findViewById(R.id.num1);
        num2 = (TextView) view.findViewById(R.id.num2);
        question = (TextView) view.findViewById(R.id.question);
        answer = (EditText) view.findViewById(R.id.answer);
        submit_btn = (Button) view.findViewById(R.id.submit_btn);
        restart_btn = (Button) view.findViewById(R.id.restart_btn);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        displayName = user.getDisplayName();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(v);
            }
        });

        restart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(v);
            }
        });

        if (savedInstanceState != null) {
            num1.setText(savedInstanceState.getString("num1", ""));
            num2.setText(savedInstanceState.getString("num2", ""));
            question_num = savedInstanceState.getInt("question_num", 0);
            correct = savedInstanceState.getInt("correct", 0);
            submit_btn.setEnabled(savedInstanceState.getBoolean("submit", true));
            setQuestionText();

        } else {

            Toast toast = Toast.makeText(getContext(), String.format("Welcome %s!", displayName), Toast.LENGTH_LONG);
            toast.show();
            get_questions();
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("num1", num1.getText().toString());
        outState.putString("num2", num2.getText().toString());
        outState.putInt("question_num", question_num);
        outState.putInt("correct", correct);
        outState.putBoolean("submitEnabled", submit_btn.isEnabled());
        super.onSaveInstanceState(outState);
    }

    // method to set the question number and number of correct questions
    public void setQuestionText() {
        question.setText(String.format("Question %s - Correct %s", question_num + "", correct + ""));
    }

    // method to get the next question
    public void get_questions() {
        answer.setText(""); // reset answer field
        question_num += 1;
        setQuestionText();
        int x, y;
        while (true) {
            x = random.nextInt(100) + 1;
            y = random.nextInt(x) + 1;
            if (x % y == 0) break;
        }

        num1.setText("" + x);
        num2.setText("÷ " + y);
    }

    // method to submit the user's answer
    public void submit(View view) {
        String answer_text = answer.getText().toString();
        if (answer_text != "") {
            try {
                int answer_int = Integer.parseInt(answer_text);
                int numerator = Integer.parseInt(num1.getText().toString());
                String denominator_string = num2.getText().toString();
                int denominator = Integer.parseInt(denominator_string.substring(2, denominator_string.length()));
                if (answer_int == numerator / denominator) {
                    correct += 1;
                }

                if (question_num == 10) {
                    setQuestionText();
                    submit_btn.setEnabled(false);
                    Toast toast = Toast.makeText(getContext(), String.format("Congrats! You answered %s out of %s correct!", correct + "", question_num + ""), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    get_questions();
                }
            }
            // catch any input other than numbers
            catch (NumberFormatException e) {
                Toast toast = Toast.makeText(getContext(), "Please input a number!", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        // if user try to submit empty answer
        else {
            Toast toast = Toast.makeText(getContext(), "Please input an answer!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // method to restart the game
    public void restart(View view) {
        question_num = 0;
        correct = 0;

        if (!submit_btn.isEnabled()) {
            submit_btn.setEnabled(true);
        }

        get_questions();
    }

}


