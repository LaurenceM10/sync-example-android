package chocoyolabs.sync;

import com.tumblr.remember.Remember;

import io.realm.Realm;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Remember.init(getApplicationContext(), "chocoyolabs.sync");
    }
}
