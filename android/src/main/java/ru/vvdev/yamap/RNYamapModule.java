package ru.vvdev.yamap;

import android.text.TextUtils;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.yandex.mapkit.MapKitFactory;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RNYamapModule extends ReactContextBaseJavaModule {
    private static final String REACT_CLASS = "yamap";

    private boolean isInitialized = false;

    RNYamapModule(ReactApplicationContext reactContext) {
        super(reactContext);

        String apiKey = "";
        String locale = "ru";
        int resId = reactContext.getResources().getIdentifier("YA_MAP_API_KEY", "string", reactContext.getPackageName());
        try {
            apiKey = reactContext.getResources().getString(resId);
        } catch (Exception ignored) { }

        MapKitFactory.setLocale(locale);

        if (!TextUtils.isEmpty(apiKey)) {
            MapKitFactory.setApiKey(apiKey);
            isInitialized = true;
        }
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Map<String, Object> getConstants() {
        return new HashMap<>();
    }

    @ReactMethod
    public void init(final String apiKey) {
        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                if (!isInitialized) {
                    MapKitFactory.setApiKey(apiKey);
                }
                MapKitFactory.initialize(getReactApplicationContext());
                MapKitFactory.getInstance().onStart();
            }
        }));
    }
}
