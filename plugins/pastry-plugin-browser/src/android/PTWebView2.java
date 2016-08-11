package asp.citic.ptframework.plugin.browser;

import android.app.Activity;

/**
 * Created by mj on 3/31/16.
 */
public class PTWebView2 extends PTWindowActivity {
    PTBrowserContainer browser;

    public void openUrl(String launchUrl){
        //open second webview, load into url
        browser.openUrl(launchUrl);
    }

    @Override
    protected void setAppViewTitle(String title) {
        super.setAppViewTitle(title);
        browser.setBrowserTitle();
    }

    @Override
    public void loadUrl(String launchUrl) {
        super.loadUrl(launchUrl);
    }

    @Override
    public boolean windowGoBack() {
        return super.windowGoBack();
    }

    @Override
    public Activity getCurActivity() {
        return browser.getCurActivity();
    }

    @Override
    public void dojs(String js) {
        super.dojs(js);
    }

    @Override
    public void goBack() {
        super.goBack();
        browser.goBack();
    }

    public void bindPTBrowser(PTBrowserContainer browser){
        this.browser = browser;
    }

    @Override
    protected PTBrowserContainer getBrowser() {
        return browser;
    }
}
