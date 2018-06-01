package com.lin.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


/**
 * 资源文件操作工具类
 */
public class PropUtil {

    private PropUtil() {

    }
    private static final Properties PROPERTIES=getProperties("properties/jedis.properties");
    
    private static final Properties IDEAPROPERTIES=getProperties("properties/idea.properties");
	
    public static final String JEDIS_HOSTNAME=PROPERTIES.getProperty("jedis_hostName");

	public static final String JEDIS_PORT=PROPERTIES.getProperty("jedis_port");
	
	/* 通讯录头像IP  */
	public static final String PIC_HTTPIP=IDEAPROPERTIES.getProperty("pic_HttpIP");
	//临时目录 供制作群组头像使用
	public static final String PIC_GROUP_TEMP_IMG=IDEAPROPERTIES.getProperty("pic_group_temp_img");
	//保存到服务器文件路径 绝对路径
	public static final String PIC_GROUP_IMG=IDEAPROPERTIES.getProperty("pic_group_img");
	//保存到数据库地址
	public static final String PIC_GROUP_DB_IMG=IDEAPROPERTIES.getProperty("pic_group_db_img");
	//服务器无法访问外网，改为拷贝本地图片
	public static final String PIC_GROUP_DB_IMG_ROOT=IDEAPROPERTIES.getProperty("pic_group_db_img_root");
	//调取积分路径
	public static final String sales_integral_add_score=IDEAPROPERTIES.getProperty("sales_integral_add_score");
	
    public static Properties getProperties(String resourcePath) {
        InputStream is = PropUtil.class.getClassLoader().getResourceAsStream(resourcePath);
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return prop;
    }
    
    public static Properties getProperties(String resourcePath,String code) {
    	InputStreamReader is = null;
    	Properties prop=new Properties();
        try {
        	 is  = new InputStreamReader(PropUtil.class.getClassLoader().getResourceAsStream(resourcePath),code);
        	 prop.load(is);  
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return prop;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new String(PropUtil.getProperties("jedis.properties").getProperty("jedis_port").getBytes("ISO-8859-1"),"UTF-8"));
        try{
        	throw new Exception("aaa");
        }catch(Exception e){
        	e.printStackTrace();
        }
        System.out.println("llll");
    }

}
