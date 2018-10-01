package com.lwhtarena.company.sys.util;

import com.lwhtarena.company.sys.obj.RandObj;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:43 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class RandomNumUtil {

    private ByteArrayInputStream inputStream;
    private String randomCode;

    public RandomNumUtil(HttpServletResponse response, int width, int height, int size, int fontSize, int mode) throws Exception {
        this.init(response, width, height, size, fontSize, mode);
    }

    private void init(HttpServletResponse response, int width, int height, int size, int fontSize, int mode) throws Exception {
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(new Color(252, 252, 184));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", 1, fontSize));
        g.setColor(this.getRandColor(200, 250));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(this.getRandColor(160, 200));

        for(int i = 0; i < 120; ++i) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(2) + 1;
            int yl = random.nextInt(4) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }

        this.randomCode = this.getRandCode(g, size, width, height, mode);
        g.dispose();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
        ImageIO.write(image, "JPEG", response.getOutputStream());
        imageOut.flush();
        imageOut.close();
        output.flush();
        output.close();
        response.getOutputStream().flush();
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        this.setInputStream(input);
        image = null;
        input.close();
        input = null;
        imageOut = null;
        output = null;
    }

    private static RandObj getRandChar(int size, int mode) {
        char[] c;
        byte l;
        RandObj ro;
        ro = new RandObj();
        int m;
        int n;
        label103:
        switch(mode) {
            case 1:
                l = 10;
                c = new char[l];
                m = 48;
                n = 0;

                while(true) {
                    if (m >= 58) {
                        break label103;
                    }

                    c[n] = (char)m;
                    ++m;
                    ++n;
                }
            case 2:
                l = 46;
                c = new char[l];
                m = 97;

                for(n = 0; m < 123; ++n) {
                    if (m != 111 && m != 105 && m != 108) {
                        c[n] = (char)m;
                    } else {
                        --n;
                    }

                    ++m;
                }

                m = 65;
                n = 23;

                while(true) {
                    if (m >= 91) {
                        break label103;
                    }

                    if (m != 79 && m != 73 && m != 76) {
                        c[n] = (char)m;
                    } else {
                        --n;
                    }

                    ++m;
                    ++n;
                }
            default:
                l = 56;
                c = new char[l];
                m = 97;

                for(n = 0; m < 123; ++n) {
                    if (m != 111 && m != 105 && m != 108) {
                        c[n] = (char)m;
                    } else {
                        --n;
                    }

                    ++m;
                }

                m = 65;

                for(n = 23; m < 91; ++n) {
                    if (m != 79 && m != 73 && m != 76) {
                        c[n] = (char)m;
                    } else {
                        --n;
                    }

                    ++m;
                }

                m = 48;

                for(n = 46; m < 58; ++n) {
                    c[n] = (char)m;
                    ++m;
                }
        }

        ro.setC(c);
        ro.setL(l);
        return ro;
    }

    public static String getRandStr(int size, int mode) {
        Random random = new Random();
        RandObj ro = getRandChar(size, mode);
        char[] c = ro.getC();
        String sRand = "";

        for(int i = 0; i < size; ++i) {
            int x = random.nextInt(ro.getL());
            String rand = String.valueOf(c[x]);
            sRand = sRand + rand;
        }

        return sRand;
    }

    private String getRandCode(Graphics g, int size, int width, int height, int mode) {
        Random random = new Random();
        RandObj ro = getRandChar(size, mode);
        char[] c = ro.getC();
        int l = ro.getL();
        String sRand = "";

        for(int i = 0; i < size; ++i) {
            int x = random.nextInt(l);
            String rand = String.valueOf(c[x]);
            sRand = sRand + rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + width / 10, height - 4);
        }

        return sRand;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getRandomCode() throws Exception {
        return this.randomCode;
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ByteArrayInputStream getInputStream() {
        return this.inputStream;
    }
}
