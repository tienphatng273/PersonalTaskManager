package com.example.personaltaskmanager.features.task_manager.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

/**
 * Entity Task lưu trong Room Database.
 * Các trường:
 *  - id (int)
 *  - title (String)
 *  - description (String)
 *  - createdAt (long)
 *  - isCompleted (boolean)
 *  - deadline (long)
 *
 * Giữ nguyên cấu trúc cũ, chỉ bổ sung trường deadline.
 */
@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public long createdAt;

    // Trạng thái hoàn thành
    public boolean isCompleted = false;

    // Deadline dạng timestamp (millis)
    public long deadline = 0L;

    // ==== CONSTRUCTOR CHÍNH CHO ROOM ====
    public Task(String title, String description, long createdAt, long deadline) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.isCompleted = false;
    }

    // ==== CONSTRUCTOR PHỤ — KHÔNG CHO ROOM DÙNG ====
    @Ignore
    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = System.currentTimeMillis();
        this.deadline = System.currentTimeMillis(); // mặc định deadline = hôm nay
        this.isCompleted = false;
    }

    // ==== GETTER ====
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public long getDeadline() {
        return deadline;
    }

    // ==== SETTER ====
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}
