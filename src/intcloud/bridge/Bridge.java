package intcloud.bridge;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;


   
public class Bridge extends Activity {
	WebView webView;
 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        
        //Inject WebAppInterface methods into Web page by having Interface 'bridge' 
        webView.addJavascriptInterface(new bridge_protocol(this), "bridge");
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/www/index.html");
                

    }
    
 public void button1_onclick(View view) {
	 js_call(webView, "Javascript_function('this is from the Java end')");

	
    }

public void js_call(WebView webView, String jsString) {
		final String webUrl = "javascript:" +  jsString;
		 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
	        webView.evaluateJavascript(jsString, null);
	        System.out.println(jsString);
	    } else {
	        webView.loadUrl(webUrl);
	        System.out.println("old way");
	    }
	}
	
	// class to comm with JS	
public class bridge_protocol {
        Context mContext;

        bridge_protocol(Context c) {
            mContext = c;
        }

        //  Show Toast Message
        // @param toast as String
         
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
  
        }
 
	}
}

