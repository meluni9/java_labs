package lab5;

import java.io.*;

class CipherFilterWriter extends FilterWriter {
    private final int key;

    protected CipherFilterWriter(Writer out, int key) {
        super(out);
        this.key = key;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + key);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(cbuf[i]);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            write(str.charAt(i));
        }
    }
}
