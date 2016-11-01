package pl.nowakprojects.socialmafia.mainmenuoptions.settings;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;

import pl.nowakprojects.socialmafia.R;

/**
 * Created by Mateusz on 01.11.2016.
 */

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
