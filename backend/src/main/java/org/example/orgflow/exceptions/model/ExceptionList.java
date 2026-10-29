package org.example.orgflow.exceptions.model;

public class ExceptionList {
    public static final String RECORD_CREATION_FAILED = "RECORD_CREATION_FAILED";
    public static final String RECORD_UPDATE_FAILED = "RECORD_UPDATE_FAILED";
    public static final String RECORD_DELETE_FAILED = "RECORD_DELETE_FAILED";
    public static final String RECORD_READ_FAILED = "RECORD_READ_FAILED";
    public static final String RECORD_NOT_FOUND = "RECORD_NOT_FOUND";
    public static final String RECORD_DUPLICATION = "RECORD_DUPLICATION";

    public static final String LOV_IS_INACTIVE = "LOV_IS_INACTIVE";

    public static final String CACHE_NOT_FOUND = "CACHE_NOT_FOUND";
    public static final String CREATE_CACHE_RECORD_FAILED = "CREATE_CACHE_RECORD_FAILED";
    public static final String DELETE_CACHE_RECORD_FAILED = "DELETE_CACHE_RECORD_FAILED";
    public static final String CACHE_READ_FAILED = "CACHE_READ_FAILED";
    public static final String UPDATE_CACHE_RECORD_FAILED = "UPDATE_CACHE_RECORD_FAILED";

    public static final String INVALID_OPERTAION_TYPE = "INVALID_OPERTAION_TYPE";

    public static final String RECORD_API_KEY_DUPLICATION = "RECORD_API_KEY_DUPLICATION";
    public static final String RECORD_NAME_DUPLICATION = "RECORD_NAME_DUPLICATION";
    public static final String ROLE_RELATION_EXCEPTION = "ERROR_ROLE_RELATION";
    public static final String INVALID_OPERATION = "INVALID_OPERATION";
    public static final String DATA_INTIGRITY_VIOLATION = "exception.DataIntegrityViolationException";

    public static String getExceptions(int error) {

        switch (error) {
            case 400:
                return "EXCEPTION.BAD_REQUEST";

            case 401:
                return "EXCEPTION.UN_AUTHORIZED";

            case 403:
                return "EXCEPTION.FORBIDDEN";
            case 404:
                return "EXCEPTION.NOT_FOUND";
            case 405:
                return "EXCEPTION.METHOD_NOT_ALLOWED";
            case 406:
                return "EXCEPTION.NOT_ACCEPTED";
            case 409:
                return "EXCEPTION.CONFLICT";
            case 410:
                return "EXCEPTION.GONE";
            case 415:
                return "EXCEPTION.UNSUPPORTED_MEDIA_TYPE";
            case 422:
                return "EXCEPTION.UN_PROCESSABLE_ENTITY";
            case 429:
                return "EXCEPTION.TO_MANY_REQUESTS";
            case 500:
                return "exception.unexpected";
            case 501:
                return "exception.NOT_IMPLEMENTED";
            case 502:
                return "exception.BAD_GATEWAY";
            case 503:
                return "exception.SERVICE_UN_AVAILABLE";
            case 504:
                return "exception.GATEWAY_TIMEOUT";
            default:
                return "exception.unexpected";


        }
    }

}

