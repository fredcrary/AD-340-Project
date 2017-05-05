package com.example.fredcrary.ad_340_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ToolBarClass {
    public static final String EXTRA_MESSAGE = "com.example.fredcrary.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the tool bar
        Toolbar appToolBar = (Toolbar) findViewById(R.id.mainToolbar);
        appToolBar.inflateMenu(R.menu.toolbar);
        setSupportActionBar(appToolBar);

        // Set up the array of values below the text entry
        GridView gridview = (GridView) findViewById(R.id.mainGrid);
        gridview.setAdapter(new TextAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, RecyclerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /** Called when the user selects the SEND button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    // To set the data in the array at the bottom of the screen
    public class TextAdapter extends BaseAdapter {
        private Context mContext;

        public TextAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mContents.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(8, 8, 8, 8);
            } else {
                textView = (TextView) convertView;
            }

            textView.setText(mContents[position]);
            return textView;
        }

        // references to our images
        private String[] mContents = {"First", "Second", "Third", "Fourth"};
    }

}
