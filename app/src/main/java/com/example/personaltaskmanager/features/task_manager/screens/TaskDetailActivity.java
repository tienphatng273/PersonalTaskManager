package com.example.personaltaskmanager.features.task_manager.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.personaltaskmanager.R;
import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.viewmodel.TaskViewModel;

/**
 * Màn hình thêm / sửa Task.
 * Sử dụng đúng kiến trúc MVVM → gọi ViewModel để lưu DB.
 */
public class TaskDetailActivity extends AppCompatActivity {

    private EditText edtTitle, edtDescription;
    private Button btnSave;

    private TaskViewModel viewModel;

    // Dùng để biết người dùng đang EDIT hay ADD
    private int taskId = -1;
    private Task currentTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_task_manager_detail);

        // Ánh xạ view
        edtTitle = findViewById(R.id.edt_task_title);
        edtDescription = findViewById(R.id.edt_task_description);
        btnSave = findViewById(R.id.btn_save_task);

        // KHỞI TẠO VIEWMODEL
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Nhận task_id nếu mở bằng EDIT
        taskId = getIntent().getIntExtra("task_id", -1);

        // Nếu có taskId → load DB để hiển thị
        if (taskId != -1) {
            currentTask = viewModel.getTaskById(taskId);
            if (currentTask != null) {
                edtTitle.setText(currentTask.getTitle());
                edtDescription.setText(currentTask.getDescription());
            }
        }

        // Xử lý nút LƯU
        btnSave.setOnClickListener(v -> {

            String title = edtTitle.getText().toString().trim();
            String desc = edtDescription.getText().toString().trim();

            if (title.isEmpty()) {
                edtTitle.setError("Tên công việc không được để trống");
                return;
            }

            // EDIT
            if (currentTask != null) {
                viewModel.updateTask(currentTask, title, desc);
                setResult(RESULT_OK);
                finish();
                return;
            }

            // ADD
            viewModel.addTask(title, desc);

            setResult(RESULT_OK);
            finish();
        });
    }
}
