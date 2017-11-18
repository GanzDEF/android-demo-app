package com.test.xyz.demo.ui.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.test.xyz.demo.R;

import butterknife.InjectView;

public abstract class BaseActivity extends AppCompatActivity {
    String TAG = BaseActivity.class.getName();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateActivity();

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            // Support back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    abstract protected void onCreateActivity();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e(TAG, "Base:onOptionsItemSelected() is called ...");

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startProgress() {
        mDialog = new ProgressDialog(this);

        mDialog.setMessage(getString(R.string.please_wait));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void endProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
