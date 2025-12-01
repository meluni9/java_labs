package lab5;

import java.io.*;

class CipherFilterReader extends FilterReader {
    private final int key;

    protected CipherFilterReader(Reader in, int key) {
        super(in);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == -1) return -1;
        return c - key;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int n = super.read(cbuf, off, len);
        if (n == -1) return -1;
        for (int i = off; i < off + n; i++) {
            cbuf[i] = (char) (cbuf[i] - key);
        }
        return n;
    }
}
