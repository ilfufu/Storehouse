package myproject.storehousebackend.common;

import lombok.Data;

@Data
public class Result {

    private int code; // code 200/400
    private String msg; // success/fail
    private Long total; // record num
    private Object data; // data

    public static Result fail() {
        return result(400, "fail", null, null);
    }

    public static Result suc() {
        return result(200, "success", null, null);
    }
    public static Result suc(Object data, Long total) {
        return result(200, "success", total, data);
    }

    private static Result result(int code, String msg, Long total, Object data) {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }
}
