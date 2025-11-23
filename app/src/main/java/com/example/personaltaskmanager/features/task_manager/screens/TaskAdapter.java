package com.example.personaltaskmanager.features.task_manager.screens;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.data.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter hiển thị danh sách Task trong RecyclerView.
 * Giữ nguyên cấu trúc cũ, chỉ bổ sung toggle Completed.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();

    private OnTaskClickListener listener;
    private OnTaskDeleteListener deleteListener;
    private OnTaskToggleListener toggleListener;   // ⭐ NEW

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public interface OnTaskDeleteListener {
        void onTaskDelete(Task task);
    }

    // ⭐ CALLBACK khi tick checkbox
    public interface OnTaskToggleListener {
        void onTaskToggle(Task task, boolean done);
    }

    public TaskAdapter(
            OnTaskClickListener listener,
            OnTaskDeleteListener deleteListener,
            OnTaskToggleListener toggleListener
    ) {
        this.listener = listener;
        this.deleteListener = deleteListener;
        this.toggleListener = toggleListener;
    }

    public void setData(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_task_manager_item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textTitle.setText(task.getTitle());
        holder.textDeadline.setText(task.getDescription());

        holder.checkboxTask.setChecked(task.isCompleted());

        // ⭐ UI cho task đã completed
        if (task.isCompleted()) {
            holder.textTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textTitle.setAlpha(0.5f);
            holder.textDeadline.setAlpha(0.5f);
        } else {
            holder.textTitle.setPaintFlags(0);
            holder.textTitle.setAlpha(1f);
            holder.textDeadline.setAlpha(1f);
        }

        // CLICK ITEM
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTaskClick(task);
        });

        // DELETE
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) deleteListener.onTaskDelete(task);
        });

        // ⭐ TOGGLE COMPLETED
        holder.checkboxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (toggleListener != null) toggleListener.onTaskToggle(task, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTask;
        TextView textTitle, textDeadline;
        CheckBox checkboxTask;
        ImageButton btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTask = itemView.findViewById(R.id.imgTask);
            textTitle = itemView.findViewById(R.id.textTaskTitle);
            textDeadline = itemView.findViewById(R.id.textTaskDeadline);
            checkboxTask = itemView.findViewById(R.id.checkboxTask);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
