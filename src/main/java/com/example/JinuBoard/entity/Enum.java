package com.example.JinuBoard.entity;

public class Enum {
    public enum Category {
        FEATURE_REQUEST,
        BUG_REPORT,
        ETC
    }

    public enum Status {
        WAITING,
        CONFIRMED,
        IN_PROGRESS,
        DONE
    }

    public enum Role {
        ADMIN,
        USER
    }
}
