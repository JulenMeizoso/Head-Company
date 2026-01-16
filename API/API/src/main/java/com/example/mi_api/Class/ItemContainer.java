package com.example.mi_api.Class;

import java.util.List;

public class ItemContainer<T extends ShowItem> {
    private int totalItems;
    private int totalPages;
    private int currentPage;
    private List<T> items;

    // Constructor
    public ItemContainer(int totalItems, int totalPages, int currentPage, List<T> items) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
		this.items = items;
	}

    // Getters
    public int getTotalItems() { return totalItems; }
	public int getTotalPages() { return totalPages; }
    public int getCurrentPage() { return currentPage; }
    public List<T> getItems() { return items; }
}