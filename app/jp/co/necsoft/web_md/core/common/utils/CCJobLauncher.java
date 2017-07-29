// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 外部コマンド（プログラム）実行クラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.16 HaiNP 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.co.necsoft.web_md.common.ChaseException;

/**
 * 外部コマンド（プログラム）実行クラス
 *
 */
public class CCJobLauncher {

    /**
     * xxJJobLauncherオブジェクトを構築。
     */
    public CCJobLauncher() {
    }

    /**
     * EXE/BAT 実行処理。<BR>
     * @param command EXE/BAT名(ﾌﾙﾊﾟｽ) args1 args2 args3.....
     * @return 0/1 success/fail
     **/
    public int exec(String command) {
        Runtime runtime = Runtime.getRuntime();
        int ret = 0;
        try {
            Process process = runtime.exec(command);
            final BufferedReader stdReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Thread stdOutThread = new Thread(new Runnable() {
                public void run() {
                    String line;
                    try {
                        while ((line = stdReader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        throw new ChaseException(e);
                    }
                }
            });
            stdOutThread.start();

            final BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Thread stdErrThread = new Thread(new Runnable() {
                public void run() {
                    String line;
                    try {
                        while ((line = errReader.readLine()) != null) {
                            System.err.println(line);
                        }
                    } catch (IOException e) {
                        throw new ChaseException(e);
                    }
                }
            });
            stdErrThread.start();

            // プロセスが終了するまで現在のスレッドを待機　戻値：外部プロセスの終了コード
            ret = process.waitFor();
        } catch (Exception err) {
            throw new ChaseException(err);
        }
        return ret;
    }

}
