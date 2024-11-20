package com.pedrobarros.employeelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditEmployeeActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail;
    Button buttonSave;
    Database database;
    int employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        editTextName = findViewById(R.id.edittext_name);
        editTextEmail = findViewById(R.id.edittext_email);
        buttonSave = findViewById(R.id.button_save);

        database = new Database(this);

        Intent intent = getIntent();
        employeeId = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");


        editTextName.setText(name);
        editTextEmail.setText(email);



        // Atualizar os dados ao clicar em salvar
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = editTextName.getText().toString();
                String updatedEmail = editTextEmail.getText().toString();

                if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
                    Toast.makeText(EditEmployeeActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    EmployeeModelClass employee = new EmployeeModelClass(employeeId, updatedName, updatedEmail);
                    database.updateEmployee(employee);
                    Toast.makeText(EditEmployeeActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}