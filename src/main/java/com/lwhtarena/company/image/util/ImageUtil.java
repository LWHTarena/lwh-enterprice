package com.lwhtarena.company.image.util;

import com.lwhtarena.company.font.obj.WaterMark;
import com.lwhtarena.company.sys.util.FileUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.Iterator;

/**
 * @Author：liwh
 * @Description:
 * @Date 20:59 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class ImageUtil {
    public ImageUtil() {
    }

    public static void cutImage(String src, String dest, int x, int y, int w, int h, Integer ow, Integer oh) throws IOException {
        File picture = new File(src);
        FileInputStream fis = new FileInputStream(picture);
        BufferedImage sourceImg = ImageIO.read(fis);
        int sw = sourceImg.getWidth();
        int sh = sourceImg.getHeight();
        int tx = sw * x / ow;
        int ty = sh * y / oh;
        int tw = sw * w / ow;
        int th = sh * h / oh;
        Iterator<?> iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(tx, ty, tw, th);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        String dirpath = FileUtil.dir(dest);
        File filePath = new File(dirpath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        ImageIO.write(bi, "jpg", new File(dest));
        in.close();
        iis.close();
        fis.close();
        in = null;
        iis = null;
        fis = null;
    }

    public static void cutSmart(String src, int dw, int dh, WaterMark wm) {
        int dw2 = dw;
        int dh2 = dh;
        Iterator<?> iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in = null;
        byte imageIndex = 0;

        try {
            in = new FileInputStream(src);
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            int sw = reader.getWidth(imageIndex);
            int sh = reader.getHeight(imageIndex);
            if (sw * dh != sh * dw) {
                if (sw * dh < sh * dw) {
                    dh = sh * dw / sw;
                    reSize(src, dw, dh, wm);
                } else {
                    dw = sw * dh / sh;
                    reSize(src, dw, dh, wm);
                }

                String tmpFile = "tmp-" + System.currentTimeMillis() + "-" + FileUtil.getFileFromPath(src);
                String tmpPath = System.getProperty("java.io.tmpdir");
                tmpFile = tmpPath + tmpFile;
                File sFile = new File(src);
                File ftmp = new File(tmpFile);

                try {
                    FileUtils.copyFile(sFile, ftmp);
                    sFile.delete();
                } catch (IOException var34) {
                    var34.printStackTrace();
                }

                if (sFile.exists()) {
                    sFile.delete();
                }

                in.close();
                in = new FileInputStream(tmpFile);
                iis = ImageIO.createImageInputStream(in);
                reader.setInput(iis, true);
                param = reader.getDefaultReadParam();
                int x = (reader.getWidth(imageIndex) - dw2) / 2;
                int y = (reader.getHeight(imageIndex) - dh2) / 2;
                if (x < 0) {
                    x = 0;
                }

                if (y < 0) {
                    y = 0;
                }

                Rectangle rect = new Rectangle(x, y, dw2, dh2);
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, "jpg", new File(src));
                in.close();
                iis.close();
                in = null;
                iis = null;
                return;
            }

            reSize(src, dw, dh, wm);
        } catch (FileNotFoundException var35) {
            var35.printStackTrace();
            return;
        } catch (IOException var36) {
            var36.printStackTrace();
            return;
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException var33) {
                    var33.printStackTrace();
                }
            }

        }

    }

    public static void reSize(String file, int width, int heigh, WaterMark wm) {
        String tmpFile = "tmp-" + System.currentTimeMillis() + "-" + FileUtil.getFileFromPath(file);
        String tmpPath = System.getProperty("java.io.tmpdir");
        tmpFile = tmpPath + tmpFile;
        File ftmp = new File(tmpFile);
        File sFile = new File(file);

        try {
            FileUtils.copyFile(sFile, ftmp);
            sFile.delete();
        } catch (IOException var28) {
            var28.printStackTrace();
        }

        if (!ftmp.exists()) {
            System.out.println("log：copy failed:" + tmpFile);
        }

        if (sFile.exists()) {
            sFile.delete();
        }

        FileInputStream in = null;
        FileOutputStream out = null;
        String ext = FileUtil.getFileExt(file);

        try {
            in = new FileInputStream(ftmp);
            out = new FileOutputStream(sFile);
            if (!ext.trim().equalsIgnoreCase("jpg") && !ext.trim().equalsIgnoreCase("jpeg")) {
                if (ext.trim().equalsIgnoreCase("gif")) {
                    resizeGif(in, out, width, heigh, wm);
                } else if (ext.trim().equalsIgnoreCase("png")) {
                    resizePng(in, out, width, heigh, wm);
                }
            } else {
                resizeJpg(in, out, width, heigh, wm);
            }
        } catch (FileNotFoundException var29) {
            var29.printStackTrace();
        } catch (IOException var30) {
            var30.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException var27) {
                    var27.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    out = null;
                } catch (IOException var26) {
                    var26.printStackTrace();
                }
            }

        }

        if (ftmp.exists()) {
            ftmp.delete();
        }

    }

    private static void resizeJpg(InputStream in, OutputStream out, int maxWidth, int maxHeight, WaterMark wm) throws IOException {
        wm = wmCheck(wm);
        checkParams(in, out, maxWidth, maxHeight);
        BufferedImage image = ImageIO.read(in);
        image = Scalr.resize(image, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, maxWidth, maxHeight, new BufferedImageOp[0]);
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), 1);
        Graphics2D g = bufferedImage.createGraphics();
        g.setComposite(AlphaComposite.getInstance(10, 1.0F));
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.drawImage(image, 0, 0, (ImageObserver)null);
        if (wm != null) {
            String[] watermark = new String[]{wm.getTitle(), wm.getButtom()};
            makeWatermark(watermark, bufferedImage, wm);
        }

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
        float quality;
        if (wm == null) {
            quality = 1.0F;
        } else {
            quality = wm.getQuality();
        }

        param.setQuality(quality, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }

    private static void resizePng(InputStream in, OutputStream out, int maxWidth, int maxHeight, WaterMark wm) throws IOException {
        wm = wmCheck(wm);
        checkParams(in, out, maxWidth, maxHeight);
        BufferedImage image = ImageIO.read(in);
        image = Scalr.resize(image, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, maxWidth, maxHeight, new BufferedImageOp[0]);
        if (wm != null) {
            String[] watermark = new String[]{wm.getTitle(), wm.getButtom()};
            makeWatermark(watermark, image, wm);
        }

        ImageIO.write(image, "png", out);
    }

    private static void resizeGif(InputStream in, OutputStream out, int maxWidth, int maxHeight, WaterMark wm) throws IOException {
        wm = wmCheck(wm);
        checkParams(in, out, maxWidth, maxHeight);
        GifDecoder gd = new GifDecoder();
        int status = gd.read(in);
        if (status == 0) {
            AnimatedGifEncoder ge = new AnimatedGifEncoder();
            ge.start(out);
            ge.setRepeat(0);

            for(int i = 0; i < gd.getFrameCount(); ++i) {
                BufferedImage frame = gd.getFrame(i);
                BufferedImage rescaled = Scalr.resize(frame, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, maxWidth, maxHeight, new BufferedImageOp[0]);
                if (wm != null) {
                    String[] watermark = new String[]{wm.getTitle(), wm.getButtom()};
                    makeWatermark(watermark, rescaled, wm);
                }

                int delay = gd.getDelay(i);
                ge.setDelay(delay);
                ge.addFrame(rescaled);
            }

            ge.finish();
        }
    }

    private static void makeWatermark(String[] text, BufferedImage image, WaterMark wm) {
        if (text != null && text.length > 0 && wm != null && image != null) {
            Graphics2D graphics = image.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setFont(wm.getFont());
            graphics.setColor(wm.getColor());

            for(int i = 0; i < text.length; ++i) {
                if (!"".equals(text[i].trim())) {
                    FontRenderContext context = graphics.getFontRenderContext();
                    Rectangle2D fontRectangle = wm.getFont().getStringBounds(text[i], context);
                    int sw = (int)fontRectangle.getWidth();
                    int sh = (int)fontRectangle.getHeight();
                    int w1 = 3;
                    int w2 = 4;
                    int h1 = 5;
                    int h2 = 3;
                    if (text.length - i == 1) {
                        graphics.setColor(wm.getFont_shadow_color());
                        graphics.drawString(text[i], image.getWidth() - sw - w1, image.getHeight() - h1);
                        graphics.setColor(wm.getFont_color());
                        graphics.drawString(text[i], image.getWidth() - sw - w2, image.getHeight() - h2);
                    } else {
                        graphics.setColor(wm.getFont_shadow_color());
                        graphics.drawString(text[i], image.getWidth() - sw - w1, image.getHeight() - sh * (text.length - 1) - h1);
                        graphics.setColor(wm.getFont_color());
                        graphics.drawString(text[i], image.getWidth() - sw - w2, image.getHeight() - sh * (text.length - 1) - h2);
                    }
                }
            }

            graphics.dispose();
        }

    }

    private static void checkParams(InputStream in, OutputStream out, int maxWidth, int maxHeight) throws IOException {
        if (in == null) {
            throw new IOException("InputStream can not be null ");
        } else if (out == null) {
            throw new IOException("OutputStream can not be null ");
        } else if (maxWidth < 1 || maxHeight < 1) {
            throw new IOException("maxWidth or maxHeight can not be less than 1 ");
        }
    }

    private static WaterMark wmCheck(WaterMark wm) {
        if (wm == null) {
            return null;
        } else if (wm.getTitle() == null && wm.getButtom() == null) {
            return null;
        } else {
            if (wm.getTitle() == null) {
                wm.setTitle("");
            }

            if (wm.getButtom() == null) {
                wm.setButtom("");
            }

            if (wm.getTitle().trim().equals("") && wm.getButtom().trim().equals("")) {
                return null;
            } else {
                float quality = wm.getQuality();
                if (quality <= 0.0F || quality > 1.0F) {
                    quality = 1.0F;
                    wm.setQuality(quality);
                }

                return wm;
            }
        }
    }
}
