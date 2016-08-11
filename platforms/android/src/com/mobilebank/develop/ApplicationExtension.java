package com.mobilebank.develop;

import android.app.Application;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import asp.citic.ptframework.PTFramework;
import asp.citic.ptframework.PTFrameworkListener;
import asp.citic.ptframework.common.util.PTAppUtil;
import asp.citic.ptframework.common.util.PTDeviceIdentification;
import asp.citic.ptframework.common.util.PTDeviceUtil;
import asp.citic.ptframework.common.util.PTJSONBusinessUtil;
import asp.citic.ptframework.messagecenter.PTMessageCenter;
import asp.citic.ptframework.security.PTHandshakeDataProvider;
import asp.citic.ptframework.security.PTSessionManager;
import asp.citic.ptframework.securitykeyboard.PTInputEncryptorManager;
import asp.citic.ptframework.securitykeyboard.impl.PTDefaultInputEncryptor;
import asp.citic.ptframework.securitykeyboard.impl.PTDefaultSecurityKeyboard;

/**
 * Created by mj on 4/20/16.
 */
public class ApplicationExtension extends Application implements PTHandshakeDataProvider {

    private static final String TAG = "ApplicationExtension";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("application", "onCreate");
        PTJSONBusinessUtil.INSTANCE.setJSONBusinessProvider(new PTJSONBusinessUtil.JSONBusinessProvider() {
            /** 成功报文 */
            @Override
            public JSONObject getSuccessBusiness() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("RETCODE", "AAAAAAA");
                } catch (JSONException e) {
                    //CBLogger.t(e);
                }
                return new JSONObject();
            }

            /** 失败报文 */
            @Override
            public JSONObject getErrorBusiness(String code, String msg) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("RETCODE", code);
                    jsonObject.put("RETMSG", msg);
                } catch (JSONException e) {
                    //CBLogger.t(e);
                }
                return new JSONObject();
            }

            /** 判断成功条件 */
            @Override
            public boolean isSuccess(JSONObject data) {
                return "AAAAAAA".equals(data.optString("RETCODE"));
            }
        });
        PTFramework.initialization(this);
        PTSessionManager.INSTANCE.setHttpTimeout(60 * 1000);
        PTFramework.setSecurityKeyboard(PTDefaultSecurityKeyboard.INSTANCE);
        /** 注册加密机 */
        PTInputEncryptorManager.registEncryptor(PTInputEncryptorManager.DEFAULT_ENCRYPTOR, PTDefaultInputEncryptor.INSTANCE);
        PTFramework.addListener(new PTFrameworkListener.SessionEventListener() {

            @Override
            public void onSessionEvent(int event, Object data) {
                // TODO Auto-generated method stub
                Log.d(TAG, "event====>" + event);
                if (event == PTMessageCenter.EVENT_HANDSHAKESUCCESS) {
                    Log.d(TAG, "data====>" + data.toString());
                    Log.d(TAG, "session state====>" + PTFramework.getHandshakeState());
                }
            }
        });
    }

    @Override
    public JSONObject getHandshakeData() {
        // 有网络
        JSONObject jsonBusiness = new JSONObject();
        try {
            jsonBusiness.put("sys-serial", PTDeviceIdentification.getAssembleEquipmentId());
            jsonBusiness.put("ser-version", "1.3");
            jsonBusiness.put("sys-version", "1.3");

            jsonBusiness.put("sys-client", PTDeviceUtil.getDeviceType());
            jsonBusiness.put("client-version", PTAppUtil.getAppVersionName());

            jsonBusiness.put("sys-screenw", "320");
            jsonBusiness.put("sys-screenh", "480");
            jsonBusiness.put("var-city", "");
            jsonBusiness.put("shake-flag", "0");
            jsonBusiness.put("user-guide", "0");

            // LOGREVINF1 品牌|型号|操作系统|OS版本
            String LOGREVINF1 = PTDeviceUtil.getPhonePhoneManuFacturer() + "|" + PTDeviceUtil.getPhoneModel() + "|" + "Android" + "|"
                    + PTDeviceUtil.getPhoneOSVersion();
            jsonBusiness.put("LOGREVINF1", LOGREVINF1);

            // LOGREVINF2 浏览器内核|浏览器内核版本|网络类型|运营商信息
            String LOGREVINF2 = PTDeviceUtil.getBrowserCoreVersion() + "|" + PTDeviceUtil.getBrowserCoreVersion() + "|" + PTDeviceUtil.getNetworkType() + "|"
                    + PTDeviceUtil.getPhoneISP();
            jsonBusiness.put("LOGREVINF2", LOGREVINF2);
        } catch (Exception e) {
//			CBLogger.t(e);
        }
        return jsonBusiness;
    }
}
