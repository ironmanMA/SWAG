package com.swiggy.swag.swagapp;

/**
 * Created by 127.0.0.1.ma on 15/07/17.
 */

import android.R;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.swiggy.swag.swagapp.common.KeywordExtractor.findGreatest;
import static com.swiggy.swag.swagapp.common.KeywordExtractor.intersect;
import static com.swiggy.swag.swagapp.common.StopWordRemoval.removeStopWords;
import static com.swiggy.swag.swagapp.comms.ApacheHttpClientGet.elasticSearchCall;
import static com.swiggy.swag.swagapp.comms.ApacheHttpClientGet.esToDao;

public abstract class AccessibilityEventCaptureService extends AccessibilityService {

    public static final int EXTRA_TYPE_NOTIFICATION = 0x19;
    public static HashMap<String, Double> KEYWORD_CACHE = new HashMap<String, Double>();
    public static final String separator = " ";

    int notifyID = 1;
    public static String TAG = AccessibilityEventCaptureService.class.getSimpleName();
    ArrayList<AccessibilityNodeInfo> textViewNodes = new ArrayList<AccessibilityNodeInfo>();
    ArrayList<RecommendedDishResponseDAO> recommendedDishResponseDAOs = new ArrayList<>();

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

        Log.i(new Date() + " TEXT : ", final_text);

        HashMap<String, Double> final_map = intersect(removeStopWords(final_text));
        KEYWORD_CACHE.putAll(final_map);

        if (KEYWORD_CACHE.size() > 15) {
        /*
           get top 5 of the final_map
         */
            List<Map.Entry<String, Double>> greatest = findGreatest(KEYWORD_CACHE, 3);

            for (Map.Entry<String, Double> entry : greatest) {
                // call elasticSearch here
                Log.i("FOR_ELS", entry.getKey() + ", " + entry.getValue());
                String response = elasticSearchCall(entry.getKey());
//                String response = elasticSearchCall("pizza");
                recommendedDishResponseDAOs.addAll(esToDao(response));
            }

            /*
            filter top10
             */
            Collections.sort(recommendedDishResponseDAOs, new Comparator<RecommendedDishResponseDAO>() {

                public int compare(RecommendedDishResponseDAO o1, RecommendedDishResponseDAO o2) {
                    return o2.getLikenessScore().compareTo(o1.getLikenessScore());
                }
            });

            ArrayList<RecommendedDishResponseDAO> top10RecommendedDishResponseDAOs = new ArrayList<>(
                    recommendedDishResponseDAOs.subList(0, Math.min(recommendedDishResponseDAOs.size(), 10))
            );

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(this);
            Intent notificationIntent = new Intent(this, SwipeDeckActivity.class);
            notificationIntent.putParcelableArrayListExtra("MY_DATA", top10RecommendedDishResponseDAOs);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

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

            boolean isPresent = false;
            for (StatusBarNotification currentNotification : notifications) {
                if (currentNotification.getId() == 1) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent && recommendedDishResponseDAOs.size() > 10) {
                nm.notify(notifyID, notification);
                KEYWORD_CACHE.clear();
//                recommendedDishResponseDAOs.clear();
            }
        }
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
//        info.eventTypes = AccessibilityEvent.TYPE_VIEW_SCROLLED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED |
//                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.packageNames = new String[]{"com.android.chrome", "com.whatsapp", "com.application.zomato", "com.instagram.android"};
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