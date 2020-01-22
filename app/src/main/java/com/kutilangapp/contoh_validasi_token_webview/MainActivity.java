package com.kutilangapp.contoh_validasi_token_webview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private WebView wv1;
    String TOKEN = "abc";
    boolean IS_TOKEN_VALID = false;
    boolean IS_TOKEN_CHECKED = false;
    EditText edTOKEN;
    TextView tvSTATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edTOKEN = findViewById(R.id.edTOKEN);
        tvSTATUS = findViewById(R.id.tvSTATUS);
        wv1 = (WebView) findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.addJavascriptInterface(new WebAppInterface(this), "Android");
        wv1.loadUrl("http://192.168.43.8/VideoGIS/public_html/ujian.html");


    }

    public void onClick(View v) {
        //   wv1.evaluateJavascript("javascript: " +
        //           "updateFromAndroid(\"" + "Saya android" + "\")", null);

        wv1.evaluateJavascript("javascript: " +
                "check_token(\"" + edTOKEN.getText() + "\")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                CB_check_token(value);
            }
        });
    }

    void CB_check_token(String value) {
        try {
            JSONObject r = new JSONObject(value);
            if (r.getBoolean("status") == true) {
                tvSTATUS.setText("VALID");
                IS_TOKEN_VALID = true;
                wv1.loadUrl("http://192.168.43.8/VideoGIS/public_html/ujian.html");
            } else {
                tvSTATUS.setText("TIDAK VALID");
                IS_TOKEN_VALID = false;
                wv1.loadUrl("http://192.168.43.8/VideoGIS/public_html/tidak_valid.html");
            }
            edTOKEN.setText(r.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        //  wv1.removeJavascriptInterface(JAVASCRIPT_OBJ);
        super.onDestroy();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // do your stuff here
            Log.wtf("TOKEN", url);
            if (IS_TOKEN_CHECKED == false) {//jika belum pernah cek token
                if (url.indexOf("ujian") > 0) {
                    wv1.evaluateJavascript("javascript: " +
                            "check_token(\"" + TOKEN + "\")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.wtf("RECEIVE", value);
                            CB_check_token(value);
                        }
                    });

                    IS_TOKEN_CHECKED = true;
                }
            }
        }
    }

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            //NextLink = toast;
            //  adRequest = new AdRequest.Builder().build();
            // mAdView.loadAd(adRequest);
            //Intent i = new Intent(MainActivity.this, AdsActivity.class);
            //startActivity(i);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    /*if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }else{
                        Toast.makeText(mContext,"Ads Cannot Load", Toast.LENGTH_SHORT).show();
                    }
                    // Toast.makeText(mContext,"Ads Cannot Load", Toast.LENGTH_SHORT).show();

                    if(!NextLink.isEmpty()){
                        wv1.loadUrl("http://192.168.43.8/webviewadsinterstate/"+NextLink);
                        NextLink = "";
                    }*/
                }
            });
            /*
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
        }
    }
}
