package com.example.personaltaskmanager.features.task_manager.domain.usecase;

import com.example.personaltaskmanager.features.task_manager.data.model.Task;
import com.example.personaltaskmanager.features.task_manager.data.repository.TaskRepository;

/**
 * UseCase thêm Task.
 * Giữ nguyên cấu trúc cũ, chỉ sửa kiểu trả về để phù hợp TaskRepository.
 */
public class AddTaskUseCase {

    private final TaskRepository repository;

    public AddTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * Trả về void vì TaskRepository.addTask() đang trả về void.
     */
    public void execute(Task task) {
        repository.addTask(task);
    }
}
