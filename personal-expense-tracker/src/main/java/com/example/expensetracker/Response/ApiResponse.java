package com.example.expensetracker.Response;


import java.time.LocalDateTime;

public class ApiResponse<T> {
    private String message;
    private LocalDateTime timestamp;
    private T data;

    // Constructor
    public ApiResponse(String message, T data) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }
}
