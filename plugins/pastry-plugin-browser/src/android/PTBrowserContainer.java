package asp.citic.ptframework.plugin.browser;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Stack;

import asp.citic.ptframework.common.exception.PTException;
import asp.citic.ptframework.common.util.PTDeviceUtil;
import asp.citic.ptframework.templatemanager.PTTemplateManager;

/**
 * Created by mj on 3/31/16.
 */
public class PTBrowserContainer extends Activity {
    public static final String TAG = "PTBrowserContainer";
    private LocalActivityManager localActivityManager = null;
    protected Stack<PTWindowActivity> webStack = null;
    protected String[] mTags = {"first", "second", "third"};
    protected RelativeLayout rootView;
    protected RelativeLayout titleView;
    protected FrameLayout container;
    private int mWebViewCount = 0;
    private final static int MAX_WEBVIEW_COUNT = 3;
    private View view1, view2, view3;
    protected View titleLayout;
    protected ImageView back;
    protected TextView titleBar = null;
    protected Resources activityRes;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRes = this.getResources();
        rootView = new RelativeLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(params);
        rootView.setBackgroundColor(Color.TRANSPARENT);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container = new FrameLayout(this);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, PTDeviceUtil.dip2px(42));
        params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleView = new RelativeLayout(this);

        titleView.setBackgroundColor(Color.DKGRAY);
        titleView.setId(Integer.valueOf(1));
        params1.addRule(RelativeLayout.BELOW, 1);
        container.setLayoutParams(params1);
        titleView.setLayoutParams(params2);
        rootView.addView(container);
        rootView.addView(titleView);
        setContentView(rootView);
        localActivityManager = new LocalActivityManager(this, true);
        localActivityManager.dispatchCreate(savedInstanceState);
        //container = (FrameLayout) findViewById(R.id.container);
        init();
        initTitleBar();
    }

    private void init() {
        webStack = new Stack<PTWindowActivity>();
        Intent intent1 = new Intent(PTBrowserContainer.this, PTWebView1.class);
        view1 = getView(mTags[0], intent1);
        Intent intent2 = new Intent(PTBrowserContainer.this, PTWebView2.class);
        view2 = getView(mTags[1], intent2);
        Intent intent3 = new Intent(PTBrowserContainer.this, PTWebView3.class);
        view3 = getView(mTags[2], intent3);
        if (webStack.size() == 0) {
            PTWindowActivity activity = (PTWindowActivity) localActivityManager.getActivity(mTags[0]);
            container.addView(view1);
            webStack.push(activity);
            ((PTWebView1) activity).bindPTBrowser(this);
            mWebViewCount++;
        }
        //setBrowserTitle();
    }

    protected void initTitleBar() {
        //back = (ImageView) findViewById(R.id.back);
        back = new ImageView(this);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(PTDeviceUtil.dip2px(40), PTDeviceUtil.dip2px(40));
        params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params1.addRule(RelativeLayout.CENTER_VERTICAL);
        int backResId = activityRes.getIdentifier("back", "drawable", this.getPackageName());
        Drawable backIcon = activityRes.getDrawable(backResId);
        back.setImageDrawable(backIcon);
        back.setScaleType(ImageView.ScaleType.FIT_CENTER);
        back.setPadding(PTDeviceUtil.dip2px(12), 0, 0, 0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurActivity() instanceof PTWindowActivity){
                    Log.d(TAG, "goback" + mWebViewCount);
                    Log.d(TAG, "activity name====>" + getCurActivity().getLocalClassName().toString());
                    ((PTWindowActivity) getCurActivity()).dojs("cordova.fireDocumentEvent('browser_back');");
                }
            }
        });
        //titleBar = (TextView) findViewById(R.id.title);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.CENTER_IN_PARENT);
        titleBar = new TextView(this);
        titleBar.setText("PTDemo");
        back.setLayoutParams(params1);
        titleBar.setLayoutParams(params2);
        titleView.addView(back);
        titleView.addView(titleBar);
        //this.container.addView(titleLayout, 1);
    }

    public void loadUrl(String launchUrl) {
        if (webStack != null && webStack.size() > 0) {
            webStack.peek().loadUrl(launchUrl);
            setBrowserTitle();
        }
    }

    public void loadData(String url, String html){
        if (webStack != null && webStack.size() > 0) {
            //Log.d(TAG, "html====>" + html);
            webStack.peek().loadData(url, html);
        }
    }

    public void openUrl(String launchUrl) {
        PTWindowActivity activity;
        Log.d(TAG, "launchUrl:" + launchUrl);
        if (mWebViewCount > MAX_WEBVIEW_COUNT) {
            //打开的CBUIWebview个数超过限制==请联系客服
            return;
        }
        Log.d(TAG, "mWebViewCount:" + mWebViewCount);
        setBrowserTitle();
        switch (mWebViewCount) {
            case 0:
                activity = (PTWindowActivity) localActivityManager.getActivity(mTags[0]);
                ((PTWebView1) activity).bindPTBrowser(this);
                container.addView(view1);
                webStack.push(activity);
                mWebViewCount++;
                break;
            case 1:
                activity = (PTWindowActivity) localActivityManager.getActivity(mTags[1]);
                ((PTWebView2) activity).bindPTBrowser(this);
                container.addView(view2);
                webStack.push(activity);
                mWebViewCount++;
                break;
            case 2:
                activity = (PTWindowActivity) localActivityManager.getActivity(mTags[2]);
                ((PTWebView3) activity).bindPTBrowser(this);
                container.addView(view3);
                webStack.push(activity);
                mWebViewCount++;
                break;
            case 3:
                //CBUIWebview个数已达到最大数量限制==请联系客服
                break;
        }
        try {
            final String html = PTTemplateManager.getTemplateByUrl(launchUrl, new PTTemplateManager.TemplateLoadedListener() {

                @Override
                public void onFinish(String url, String html) {
                    // TODO Auto-generated method stub
                    //Log.d(TAG, "html====>" + html);
                    webStack.peek().loadData(url, html);
                }
            });
        } catch (PTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //webStack.peek().loadUrl(launchUrl);
    }

    public Stack<PTWindowActivity> getWebStack(){
        if(webStack == null)
            return null;
        return webStack;
    }

    private View getView(String id, Intent intent) {
        return localActivityManager.startActivity(id, intent).getDecorView();
    }

    public int getWebViewCount() {
        return mWebViewCount;
    }

    public Activity getCurActivity() {
        return webStack.peek();
    }

    public void goBack() {
        if (mWebViewCount == 0) {
            Toast.makeText(this, "已经到达最后！", Toast.LENGTH_LONG).show();
            return;
        }
        String tag = mTags[mWebViewCount - 1];
        Log.d(TAG, "goback=================>" + mWebViewCount);
        webStack.peek().loadData("", "");
        switch (mWebViewCount){
            case 1:
                finish();
                break;
            case 2:
                Log.d(TAG, "webview2 goback!");
                container.removeView(view2);
                webStack.pop();
                mWebViewCount--;
                break;
            case 3:
                Log.d(TAG, "webview3 goback!");
                container.removeView(view3);
                webStack.pop();
                mWebViewCount--;
                break;
        }
        setBrowserTitle();
//        PTWindowActivity activity = (PTWindowActivity) localActivityManager.getActivity(tag);
//        activity.goBack();
    }

    protected void setBrowserTitle(){
        //this.title.setText(title);
        this.titleBar.setText(((PTWindowActivity) getCurActivity()).getAppViewTitle());
    }

}
