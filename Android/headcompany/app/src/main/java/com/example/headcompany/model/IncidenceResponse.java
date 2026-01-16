package com.example.headcompany.model;

import org.tensorflow.lite.schema.Model;

import java.util.List;

public class IncidenceResponse {

    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<Incidence> items;

    // Getters
    public int getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public int getCurrentPage() { return currentPage; }
    public List<Incidence> getItems() { return items; }
}
