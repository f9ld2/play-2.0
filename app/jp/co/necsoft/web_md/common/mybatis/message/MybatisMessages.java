/*
 * COPYRIGHT (C) 2014 NEC CORPORATION
 * 
 * ALL RIGHTS RESERVED BY NEC CORPORATION, THIS PROGRAM MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED
 * BY NEC CORPORATION, NO PART OF THIS PROGRAM MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM WITHOUT THE PRIOR
 * WRITTEN PERMISSION OF NEC CORPORATION.
 * 
 * NEC CONFIDENTIAL AND PROPRIETARY
 */
package jp.co.necsoft.web_md.common.mybatis.message;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * メーカマスタメンテのMybatisMessagesクラス
 * @author NECVN
 *
 */
public final class MybatisMessages {
    /** BUNDLE_NAME */
    private static final String BUNDLE_NAME = "jp.co.necsoft.web_md.common.mybatis.message.MybatisMessages";
    /** RESOURCE_BUNDLE */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /** MybatisMessages */
    private MybatisMessages() {
    }

    /**
     * getString.
     * @param key String
     * @return String
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    /**
     * getMessage
     * @param key String 
     * @param args String...
     * @return String
     */
    public static String getMessage(String key, String... args) {
        String message = getString(key);

        if (message == null) {
            return null;
        } else if (args == null || args.length == 0) {
            return message;
        }

        MessageFormat format = new MessageFormat(message);

        return format.format(args);
    }

    /**
     * getFullMessage
     * @param key String 
     * @param args String...
     * @return String
     */
    public static String getFullMessage(String key, String... args) {
        StringBuffer buf = new StringBuffer();
        buf.append(key);
        buf.append(':');
        buf.append(getString(key));
        if (args != null && args.length > 0) {
            buf.append(':');
            buf.append(getMessage(key + "D", args));
        }
        return buf.toString();
    }
}
