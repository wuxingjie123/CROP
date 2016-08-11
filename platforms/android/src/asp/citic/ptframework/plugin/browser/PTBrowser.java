package asp.citic.ptframework.plugin.browser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.engine.SystemWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by mj on 3/28/16.
 */
public class PTBrowser extends CordovaPlugin {

    private static String TAG = "PTBrowser";

    @Override
    public boolean execute(String action, final JSONArray rawArgs, final CallbackContext callbackContext) throws JSONException {
        super.execute(action, rawArgs, callbackContext);
        final CordovaInterface cordova  = this.cordova;
        final Activity activity = cordova.getActivity();
        PTWindowActivity webview = (PTWindowActivity)activity;
        String activityName = activity.getLocalClassName().toString();

        //CordovaWebView app = (SystemWebView) (((PTWindowActivity) (((PTWindowActivity) cordova.getActivity()).getCurActivity())).appView;
        Log.d(TAG, "activityName====>" + activityName);
        Log.d(TAG, "action:" + action);
        if("jsOpenWindow".equals(action)){
            //showToast(cordova.getActivity().getLocalClassName(), 1);
            //launchUrl = "file:///android_asset/www/main/demo/test1.html";
            JSONObject json = new JSONObject(rawArgs.getString(0));
            Log.d(TAG, "json:" + json);
            String dataStr = json.getString("data");
            JSONObject data = new JSONObject(dataStr);
            final String launchUrl = "main/demo/" + data.getString("url");
            final String param = data.getString("param");
            final String js = ";(function() {"
                    + "window.fw_param = " + param + ";"
                    + "})();";
            Log.d(TAG, "activity:" + activity.getLocalClassName());
            Log.d(TAG, "launchUrl:" + launchUrl);
            Log.d(TAG, "param:" + param);
            PTWindowActivity ptWebview = getBrowser().getWebStack().peek();
            Log.d(TAG, "webview name:" + ptWebview.getLocalClassName());
            ptWebview.setCallback(callbackContext);
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((PTWindowActivity) cordova.getActivity()).openUrl(launchUrl);
                    final PTWindowActivity curPTWebView = (PTWindowActivity) (((PTWindowActivity) cordova.getActivity()).getCurActivity());
                    curPTWebView.dojs(js);
                }
            });
        }else if ("jsSetBrowserTitle".equals(action)){
            String json = rawArgs.getString(0);
            JSONObject jsonObj = new JSONObject(json);
            String data = jsonObj.getString("data");
            JSONObject dataObj = new JSONObject(data);
            final String title = dataObj.getString("title");
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "activity name====>:" + activity.getLocalClassName());
                    ((PTWindowActivity) activity).setAppViewTitle(title);
                }
            });
        }else if("jsCloseWindow".equals(action)){
            Log.d(TAG, "jsCloseWindow:" + "====================>" + rawArgs.getString(0));
            JSONObject jsonObj = new JSONObject(rawArgs.getString(0));
            final String data = jsonObj.getString("data");
            Log.d(TAG, "data result==========>" + data);
            if (data != null && !"".equals(data)){
                JSONObject dataObj = new JSONObject(data);
                String param = "";
                if(dataObj.has("param")){
                    param = dataObj.getString("param");
                    final JSONObject resultObj = new JSONObject();
                    resultObj.put("data", dataObj.getJSONObject("param"));
                    Log.d(TAG, "close result==========>" + resultObj.toString());
                    this.cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "window can go back:" + ((PTWindowActivity) activity).windowGoBack());
                            Log.d(TAG, "goback=====================>");
                            ((PTWindowActivity) activity).goBack();
                            Log.d(TAG, "close======================>" + getBrowser().getWebStack().peek().getLocalClassName());
                            if(!"".equals(data)){
                                getBrowser().getWebStack().peek().getCallback().success(resultObj);
                            }

//                    if (((PTWindowActivity) activity).windowGoBack()) {
//
//                    }
                        }
                    });
                }else {
                    Log.d(TAG, "param is null==========>");
                    this.cordova.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((PTWindowActivity) activity).goBack();
                        }
                    });
                }


            }else {
                Log.d(TAG, "data is null==========>");
                this.cordova.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((PTWindowActivity) activity).goBack();
                    }
                });

            }


            return true;
        }else if("jsLoadWindowPage".equals(action)){
            Log.d(TAG, "jsLoadWindowPage:" + "====================>" + rawArgs.getString(0));
            String json = rawArgs.getString(0);
            JSONObject jsonObj = new JSONObject(json);
            String data = jsonObj.getString("data");
            JSONObject dataObj = new JSONObject(data);
            final String title = dataObj.getString("title");
            this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //((PTWindowActivity)activity).loadUrl(rawArgs.getString(0));
                }
            });
        }
        //callbackContext.success();
        return true;
    }

    protected PTBrowserContainer getBrowser(){
        return ((PTWindowActivity)this.cordova.getActivity()).getBrowser();
    }

    public void showToast(String text, int type){
        CordovaInterface cordova  = this.cordova;
        if(type == 0){
            Toast.makeText(cordova.getActivity(), text, Toast.LENGTH_SHORT).show();
        }else if (type == 1){
            Toast.makeText(cordova.getActivity(), text, Toast.LENGTH_LONG).show();
        }
    }
}
