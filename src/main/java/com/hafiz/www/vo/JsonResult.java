package com.hafiz.www.vo;

/**
 * Desc:ajax请求的json结果
 * Created by hafiz.zhang on 2017/7/21.
 */
public class JsonResult {
    private Integer code = 200;         // 请求结果码
    private Boolean success = true;     // 请求结果
    private String msg;                 // 请求错误信息
    private Object data;                // 结果数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static JsonResult newInstanceSuccess() {
        return newInstanceSuccess(null);
    }

    public static JsonResult newInstanceSuccess(Object data) {
        JsonResult jr = new JsonResult();
        jr.setData(data);
        return jr;
    }

    public static JsonResult newInstanceFail(String msg) {
        JsonResult jr = new JsonResult();
        jr.setSuccess(false);
        jr.setMsg(msg);
        return jr;
    }
}
