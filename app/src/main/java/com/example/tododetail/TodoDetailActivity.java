package com.example.tododetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TodoDetailActivity extends AppCompatActivity {

    private static final String TODO_INDEX = "com.example.todoIndex";

    /* name, value pair to be returned in an intent */
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";


    private String[] mTodosDetail;
    private TextView todoDetailTextView;

    private CheckBox checkboxIsComplete;




    /* Any calling activity would call this static method and pass the necessary
      key, value data pair in an intent object. */
    public static Intent newIntent(Context packageContext, int todoIndex) {
        Intent intent = new Intent(packageContext, TodoDetailActivity.class);
        intent.putExtra(TODO_INDEX, todoIndex);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        mTodosDetail = getResources().getStringArray(R.array.todo_detail);

        todoDetailTextView = findViewById(R.id.textViewTodoDetail);


        Intent intent = getIntent();
        int mTodoIndex = intent.getIntExtra(TODO_INDEX, 0);
        todoDetailTextView.setText(mTodosDetail[mTodoIndex]);


         checkboxIsComplete =findViewById(R.id.checkBoxIsComplete);

        checkboxIsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIsComplete(checkboxIsComplete.isChecked());
                finish();
            }
        });

    }



        private void setIsComplete(boolean isChecked) {

            /* celebrate with a static Toast! */
            if(isChecked){
                Toast.makeText(TodoDetailActivity.this,
                        "Hurray, it's done!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TodoDetailActivity.this,
                        "There is always tomorrow!", Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent();
            intent.putExtra(IS_TODO_COMPLETE, isChecked);
            setResult(RESULT_OK, intent);
        }
}
