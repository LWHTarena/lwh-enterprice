package com.lwhtarena.company.sys.util;

import java.io.*;

/**
 * @Author：liwh
 * @Description:
 * @Date 23:55 2018/8/6
 * @Modified By:
 * <h1></h1>
 * <ol></ol>
 */
public class UnicodeReader extends Reader {
    PushbackInputStream internalIn;
    InputStreamReader internalIn2 = null;
    String defaultEnc;
    private static final int BOM_SIZE = 4;

    UnicodeReader(InputStream in, String defaultEnc) throws IOException {
        this.internalIn = new PushbackInputStream(in, 4);
        this.defaultEnc = defaultEnc;
        this.internalIn.close();
        this.internalIn = null;
    }

    public String getDefaultEncoding() {
        return this.defaultEnc;
    }

    public String getEncoding() {
        return this.internalIn2 == null ? null : this.internalIn2.getEncoding();
    }

    protected void init() throws IOException {
        if (this.internalIn2 == null) {
            byte[] bom = new byte[4];
            int n = this.internalIn.read(bom, 0, bom.length);
            String encoding;
            int unread;
            if (bom[0] == 0 && bom[1] == 0 && bom[2] == -2 && bom[3] == -1) {
                encoding = "UTF-32BE";
                unread = n - 4;
            } else if (bom[0] == -1 && bom[1] == -2 && bom[2] == 0 && bom[3] == 0) {
                encoding = "UTF-32LE";
                unread = n - 4;
            } else if (bom[0] == -17 && bom[1] == -69 && bom[2] == -65) {
                encoding = "UTF-8";
                unread = n - 3;
            } else if (bom[0] == -2 && bom[1] == -1) {
                encoding = "UTF-16BE";
                unread = n - 2;
            } else if (bom[0] == -1 && bom[1] == -2) {
                encoding = "UTF-16LE";
                unread = n - 2;
            } else {
                encoding = this.defaultEnc;
                unread = n;
            }

            if (unread > 0) {
                this.internalIn.unread(bom, n - unread, unread);
            }

            if (encoding == null) {
                this.internalIn2 = new InputStreamReader(this.internalIn);
            } else {
                this.internalIn2 = new InputStreamReader(this.internalIn, encoding);
            }

        }
    }


    @Override
    public void close() throws IOException {
        this.init();
        this.internalIn2.close();
        this.internalIn2 = null;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        this.init();
        return this.internalIn2.read(cbuf, off, len);
    }
}
