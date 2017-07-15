package com.swiggy.swag.swagapp;

/**
 * Created by 127.0.0.1.ma on 15/07/17.
 */

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class TestService extends AccessibilityEventCaptureService {
    public TestService() {
        super();
        this.setTag(TestService.class.getSimpleName());
    }

    @Override
    protected boolean isDebugMode() {
        return true;
    }
}
