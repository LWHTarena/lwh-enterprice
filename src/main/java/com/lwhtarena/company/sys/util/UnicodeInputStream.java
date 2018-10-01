package com.lwhtarena.company.sys.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:53 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class UnicodeInputStream extends InputStream {

    PushbackInputStream internalIn;
    boolean isInited = false;
    String defaultEnc;
    String encoding;
    private static final int BOM_SIZE = 4;

    UnicodeInputStream(InputStream in, String defaultEnc) {
        this.internalIn = new PushbackInputStream(in, 4);
        this.defaultEnc = defaultEnc;
    }

    public String getDefaultEncoding() {
        return this.defaultEnc;
    }

    public String getEncoding() {
        if (!this.isInited) {
            try {
                this.init();
            } catch (IOException var3) {
                IllegalStateException ise = new IllegalStateException("Init method failed.");
                ise.initCause(ise);
                throw ise;
            }
        }

        return this.encoding;
    }

    protected void init() throws IOException {
        if (!this.isInited) {
            byte[] bom = new byte[4];
            int n = this.internalIn.read(bom, 0, bom.length);
            int unread;
            if (bom[0] == 0 && bom[1] == 0 && bom[2] == -2 && bom[3] == -1) {
                this.encoding = "UTF-32BE";
                unread = n - 4;
            } else if (bom[0] == -1 && bom[1] == -2 && bom[2] == 0 && bom[3] == 0) {
                this.encoding = "UTF-32LE";
                unread = n - 4;
            } else if (bom[0] == -17 && bom[1] == -69 && bom[2] == -65) {
                this.encoding = "UTF-8";
                unread = n - 3;
            } else if (bom[0] == -2 && bom[1] == -1) {
                this.encoding = "UTF-16BE";
                unread = n - 2;
            } else if (bom[0] == -1 && bom[1] == -2) {
                this.encoding = "UTF-16LE";
                unread = n - 2;
            } else {
                this.encoding = this.defaultEnc;
                unread = n;
            }

            if (unread > 0) {
                this.internalIn.unread(bom, n - unread, unread);
            }

            this.isInited = true;
        }
    }

    @Override
    public void close() throws IOException {
        this.isInited = true;
        this.internalIn.close();
        this.internalIn = null;
    }

    @Override
    public int read() throws IOException {
        this.isInited = true;
        return this.internalIn.read();
    }
}
