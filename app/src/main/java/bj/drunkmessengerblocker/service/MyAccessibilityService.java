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

import javax.inject.Singleton;

import bj.drunkmessengerblocker.R;

/**
 * Created by j on 15/01/2017.
 */

@Singleton
public class MyAccessibilityService extends AccessibilityService
{
    private View disablingView;
    private View thinkAboutView;
    private Handler handler;
    private WindowManager.LayoutParams windowManagerParams;
    private WindowManager wm;
    private LayoutInflater layoutInflater;

    @Override
    public void onCreate()
    {
        // Handler will get associated with the current thread,
        // which is the main thread.
        handler = new Handler();
        super.onCreate();
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
        if (accessibilityEvent.getContentDescription() == null || accessibilityEvent.getContentDescription() == "\uD83D\uDC0E"
                || accessibilityEvent.getContentDescription() == "\uD83D\uDC0E, " || accessibilityEvent.getContentDescription() == "Free Call"
                || accessibilityEvent.getContentDescription() == "Free Video Call" || accessibilityEvent.getContentDescription() == "Thread Details"
                || accessibilityEvent.getContentDescription() == "Like" || accessibilityEvent.getContentDescription() == "Send")
        {
            return;
        }
        final AccessibilityNodeInfo source = accessibilityEvent.getSource();
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i("Event", accessibilityEvent.toString() + "");
                Log.i("Source", source.toString() + "");
//        Log.i("Class name", accessibilityEvent.getClassName().toString());
//        Log.i("Window id", source.getWindowId() + "");
//        Log.i("View id", source.getViewIdResourceName() + "");

                if (source.getClassName().equals("android.widget.FrameLayout") && source.getContentDescription() != null && !source.getContentDescription().toString().isEmpty())
                {
                    // Check if the name is blacklisted
                    if (source.getContentDescription().toString().contains("Katy Perry"))
                    {
                        Log.i("CAUGHT EVENT", accessibilityEvent.toString() + "");
                        disablingView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cover_screen, null);
                        disablingView.setClickable(true);
                        disablingView.setLayoutParams(windowManagerParams);
                        wm.addView(disablingView, windowManagerParams);
//                        Instrumentation inst = new Instrumentation();
//                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//            if (source.getClassName().equals("android.widget.TextView") && source.getParent() != null && source.getParent().getClassName().equals("android.widget.FrameLayout") && source.getParent().getParent() == null)
//            {
//                return recivedText;
//            }
                    }

//        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId = source.findAccessibilityNodeInfosByViewId("YOUR PACKAGE NAME:id/RESOURCE ID FROM WHERE YOU WANT DATA");
//        if (findAccessibilityNodeInfosByViewId.size() > 0)
//        {
//            AccessibilityNodeInfo parent = (AccessibilityNodeInfo) findAccessibilityNodeInfosByViewId.get(0);
//            // You can also traverse the list if required data is deep in view hierarchy.
//            String requiredText = parent.getText().toString();
//            Log.i("Required Text", requiredText);
//        }
                }
            }
        });
    }

    @Override
    public void onInterrupt()
    {

    }

    @Override
    public void onServiceConnected()
    {
        windowManagerParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

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
//        info.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        // We are keeping the timeout to 0 as we don’t need any delay or to pause our accessibility events
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

    @Override
    protected boolean onKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (disablingView != null || thinkAboutView != null)
                if (disablingView.isShown() || thinkAboutView.isShown())
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            wm.removeView(disablingView);
                            thinkAboutView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.think_about_screen, null);
                            thinkAboutView.setClickable(false);
                            thinkAboutView.setLayoutParams(windowManagerParams);
                            wm.addView(thinkAboutView, windowManagerParams);
                            try
                            {
                                Thread.sleep(1000);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            wm.removeView(thinkAboutView);
                        }
                    });
                }
        }
        return super.onKeyEvent(event);
    }
}
