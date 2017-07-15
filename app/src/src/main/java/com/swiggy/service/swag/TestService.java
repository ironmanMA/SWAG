package com.swiggy.service.swag;

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
