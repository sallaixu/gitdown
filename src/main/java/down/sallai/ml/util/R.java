package down.sallai.ml.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.util.JSONPObject;

public  class R {
    int code = 200;
    String msg = "";
    Object data="";

    public int getCode() {
        return code;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public R setData(Object data) {
        this.data = data;
        return this;

    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
