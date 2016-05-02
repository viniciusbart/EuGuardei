package com.pineapple.euguardei;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vinicius on 21/04/16.
 */
public class ParseJSON {

    public static String[] descs;
    public static String[] dates;
    public static String[] ids;
    public static String[] locals;

    public static final String JSON_ARRAY = "list";
    public static final String KEY_DESC = "desc_item";
    public static final String KEY_DATE = "date_item";
    public static final String KEY_ID = "id_item";
    public static final String KEY_LOCAL = "local_item";

    private JSONArray jsonArray = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray(JSON_ARRAY);

            descs = new String[jsonArray.length()];
            dates = new String[jsonArray.length()];
            ids = new String[jsonArray.length()];

            for(int i = 0; i< jsonArray.length(); i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                descs[i] = jo.getString(KEY_DESC);
                dates[i] = jo.getString(KEY_DATE);
                ids[i] = jo.getString(KEY_ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void toViewJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray(JSON_ARRAY);

            descs = new String[jsonArray.length()];
            dates = new String[jsonArray.length()];
            locals = new String[jsonArray.length()];

            for(int i = 0; i< jsonArray.length(); i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                descs[i] = jo.getString(KEY_DESC);
                dates[i] = jo.getString(KEY_DATE);
                locals[i] = jo.getString(KEY_LOCAL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
