package com.swiggy.service.swag;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R;
import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityRecord;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.util.Locale;

public abstract class AccessibilityEventCaptureService extends AccessibilityService {

    public static final int EXTRA_TYPE_NOTIFICATION = 0x19;
    public static final String separator = " ";

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
        final_text.toString();
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
