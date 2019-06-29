package com.rabobank.customer.model;

public class ValidationResult {

    private int reference;
    private String description;

    public ValidationResult(final int reference, final String description) {
        this.reference = reference;
        this.description = description;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
