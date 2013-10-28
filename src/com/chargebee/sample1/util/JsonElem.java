package com.chargebee.sample1.util;

import org.json.*;
import java.sql.Timestamp;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;

public class JsonElem {
    
    public static JsonElem loadCustomer(HttpServletRequest req) throws IOException {
        Integer custId = Integer.parseInt(req.getParameter("id"));
        String contextPath = req.getSession().getServletContext().getRealPath("/");
        JsonElem list = JsonElem.createFromFile(contextPath + "/data/customers.json");
        
        List<JsonElem> customers = list.nodes("customers");
        for (JsonElem cjson : customers) {
            if(custId.equals(cjson.integer("id"))) {
                return cjson;
            }
        }
        return null;
    }
    
    public static JsonElem createFromFile(String path) throws IOException {
        String jsonStr = FileUtils.readFileToString(new File(path));
        try {
            return new JsonElem(new JSONObject(jsonStr));
        } catch(JSONException jexp) {
            throw new RuntimeException(jexp);
        }
    }
    
    private JSONObject jobj;

    public JsonElem(JSONObject json) {
        this.jobj = json;
    }
    
    // underlying JSONObject instance
    public JSONObject obj() {
        return jobj;
    }
    
    public String str(String key) {
        String val = optStr(key);
        return nullCheck(key, val);
    }
    
    public Integer integer(String key) {
        Integer val = optInt(key);
        return nullCheck(key, val);
    }
    
    public Boolean bool(String key) {
        Boolean val = optBool(key);
        return nullCheck(key, val);
    }
    
    public Timestamp unixTime(String key) {
        Timestamp val = optUnixTime(key);
        return nullCheck(key, val);
    }
    
    public JsonElem node(String key) {
        JsonElem val = optNode(key);
        return nullCheck(key, val);
    }
    
    public List<JsonElem> nodes(String key) {
        List<JsonElem> val = optNodes(key);
        return nullCheck(key, val);
    }
    
    public String optStr(String key) {
        return jobj.optString(key, null);
    }
    
    public Integer optInt(String key) {
        String val = jobj.optString(key, null);
        return val == null ? null : Integer.valueOf(val);
    }
    
    public Boolean optBool(String key) {
        String val = jobj.optString(key, null);
        return val == null ? null : Boolean.valueOf(val);
    }
    
    public Timestamp optUnixTime(String key) {
        Integer val = jobj.optInt(key);
        return val == null ? null : new Timestamp(val * 1000l);
    }
    
    public JsonElem optNode(String key) {
        JSONObject obj = jobj.optJSONObject(key);
        return obj == null ? null : new JsonElem(obj);
    }
    
    public List<JsonElem> optNodes(String key) {
        JSONArray arr = jobj.optJSONArray(key);
        if(arr == null) {
            return null;
        }
        List<JsonElem> toRet = new ArrayList<JsonElem>(); 
        for (int i = 0; i < arr.length(); i++) {
            try {
                toRet.add(new JsonElem(arr.getJSONObject(i)));
            } catch(JSONException exp) {
                throw new RuntimeException(exp);
            }
        }
        return toRet;
    }
    
    public List<String> optStrList(String key) {
        JSONArray arr = jobj.optJSONArray(key);
        if(arr == null) {
            return null;
        }
        List<String> toRet = new ArrayList<String>(); 
        for (int i = 0; i < arr.length(); i++) {
            try {
                toRet.add(arr.getString(i));
            } catch(JSONException exp) {
                throw new RuntimeException(exp);
            }
        }
        return toRet;
    }
    
    private <T> T nullCheck(String key,T val) {
        if(val == null){
            throw new RuntimeException("The property [" + key + "] is not present ");
        }
        return val;
    }

    @Override
    public String toString() {
        return toString(2);
    }
    
    public String toString(int indent) {
        try {
            return jobj.toString(indent);
        } catch(JSONException exp) {
            throw new RuntimeException(exp);
        }
    }
}
