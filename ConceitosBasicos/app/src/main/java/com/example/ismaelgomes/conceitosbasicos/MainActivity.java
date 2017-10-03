package com.example.ismaelgomes.conceitosbasicos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListeners();
    }

    private void setListeners(){
        final EditText editText = (EditText)findViewById(R.id.editText);
        Button button1 = (Button)findViewById(R.id.bt1);
        Button button2 = (Button)findViewById(R.id.bt2);

        //Mostrando conteúdo do EditText
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //Indo para outra activity
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itent = new Intent(MainActivity.this, Alunos.class);
                MainActivity.this.startActivity(itent);
            }
        });

    }
}
