package com.example.tododetail;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    private Button nextButton;
    private Button prevButton;
    private Button detailButton;
    private String[] mTodos;
    private int mTodoIndex = 0;
    private TextView todoTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.buttonNext);
        prevButton = findViewById(R.id.buttonPrev);
        detailButton = findViewById(R.id.buttonTodoDetail);
        todoTextView = findViewById(R.id.textViewTodo);

        setTextViewComplete("");


        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);

        todoTextView.setText(mTodos[mTodoIndex]);


        ActivityResultLauncher<Intent> todoDetailActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        boolean isTodoComplete = intent.getBooleanExtra(IS_TODO_COMPLETE, false);
                        updateTodoComplete(isTodoComplete);
                    } else {
                        Toast.makeText(this, R.string.back_button_pressed, Toast.LENGTH_SHORT).show();
                   }

                });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTodoIndex = (mTodoIndex + 1) % mTodos.length;
                todoTextView.setText(mTodos[mTodoIndex]);
            }
        });

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = TodoDetailActivity.newIntent(MainActivity.this, mTodoIndex);
                todoDetailActivityResultLauncher.launch(intent);
            }
        });
    }



    private void updateTodoComplete(boolean is_todo_complete) {

        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);

        if (is_todo_complete) {
            textViewTodo.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.backgroundSuccess));
            textViewTodo.setTextColor(
                    ContextCompat.getColor(this, R.color.colorSuccess));

            setTextViewComplete("\u2713");
        }
    }

    private void setTextViewComplete( String message ){
        final TextView textViewComplete;
        textViewComplete = (TextView) findViewById(R.id.textViewComplete);

        textViewComplete.setText(message);
    }
}