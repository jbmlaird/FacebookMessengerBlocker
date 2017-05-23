package bj.drunkmessengerblocker.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import java.util.List;

import javax.inject.Inject;

import bj.drunkmessengerblocker.App;
import bj.drunkmessengerblocker.R;
import bj.drunkmessengerblocker.utils.SharedPrefsManager;

/**
 * Created by j on 15/01/2017.
 * <p>
 * Accessibility service that monitors wants on the screen.
 */
public class MyAccessibilityService extends AccessibilityService
{
    @Inject SharedPrefsManager sharedPrefsManager;
    private View disablingView;
    private Handler handler;
    private WindowManager.LayoutParams windowManagerParams;
    private WindowManager wm;

    @Override
    public void onCreate()
    {
        super.onCreate();
        // Handler will get associated with the current thread,
        // which is the main thread.
        handler = new Handler();
        DaggerServiceComponent.builder()
                .appComponent(App.component)
                .serviceModule(new ServiceModule(this))
                .build()
                .inject(this);
    }

    private void runOnUiThread(Runnable runnable)
    {
        handler.post(runnable);
    }

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent accessibilityEvent)
    {
        if (accessibilityEvent.getText() != null)
        {
            if (accessibilityEvent.getText().size() > 0)
                if (accessibilityEvent.getText().get(0).toString().equals("Messenger"))
                {
                    // User resumed Messenger
                }
        }
        // Ignore accessibility events we don't care about
        if (accessibilityEvent.getContentDescription() == null || accessibilityEvent.getContentDescription() == "\uD83D\uDC0E"
                || accessibilityEvent.getContentDescription() == "\uD83D\uDC0E, " || accessibilityEvent.getContentDescription() == "Free Call"
                || accessibilityEvent.getContentDescription() == "Free Video Call" || accessibilityEvent.getContentDescription() == "Thread Details"
                || accessibilityEvent.getContentDescription() == "Like" || accessibilityEvent.getContentDescription() == "Send")
            return;
        final AccessibilityNodeInfo source = accessibilityEvent.getSource();
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i("Event", accessibilityEvent.toString() + "");
                Log.i("Source", source.toString() + "");

                // Check if a user has opened a chat window
                if (source.getClassName().equals("android.widget.FrameLayout") && source.getContentDescription() != null && !source.getContentDescription().toString().isEmpty())
                {
                    for (String storedName : sharedPrefsManager.getStoredNames())
                    {
                        // If it matches something in the blacklist, display the system window
                        if (source.getContentDescription().toString().contains(storedName))
                        {
                            Log.i("CAUGHT EVENT", accessibilityEvent.toString() + "");
                            disablingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cover_screen, null);
                            disablingView.setClickable(true);
                            disablingView.setLayoutParams(windowManagerParams);
                            disablingView.setOnClickListener(new View.OnClickListener()
                                                             {
                                                                 @Override
                                                                 public void onClick(View v)
                                                                 {
                                                                     Log.e("yeeson", "yeeeeeheheheheh");
                                                                 }
                                                             }
                            );
                            wm.addView(disablingView, windowManagerParams);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onInterrupt()
    {

    }

    /**
     * Initialise Service settings.
     */
    @Override
    public void onServiceConnected()
    {
        windowManagerParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        Log.e("Accessibility Service", "Service connected");
        AccessibilityServiceInfo info = getServiceInfo();
        // Set the type of events that this service wants to listen to. Others won't be passed to this service.
        // We are only considering windows state changed event.
        info.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED | AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED;
        // If you only want this service to work with specific applications, set their package names here. Otherwise, when the service is activated, it will listen to events from all applications.
        info.packageNames = new String[]{"com.facebook.orca"};
        // Set the type of feedback your service will provide. We are setting it to GENERIC.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        // Default services are invoked only if no package-specific ones are present for the type of AccessibilityEvent generated.
        // This is a general-purpose service, so we will set some flags
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;
        info.notificationTimeout = 0;
        this.setServiceInfo(info);
    }

    @Override
    public List<AccessibilityWindowInfo> getWindows()
    {
        return super.getWindows();
    }

    @Override
    public AccessibilityNodeInfo getRootInActiveWindow()
    {
        return super.getRootInActiveWindow();
    }

    /**
     * Remove the system window if the user has pressed the back button.
     */
    @Override
    protected boolean onKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (disablingView != null && disablingView.isShown())
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        wm.removeView(disablingView);
                    }
                });
        }
        return super.onKeyEvent(event);
    }
}
