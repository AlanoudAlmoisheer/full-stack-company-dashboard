package org.example.orgflow.exceptions.model;

public class EntityNotFoundException extends BusinessException {
    private String id;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
