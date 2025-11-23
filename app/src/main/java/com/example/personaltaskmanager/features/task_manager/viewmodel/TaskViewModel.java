package com.example.personaltaskmanager.features.task_manager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.data.repository.TaskRepository;
import com.example.personaltaskmanager.features.task_manager.domain.usecase.AddTaskUseCase;
import com.example.personaltaskmanager.features.task_manager.domain.usecase.GetTasksUseCase;
import com.example.personaltaskmanager.features.task_manager.domain.usecase.DeleteTaskUseCase;

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
    private final DeleteTaskUseCase deleteTaskUseCase;

    private final LiveData<List<Task>> allTasksLiveData;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        repository = new TaskRepository(application);
        getTasksUseCase = new GetTasksUseCase(repository);
        addTaskUseCase = new AddTaskUseCase(repository);
        deleteTaskUseCase = new DeleteTaskUseCase(repository);

        allTasksLiveData = getTasksUseCase.execute();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasksLiveData;
    }

    public Task getTaskById(int id) {
        return repository.getTaskById(id);
    }

    public void addTask(String title, String description) {
        Task task = new Task(title, description, System.currentTimeMillis());
        addTaskUseCase.execute(task);
    }

    public void updateTask(Task task, String newTitle, String newDesc) {
        task.setTitle(newTitle);
        task.setDescription(newDesc);
        repository.updateTask(task);
    }

    public void deleteTask(Task task) {
        deleteTaskUseCase.execute(task);
    }

    // UPDATE Completed
    public void toggleCompleted(Task task, boolean done) {
        task.setCompleted(done);
        repository.updateCompleted(task, done);
    }
}
