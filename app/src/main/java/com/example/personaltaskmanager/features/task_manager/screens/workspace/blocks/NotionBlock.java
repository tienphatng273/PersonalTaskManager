package com.example.personaltaskmanager.features.task_manager.screens.workspace.blocks;

public class NotionBlock {

    public enum Type {
        PARAGRAPH,
        TODO,
        BULLET,
        DIVIDER
    }

    public String id;
    public Type type;
    public String text;
    public boolean isChecked;

    public NotionBlock(String id, Type type, String text, boolean isChecked) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.isChecked = isChecked;
    }
}
