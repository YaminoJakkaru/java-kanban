package ru.yandex.practicum.Tasks;

public enum Type {
    TASK("task"), EPIC("epic"), SUBTASK("subtask");

    private final String tag;

    Type(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
