package com.example.todolist;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.api.APIServices;
import com.example.todolist.api.RetrofitClient;
import com.example.todolist.model.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todoList;
    private APIServices apiServices;
    private onTodoActionListener listener;

    public interface onTodoActionListener {

        void onDelete(Todo todo);
    }

    public TodoAdapter(List<Todo> todoList, onTodoActionListener listener) {
        setHasStableIds(true);
        this.todoList = todoList;
        this.listener = listener;
        this.apiServices = RetrofitClient.getInstance().create(APIServices.class);
    }

    public void setTodoList(List<Todo> list) {
        this.todoList.clear();
        this.todoList.addAll(list);
        notifyDataSetChanged();
        Log.d("Data : ", list.toString());
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.list.setText(todo.getList());
        holder.status.setText(todo.getStatus() );
        holder.delete.setOnClickListener(v -> listener.onDelete(todo));
        updatetextStyle(holder.list,todo.isCompleted());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            todo.setCompleted(isChecked ? 1 : 0);
            if(isChecked){
                holder.status.setText("Complete");
                holder.status.setTextColor(Color.GREEN);
            }else{
                holder.status.setText("in progress");
                holder.status.setTextColor(Color.BLUE);
            }
            updatetextStyle(holder.list,isChecked);
            updateStatus(todo,holder);

        });
        String status =  todo.isCompleted() ? "complete" : "No";
        holder.checkBox.setChecked(todo.isCompleted());
        holder.delete.setOnClickListener(v -> listener.onDelete(todo));
    }

    public int getItemCount() {
        return todoList.size();
    }

    public long getItemId(int position) {
        return todoList.get(position).getId();
    }

    public void updateStatus(Todo todo, TodoViewHolder holder) {
        String newStatus = todo.isCompleted() ? "Complete" : "in progress";
        apiServices.updateTodoStatus(todo.getId(), newStatus, 1).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(holder.itemView.getContext(), "Status updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(holder.itemView.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatetextStyle(TextView textView , boolean isComplete){
        if(isComplete){
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView list, status;
        CheckBox checkBox;
        ImageButton delete;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            list = itemView.findViewById(R.id.list);
            status = itemView.findViewById(R.id.status);
            checkBox = itemView.findViewById(R.id.checkBox2);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
