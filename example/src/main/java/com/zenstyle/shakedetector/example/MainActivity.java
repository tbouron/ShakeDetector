package com.zenstyle.shakedetector.example;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zenstyle.shakedetector.library.ShakeDetector;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity implements ShakeDetector.OnShakeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final String STATUS = "status";
    private static final String SENSIBILITY = "sensibility";
    private static final String SHAKE_NUMBER = "shake_number";

    @InjectView(R.id.status)
    protected TextView mStatus;
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @InjectView(R.id.right_drawer)
    protected LinearLayout mRightDrawer;
    @InjectView(R.id.sensibility)
    protected SeekBar mSensibility;
    @InjectView(R.id.shake_number)
    protected SeekBar mShakeNumber;
    @InjectView(R.id.sensibility_label)
    protected TextView mSensibilityLabel;
    @InjectView(R.id.shake_number_label)
    protected  TextView mShakeNumberLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATUS)) {
            mStatus.setText(savedInstanceState.getString(STATUS));
            mSensibility.setProgress(savedInstanceState.getInt(SENSIBILITY));
            mShakeNumber.setProgress(savedInstanceState.getInt(SHAKE_NUMBER));
        }

        // We create and start the shake detector here.
        if (ShakeDetector.create(this, this)) {
            final float sensibility = (float) (mSensibility.getProgress() + 10) / 10;
            addStatusMessage(getString(R.string.shake_detector_created));
            ShakeDetector.updateConfiguration(sensibility, mShakeNumber.getProgress());
            updateSeekBarLabel(mSensibilityLabel, String.format("%.1f", sensibility));
            updateSeekBarLabel(mShakeNumberLabel, String.valueOf(mShakeNumber.getProgress()));
        } else {
            addStatusMessage(getString(R.string.shake_detector_error));
        }

        mSensibility.setOnSeekBarChangeListener(this);
        mSensibility.setOnTouchListener(this);
        mShakeNumber.setOnSeekBarChangeListener(this);
        mShakeNumber.setOnTouchListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // We restart shake detector if the activity was in background.
        if (ShakeDetector.start()) {
            addStatusMessage(getString(R.string.shake_detector_restarted));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // We stop shake detector when the activity goes to the background.
        ShakeDetector.stop();

        addStatusMessage(getString(R.string.shake_detector_stopped));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // We destroy shake detector when the activity finishes to release the resources.
        ShakeDetector.destroy();

        addStatusMessage(getString(R.string.shake_detector_destroyed));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATUS, mStatus.getText().toString());
        outState.putInt(SENSIBILITY, mSensibility.getProgress());
        outState.putInt(SHAKE_NUMBER, mShakeNumber.getProgress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (mDrawerLayout.isDrawerOpen(mRightDrawer)) {
                mDrawerLayout.closeDrawer(mRightDrawer);
            } else {
                mDrawerLayout.openDrawer(mRightDrawer);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnShake() {
        // This callback is triggered by the ShakeDetector. In a real implementation, you should
        // do here a real action.
        addStatusMessage(getString(R.string.shake_detected));
        Toast.makeText(this, getString(R.string.device_shaken), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (seekBar.getId() == R.id.sensibility) {
                float sensibility = (float) (progress + 10) / 10;
                ShakeDetector.updateConfiguration(sensibility, mShakeNumber.getProgress());
                updateSeekBarLabel(mSensibilityLabel, String.format("%.1f", sensibility));
                addStatusMessage(getString(R.string.update_sensibility, sensibility));
            } else if (seekBar.getId() == R.id.shake_number) {
                ShakeDetector.updateConfiguration((mSensibility.getProgress() + 10) / 10, progress);
                updateSeekBarLabel(mShakeNumberLabel, String.valueOf(progress));
                addStatusMessage(getString(R.string.update_shake_number, progress));
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Nothing to see here
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Nothing to see here
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow Drawer to intercept touch events.
                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                // Allow Drawer to intercept touch events.
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        // Handle seekbar touch events.
        view.onTouchEvent(motionEvent);
        return true;
    }

    private void addStatusMessage(String message) {
        String date = new SimpleDateFormat("HH:mm:ss-SSS").format(new Date());
        String status = String.format("\n[%s] %s", date, message);

        mStatus.append(status);
        Log.d(TAG, status);
    }

    private void updateSeekBarLabel(TextView view, String textToAppend) {
        String label = "";
        if (view.getId() == R.id.sensibility_label) {
            label = getString(R.string.label_sensibility, textToAppend);
        }
        if (view.getId() == R.id.shake_number_label) {
            label = getString(R.string.label_shake_number, textToAppend);
        }
        view.setText(label);
    }
}
