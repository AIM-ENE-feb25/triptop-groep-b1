package org.prototype.demo.facade;

public abstract class Response {
    // Base class for all service responses
    // Add common response properties here if needed

    public boolean isValid() {
        return true; // Default implementation, can be overridden by subclasses
    }
} 