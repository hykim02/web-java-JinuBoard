package com.example.JinuBoard.entity;

public class Enum {
    public enum Category {
        feature_request,
        bug_report,
        etc
    }

    public enum Status {
        waiting,
        confirmed,
        in_progress,
        done
    }

    public enum Role {
        admin,
        user
    }
}
