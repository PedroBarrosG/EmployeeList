package com.pedrobarros.employeelist;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewEmployeeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Database databaseHelperClass = new Database(this);
        List<EmployeeModelClass> employeeModelClasses = databaseHelperClass.getEmployeeList();

        if (employeeModelClasses.size() > 0){
            EmployeeAdapterClass employeadapterclass = new EmployeeAdapterClass(employeeModelClasses,ViewEmployeeActivity.this);
            recyclerView.setAdapter(employeadapterclass);
        }else {
            Toast.makeText(this, "Não existem colaboradores no Banco de Dados.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Atualiza a lista de colaboradores
        Database databaseHelperClass = new Database(this);
        List<EmployeeModelClass> employeeModelClasses = databaseHelperClass.getEmployeeList();

        if (employeeModelClasses.isEmpty()) {
            // Se não há colaboradores, retorna para a tela inicial
            Toast.makeText(this, "O Banco de Dados está vazio.", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a Activity atual
        } else {
            // Atualiza o RecyclerView com os colaboradores restantes
            EmployeeAdapterClass employeeAdapterClass = new EmployeeAdapterClass(employeeModelClasses, ViewEmployeeActivity.this);
            recyclerView.setAdapter(employeeAdapterClass);
        }
    }
}