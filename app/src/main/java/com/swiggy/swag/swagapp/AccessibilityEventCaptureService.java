package com.swiggy.swag.swagapp;

/**
 * Created by 127.0.0.1.ma on 15/07/17.
 */

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.R;

import java.util.Map;
import java.util.PriorityQueue;

import static com.swiggy.swag.swagapp.common.KeywordExtractor.findGreatest;
import static com.swiggy.swag.swagapp.common.KeywordExtractor.intersect;
import static com.swiggy.swag.swagapp.common.StopWordRemoval.removeStopWords;

public abstract class AccessibilityEventCaptureService extends AccessibilityService {

    public static final int EXTRA_TYPE_NOTIFICATION = 0x19;
    public static HashMap <String,Double> KEYWORD_CACHE = new HashMap <String,Double>();
    public static final String separator = " ";

    int notifyID = 1;
    public static String TAG = AccessibilityEventCaptureService.class.getSimpleName();
    ArrayList<AccessibilityNodeInfo> textViewNodes = new ArrayList<AccessibilityNodeInfo>();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        textViewNodes = new ArrayList<AccessibilityNodeInfo>();
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        findChildViews(rootNode);
        String final_text = "";
        for (AccessibilityNodeInfo mNode : textViewNodes) {
            if (mNode.getText() != null)
                final_text += separator + mNode.getText().toString();
            if (mNode.getContentDescription() != null)
                final_text += separator + mNode.getContentDescription().toString();
        }
        System.out.println(final_text);
        Log.i(new Date() + " TEXT : ", final_text);

        HashMap <String,Double> final_map= intersect(removeStopWords(final_text));
        KEYWORD_CACHE.putAll(final_map);

        if (KEYWORD_CACHE.size()>10){
        /*
           get top 5 of the final_map
         */
            List<Map.Entry<String, Double>> greatest = findGreatest(KEYWORD_CACHE, 5);
            for (Map.Entry<String, Double> entry : greatest) {
                // call elasticSearch here
                System.out.println(entry);
            }
        }


        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this, SwipeDeckActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.drawable.sym_def_app_icon);
        builder.setContentText("We have found pretty good recommendations for you !!");
        builder.setContentTitle("EAT WITH SWAG");
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();

        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        StatusBarNotification[] notifications =
                nManager.getActiveNotifications();

        boolean isPresent=false;
        for (StatusBarNotification currentNotification : notifications) {
            if (currentNotification.getId() == 1) {
                isPresent=true;
                break;
            }
        }
        if(!isPresent)
            nm.notify(notifyID,notification);

    }

    private void findChildViews(AccessibilityNodeInfo parentView) {
        if (parentView == null || parentView.getClassName() == null) {
            return;
        }
//        android.view.View
        int childCount = parentView.getChildCount();

        if (childCount == 0 &&
                ((parentView.getClassName().toString().contentEquals("android.widget.TextView"))
                        || (parentView.getClassName().toString().contentEquals("android.view.View"))
                        || (parentView.getClassName().toString().contentEquals("com.instagram.ui.widget.textview.IgTextLayoutView"))
                )) {
            textViewNodes.add(parentView);
        } else {
            for (int i = 0; i < childCount; i++) {
                findChildViews(parentView.getChild(i));
            }
        }
    }

    protected abstract boolean isDebugMode();

    protected void setTag(String tag) {
        AccessibilityEventCaptureService.TAG = tag;
    }

    public AccessibilityEventCaptureService() {
    }

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED | AccessibilityEvent.TYPE_VIEW_SCROLLED |
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
//        info.packageNames = new String[] {"com.whatsapp","com.facebook","com.zomato","com.android.browser"};
        info.packageNames = new String[]{"com.android.chrome", "com.whatsapp"};
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        info.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        info.notificationTimeout = 1000;
        this.setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {
    }

    private boolean hasMessage(AccessibilityEvent event) {
        return event != null && (event.getText().size() > 0);
    }
}