package com.example.todolist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.api.APIServices;
import com.example.todolist.api.RetrofitClient;
import com.example.todolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TodoAdapter.onTodoActionListener{

    private APIServices apiServices;
    private RecyclerView recylerView;
    private TodoAdapter todoAdapter;
    private Button createList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recylerView = findViewById(R.id.listTodo);
        recylerView.setLayoutManager(new LinearLayoutManager(this));

        apiServices = RetrofitClient.getInstance().create(APIServices.class);

        getList();
        createList = findViewById(R.id.create_list);
        createList.setOnClickListener(v-> onCreateData());

    }
    public void onCreateData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create new data");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10,10,10,10);

        EditText list = new EditText(this);
        list.setHint("Enter data");
        layout.addView(list);

        builder.setView(layout);

        builder.setPositiveButton("Create",(dialog,which)->{
            String todo = list.getText().toString();
            apiServices.createTodo(todo, "in progress",0).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()){
                        Log.e("api_err", response.toString());
                        Toast.makeText(MainActivity.this, "successfully added data", Toast.LENGTH_SHORT).show();

                        getList();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });

        builder.setNegativeButton("cancel",null);
        builder.show();
    }

    public void onDelete(Todo todo){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete data");
        builder.setMessage("Are you sure ?");

        builder.setPositiveButton("Delete",(dialog,which)->{
            apiServices.deleteTodo(todo.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                        getList();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        builder.setNegativeButton("cancel",null);
        builder.show();
    }


    private void getList(){
        todoAdapter = new TodoAdapter(new ArrayList<>(),this);
        recylerView.setAdapter(todoAdapter);

        Call<List<Todo>> call = apiServices.getAllTodo();
        call.enqueue(new Callback<List<Todo>>() {

            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if(response.isSuccessful()){
                    List<Todo> todoList = response.body();
                    todoAdapter.setTodoList(todoList);
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failes to load data ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}