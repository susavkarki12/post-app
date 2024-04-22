package com.one.spring_boot_rest_api.exception;

public class ResourceNotFound extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFound(String resourceName, long fieldValue, String fieldName) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }
    public String getResourceName() {
        return resourceName;
    }
    public String getFieldName() {
        return fieldName;
    }
    public long getFieldValue() {
        return fieldValue;
    }
}
