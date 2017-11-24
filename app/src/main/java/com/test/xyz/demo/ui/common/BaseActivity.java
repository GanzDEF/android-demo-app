package com.test.xyz.demo.ui.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.test.xyz.demo.R;

import butterknife.BindView;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog mDialog;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateActivity();
        initializeToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProgressDialog() {
        mDialog = new ProgressDialog(this);

        mDialog.setMessage(getString(R.string.please_wait));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void dismissAllDialogs() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    private void initializeToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            // Support back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    abstract protected void onCreateActivity();
}
