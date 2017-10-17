package com.apass.gfb.framework.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jie.xu on 17/10/16.
 */
public class FTPUtils {
  private static final Logger LOG = LoggerFactory.getLogger(FTPUtils.class);

  /**
   * 文件上传
   */
  public static void uploadFile(String url, int port, String username,
                                String password, String path, String filename,
                                InputStream input) {

    FTPClient ftp = new FTPClient();
    try {
      int reply;
      ftp.connect(url, port);//连接FTP服务器
      ftp.login(username, password);//登录
      reply = ftp.getReplyCode();
      if (!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
      }
      ftp.changeWorkingDirectory(path);
      ftp.storeFile(filename, input);
      input.close();
      ftp.logout();
    } catch (IOException e) {
      LOG.error("[ftp] upload file happened error !", e);
    } finally {
      if (ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch (IOException ioe) {
          LOG.error("[ftp] upload file happened error !", ioe);
        }
      }
    }
  }

  /**
   * 下载文件
   */
  public static void downloadFile(String url, int port, String username,
                                  String password, String remotePath,
                                  String fileName, String localPath) {
    FTPClient ftp = new FTPClient();
    try {
      int reply;
      ftp.connect(url, port);
      ftp.login(username, password);//登录
      reply = ftp.getReplyCode();
      if (!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
      }
      ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
      FTPFile[] fs = ftp.listFiles();
      for (FTPFile ff : fs) {
        if (ff.getName().equals(fileName)) {
          File localFile = new File(localPath + "/" + ff.getName());
          OutputStream is = new FileOutputStream(localFile);
          ftp.retrieveFile(ff.getName(), is);
          is.close();
        }
      }
      ftp.logout();
    } catch (IOException e) {
      LOG.error("[ftp] download file happened error !", e);
    } finally {
      if (ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch (IOException ioe) {
          LOG.error("[ftp] download file happened error !", ioe);
        }
      }
    }
  }
}
