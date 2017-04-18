package com.apass.gfb.framework.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.apass.gfb.framework.exception.BusinessException;


/**
 * 
 * @description IO Utils
 *
 * @author listening
 * @version $Id: ListeningIOUtils.java, v 0.1 2015年11月4日 下午10:17:51 listening Exp $
 */
public class ListeningIOUtils {
    /**
     * Close Input And Output Stream
     * 
     * @param inputStream
     * @param outputStream
     */
    public static void closeQuietly(InputStream inputStream, OutputStream outputStream) {
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);
    }

    /**
     * Close Input And Output Stream
     * 
     * @param inputStream
     * @param outputStream
     */
    public static void closeQuietly(InputStream inputStream) {
        IOUtils.closeQuietly(inputStream);
    }

    /**
     * Close Input And Output Stream
     * 
     * @param inputStream
     * @param outputStream
     */
    public static void closeQuietly(OutputStream outputStream) {
        IOUtils.closeQuietly(outputStream);
    }

    /**
     * Convert InputStream To String
     * 
     * @param input
     * @param encoding
     * @return String
     * @throws IOException
     */
    public static String toString(InputStream input, String encoding) throws IOException {
        return IOUtils.toString(input, encoding);
    }

    /**
     * Convert InputStream To String
     * 
     * @param input
     * @param encoding
     * @return String
     * @throws IOException
     */
    public static String toString(InputStream input) throws IOException {
        return IOUtils.toString(input, "utf-8");
    }

    /**
     * Read File To Byte Array
     * 
     * @param file
     * @return
     * @throws BusinessException
     */
    public static final byte[] readStreamToByteArray(InputStream inputStream) throws BusinessException {
        ByteArrayOutputStream output = null;
        BufferedInputStream input = null;
        try {
            output = new ByteArrayOutputStream();
            input = new BufferedInputStream(inputStream, 1024);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            return output.toByteArray();
        } catch (Exception e) {
            throw new BusinessException("read stream to array fail", e);
        } finally {
            ListeningIOUtils.closeQuietly(input, output);
        }
    }
}
