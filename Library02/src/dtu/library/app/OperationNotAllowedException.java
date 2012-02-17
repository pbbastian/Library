package dtu.library.app;

public class OperationNotAllowedException extends Exception {
    private String operation;
    
    public OperationNotAllowedException(String message, String operation) {
        super(message);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
