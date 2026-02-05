package com.example.headcompany.model;

import org.tensorflow.lite.schema.Model;

import java.util.List;

public class IncidenceResponse extends Response {
    // ATTRIBUTES
    private List<Incidence> items;

    // GETTERS
    public List<Incidence> getItems() { return items; }
}
