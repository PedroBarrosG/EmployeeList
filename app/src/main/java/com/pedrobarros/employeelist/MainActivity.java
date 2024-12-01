package com.pedrobarros.employeelist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText_name, editText_email;
    Button button_add, button_view, button_edit, button_toggle_night_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recupera o estado salvo do Night Mode
        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean nightMode = preferences.getBoolean("NightMode", false);

        // Aplica o tema
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        editText_name = findViewById(R.id.edittext_name);
        editText_email = findViewById(R.id.edittext_email);
        button_add = findViewById(R.id.button_add);
        button_view = findViewById(R.id.button_view);
        button_edit = findViewById(R.id.button_edit);
        button_toggle_night_mode = findViewById(R.id.button_toggle_night_mode);

        // Atualiza o ícone do botão inicial
        updateButtonIcon();

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditEmployeeActivity.class);
                String stringName = editText_name.getText().toString();
                String stringEmail = editText_email.getText().toString();

                if (stringName.length() <= 0 || stringEmail.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Insira todos os dados!", Toast.LENGTH_SHORT).show();
                } else {
                    Database databaseHelperClass = new Database(MainActivity.this);
                    EmployeeModelClass employeeModelClass = new EmployeeModelClass(stringName, stringEmail);
                    databaseHelperClass.addEmployee(employeeModelClass);
                    Toast.makeText(MainActivity.this, "Colaborador adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewEmployeeActivity.class);
                startActivity(intent);
            }
        });

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database databaseHelper = new Database(MainActivity.this);
                List<EmployeeModelClass> employeeList = databaseHelper.getEmployeeList();

                if (employeeList.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Não existem colaboradores no Banco de Dados.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ViewEmployeeActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Night Mode
        button_toggle_night_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                // State Save SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("NightMode", AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
                editor.apply();

                updateButtonIcon();
            }
        });
    }

    private void updateButtonIcon() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            button_toggle_night_mode.setText("☀\uFE0F"); // Sol
        } else {
            button_toggle_night_mode.setText("\uD83C\uDF19"); // Lua
        }
    }
}