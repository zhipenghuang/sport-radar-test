package com.yunmu.uof.dto;

import lombok.Data;

/**
 * Created by pandazhong on 18/2/26.
 */
@Data
public class BaseResponse {

    private int code;
    private String msg;

    public static final int ERROR_CODE_SUCCESSFUL = 0;
    public static final int ERROR_CODE_FORMAT_ERROR = 400;
    public static final int ERROR_CODE_NOT_FOUND = 404;
    public static final int ERROR_CODE_SERVER_IS_NOT_AVAILABLE = 502;
}
