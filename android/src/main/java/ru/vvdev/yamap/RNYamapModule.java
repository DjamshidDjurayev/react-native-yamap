package ru.vvdev.yamap;

import android.text.TextUtils;
import android.util.Log;

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
        String apiKey = "key";
        final String locale = "ru";
        int resId = getReactApplicationContext().getResources().getIdentifier("YA_MAP_API_KEY", "integer", getReactApplicationContext().getPackageName());
        try {
            apiKey = getReactApplicationContext().getResources().getString(resId);
        } catch (Exception ignored) { }

        Log.v("YA_MAP_API_KEY", apiKey);

        final String finalApiKey = apiKey;
        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                MapKitFactory.setLocale(locale);
                MapKitFactory.setApiKey(finalApiKey);
                MapKitFactory.initialize(getReactApplicationContext());
                MapKitFactory.getInstance().onStart();
            }
        }));
    }
}
