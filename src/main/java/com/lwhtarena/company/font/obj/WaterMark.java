package com.lwhtarena.company.font.obj;

import java.awt.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 20:37 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class WaterMark {
    private Font font;
    private String title;
    private String buttom;
    private float quality;
    private Color color;
    private Color font_color;
    private Color font_shadow_color;

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getButtom() {
        return buttom;
    }

    public void setButtom(String buttom) {
        this.buttom = buttom;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getFont_color() {
        return font_color;
    }

    public void setFont_color(Color font_color) {
        this.font_color = font_color;
    }

    public Color getFont_shadow_color() {
        return font_shadow_color;
    }

    public void setFont_shadow_color(Color font_shadow_color) {
        this.font_shadow_color = font_shadow_color;
    }
}
