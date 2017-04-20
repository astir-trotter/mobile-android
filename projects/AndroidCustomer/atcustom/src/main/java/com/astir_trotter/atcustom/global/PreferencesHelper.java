/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */

package com.astir_trotter.atcustom.global;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

public class PreferencesHelper {
	private static final String TAG = PreferencesHelper.class.getSimpleName();

	private static PreferencesHelper _instance;
	private static Gson GSON = new Gson();
	public static PreferencesHelper getInstance() {
		if (_instance == null)
			_instance = new PreferencesHelper();

		return _instance;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	private SharedPreferences preferences;

	private PreferencesHelper() {
		preferences = PreferenceManager.getDefaultSharedPreferences(ATCache.getInstance().getContext());
	}

	public void putObject(String key, Object object) {
		if (object == null)
			throw new IllegalArgumentException("object is null");

		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("key is empty or null");

		preferences.edit().putString(key, GSON.toJson(object)).apply();
	}

	@Nullable
	public <T> T getObject(String key, Class<T> a) {
		String gson = preferences.getString(key, null);
		if (gson == null) {
			return null;
		} else {
			try{
				return GSON.fromJson(gson, a);
			} catch (Exception e) {
				throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");				
			}
		}
	}

	public <T> void putValue(String key, T value) {
		if (value instanceof Integer)
			preferences.edit().putInt(key, (Integer) value).apply();
		if (value instanceof Boolean)
			preferences.edit().putBoolean(key, (Boolean) value).apply();
		else if (value instanceof String)
			preferences.edit().putString(key, (String) value).apply();
		else
			throw new IllegalArgumentException("Unknown type");
	}

	public <T> T getValue(String key, T defaultValue) {
		if (defaultValue instanceof Integer)
			return (T) (Integer) preferences.getInt(key, (Integer) defaultValue);
		if (defaultValue instanceof Boolean)
			return (T) (Boolean) preferences.getBoolean(key, (Boolean) defaultValue);
		else if (defaultValue instanceof String)
			return (T) preferences.getString(key, (String) defaultValue);
		else
			throw new IllegalArgumentException("Unknown type");
	}

	public void reset() {
		preferences.edit().clear().apply();
	}
}
