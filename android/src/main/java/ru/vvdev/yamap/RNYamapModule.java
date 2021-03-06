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

    private ReactApplicationContext getContext() {
        return getReactApplicationContext();
    }

    RNYamapModule(ReactApplicationContext reactContext) {
        super(reactContext);

        String apiKey = "";
        String locale = "ru_RU";
        int resId = reactContext.getResources().getIdentifier("YA_MAP_API_KEY", "string", reactContext.getPackageName());
        try {
            apiKey = reactContext.getResources().getString(resId);
        } catch (Exception e) {
        }

        MapKitFactory.setLocale(locale);

        if (!TextUtils.isEmpty(apiKey)) {
            MapKitFactory.setApiKey(apiKey);
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
    public void init() {
        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                MapKitFactory.initialize(getReactApplicationContext());
                MapKitFactory.getInstance().onStart();
            }
        }));
    }
}

