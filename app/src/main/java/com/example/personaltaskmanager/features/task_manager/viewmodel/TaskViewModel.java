package com.example.personaltaskmanager.features.task_manager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.data.repository.TaskRepository;
import com.example.personaltaskmanager.features.task_manager.domain.usecase.AddTaskUseCase;
import com.example.personaltaskmanager.features.task_manager.domain.usecase.GetTasksUseCase;

import java.util.List;

/**
 * TaskViewModel quản lý dữ liệu Task theo mô hình MVVM.
 * - Lấy dữ liệu từ DB qua Repository + UseCase
 * - Expose LiveData để Activity observe (UI tự cập nhật)
 */
public class TaskViewModel extends AndroidViewModel {

    private final TaskRepository repository;
    private final GetTasksUseCase getTasksUseCase;
    private final AddTaskUseCase addTaskUseCase;

    // LiveData UI quan sát
    private final LiveData<List<Task>> allTasksLiveData;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        repository = new TaskRepository(application);
        getTasksUseCase = new GetTasksUseCase(repository);
        addTaskUseCase = new AddTaskUseCase(repository);

        // Nhận LiveData từ UseCase
        allTasksLiveData = getTasksUseCase.execute();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasksLiveData;
    }

    /**
     * LẤY 1 TASK (PHỤC VỤ EDIT)
     */
    public Task getTaskById(int id) {
        return repository.getTaskById(id);
    }

    /**
     * Thêm task mới vào DB
     */
    public void addTask(String title, String description) {
        Task task = new Task(title, description, System.currentTimeMillis());
        addTaskUseCase.execute(task);
    }

    /**
     * CẬP NHẬT TASK (EDIT)
     */
    public void updateTask(Task task, String newTitle, String newDesc) {
        task.setTitle(newTitle);
        task.setDescription(newDesc);
        repository.updateTask(task);
    }
}
