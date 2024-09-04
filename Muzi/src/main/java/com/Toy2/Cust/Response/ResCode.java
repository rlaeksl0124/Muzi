package com.Toy2.Cust.Response;


public enum ResCode {
    /* 성공 에러코드 */
    SUCCESS_AUTH_EMAIL(200, "S001","인증번호가 이메일로 발송되었습니다."),
    SUCCESS_AUTH_OK(200, "S002", "인증 성공"),


    /* 실패 에러코드 */
    /* 잘못된 요청 */
    INVALID_PARAMETER(400, "E001", "잘못된 요청입니다. 다시 요청해주세요."),
    DISPLAY_NOT_FOUND(404, "E002", "존재하지않는 페이지 입니다."),

    /* 404 */
    ALREADY_USE_EMAIL(409, "U001", "이미 사용중인 이메일입니다."),
    DIFF_AUTH_EMAIL(409, "U002","잘못된 인증번호입니다 다시 입력해주세요"),
    EMAIL_NOTFOUNT(409, "U003", "해당 이메일로 사용자를 찾을수 없습니다."),
    EMAIL_ALREADY(409, "U004", "이미 존재하는 이메일입니다.."),

    /* 500 */
    SERVER_AUTH_EMAIL(500, "S001", "인증과정에서 오류가 발생하였습니다");


    private final int status;
    private final String code;
    private final String message;

    ResCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

