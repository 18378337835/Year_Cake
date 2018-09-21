package utilpacket.utils;

/**
 * @author Year_Cake
 * @date 2017/12/21
 */
public class PostParamsBuilder {

    private StringBuilder sb = new StringBuilder("{");

    public PostParamsBuilder(){

    }

    /**
     * 拼接参数
     * @param key
     * @param value
     * @return
     */
    public PostParamsBuilder setParams(String key, String value){
        String endodeKey = key;
        String encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":\"").append(encodeValue).append("\",");
        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public PostParamsBuilder setParams(String key, int value){
        String endodeKey = key;
        int encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":\"").append(encodeValue).append("\",");
        return this;
    }

    /**
     * 拼接参数
     * @param key
     * @param value
     * @return
     */
    public PostParamsBuilder setListParam(String key, String value){
        String endodeKey = key;
        String encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":").append(encodeValue).append(",");
        return this;
    }

    /**
     * 拼接参数
     * @param key
     * @param value
     * @return
     */
    public PostParamsBuilder setListParam(String key, int value){
        String endodeKey = key;
        int encodeValue = value;
        sb.append("\"").append(endodeKey).append("\":").append(encodeValue).append(",");
        return this;
    }

    /**
     * 返回最终的拼接 字段
     * @return
     */
    public String build(){
        String str = sb.toString();
        if(str.endsWith(",")){
            str = str.substring(0,str.length()-1)+"}";
        }else{
            str = str+"}";
        }
        LogUtils.e("加密前-->"+str);
        try{
            str = new AESUtils().encrypt(str);
        }catch (Exception e){
            e.printStackTrace();
        }

        LogUtils.e("加密后-->"+str);
        return str;
    }

}