package me.spark.mvvm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.spark.mvvm.base.BaseApplication;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2018/5/17
 * 描    述：SP工具类
 * 修订历史：
 * ================================================
 */
@SuppressLint("ApplySharedPref")
public final class SPUtils {
    private static SimpleArrayMap<String, SPUtils> SP_UTILS_MAP = new SimpleArrayMap<>();
    private SharedPreferences sp;
    private String TGC = "cas_tgc";//TGC
    private String USERJSON = "userJson";//用户登录信息
    private String ISLOGIN = "isLogin";//上次是否为登录状态
    private String FAVORFINDLIST = "favorFindList";//全部收藏币种
    private String ISHIDEACCOUNT = "isHideAccount";//是否隐藏资产详情
    private String ISHIDEACCOUNTSPOT = "isHideAccountSpot";//是否隐藏资产详情(币币)
    private String ISHIDEACCOUNTOTC = "isHideAccountOtc";//是否隐藏资产详情(法币)
    private String ISHIDEACCOUNTCFD = "isHideAccountCfd";//是否隐藏资产详情(合约)

    private String ACCOOKIE = "acCookie";//cookie
    private String UCCOOKIE = "ucCookie";
    private String CFDCOOKIE = "cfdCookie";
    private String OTCCOOKIE = "otcCookie";
    private String SPOTCOOKIE = "spotCookie";
    private String QUANTIZATIONCOOKIE = "quantizationCookie";

    private String GAINMODE = "gainMode";

    /*-----------TGC-----------*/
    public void setTgc(String tgc) {
        getInstance().put(TGC, tgc);
    }

    public String getTgc() {
        return getInstance().getString(TGC, "");
    }
    /*-----------TGC-----------*/

    /*-----------USERJSON-----------*/
    public void setUserJson(String userJson) {
        getInstance().put(USERJSON, userJson);
    }

    public String getUserJson() {
        return getInstance().getString(USERJSON, "");
    }
    /*-----------USERJSON-----------*/

    /*-----------ISLOGIN-----------*/
    public void setIsLogin(boolean isLogin) {
        getInstance().put(ISLOGIN, isLogin);
    }

    public boolean IsLogin() {
        return getInstance().getBoolean(ISLOGIN, false);
    }
    /*-----------ISLOGIN-----------*/

    //全部收藏币种
    public void setFavorFindList(List<String> favorFindList) {
        getInstance().put(FAVORFINDLIST, BaseApplication.gson.toJson(favorFindList));
    }

    public List<String> getFavorFindList() {
        List<String> datalist = new ArrayList<>();
        String strJson = getInstance().getString(FAVORFINDLIST, null);
        if (null == strJson) {
            return datalist;
        }
        return BaseApplication.gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
    }

    /*-----------isHideAccount-----------*/
    public void setIsHideAccount(boolean isHideAccount) {
        getInstance().put(ISHIDEACCOUNT, isHideAccount);
    }

    public boolean isHideAccount() {
        return getInstance().getBoolean(ISHIDEACCOUNT, false);
    }
    /*-----------isHideAccount-----------*/

    /*-----------isHideAccountSpot-----------*/
    public void setIsHideAccountSpot(boolean isHideAccountSpot) {
        getInstance().put(ISHIDEACCOUNTSPOT, isHideAccountSpot);
    }

    public boolean isHideAccountSpot() {
        return getInstance().getBoolean(ISHIDEACCOUNTSPOT, false);
    }
    /*-----------isHideAccountSpot-----------*/

    /*-----------isHideAccountOtc-----------*/
    public void setIsHideAccountOtc(boolean isHideAccountOtc) {
        getInstance().put(ISHIDEACCOUNTOTC, isHideAccountOtc);
    }

    public boolean isHideAccountOtc() {
        return getInstance().getBoolean(ISHIDEACCOUNTOTC, false);
    }
    /*-----------isHideAccountOtc-----------*/

    /*-----------isHideAccountCfd-----------*/
    public void setIsHideAccountCfd(boolean isHideAccountCfd) {
        getInstance().put(ISHIDEACCOUNTCFD, isHideAccountCfd);
    }

    public boolean isHideAccountCfd() {
        return getInstance().getBoolean(ISHIDEACCOUNTCFD, false);
    }
    /*-----------isHideAccountCfd-----------*/


    /*-----------cookie-----------*/
    public void setAcCookie(String acCookie) {
        getInstance().put(ACCOOKIE, acCookie);
    }

    public String getAcCookie() {
        return getInstance().getString(ACCOOKIE, "");
    }

    public void setUcCookie(String ucCookie) {
        getInstance().put(UCCOOKIE, ucCookie);
    }

    public String getUcCookie() {
        return getInstance().getString(UCCOOKIE, "");
    }

    public void setCfdCookie(String cfdCookie) {
        getInstance().put(CFDCOOKIE, cfdCookie);
    }

    public String getCfdCookie() {
        return getInstance().getString(CFDCOOKIE, "");
    }

    public void setOtcCookie(String otcCookie) {
        getInstance().put(OTCCOOKIE, otcCookie);
    }

    public String getOtcCookie() {
        return getInstance().getString(OTCCOOKIE, "");
    }

    public void setSpotCookie(String spotCookie) {
        getInstance().put(SPOTCOOKIE, spotCookie);
    }

    public String getSpotCookie() {
        return getInstance().getString(SPOTCOOKIE, "");
    }

    public void setQuantizationCookie(String quantizationCookie) {
        getInstance().put(QUANTIZATIONCOOKIE, quantizationCookie);
    }

    public String getQuantizationCookie() {
        return getInstance().getString(QUANTIZATIONCOOKIE, "");
    }
    /*-----------cookie-----------*/
    /*-----------K线颜色-----------*/
    public void setGainMode(boolean gainMode) {
        getInstance().put(GAINMODE, gainMode);
    }

    public boolean getGainMode() {
        return getInstance().getBoolean(GAINMODE, true);
    }

    /*-----------K线颜色-----------*/
    /**
     * Return the single {@link SPUtils} instance
     *
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance() {
        return getInstance("");
    }

    /**
     * Return the single {@link SPUtils} instance
     *
     * @param spName The name of sp.
     * @return the single {@link SPUtils} instance
     */
    public static SPUtils getInstance(String spName) {
        if (isSpace(spName)) spName = "casLoginSP";
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(spName);
            SP_UTILS_MAP.put(spName, spUtils);
        }
        return spUtils;
    }

    private SPUtils(final String spName) {
        sp = Utils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * Put the string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, @NonNull final String value) {
        put(key, value, false);
    }

    /**
     * Put the string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key,
                    @NonNull final String value,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    /**
     * Return the string value in sp.
     *
     * @param key The key of sp.
     * @return the string value if sp exists or {@code ""} otherwise
     */
    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    /**
     * Return the string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the string value if sp exists or {@code defaultValue} otherwise
     */
    public String getString(@NonNull final String key, @NonNull final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * Put the int value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final int value) {
        put(key, value, false);
    }

    /**
     * Put the int value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    /**
     * Return the int value in sp.
     *
     * @param key The key of sp.
     * @return the int value if sp exists or {@code -1} otherwise
     */
    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    /**
     * Return the int value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the int value if sp exists or {@code defaultValue} otherwise
     */
    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * Put the long value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final long value) {
        put(key, value, false);
    }

    /**
     * Put the long value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    /**
     * Return the long value in sp.
     *
     * @param key The key of sp.
     * @return the long value if sp exists or {@code -1} otherwise
     */
    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    /**
     * Return the long value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the long value if sp exists or {@code defaultValue} otherwise
     */
    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * Put the float value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final float value) {
        put(key, value, false);
    }

    /**
     * Put the float value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    /**
     * Return the float value in sp.
     *
     * @param key The key of sp.
     * @return the float value if sp exists or {@code -1f} otherwise
     */
    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    /**
     * Return the float value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the float value if sp exists or {@code defaultValue} otherwise
     */
    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, final boolean value) {
        put(key, value, false);
    }

    /**
     * Put the boolean value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key The key of sp.
     * @return the boolean value if sp exists or {@code false} otherwise
     */
    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    /**
     * Return the boolean value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the boolean value if sp exists or {@code defaultValue} otherwise
     */
    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key   The key of sp.
     * @param value The value of sp.
     */
    public void put(@NonNull final String key, @NonNull final Set<String> value) {
        put(key, value, false);
    }

    /**
     * Put the set of string value in sp.
     *
     * @param key      The key of sp.
     * @param value    The value of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void put(@NonNull final String key,
                    @NonNull final Set<String> value,
                    final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit();
        } else {
            sp.edit().putStringSet(key, value).apply();
        }
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key The key of sp.
     * @return the set of string value if sp exists or {@code Collections.<String>emptySet()} otherwise
     */
    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * Return the set of string value in sp.
     *
     * @param key          The key of sp.
     * @param defaultValue The default value if the sp doesn't exist.
     * @return the set of string value if sp exists or {@code defaultValue} otherwise
     */
    public Set<String> getStringSet(@NonNull final String key,
                                    @NonNull final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * Return all values in sp.
     *
     * @return all values in sp
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * Return whether the sp contains the preference.
     *
     * @param key The key of sp.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key The key of sp.
     */
    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    /**
     * Remove the preference in sp.
     *
     * @param key      The key of sp.
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * Remove all preferences in sp.
     */
    public void clear() {
        clear(false);
    }

    /**
     * Remove all preferences in sp.
     *
     * @param isCommit True to use {@link SharedPreferences.Editor#commit()},
     *                 false to use {@link SharedPreferences.Editor#apply()}
     */
    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

