package com.example.manda.TransApi;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public void getTransResult(final TextView trans, String query, String from, String to) {
        HttpParams params = buildParams(query, from, to);
        KJHttp getTrans = new KJHttp();
        getTrans.get(TRANS_API_HOST, params, new HttpCallBack() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        ViewInject.longToast("请求成功");
                        try {
                            JSONObject tmp = new JSONObject(t);
                            String a = tmp.getJSONArray("trans_result").getJSONObject(0).getString("dst");
                            trans.setText(a);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        KJLoger.debug("log:" + t.toString());
                    }
                });
//        return String.valueOf(HttpGet.get(TRANS_API_HOST, params).getJSONArray("trans_result").getJSONObject(0).get("dst"));
    }

    private HttpParams buildParams(String query, String from, String to) {
        HttpParams params = new HttpParams();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
