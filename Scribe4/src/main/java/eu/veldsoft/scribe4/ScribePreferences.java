package eu.veldsoft.scribe4;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ScribePreferences extends PreferenceActivity implements
        OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.prefs);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        updateGameModeSummary();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        updateGameModeSummary();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(this.getString(R.string.gameMode))) {
            updateGameModeSummary();
        }
    }

    private void updateGameModeSummary() {
        String key = this.getString(R.string.gameMode);
        String value = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(key, "majority");

        Preference pref = getPreferenceScreen().findPreference(key);
        pref.setSummary(value.equals("majority") ? R.string.majoritySummary
                : R.string.superglyphSummary);
    }

}
