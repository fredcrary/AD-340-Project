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
    int mCurrentPageId = 0;     // ID of the toolbar menu item for the current page.
                                // This item will be removed from the toolbar menu.
                                // (Would -1 be a better choice for a "no ID" value?)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        menu.removeItem(mCurrentPageId);    // Remove current page from the toolbar menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {

            // Toolbar menu items
            case R.id.locationAction:
                intent = new Intent(ToolBarClass.this, LocationActivity.class);
                startActivity(intent);
                return true;
            case R.id.aboutAction:
                intent = new Intent(ToolBarClass.this, AboutPage.class);
                startActivity(intent);
                return true;
            case R.id.recycleAction:
                intent = new Intent(ToolBarClass.this, RecyclerActivity.class);
                startActivity(intent);
                return true;
            case R.id.xmlAction:
                intent = new Intent(ToolBarClass.this, XmlActivity.class);
                startActivity(intent);
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
