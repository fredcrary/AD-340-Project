package com.example.fredcrary.ad_340_project;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ToolBarClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Toolbar appToolBar = (Toolbar) findViewById(R.id.commonToolbar);
        appToolBar.inflateMenu(R.menu.toolbar);
        setSupportActionBar(appToolBar);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Toolbar menu items
            case R.id.aboutAction:
                Intent intent1 = new Intent(ToolBarClass.this, AboutPage.class);
                startActivity(intent1);
                return true;
            case R.id.recycleAction:
                Intent intent2 = new Intent(ToolBarClass.this, RecyclerActivity.class);
                startActivity(intent2);
                return true;
            // Up/home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            // We don't handle this one
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
