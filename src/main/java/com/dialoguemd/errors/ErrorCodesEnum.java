package com.dialoguemd.errors;

public enum ErrorCodesEnum {
    DUPLICATE_EMAIL(1001),
    PATIENT_NOT_FOUND(1002),
    FIELD_MISSING(1003),
    BAD_FORMAT(1004),
    SERVER_ERROR(1005);
 
    public final int code;
 
    private ErrorCodesEnum(int code) {
        this.code = code;
    }
    
}
