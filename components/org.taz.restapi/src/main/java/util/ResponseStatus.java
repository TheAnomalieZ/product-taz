package util;

public enum ResponseStatus {
    SUCCESS("S1000", "Success"),
    SERVER_ERROR("E1001", "Internal Server Error"),
    INVALID_FILE("E1002", "Not a Valid Image"),
    EMPTY_FILE("E1003", "File is Empty"),
    ERROR("E1000", "Unknown error occurred in operation");


    private final String statusCode;
    private final String statusDescription;

    ResponseStatus(String statusCode, String successDescription) {
        this.statusCode = statusCode;
        this.statusDescription = successDescription;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public boolean isSuccess() {
        return this.statusCode.equals(ResponseStatus.SUCCESS.statusCode);
    }

}
