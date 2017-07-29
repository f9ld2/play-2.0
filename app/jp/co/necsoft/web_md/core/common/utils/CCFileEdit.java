// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : ファイル操作クラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.02 T.Matsuda 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import jp.co.necsoft.web_md.common.ChaseException;

/**
 * ファイル操作クラス<BR>
 */
public class CCFileEdit {
    /** BYTEバッファーサイズ既定値 */
    private static final int CNST_IBYTE_SIZE = 8192;
    /** Charset of out file */
    public static final String DEFAULT_OUTPUT_CHARSET = "UTF8";
    /** Charset extend from sjis */
    public static final String DEFAULT_INPUT_CHARSET = "MS932";
    /**
     * ファイル存在チェックをします。
     * 
     * @param filepath
     *            ファイルパス(絶対パス指定)
     * @return true：存在する、false：存在しない
     **/
    public static boolean isExist(String filepath) {
        File exstfile = new File(filepath);
        if (!exstfile.exists()) {
            return false;
        }
        return true;
    }

    /**
     * ファイルを削除します。
     * 
     * @param filepath
     *            削除ファイルパス(絶対パス指定)
     * @return true：正常終了、false：異常終了
     **/
    public static boolean deleteFile(String filepath) {
        File delfile = new File(filepath);
        if (!delfile.delete()) {
            return false;
        }
        return true;
    }

    /**
     * ファイルを移動します。
     * 
     * @param filepathfrom
     *            移動元ファイルパス(絶対パス指定)
     * @param filepathto
     *            移動ファイルパス(絶対パス指定)
     * @return true：正常終了、false：異常終了
     **/
    public static boolean moveFile(String filepathfrom, String filepathto) {
        File srcfile = new File(filepathfrom);
        File destfile = new File(filepathto);
        if (!srcfile.renameTo(destfile)) {
            return false;
        }
        return true;
    }

    /**
     * ファイルをコピーします。
     * 
     * @param filepathfrom
     *            コピー元ファイルパス(絶対パス指定)
     * @param filepathto
     *            コピーファイルパス(絶対パス指定)
     **/
    public static void copyFile(String filepathfrom, String filepathto) {
        copyFile(filepathfrom, filepathto, CNST_IBYTE_SIZE);
    }
    
    /**
     * ファイルをコピーします。
     * 
     * @param filepathfrom
     *            コピー元ファイルパス(絶対パス指定)
     * @param filepathto
     *            コピーファイルパス(絶対パス指定)
     * @param ibytesize
     *            バイト読み込みバッファーサイズ
     **/
    public static void copyFile(String filepathfrom, String filepathto, int ibytesize) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(filepathfrom);
            fos = new FileOutputStream(filepathto);
            byte[] buffer = new byte[ibytesize];
            int rsize;
            while ((rsize = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, rsize);
            }
            fos.flush();
            fis.close();
            fos.close();
        } catch (Exception e) {
            try {
                fis.close();
                fos.close();
            } catch (Exception ee) {
                throw new ChaseException(ee);
            }
            throw new ChaseException(e);
        }
    }

    /**
     * ファイルをコピーします。
     * 
     * @param filepathfrom
     *            コピー元ファイルパス(絶対パス指定)
     * @param filepathto
     *            コピーファイルパス(絶対パス指定)
     * @param ibytesize
     *            バイト読み込みバッファーサイズ
     **/
    public static void copyFile(String filepathfrom, String filepathto, String inputCharSet, String outputCharSet) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            fis = new FileInputStream(new File(filepathfrom));
            in = new BufferedReader(new InputStreamReader(fis, inputCharSet));

            fos = new FileOutputStream(new File(filepathto));
            out = new BufferedWriter(new OutputStreamWriter(fos, outputCharSet));

            int len = 80;
            char buf[] = new char[len];

            int numRead;
            while ((numRead = in.read(buf, 0, len)) != -1) {
                out.write(buf, 0, numRead);
            }

            out.flush();
            in.close();
            out.close();
        } catch (Exception e) {
            try {
                in.close();
                out.close();
            } catch (Exception ee) {
                throw new ChaseException(ee);
            }
            throw new ChaseException(e);
        }
    }
    
    /**
     * Write a small string to a File - Use a FileWriter
     * @param content File Content
     * @param filePath Path of file
     */
    public static void writeFile(String content, String filePath) {
        Writer writer = null;

        try {
            writer = new FileWriter(filePath);
            writer.write(content);
        } catch (IOException e) {
            throw new ChaseException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new ChaseException(e);
                }
            }

        }
    }
    
    /**
     * Read all in file
     * @param file
     * @return 
     */
    public static List<String> readLines(File file) {
        List<String> recList = new ArrayList<String>();
        String line;
        InputStream input = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            input = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(input, DEFAULT_INPUT_CHARSET);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null) {
                recList.add(line);
                line = bufferedReader.readLine();
            }
            
        } catch (IOException ioe) {
            throw new ChaseException(ioe);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return recList;
    }
}
