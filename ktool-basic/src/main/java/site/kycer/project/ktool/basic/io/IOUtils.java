package site.kycer.project.ktool.basic.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * IO 工具类
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-18
 */
public class IOUtils {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int DEFAULT_MIDDLE_BUFFER_SIZE = 16384;
    public static final int DEFAULT_LARGE_BUFFER_SIZE = 32768;
    public static final int EOF = -1;

    private IOUtils() {
    }


    /**
     * 读取文本, 默认缓冲大小
     *
     * @param stream  {@link InputStream}
     * @param charset {@link Charset} 编码
     * @return 文本内容
     */
    public static String read(InputStream stream, Charset charset) {
        return read(stream, DEFAULT_BUFFER_SIZE, charset);
    }

    /**
     * 读取文本, 默认缓冲大小
     *
     * @param stream  {@link InputStream}
     * @param charset 编码
     * @return 文本内容
     */
    public static String read(InputStream stream, int bufferSize, Charset charset) {
        return read(new InputStreamReader(stream, charset), bufferSize);
    }

    /**
     * 读取文本
     *
     * @param reader     {@link InputStreamReader}
     * @param bufferSize 缓冲大小
     * @return 文本内容
     */
    public static String read(InputStreamReader reader, int bufferSize) {
        String str = "";
        try (BufferedReader bufferedReader = toBuffered(reader)) {
            int len;
            char[] arr = new char[bufferSize];
            while ((len = bufferedReader.read(arr)) != EOF) {
                str = new String(arr, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * readBytes 默认缓冲大小
     *
     * @param stream {@link InputStream}
     * @return byte []
     * @throws IOException IO 异常
     */
    public static byte[] readBytes(InputStream stream) throws IOException {
        return readBytes(stream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * readBytes
     *
     * @param stream     {@link InputStream}
     * @param bufferSize 缓冲大小
     * @return byte []
     * @throws IOException IO 异常
     */
    public static byte[] readBytes(InputStream stream, int bufferSize) throws IOException {
        try (BufferedInputStream bufferedInputStream = toBuffered(stream)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != EOF) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        }
    }

    /**
     * 读取文本, 默认缓冲大小
     *
     * @param stream  {@link InputStream}
     * @param charset 编码
     * @return 文本内容
     */
    public static String write(InputStream stream, int bufferSize, Charset charset) {
        return read(new InputStreamReader(stream, charset), bufferSize);
    }

    /**
     * write byte
     *
     * @param stream {@link OutputStream}
     * @param b      the data.
     * @param off    the start offset in the data.
     * @param len    the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     */
    public static void write(OutputStream stream, byte[] b, int off, int len) throws IOException {
        try (BufferedOutputStream buffered = toBuffered(stream)) {
            buffered.write(b, off, len);
            buffered.flush();
        }
    }


    /**
     * 转换未 BufferedReader
     *
     * @param reader {@link Reader}
     * @return {@link BufferedReader}
     */
    public static BufferedReader toBuffered(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 转换未 BufferedWriter
     *
     * @param writer {@link Reader}
     * @return {@link BufferedWriter}
     */
    public static BufferedWriter toBuffered(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }


    /**
     * 转换未 BufferedInputStream
     *
     * @param stream {@link InputStream}
     * @return {@link BufferedInputStream}
     */
    public static BufferedInputStream toBuffered(InputStream stream) {
        return stream instanceof BufferedInputStream ? (BufferedInputStream) stream : new BufferedInputStream(stream);
    }

    /**
     * 转换未 BufferedOutputStream
     *
     * @param stream {@link OutputStream}
     * @return {@link BufferedOutputStream}
     */
    public static BufferedOutputStream toBuffered(OutputStream stream) {
        return stream instanceof BufferedOutputStream ? (BufferedOutputStream) stream : new BufferedOutputStream(stream);
    }


    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
