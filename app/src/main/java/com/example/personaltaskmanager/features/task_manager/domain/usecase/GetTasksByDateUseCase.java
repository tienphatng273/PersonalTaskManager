package com.example.personaltaskmanager.features.task_manager.domain.usecase;

import androidx.lifecycle.LiveData;

import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.data.repository.TaskRepository;

import java.util.List;

/**
 * UseCase lấy Task theo ngày (deadline range).
 */
public class GetTasksByDateUseCase {

    private final TaskRepository repository;

    public GetTasksByDateUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Task>> execute(long start, long end) {
        return repository.getTasksByDate(start, end);
    }
}
