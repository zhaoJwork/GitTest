package com.lin.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 用来处理头像组合
 * package cn.sh.ideal.ent.utils
 * @author zhangleimin
 * @date 17-2-27
 */
public class ImageUtil {

    private static String[] getXy(int size) {
        String[] s = new String[size];
        int _x = 0;
        int _y = 0;
        if (size == 1) {
            _x = _y = 6;
            s[0] = "6,6";
        }
        if (size == 2) {
            _x = _y = 4;
            s[0] = "4," + (132 / 2 - 60 / 2);
            s[1] = 60 + 2 * _x + "," + (132 / 2 - 60 / 2);
        }
        if (size == 3) {
            _x = _y = 4;
            s[0] = (132 / 2 - 60 / 2) + "," + _y;
            s[1] = _x + "," + (60 + 2 * _y);
            s[2] = (60 + 2 * _y) + "," + (60 + 2 * _y);
        }
        if (size == 4) {
            _x = _y = 4;
            s[0] = _x + "," + _y;
            s[1] = (_x * 2 + 60) + "," + _y;
            s[2] = _x + "," + (60 + 2 * _y);
            s[3] = (60 + 2 * _y) + "," + (60 + 2 * _y);
        }
        if (size == 5) {
            _x = _y = 3;
            s[0] = (132 - 40 * 2 - _x) / 2 + "," + (132 - 40 * 2 - _y) / 2;
            s[1] = ((132 - 40 * 2 - _x) / 2 + 40 + _x) + "," + (132 - 40 * 2 - _y) / 2;
            s[2] = _x + "," + ((132 - 40 * 2 - _x) / 2 + 40 + _y);
            s[3] = (_x * 2 + 40) + "," + ((132 - 40 * 2 - _x) / 2 + 40 + _y);
            s[4] = (_x * 3 + 40 * 2) + "," + ((132 - 40 * 2 - _x) / 2 + 40 + _y);
        }
        if (size == 6) {
            _x = _y = 3;
            s[0] = _x + "," + ((132 - 40 * 2 - _x) / 2);
            s[1] = (_x * 2 + 40) + "," + ((132 - 40 * 2 - _x) / 2);
            s[2] = (_x * 3 + 40 * 2) + "," + ((132 - 40 * 2 - _x) / 2);
            s[3] = _x + "," + ((132 - 40 * 2 - _x) / 2 + 40 + _y);
            s[4] = (_x * 2 + 40) + "," + ((132 - 40 * 2 - _x) / 2 + 40 + _y);
            s[5] = (_x * 3 + 40 * 2) + "," + ((132 - 40 * 2 - _x) / 2 + 40 + _y);
        }
        if (size == 7) {
            _x = _y = 3;
            s[0] = (132 - 40) / 2 + "," + _y;
            s[1] = _x + "," + (_y * 2 + 40);
            s[2] = (_x * 2 + 40) + "," + (_y * 2 + 40);
            s[3] = (_x * 3 + 40 * 2) + "," + (_y * 2 + 40);
            s[4] = _x + "," + (_y * 3 + 40 * 2);
            s[5] = (_x * 2 + 40) + "," + (_y * 3 + 40 * 2);
            s[6] = (_x * 3 + 40 * 2) + "," + (_y * 3 + 40 * 2);
        }
        if (size == 8) {
            _x = _y = 3;
            s[0] = (132 - 80 - _x) / 2 + "," + _y;
            s[1] = ((132 - 80 - _x) / 2 + _x + 40) + "," + _y;
            s[2] = _x + "," + (_y * 2 + 40);
            s[3] = (_x * 2 + 40) + "," + (_y * 2 + 40);
            s[4] = (_x * 3 + 40 * 2) + "," + (_y * 2 + 40);
            s[5] = _x + "," + (_y * 3 + 40 * 2);
            s[6] = (_x * 2 + 40) + "," + (_y * 3 + 40 * 2);
            s[7] = (_x * 3 + 40 * 2) + "," + (_y * 3 + 40 * 2);
        }
        if (size == 9) {
            _x = _y = 3;
            s[0] = _x + "," + _y;
            s[1] = _x * 2 + 40 + "," + _y;
            s[2] = _x * 3 + 40 * 2 + "," + _y;
            s[3] = _x + "," + (_y * 2 + 40);
            s[4] = (_x * 2 + 40) + "," + (_y * 2 + 40);
            s[5] = (_x * 3 + 40 * 2) + "," + (_y * 2 + 40);
            s[6] = _x + "," + (_y * 3 + 40 * 2);
            s[7] = (_x * 2 + 40) + "," + (_y * 3 + 40 * 2);
            s[8] = (_x * 3 + 40 * 2) + "," + (_y * 3 + 40 * 2);
        }
        return s;
    }

    public static int getWidth(int size) {
        int width = 0;
        if (size == 1) {
            width = 120;
        }
        if (size > 1 && size <= 4) {
            width = 60;
        }
        if (size >= 5) {
            width = 40;
        }
        return width;
    }
    
    public static void main(String[] args) throws Exception {
    	
    	
		//FileUtils.copyURLToFile(new URL("http://42.99.16.145/mpi/plist_dir/plist_dir/pic/3/193492.png"), new File("/Users/yanrongsheng/1.png"));
    	List<File> list=new ArrayList<File>();
    	File f1=new File("C:\\Users\\zhang\\Desktop\\中北信\\项目资料\\通讯录\\群组图片测试\\1.png");
    	list.add(f1);
    	File f2=new File("C:\\Users\\zhang\\Desktop\\中北信\\项目资料\\通讯录\\群组图片测试\\2.png");
    	list.add(f2);
    	File f3=new File("C:\\Users\\zhang\\Desktop\\中北信\\项目资料\\通讯录\\群组图片测试\\3.png");
    	list.add(f3);
    	
    	String outPath="C:\\Users\\zhang\\Desktop\\中北信\\项目资料\\通讯录\\群组图片测试\\输出结果\\"+System.currentTimeMillis()+".png";
    	createImage(list, outPath, "");
    
    }

    public static String zoom(String sourcePath, String targetPath, int width, int height) throws IOException {
        File imageFile = new File(sourcePath);
        if (!imageFile.exists()) {
            throw new IOException("Not found the images:" + sourcePath);
        }
        if (targetPath == null || targetPath.isEmpty())
            targetPath = sourcePath;
        String format = sourcePath.substring(sourcePath.lastIndexOf(".") + 1, sourcePath.length());
        BufferedImage image = ImageIO.read(imageFile);
        image = zoom(image, width, height);
        ImageIO.write(image, format, new File(targetPath));
        return targetPath;
    }

    public static String zoom(String sourcePath, String targetPath, int percent) throws IOException {
        File imageFile = new File(sourcePath);
        if (!imageFile.exists()) {
            throw new IOException("Not found the images:" + sourcePath);
        }
        if (targetPath == null || targetPath.isEmpty())
            targetPath = sourcePath;
        String format = sourcePath.substring(sourcePath.lastIndexOf(".") + 1, sourcePath.length());
        BufferedImage image = ImageIO.read(imageFile);
        int width = image.getWidth() * percent / 100;
        int height = image.getHeight() * percent / 100;
        image = zoom(image, width, height);
        ImageIO.write(image, format, new File(targetPath));
        return targetPath;
    }

    private static BufferedImage zoom(BufferedImage sourceImage, int width, int height) {
        BufferedImage zoomImage = new BufferedImage(width, height, sourceImage.getType());
        Image image = sourceImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Graphics gc = zoomImage.getGraphics();
        gc.setColor(Color.WHITE);
        gc.drawImage(image, 0, 0, null);
        return zoomImage;
    }

    /**
     * 创建图片，将几个图片合成一个
     * @param files 源文件
     * @param outPath   目标路径
     * @param tmp   临时目录
     * @throws Exception
     */
    public static void createImage(List<File> files, String outPath, String tmp) throws Exception {
        int imgCount = files.size() > 9 ? 9 : files.size();
        String[] imageSize = getXy(imgCount);
        int width = getWidth(files.size());
        BufferedImage ImageNew = new BufferedImage(132, 132, BufferedImage.TYPE_INT_RGB);
        // 设置背景为白色
        for (int m = 0; m < 132; m++) {
            for (int n = 0; n < 132; n++) {
                ImageNew.setRGB(m, n, 0xD6D6D6);
            }
        }
        for (int i = 0; i < imageSize.length; i++) {
            String size = imageSize[i];
            String[] sizeArr = size.split(",");
            int x = Integer.valueOf(sizeArr[0]);
            int y = Integer.valueOf(sizeArr[1]);
            String f = zoom(files.get(i).getPath(), tmp, width, width);
            File fileOne = new File(f);
            BufferedImage ImageOne = ImageIO.read(fileOne);

            // 从图片中读取RGB
            int[] ImageArrayOne = new int[width * width];
            ImageArrayOne = ImageOne.getRGB(0, 0, width, width, ImageArrayOne, 0, width);
            ImageNew.setRGB(x, y, width, width, ImageArrayOne, 0, width);// 设置左半部分的RGB
        }
        File outFile = new File(outPath);
        ImageIO.write(ImageNew, "png", outFile);// 写图片
    }
}
