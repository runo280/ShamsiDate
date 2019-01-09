package ir.runo.shamsidate;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    private static final int REQUEST_PERMISSIONS = 1234;
    private String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private TextClock clock;
    private TextView tvDate;
    /*private CheckBox hideLauncherIconCheckBox;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        bindData();

        askForPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    refreshUI();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    private void initViews() {
        clock = findViewById(R.id.clock);
        tvDate = findViewById(R.id.tvDate);
        /*hideLauncherIconCheckBox = findViewById(R.id.hideLauncherIconCheckBox);*/
    }

    private void bindData() {
        tvDate.setText(Utils.getPersianDate());

        /*hideLauncherIconCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Utils.hideLauncherIcon(MainActivity.this, isChecked);
        });*/
    }

    private void askForPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            refreshUI();
            return;
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, PERMISSION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{PERMISSION},
                        REQUEST_PERMISSIONS);

                // REQUEST_PERMISSIONS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            refreshUI();
        }
    }

    private void refreshUI() {
        if (!Utils.fontIsPresent(this)) {
            clock.setTextColor(Color.RED);
            tvDate.setTextColor(Color.RED);
            return;
        }

        int darkGreen = Utils.makeColorDarker(Color.GREEN, 0.5f);

        clock.setTextColor(darkGreen);
        tvDate.setTextColor(darkGreen);

        clock.setTypeface(Utils.getClockTypeFace(this));
        tvDate.setTypeface(Utils.getClockTypeFace(this));
    }
}