/*
 * COPYRIGHT (C) 2014 NEC CORPORATION
 * 
 * ALL RIGHTS RESERVED BY NEC CORPORATION, THIS PROGRAM MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED
 * BY NEC CORPORATION, NO PART OF THIS PROGRAM MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM WITHOUT THE PRIOR
 * WRITTEN PERMISSION OF NEC CORPORATION.
 * 
 * NEC CONFIDENTIAL AND PROPRIETARY
 */
package jp.co.necsoft.web_md.common;

/**
 * 
 * メーカマスタメンテのChaseExceptionクラス
 *
 */
public class ChaseException extends RuntimeException {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * ChaseException
     * @param message String
     */
    public ChaseException(String message) {
        super(message);
    }
    
    /**
     * ChaseException
     * @param cause Throwable
     */
    public ChaseException(Throwable cause) {
        super(cause);
    }
}
