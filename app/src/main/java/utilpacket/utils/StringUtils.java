package utilpacket.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Patterns;
import android.widget.EditText;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author koma
 * @date 2017/12/18
 * @describe 字符串工具类
 */

public class StringUtils {

    /**
     * 检查字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str);
    }

    /**
     * 检查数组是否为空
     * @param list
     * @return
     */
    public static boolean isListEmpty(List<?> list){
        return list == null || list.size() == 0;
    }

    /**
     * 判断object是否为空
     * @param o
     * @return
     */
    public static boolean isNull(Object o){
        return null == o;
    }

    /**
     * 判断某个字符在字符串出现的次数
     * @param str
     * @param key
     * @return
     */
    public static int countStr(String str, char key) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == key) {
                count++;
            }
        }
        return count == 0 ? -1 : count;
    }

    /**
     * 实际替换动作
     *
     * @param username username
     * @param regular  正则
     * @return
     */
    private static String replaceAction(String username, String regular) {
        return username.replaceAll(regular, "*");
    }

    /**
     * 银行卡替换，保留前四位和后四位
     *
     * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param bankCard 银行卡号
     * @return
     */
    public static String bankCardReplaceWithStar(String bankCard) {

        if (bankCard.isEmpty() || bankCard == null) {
            return null;
        } else {
            return replaceAction(bankCard, "(?<=\\d{4})\\d(?=\\d{4})");
        }
    }

    /**
     * 手机号替换，保留前三位和后四位
     *
     * 如果手机号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param mobile 手机号
     * @return
     */
    public static String mobileNoReplaceWithStart(String mobile){
        if (mobile.isEmpty() || mobile == null) {
            return null;
        } else {
            return replaceAction(mobile, "(?<=\\d{3})\\d(?=\\d{4})");
        }
    }

    /**
     * 身份证号替换，保留前四位和后四位
     *
     * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param idNo 身份证号
     * @return
     */
    public static String idNoReplaceWithStart(String idNo){
        if (idNo.isEmpty() || idNo == null) {
            return null;
        } else {
            return replaceAction(idNo, "(?<=\\d{4})\\d(?=\\d{4})");
        }
    }

    /**
     * 身份证号替换，保留前front位和后back位
     *
     * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     * @param idNo
     * @param front
     * @param back
     * @return
     */
    public static String idNoReplaceWithStart(String idNo, int front, int back){
        if (idNo.isEmpty() || idNo == null) {
            return null;
        } else {
            return replaceAction(idNo, "(?<=\\d{"+front+"})\\d(?=\\d{"+back+"})");
        }
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }


    /**
     * 判断字符串是否是URL
     * @param url
     * @return
     */
    public static Boolean isUrl(String url) {
        Boolean temp = false;
        if (Patterns.WEB_URL.matcher(url).matches()) {
            //符合标准
            temp = true;
        } else {
            temp = false;
        }
        return temp;
    }

    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    /**
     * 获取颜色
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context, @ColorRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * 设置Html文本
     * @param context
     * @param id
     * @return
     */
    public static Spanned getHtmlText(Context context, int id) {
        return getHtmlText(context, context.getResources().getString(id));
    }

    /**
     * 设置Html文本
     * @param context
     * @param str
     */
    public static Spanned getHtmlText(Context context, String str){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(str);
        }
    }


    /**
     * 获取文本宽度
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(String str, Paint paint) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    /**
     * 获得文本的高度
     * @param text
     * @param paint
     * @return
     */
    public static int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int height = bounds.bottom + bounds.height();
        return height;
    }

    /**
     * 每三位数字加一个逗号
     * @param data
     * @return
     */
    public static String formatString(int data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }


    /**
     * 生成随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


    public static SpannableStringBuilder getSpannable(String text, Context context){
        StringBuilder sb = new StringBuilder();
            sb.append(text);
        SpannableStringBuilder spanStrBuilder = new SpannableStringBuilder(sb.toString());
        int myColor = Color.rgb(33,43,106);
        spanStrBuilder.setSpan(new ForegroundColorSpan(myColor),0, sb.length(),
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spanStrBuilder;
    }

    /**
     * 数据为null或空，返回空字符串，否则返回原数据
     * @param str
     * @return
     */
    public static String getText(String str){
        if(isEmpty(str)){
            return "";
        }
        return str;
    }

    /**
     * 获取EditText数据
     * @param editText
     * @return
     */
    public static String getEditText(EditText editText){
        if(isEmpty(editText.getText().toString().trim())){
            return "";
        }
        return editText.getText().toString().trim();
    }

    /**
     * 判断文本是否为空，基数相加
     * @param s
     * @return
     */
    public static int getTextNoEmptyCount(String s){
        int count = 0;
        if(!isEmpty(s)){
            count ++;
        }
        return count;
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date=formatter.format(new Date());
        return date;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=formatter.format(new Date());
        return date;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 字符串从右边每三位加一个逗号
     * @param string
     * @return
     */
    public static String formatStringComma(String string){
        if(TextUtils.isEmpty(string)){
            return "";
        }else{
            String str1 = new StringBuilder(string).reverse().toString();     //先将字符串颠倒顺序
            String str2 = "";
            for(int i = 0; i < str1.length(); i++){
                if(i * 3 + 3 > str1.length()){
                    str2 += str1.substring(i * 3, str1.length());
                    break;
                }
                str2 += str1.substring(i * 3, i * 3 + 3) + ",";
            }
            if(str2.endsWith(",")){
                str2 = str2.substring(0, str2.length()-1);
            }
            //最后再将顺序反转过来
            return new StringBuilder(str2).reverse().toString();
        }
    }

    /**
     * 去除城市里的“市”字
     * @param city
     * @return
     */
    public static String regCity(String city){
        if(isEmpty(city)) {
            return "";
        }else{
            return city.contains("市") ? city.replace("市" ,"") : city;
        }
    }

    /**
     * 压缩bitmap并转化为base64
     * @param bitmap
     * @return
     */
    public static String bitmap2Base64String(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos   = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.replaceAll("[\\s*\t\n\r]", "");

    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    /**
     * jsonobject转List
     * @param json
     * @param jsonObject
     * @return
     */
    public static List<String> jsonObject2List(String json, JSONObject jsonObject){
        List<String> jsonList = new ArrayList<>();
        JSONObject customerJson = isEmpty(json) ? jsonObject : jsonObject.getJSONObject(json);
        List<String> keys = sortList(getJsonObjectKey(customerJson));
        for (int i = 0; i < keys.size(); i++) {
            jsonList.add(customerJson.getString(keys.get(i)));
        }
        return jsonList;
    }

    public static List<String> jsonObject2List(JSONObject jsonObject){
        List<String> jsonList = new ArrayList<>();
        List<String> keys = sortList(getJsonObjectKey(jsonObject)
        );
        for (int i = 0; i < keys.size(); i++) {
            jsonList.add(jsonObject.getString(keys.get(i)));
        }
        return jsonList;
    }


    /**
     * jsonArray数组转为List
     * @param json
     * @param jsonObject
     * @return
     */
    public static List<String> jsonArray2List(String json, JSONObject jsonObject){
        List<String> jsonList = new ArrayList<>();
        JSONArray customerJson = jsonObject.getJSONArray(json);
        for (int i = 0; i < customerJson.size(); i++) {
            jsonList.add(customerJson.getString(i));
        }
        return jsonList;
    }

    /**
     * 获取jsonobject的key值,转为List
     * @param json
     * @return
     */
    public static List<String> getJsonObjectKey(JSONObject json){
        List<String> keys = new ArrayList<>();
        if(null != json){
            LinkedHashMap<String, String> linkedHashMap = JSON.parseObject(json.toString(),
                    new TypeReference<LinkedHashMap<String, String>>() {}, Feature.OrderedField);
            for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
                if(!isEmpty(entry.getKey())){
                    keys.add(entry.getKey());
                }
            }
        }
        return keys;
    }

    /**
     * 获取键值在jsonobject的索引
     * @param key
     * @param jsonObject
     * @return
     */
    public static int getKeyPosition(String key, JSONObject jsonObject){
        int position = 0;
        if(isEmpty(key)){
            return position;
        }
        List<String> keys = sortList(getJsonObjectKey(jsonObject));
        for(int i = 0; i < keys.size(); i++){
            if(key.equals(keys.get(i))){
                return i;
            }
        }
        return position;
    }

    /**
     * 根据value值获取到对应的一个key值
     * @param value
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static String getJSONObjectKey(String value, String seed, JSONObject jsonObject) {
        String key = null;
        if(TextUtils.isEmpty(value)){
            key = "0";
        }else {
            JSONObject customerJson = isEmpty(seed) ? jsonObject : jsonObject.getJSONObject(seed);
            List<String> keys = getJsonObjectKey(customerJson);
            Map<String, String> map = new HashMap<>();
            for(int i = 0; i < keys.size(); i++){
                map.put(keys.get(i), customerJson.getString(keys.get(i)));
            }
            for (String getKey : map.keySet()) {
                if (map.get(getKey).equals(value)) {
                    key = getKey;
                }
            }
        }
        return key;
    }


    /**
     * 根据键获得jsonobject的值
     * @param key
     * @param jsonObject
     * @return
     */
    public static String getJsonValue(String key, String seed, JSONObject jsonObject){
        String content;
        if(TextUtils.isEmpty(key)){
            content="";
        }else {
            JSONObject customerJson = isEmpty(seed ) ? jsonObject :jsonObject.getJSONObject(seed);
            if(!isListEmpty(getJsonObjectKey(customerJson))){
                List<String> keys = sortList(
                        getJsonObjectKey(customerJson)
                );
                Map<String, String> map = new HashMap<>();
                for(int i = 0; i < keys.size(); i++){
                    map.put(keys.get(i), customerJson.getString(keys.get(i)));
                }
                content=map.get(key);
            }else{
                content = "";
            }
        }
        return  content;
    }


    /**
     * 获取jsonArray的值
     * @param str
     * @param jsonArray
     * @return
     */
    public static String getJsonValue(String str, JSONArray jsonArray){
        if(TextUtils.isEmpty(str) || str.equals("-1")){
            str = "";
        }else {
            str = jsonArray.getString(Integer.parseInt(str));
        }
        return str;
    }

    /**
     * 获取下拉选项数据
     *
     * @param json
     * @return
     */
    public static List<String> getJsonArray(JSONObject jsonObject, String json) {

        List<String> jsonList = new ArrayList<>();

        JSONArray customerJson = jsonObject.getJSONArray(json);
        for (int i = 0; i < customerJson.size(); i++) {
            jsonList.add(customerJson.getString(i));
            LogUtils.e("value-->" + customerJson.getString(i));
        }
        return jsonList;
    }


    /**
     * options字段键值排序
     * @param list
     * @return
     */
    public static List<String> sortList(List<String> list){
        if(null == list || list.isEmpty()){
            return null;
        }
        try{
            List<Integer> intList = new LinkedList<>();
            for(int i = 0; i < list.size(); i++){
                intList.add(Integer.parseInt(list.get(i)));
            }
            for (int i = 0; i < intList.size(); i++) {
                for(int j = 0; j < intList.size() - 1; j++){
                    if(intList.get(j) > intList.get(j + 1)){
                        int temp = intList.get(j);
                        intList.set(j, intList.get(j + 1));
                        intList.set(j + 1, temp);
                    }
                }
            }
            List<String> resultList = new LinkedList<>();
            for(int i = 0; i < intList.size(); i++){
                resultList.add(String.valueOf(intList.get(i)));
            }
            return resultList;
        }catch (NumberFormatException e){
            return null;
        }
    }


    /**
     * 判断数据，如果数据为空返回默认值，否则返回原数据
     * @param s
     * @param defaultString
     * @return
     */
    public static String matchString(String s, String defaultString){
        if(isEmpty(s)){
            return defaultString;
        }
        return s;
    }




    /**
     * 获取整数是几位数
     *
     * @param num
     * @return
     */
    public static int getDigit(int num) {
        int count = 0;
        if (num == 0) {
            count = 1;
        } else {
            while (num > 0) {
                num = num / 10;
                count++;
            }
        }
        return count;
    }


    /**
     * 在min和max范围内获取随机字符串
     * @param min
     * @param max
     * @return
     */
    public static int getRadom(int min, int max){
        return new Random().nextInt(max - min + 1) + min;
    }

    /**
     * 整数小于10前面加0
     * @param num
     * @return
     */
    public static String addZero(int num){
        return num < 10 ? "0" + num : String.valueOf(num);
    }


    //从资源文件中获取分类json
    public static String getAssetsData(Context context, String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = context.getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }


    /**
     * 清除对象
     * @param object
     */
    public static void clearObject(Object object){
        if(null != object){
            object = null;
        }
    }


    /**
     * 获取jsonobject中值不为空的总数
     * @param jsonObject
     * @return
     */
    public static int getJsonNoEmptyCount(JSONObject jsonObject){
        int count = 0;
        List<String> keys = new ArrayList<>();
        LinkedHashMap<String, String> linkedHashMap = JSON.parseObject(jsonObject.toJSONString(),
                new TypeReference<LinkedHashMap<String, String>>() {
                });
        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            if(!isEmpty(jsonObject.getString(entry.getKey()))){
                count ++;
            }
        }
        return count;
    }


    /**
     * 获取udid
     * @param mContext
     * @return
     */
    public static String getUUIDString(Context mContext) {
        String KEY_UUID = "key_uuid";
        String uuid = SPUtil.getString(mContext, KEY_UUID, "");
        if (uuid != null && uuid.trim().length() != 0)
            return uuid;

        uuid = UUID.randomUUID().toString();
        uuid = Base64.encodeToString(uuid.getBytes(), Base64.DEFAULT);
        SPUtil.putString(mContext, KEY_UUID, uuid);
        return uuid;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static String saveJPGFile(Context mContext, byte[] data, String key) {

        LogUtils.e("step---> 2");
        if (data == null)
            return null;

        LogUtils.e("step---> 3");
        File mediaStorageDir = mContext.getExternalFilesDir("livenessDemo_image");

        LogUtils.e("step---> 4");
        if (!mediaStorageDir.exists()) {
            LogUtils.e("step---> 5");
            if (!mediaStorageDir.mkdirs()) {
                LogUtils.e("step---> 6");
                return null;
            }
        }
        LogUtils.e("step---> 7");
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            LogUtils.e("step---> 8");
            String jpgFileName = System.currentTimeMillis() + "" + new Random().nextInt(1000000) + "_" + key + ".jpg";
            LogUtils.e("step---> 9");
            fos = new FileOutputStream(mediaStorageDir + "/" + jpgFileName);
            LogUtils.e("step---> 10");
            bos = new BufferedOutputStream(fos);
            LogUtils.e("step---> 11");
            bos.write(data);
            LogUtils.e("step---> 12");
            LogUtils.e("filename-->" + mediaStorageDir.getAbsolutePath() + "/" + jpgFileName);
            return mediaStorageDir.getAbsolutePath() + "/" + jpgFileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取下拉选项数据
     *
     * @param json
     * @return
     */
    public static List<String> getJson(JSONObject jsonObject, String json) {

        List<String> jsonList = new ArrayList<>();

        JSONObject customerJson = jsonObject.getJSONObject(json);
        List<String> keys = sortList(AppUtils.getJsonObjectKey(customerJson));
        for (int i = 0; i < keys.size(); i++) {
            jsonList.add(customerJson.getString(keys.get(i)));
            LogUtils.e("value-->" + customerJson.getString(keys.get(i)));
        }
        return jsonList;
    }

    /**
     * 获取下拉选项数据
     *
     * @param json
     * @return
     */
    public static List<String> getJsonInfo(JSONObject jsonObject, String json) {

        List<String> jsonList = new ArrayList<>();

        JSONObject customerJson = jsonObject.getJSONObject(json);
        List<String> keys = AppUtils.getJsonObjectKey(customerJson);
        for (int i = 0; i < keys.size(); i++) {
            jsonList.add(customerJson.getString(keys.get(i)));
            LogUtils.e("value-->" + customerJson.getString(keys.get(i)));
        }
        return jsonList;
    }
    /**
     * 根据身份证得到年龄
     *
     * @param identifyNo
     * @return
     */
    public static int getAgeFromId(String identifyNo) {


        if (TextUtils.isEmpty(identifyNo) || ((identifyNo.length() != 18 && identifyNo.length() != 15))) {
            return 0;
        }

        if (identifyNo.length() == 15) {
            identifyNo = from15to18(20, identifyNo);
        }
        String year = identifyNo.substring(6).substring(0, 4);// 得到年份
        String month = identifyNo.substring(10).substring(0, 2);// 得到月份
        String day = identifyNo.substring(12).substring(0, 2);//得到日
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        String fday = format.format(date).substring(8, 10);
        int age = 0;
        if (Integer.parseInt(month) < Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year);
        } else if (Integer.parseInt(month) > Integer.parseInt(fyue)) {// 当前用户还没过生
            age = Integer.parseInt(fyear) - Integer.parseInt(year) - 1;
        } else {      //当前月份等于用户出身的月份，根据日期判断
            if (Integer.parseInt(day) <= Integer.parseInt(fday)) {
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            } else {
                age = Integer.parseInt(fyear) - Integer.parseInt(year) - 1;
            }
        }
        return age;
    }
    /**
     * @param century    19xx 年用 19，20xx 年用 20
     * @param idCardNo15 待转换的 15 位身份证号码
     * @return
     */
    public static String from15to18(int century, String idCardNo15) {

        String centuryStr = "" + century;
        if (century < 0 || centuryStr.length() != 2)
            throw new IllegalArgumentException("世纪数无效！应该是两位的正整数。");
        if (!(isIdCardNo(idCardNo15) && idCardNo15.length() == 15))
            throw new IllegalArgumentException("旧的身份证号格式不正确！");

        int[] weight = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

        // 通过加入世纪码, 变成 17 为的新号码本体.
        String newNoBody = idCardNo15.substring(0, 6) + centuryStr + idCardNo15.substring(6);

        //下面算最后一位校验码

        int checkSum = 0;
        for (int i = 0; i < 17; i++) {
            int ai = Integer.parseInt("" + newNoBody.charAt(i)); // 位于 i 位置的数值
            checkSum = checkSum + ai * weight[i];
        }

        int checkNum = checkSum % 11;
        String checkChar = null;

        switch (checkNum) {
            case 0:
                checkChar = "1";
                break;
            case 1:
                checkChar = "0";
                break;
            case 2:
                checkChar = "X";
                break;
            default:
                checkChar = "" + (12 - checkNum);
        }

        return newNoBody + checkChar;

    }
    /**
     * 判断给定的字符串是不是符合身份证号的要求
     *
     * @param str
     * @return
     */
    public static boolean isIdCardNo(String str) {

        if (str == null)
            return false;

        int len = str.length();
        if (len != 15 && len != 18)
            return false;

        for (int i = 0; i < len; i++) {
            try {
                Integer.parseInt("" + str.charAt(i));
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * 根据身份证号得到性别
     *
     * @param identifyNo
     * @return 0--女  1--男
     */
    public static int getSexFromId(String identifyNo) {

        if (TextUtils.isEmpty(identifyNo) || (identifyNo.length() != 18 && identifyNo.length() != 15)) {
            return 1;
        }

        if (identifyNo.length() == 15) {
            identifyNo = from15to18(20, identifyNo);
        }

        if (Integer.parseInt(identifyNo.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            return 0;
        } else {
            return 1;
        }
    }
}

