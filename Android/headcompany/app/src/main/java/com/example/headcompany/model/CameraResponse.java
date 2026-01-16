package com.example.headcompany.model;

import java.util.List;

public class CameraResponse {

    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<Camera> items;

    // Getters
    public int getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public int getCurrentPage() { return currentPage; }
    public List<Camera> getItems() { return items; }
}
