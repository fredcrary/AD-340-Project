package com.example.fredcrary.ad_340_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class XmlActivity extends ToolBarClass {

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

    }
}
