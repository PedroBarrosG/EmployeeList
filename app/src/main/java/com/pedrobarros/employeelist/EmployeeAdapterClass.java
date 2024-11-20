package com.pedrobarros.employeelist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapterClass extends RecyclerView.Adapter<EmployeeAdapterClass.ViewHolder> {

    List<EmployeeModelClass> employee;
    Context context;
    Database database;

    public EmployeeAdapterClass(List<EmployeeModelClass> employee, Context context) {
        this.employee = employee;
        this.context = context;
        database = new Database(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.employee_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final EmployeeModelClass employeeModelClass = employee.get(position);

        holder.textViewID.setText(Integer.toString(employeeModelClass.getId()));
        holder.editText_Name.setText(employeeModelClass.getName());
        holder.editText_Email.setText(employeeModelClass.getEmail());

        holder.button_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envia os dados do colaborador para a tela de edição
                Intent intent = new Intent(context, EditEmployeeActivity.class);
                intent.putExtra("id", employeeModelClass.getId());
                intent.putExtra("name", employeeModelClass.getName());
                intent.putExtra("email", employeeModelClass.getEmail());
                context.startActivity(intent);
            }
        });

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deleta o colaborador do banco de dados
                database.deleteEmployee(employeeModelClass.getId());

                // Remove o colaborador da lista local
                employee.remove(position);

                // Notifica o adaptador sobre as mudanças
                notifyDataSetChanged();

                // Verifica se a lista ficou vazia
                if (employee.isEmpty()) {
                    // Exibe mensagem e finaliza a Activity
                    Toast.makeText(context, "Todos os colaboradores foram removidos. Retornando à tela inicial.", Toast.LENGTH_SHORT).show();
                    ((Activity) context).finish();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return employee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewID;
        EditText editText_Name;
        EditText editText_Email;
        Button button_Edit;
        Button button_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.text_id);
            editText_Name = itemView.findViewById(R.id.edittext_name);
            editText_Email = itemView.findViewById(R.id.edittext_email);
            button_delete = itemView.findViewById(R.id.button_delete);
            button_Edit = itemView.findViewById(R.id.button_edit);

        }
    }
}