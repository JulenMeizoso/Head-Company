package com.example.headcompany.model;

import java.util.List;

public class UsersResponse {

    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<Model.Usuario> items;

    // Getters
    public int getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }
    public int getCurrentPage() { return currentPage; }
    public List<Model.Usuario> getItems() { return items; }
}
