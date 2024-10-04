package com.example.listadetarefas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etTarefa;
    private Button btnAdicionar, btnSobre;
    private ListView lvTarefas;
    private ArrayList<String> listaTarefas;
    private ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Vincula ao layout activity_main.xml

        // Vincular os componentes do layout
        etTarefa = findViewById(R.id.etTarefa);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnSobre = findViewById(R.id.btnAdicionar);
        lvTarefas = findViewById(R.id.lvTarefas);

        // Inicializar a lista de tarefas
        listaTarefas = new ArrayList<>();

        // Configurar o adaptador para exibir as tarefas no ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTarefas);
        lvTarefas.setAdapter(adapter);

        // Definir o comportamento do botão "Adicionar"
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tarefa = etTarefa.getText().toString();
                if (!tarefa.isEmpty()) {
                    listaTarefas.add(tarefa); // Adicionar a tarefa à lista
                    adapter.notifyDataSetChanged(); // Atualizar o ListView
                    etTarefa.setText(""); // Limpar o campo de texto
                }
            }
        });

        // Adicionar um LongClickListener para editar ou excluir tarefas
        lvTarefas.setOnItemLongClickListener((parent, view, position, id) -> {
            String tarefaSelecionada = listaTarefas.get(position);
            showEditDeleteDialog(tarefaSelecionada, position);
            return true; // Retorna true para indicar que o evento foi tratado
        });

        // Definir o comportamento do botão "Sobre"
        btnSobre.setOnClickListener(v -> showAboutDialog());
    }

    private void showAboutDialog() {
    }

    private void showEditDeleteDialog(String tarefa, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma ação")
                .setItems(new String[]{"Editar", "Excluir"}, (dialog, which) -> {
                    if (which == 0) {
                        showEditTaskDialog(tarefa, position);
                    } else if (which == 1) {
                        listaTarefas.remove(position); // Remover a tarefa
                        adapter.notifyDataSetChanged(); // Atualizar o ListView
                        Toast.makeText(MainActivity.this, "Tarefa excluída", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void showEditTaskDialog(String tarefa, int position) {
        EditText editText = new EditText(this);
        editText.setText(tarefa);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarefa")
                .setView(editText)
                .setPositiveButton("Salvar", (dialog, which) -> {
                    String novaTarefa = editText.getText().toString();
                    listaTarefas.set(position, novaTarefa); // Atualizar a tarefa
                    adapter.notifyDataSetChanged(); // Atualizar o ListView
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


}
