package com.example.fredcrary.ad_340_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.List;

public class XmlActivity extends ToolBarClass {
    private static final String TAG = XmlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);

        // Set up the toolbar
        Toolbar appToolBar = (Toolbar) findViewById(R.id.xmlToolbar);
        appToolBar.inflateMenu(R.menu.toolbar);
        setSupportActionBar(appToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.mCurrentPageId = R.id.xmlAction;    // Remove this page from the toolbar

        // Set up the Volley RequestQueue
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024); // 1MB cache
        Network network = new BasicNetwork(new HurlStack());    // Use HTTP connection
        mRequestQueue = new RequestQueue(cache, network);       // Initiate the RequestQueue
        mRequestQueue.start();                                  // Start the queue

        String url = "http://www.STARparent.com/AD340/AD340.xml";

        // Send the request and handle the response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showBooks(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // from www.programcreek.com/java-api-examples/index.php
                        String errMsg = "Volley returned an error:\n\n";
                        if(error.networkResponse == null) {
                            errMsg += error.getMessage();
                        } else {
                            errMsg += error.getMessage() + ", status "
                                    + error.networkResponse.statusCode
                                    + " - " + error.networkResponse.toString();
                        }
                        ((TextView) findViewById(R.id.xmlMsg)).setText(errMsg);
                    }
                });
        mRequestQueue.add(stringRequest);
    }

    public void showBooks(String xmlBookList) {
        try {
            XmlParser parser = new XmlParser();
            List<XmlParser.Entry> bookList =
                    parser.parse(new ByteArrayInputStream(xmlBookList.getBytes()));
            ((TextView) findViewById(R.id.xmlMsg)).setText("XML parsing completed");
        } catch (Exception e) {
            String errMsg = "XML parsing threw an exception:\n\n" + e.getMessage();
            ((TextView) findViewById(R.id.xmlMsg)).setText(errMsg);
        }
    }
}
