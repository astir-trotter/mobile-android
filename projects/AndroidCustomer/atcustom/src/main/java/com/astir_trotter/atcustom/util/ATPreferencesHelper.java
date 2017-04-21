/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 12/2/16
 */

package com.astir_trotter.atcustom.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.astir_trotter.atcustom.singleton.Cache;
import com.google.gson.Gson;

public class ATPreferencesHelper {
	private static final String TAG = ATPreferencesHelper.class.getSimpleName();

	private static ATPreferencesHelper _instance = null;
	private static Gson GSON = new Gson();
	private static ATPreferencesHelper getInstance() {
		if (_instance == null)
			_instance = new ATPreferencesHelper();

		return _instance;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	private SharedPreferences preferences;

	private ATPreferencesHelper() {
		preferences = PreferenceManager.getDefaultSharedPreferences(Cache.getInstance().getContext());
	}

	public static void putObject(String key, Object object) {
		if (object == null)
			throw new IllegalArgumentException("object is null");

		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("key is empty or null");

		getInstance().preferences.edit().putString(key, GSON.toJson(object)).apply();
	}

	@Nullable
	public static <T> T getObject(String key, Class<T> a) {
		String gson = getInstance().preferences.getString(key, null);
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

	public static <T> void putValue(String key, T value) {
		if (value instanceof Integer)
			getInstance().preferences.edit().putInt(key, (Integer) value).apply();
		if (value instanceof Boolean)
			getInstance().preferences.edit().putBoolean(key, (Boolean) value).apply();
		else if (value instanceof String)
			getInstance().preferences.edit().putString(key, (String) value).apply();
		else
			throw new IllegalArgumentException("Unknown type");
	}

	public static <T> T getValue(String key, T defaultValue) {
		if (defaultValue instanceof Integer)
			return (T) (Integer) getInstance().preferences.getInt(key, (Integer) defaultValue);
		if (defaultValue instanceof Boolean)
			return (T) (Boolean) getInstance().preferences.getBoolean(key, (Boolean) defaultValue);
		else if (defaultValue instanceof String)
			return (T) getInstance().preferences.getString(key, (String) defaultValue);
		else
			throw new IllegalArgumentException("Unknown type");
	}

	public static void reset() {
		getInstance().preferences.edit().clear().apply();
	}
}
