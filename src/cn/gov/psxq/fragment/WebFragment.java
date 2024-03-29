package cn.gov.psxq.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import cn.gov.psxq.AppData;
import cn.gov.psxq.R;
import cn.gov.psxq.common.UIHelper;
import cn.gov.psxq.ui.BackHandledFragment;
import cn.gov.psxq.widget.ExtendedWebView;

public class WebFragment extends BackHandledFragment {

    ExtendedWebView            webView;
    private ValueCallback<Uri> mUploadMessage;
    private final static int   FILECHOOSER_RESULTCODE = 1;
    String                     title;
    String                     catalogName;
    ActionBar                  mActionBar;

    public void getResultData(int requestCode, int resultCode, Intent intent) {

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent
                .getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 初始化Handler
        View view = inflater.inflate(R.layout.web_fragment, null);
        getActivity().getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                    | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        webView = (ExtendedWebView) view.findViewById(R.id.web_detail_webview);
        catalogName = this.getArguments().getString("catalogName");
        title = this.getArguments().getString("title");
        FragmentManager fm = this.getFragmentManager();
        webView.loadUrl(AppData.urlList.get(catalogName));
        webView.setWebChromeClient(new WebChromeClient() {
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getParentFragment().startActivityForResult(Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE);

            }

            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            // For Android 3.0+  
            @SuppressWarnings("unchecked")
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getParentFragment().startActivityForResult(Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
            }

            //For Android 4.1  
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType,
                                        String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                getParentFragment().startActivityForResult(Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE);

            }
        });
        webView.setWebViewClient(UIHelper.getWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(false);

        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setSavePassword(true);

        webView.getSettings().setTextZoom(100);

        int minimumFontSize = 1;
        webView.getSettings().setMinimumFontSize(minimumFontSize);
        webView.getSettings().setMinimumLogicalFontSize(minimumFontSize);

        CookieManager.getInstance().setAcceptCookie(true);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setEnableSmoothTransition(true);

        // HTML5 API flags
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // HTML5 configuration settings.
        webView.getSettings().setAppCacheMaxSize(3 * 1024 * 1024);

        webView.getSettings().setAppCachePath(
            this.getActivity().getApplicationContext().getDir("appcache", Context.MODE_PRIVATE)
                .getPath());
        webView.getSettings().setDatabasePath(
            this.getActivity().getApplicationContext().getDir("databases", Context.MODE_PRIVATE)
                .getPath());
        webView.getSettings().setGeolocationDatabasePath(
            this.getActivity().getApplicationContext().getDir("geolocation", Context.MODE_PRIVATE)
                .getPath());
        webView.setDownloadListener(new MyDownloadListener());
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack(); //后退    
                        return true; //已处理    
                    }
                }
                return false;
            }
        });
        return view;
    }

    private class MyDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        //mLocationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mLocationClient.stop();
    };

    @Override
    public void onResume() {
        super.onResume();
        //mLocationClient.start();
    }

    @Override
    protected boolean onBackPressed() {
        return true;

    }
}
