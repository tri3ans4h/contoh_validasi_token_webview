package com.kutilangapp.contoh_validasi_token_webview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.addJavascriptInterface(new WebAppInterface(this), "Android");
        wv1.loadUrl("http://192.168.43.8/VideoGIS/public_html/ujian.html");

        wv1.evaluateJavascript("javascript: " +
                "updateFromAndroid(\"" + "Saya android"+ "\")", null);
    }
   public void onClick(View v){
        wv1.evaluateJavascript("javascript: " +
                "updateFromAndroid(\"" + "Saya android"+ "\")", null);
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
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
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
