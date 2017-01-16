package bj.drunkmessengerblocker.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import bj.drunkmessengerblocker.AppComponent;
import bj.drunkmessengerblocker.BaseActivity;
import bj.drunkmessengerblocker.R;
import bj.drunkmessengerblocker.blacklist.BlacklistActivity;
import bj.drunkmessengerblocker.service.MyAccessibilityService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View
{
    /**
     * code to post/handler request for permission
     */
    public final static int REQUEST_CODE = 5376; /*(see edit II)*/
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 20;
    private final String TAG = "BlacklistActivity";
    @Inject MainPresenter presenter;
    @BindView(R.id.lytSwitch) RelativeLayout lytSwitch;
    @BindView(R.id.toggleSwitch) SwitchCompat toggleSwitch;
    @BindView(R.id.tvBlock) TextView tvBlock;
    private int disableCount = 0;
    private MainComponent component;
    private MaterialDialog dialog;
    private boolean overlayEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupComponent(AppComponent appComponent)
    {
        component = DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build();

        component.inject(this);
    }

    @OnClick(R.id.lytSwitch)
    public void launchAccessibilityOptionsDialog()
    {
        dialog = new MaterialDialog.Builder(this)
                .content("You will be taken to the Settings page. Scroll down to Services and set DrunkMessengerBlocker to On. To disable it, come back into this app or the Settings page after the timer has expired.")
                .positiveText("Settings")
                .negativeText("Dismiss")
                .onPositive(new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }

    @OnClick(R.id.lytBlock)
    public void enableBlock()
    {
        if (tvBlock.getText().equals("Unblock"))
        {
            if (disableCount == 0)
            {
                dialog = new MaterialDialog.Builder(this)
                        .content("Nope! You played yourself")
                        .negativeText("Dismiss")
                        .build();
                dialog.show();
            }
            else if (disableCount == 1)
            {
                dialog = new MaterialDialog.Builder(this)
                        .content("You're an idiot")
                        .negativeText("Dismiss")
                        .build();
                dialog.show();
            }
            else if (disableCount == 2)
            {
                dialog = new MaterialDialog.Builder(this)
                        .content("Stop being a mug")
                        .negativeText("Dismiss")
                        .build();
                dialog.show();
            }
            else if (disableCount > 2)
            {
                dialog = new MaterialDialog.Builder(this)
                        .content("Pathetic")
                        .negativeText("Dismiss")
                        .build();
                dialog.show();
            }
            disableCount++;
        }
//        else if (overlayEnabled)
        else if (toggleSwitch.isChecked())
        {
            launchAccessibilityOptionsDialog();
        }
        else
        {
            dialog = new MaterialDialog.Builder(this)
                    .content("You must enable the overlay to block yourself from doing dumb shit.")
                    .negativeText("Dismiss")
                    .build();
            dialog.show();
        }
    }

    @OnClick(R.id.lytFirst)
    public void modifyBlacklist()
    {
        Intent intent = new Intent(this, BlacklistActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.toggleSwitch)
    public void switchToggled()
    {
        if (!toggleSwitch.isChecked())
        {
            dialog = new MaterialDialog.Builder(this)
                    .content("You will be taken to the Settings page. To disable the overlay, disable the checkbox next to DrunkMessengerBlocker.")
                    .positiveText("Settings")
                    .negativeText("Dismiss")
                    .onPositive(new MaterialDialog.SingleButtonCallback()
                    {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                        {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:" + getPackageName()));
                            /** request permission via start activity for result */
                            startActivityForResult(intent, REQUEST_CODE);
                            dialog.dismiss();
                        }
                    })
                    .build();
            dialog.show();
        }
        else
        {
            checkDrawOverlayPermission();
        }
//        toggleSwitch.setChecked(false);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (Settings.canDrawOverlays(this))
            {
                overlayEnabled = true;
                toggleSwitch.setChecked(true);
            }
            else
            {
                overlayEnabled = false;
                toggleSwitch.setChecked(false);
            }
        }

        if (isAccessibilitySettingsOn(this))
        {
            tvBlock.setText("Unblock");
        }
    }

    // To check if service is enabled
    private boolean isAccessibilitySettingsOn(Context mContext)
    {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + MyAccessibilityService.class.getCanonicalName();
        try
        {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        }
        catch (Settings.SettingNotFoundException e)
        {
            Log.e(TAG, "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1)
        {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null)
            {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext())
                {
                    String accessibilityService = mStringColonSplitter.next();

                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service))
                    {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        }
        else
        {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }

    public void checkDrawOverlayPermission()
    {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!Settings.canDrawOverlays(this))
            {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == REQUEST_CODE)
        {
            /** if so check once again if we have permission */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (Settings.canDrawOverlays(this))
                {
                    toggleSwitch.setChecked(true);
                }
            }
        }
    }
}
