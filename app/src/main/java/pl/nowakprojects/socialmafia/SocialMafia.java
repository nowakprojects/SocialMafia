package pl.nowakprojects.socialmafia;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class SocialMafia extends Application {

    //public final static boolean premimumVersion = ;
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

    }
}
