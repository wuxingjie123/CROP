package asp.citic.ptframework.plugin.browser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewEngine;
import org.apache.cordova.engine.SystemWebView;

import asp.citic.ptframework.common.PTConstant;

/**
 * Created by mj on 3/31/16.
 */
public class PTWindowActivity extends CordovaActivity{
    // The webview for our app
    static final String TAG = "PTWindowActivity";
    protected static final String baseUrl = "file://" + PTConstant.PATH_RELEASE;
    PTBrowserContainer browser;
    protected String appViewTitle;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    protected CallbackContext callback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadUrl(String launchUrl){
        super.loadUrl(launchUrl);
    }

    public Activity getCurActivity() {
        return null;
    }

    public void setCallback(CallbackContext callback){
        this.callback = callback;
    }

    public CallbackContext getCallback(){
        return this.callback;
    }

    public void loadData(final String url, final String html) {
        if (appView == null) {
            init();
        }
        // If keepRunning
        this.keepRunning = preferences.getBoolean("KeepRunning", true);
        //Log.d(TAG, "html====>" + html);

        int n = url==null?-1:url.lastIndexOf("/");
        String subPath = (n == -1)?"":url.substring(0, n + 1);
        String dataUrl = baseUrl+subPath;
        if(appView.getEngine().getView() instanceof SystemWebView){
            SystemWebView webView = (SystemWebView)appView.getEngine().getView();
            Log.d(TAG, "dataUrl====>" + dataUrl);
            webView.loadDataWithBaseURL(dataUrl, html, "text/html", "UTF-8",null);
            //webView.loadData(html, "text/html", "UTF-8");
        }
    }

    /**
     *
     * @param js
     */
    public void dojs(final String js){
    	if (appView == null) {
            init();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(appView.getEngine().getView() instanceof SystemWebView){
                    SystemWebView webView = (SystemWebView)appView.getEngine().getView();
                    webView.loadUrl(String.format("javascript:%s",js));
                }
            }
        });
    }

    public void openUrl(String launchUrl){
        //open second webview, load into url
    }

    public boolean windowGoBack(){
        boolean isCloseWindow = false;
        if (appView == null) {
            init();
        }
        if(appView.getEngine().getView() instanceof SystemWebView){
            SystemWebView webView = (SystemWebView)appView.getEngine().getView();
            String url = webView.getUrl().toString();
            if(url.contains("http") || url.contains("www")){
                if (appView.canGoBack()){
                    webView.goBack();
                }else {
                    isCloseWindow = true;
                }
            }else {
                //appView.sendJavascript("cordova.fireDocumentEvent('browser_back');");
                isCloseWindow = false;
            }
        }
        return isCloseWindow;
    }

    public void goBack(){
        //appView.sendJavascript("cordova.fireDocumentEvent('browser_back');");
    }

    public void bindPTBrowser(PTBrowserContainer browser){
        //bind container
    }

    protected void setAppViewTitle(String title){
        this.appViewTitle = title;

    }

    protected String getAppViewTitle(){
        return this.appViewTitle;
    }

    protected PTBrowserContainer getBrowser(){
        return null;
    }

    public CordovaWebView getAppView(){
        return appView;
    }

}
