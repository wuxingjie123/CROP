package asp.citic.ptframework.plugin.browser;

import android.app.Activity;
import android.util.Log;

/**
 * Created by mj on 3/31/16.
 */
public class PTWebView1 extends PTWindowActivity {

    PTBrowserContainer browser;

    @Override
    public void loadUrl(String launchUrl) {
        super.loadUrl(launchUrl);
    }

    @Override
    public void openUrl(String launchUrl) {
        super.openUrl(launchUrl);
        browser.openUrl(launchUrl);
    }

    @Override
    public Activity getCurActivity() {
        //return super.getCurActivity();
        return browser.getCurActivity();
    }

    @Override
    public void dojs(String js) {
        super.dojs(js);
    }

    @Override
    protected void setAppViewTitle(String title) {
        Log.d("PTWebView1", "appViewTitle:" + title);
        super.setAppViewTitle(title);
        browser.setBrowserTitle();
    }

    @Override
    public boolean windowGoBack() {
        return super.windowGoBack();

    }

    @Override
    public void goBack() {
        super.goBack();
        browser.goBack();
    }

    @Override
    protected PTBrowserContainer getBrowser() {
        return browser;
    }

    @Override
    public void bindPTBrowser(PTBrowserContainer browser) {
        super.bindPTBrowser(browser);
        this.browser = browser;
    }
}
