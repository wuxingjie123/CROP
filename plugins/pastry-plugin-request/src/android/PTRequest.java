package asp.citic.ptframework.plugin.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import asp.citic.ptframework.common.exception.PTCommunicationException;
import asp.citic.ptframework.common.exception.PTException;
import asp.citic.ptframework.communication.PTCommunicationHelper;
import asp.citic.ptframework.security.PTComPackage;

/**
 * Created by mj on 4/26/16.
 */
public class PTRequest extends CordovaPlugin {
    static final String TAG = "PTRequest";
    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.d(TAG, "action====>" + action);
        Log.d(TAG, "args====>" + args.toString());
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = args.getString(0);
                    JSONObject jsonObj = new JSONObject(json);
                    String url = jsonObj.getString("url");
                    String data = jsonObj.getString("data");
                    PTComPackage pkg = null;
                    JSONObject jsonBusiness;

                    jsonBusiness = new JSONObject(data);
                    final JSONObject response = new JSONObject();
                    HttpPost httpPost = PTCommunicationHelper.createHttpPost(url, jsonBusiness);
                    pkg = PTCommunicationHelper.execHttpPost(httpPost);
                    jsonBusiness = new JSONObject( new String(pkg.getDataPackage().getBusiness()));
                    response.put("data", jsonBusiness);
                    callbackContext.success(response);
                } catch (PTCommunicationException e) {
                    e.printStackTrace();
                } catch (PTException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }
}
