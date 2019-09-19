package site.kycer.project.ktool.basic.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * IO 工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class IOUtils {

    private IOUtils() {
    }

    public static byte[] toByteArray(InputStream stream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int read = 0;

        while (read != -1) {
            read = stream.read(buffer);
            if (read > 0) {
                outputStream.write(buffer, 0, read);
            }
        }

        return outputStream.toByteArray();
    }

    public static byte[] toByteArray(ByteBuffer buffer, int length) {
        if (buffer.hasArray() && buffer.arrayOffset() == 0) {
            return buffer.array();
        } else {
            byte[] data = new byte[length];
            buffer.get(data);
            return data;
        }
    }

    public static int readFully(InputStream in, byte[] b) throws IOException {
        return readFully(in, b, 0, b.length);
    }

    public static int readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        int total = 0;

        do {
            int got = in.read(b, off + total, len - total);
            if (got < 0) {
                return total == 0 ? -1 : total;
            }

            total += got;
        } while (total != len);

        return total;
    }

    public static int readFully(ReadableByteChannel channel, ByteBuffer b) throws IOException {
        int total = 0;

        do {
            int got = channel.read(b);
            if (got < 0) {
                return total == 0 ? -1 : total;
            }

            total += got;
        } while (total != b.capacity() && b.position() != b.capacity());

        return total;
    }

    public static void copy(InputStream inp, OutputStream out) throws IOException {
        byte[] buff = new byte[4096];

        int count;
        while ((count = inp.read(buff)) != -1) {
            if (count > 0) {
                out.write(buff, 0, count);
            }
        }

    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception var2) {

        }

    }
}
