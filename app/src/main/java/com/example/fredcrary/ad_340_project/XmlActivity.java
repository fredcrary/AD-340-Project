package com.example.fredcrary.ad_340_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // For the recycler display
    private RecyclerView bookRecyclerView;
    private LinearLayoutManager mLayoutManager;
    List<XmlParser.Entry> bookList;

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

        // Set a display to overwrite the default "TextView" display
        ((TextView) findViewById(R.id.xmlMsg)).setText("Working . . .");

        // Set up the Volley RequestQueue
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cache
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
                        if (error.networkResponse == null) {
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
            bookList = parser.parse(new ByteArrayInputStream(xmlBookList.getBytes()));


/*
            String msg = "XML parsing completed\n\n";
            msg += "\n" + bookList.size() + " entries\n";
            msg += "\n" + bookList.get(71).title;
            msg += "\n" + bookList.get(71).author;
            msg += "\n" + bookList.get(71).isbn;
            msg += "\n" + bookList.get(71).price;
            msg += "\n" + bookList.get(71).cover;
            ((TextView) findViewById(R.id.xmlMsg)).setText(msg);
            */
        } catch (Exception e) {
            // Well, that didn't work. Let the user know as much as we do
            String errMsg = "XML parsing threw an exception:\n\n" + e.getMessage();
            ((TextView) findViewById(R.id.xmlMsg)).setText(errMsg);
            return;
        }

        // We got this far, let's turn off the "Working..." display
        ((TextView) findViewById(R.id.xmlMsg)).setText("");
        // Set up the recycler view
        bookRecyclerView = (RecyclerView) findViewById(R.id.bookListView);
        bookRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        bookRecyclerView.setLayoutManager(mLayoutManager);
        bookRecyclerView.setAdapter(new BookListAdapter());

    }

    public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView bookTitle;
            TextView bookInfo;

            public ViewHolder(View v) {
                super(v);
                bookTitle = (TextView) v.findViewById(R.id.titleView);
                bookInfo = (TextView) v.findViewById(R.id.infoView);
            }
        }

        @Override
        public BookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            // Create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_view, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bookTitle.setText(bookList.get(position).title);
            String info = bookList.get(position).author + "\n" +
                    bookList.get(position).isbn + "\n" +
                    bookList.get(position).price;
            holder.bookInfo.setText(info);
        }

        // Return the size of the dataset
        @Override
        public int getItemCount() {
            return bookList.size();
        }

    }
}
