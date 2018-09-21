package com.hjn.year_cake.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 保存对象时候,对象必须实现Serializable
 * <p/>
 * Created by KK on 15/12/18.
 */
public class PrefsUtil {
    public SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PrefsUtil(Context context, String prefsName) {
        this.prefs = context.getSharedPreferences(prefsName, context.MODE_PRIVATE);
    }

    public int getInt(String key, int defValue) {
        return this.prefs.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return this.prefs.getLong(key, defValue);
    }

    public String getString(String key, String defValue) {
        return this.prefs.getString(key, defValue);
    }

    public Object getObject(String key) {
        try {
            String e = this.prefs.getString(key, "");
            if (TextUtils.isEmpty(e)) {
                return null;
            } else {
                byte[] base64Bytes= Base64.decode(e.getBytes(),0);
                ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }


    public void putInt(String key, int v) {
        this.ensureEditorAvailability();
        this.editor.putInt(key, v);
        this.save();
    }

    public void putLong(String key, long v) {
        this.ensureEditorAvailability();
        this.editor.putLong(key, v);
        this.save();
    }

    public void putString(String key, String v) {
        this.ensureEditorAvailability();
        this.editor.putString(key, v);
        this.save();
    }

    public void putObject(String key, Object obj) {
        this.ensureEditorAvailability();

        try {
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(e);
            oos.writeObject(obj);
//            String stringBase64 = new String(Base64.encodeBase64(e.toByteArray()));
            String stringBase64 = new String(Base64.encode(e.toByteArray(),0));
            this.editor.putString(key, stringBase64);
            this.save();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    public void save() {//提交
        if (this.editor != null) {
            this.editor.commit();
        }
    }

    private void ensureEditorAvailability() {//确定editor是否存在
        if (this.editor == null) {
            this.editor = this.prefs.edit();
        }

    }

    public void remove(String key) {//根据key移除
        this.ensureEditorAvailability();
        this.editor.remove(key);
        this.save();
    }

    public void clear() {//清空
        this.ensureEditorAvailability();
        this.editor.clear();
        this.save();
    }

}
