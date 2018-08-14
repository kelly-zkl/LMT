package com.wisec.scanner.utils;

import com.wisec.scanner.BuildConfig;

import java.io.DataOutputStream;

/**
 * Created by xxx on 2018/2/8.
 */

public class SystemManager {

    private static String packageName = BuildConfig.APPLICATION_ID;

    public static void init() {

        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("touch /data/dissector_para_in\n");
            os.writeBytes("chmod 777 /data/dissector_para_in\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            System.out.println("create /data/dissector_para_in fail.");
        }
    }

    public static void start_qcd() {

        stop_process("libqcd");

        String chmod_cmd[] = {"/system/xbin/su", "-c", "exec " + "chmod 777 /dev/diag"};
        String setenforce_cmd[] = {"/system/xbin/su", "-c", "exec " + "setenforce 0"};
        String qcd = "/data/data/" + packageName + "/lib/libqcd.so";
        String qcd_daemon_cmd[] = {"/system/xbin/su", "-c", "exec " + qcd + " hello" + " /dev/diag"};

        try {
            Runtime.getRuntime().exec(chmod_cmd);
            Runtime.getRuntime().exec(setenforce_cmd);
            Runtime.getRuntime().exec(qcd_daemon_cmd);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("start_qcd cmd fail.");
        }
    }

    public static void stop_process(String proc_name) {

        System.out.println("stop_process: " + proc_name);

        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("date >> /data/data/" + packageName + "/cache/qcd.log\n");
            os.writeBytes("ps | grep " + proc_name + " >> /data/data/" + packageName + "/cache/qcd.log\n");
            os.writeBytes("PROID=`ps | grep " + proc_name + "` >> /data/data/" + packageName + "/cache/qcd.log\n");
            os.writeBytes("kill $PROID >> /data/data/" + packageName + "/cache/qcd.log\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            System.out.println("stop_process " + proc_name + " cmd fail.");
        }
    }

    private static void reboot() {

        String reboot_cmd[] = {"/system/xbin/su", "-c", "exec /data/data/" + packageName +
                "/lib/libreboot.so " + " >> /data/earfcn_lock.log"};
        System.out.println("reboot: " + reboot_cmd[2]);

        try {
            Runtime.getRuntime().exec(reboot_cmd);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("reboot cmd fail.");
        }
    }

    private static void exec_cmd(String cmd[]) {

        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("exec_cmd fail. cmd: " + cmd[2]);
        }

        System.out.println("exec_cmd " + cmd[2] + " success! ");
    }

    public static void lock_earfcn(int earfcn_2_lock) {

        stop_process("libqcd");
        stop_process("libctrl");

        String lock_cmd[] = {"/system/xbin/su", "-c", "exec /data/data/" + packageName +
                "/lib/libctrl.so earfcn_lock " + String.format("%d", earfcn_2_lock) + " >> /data/earfcn_lock.log"};
        System.out.println("lock_earfcn: " + lock_cmd[2]);

        exec_cmd(lock_cmd);
        reboot();
    }

    public static void unlock_earfcn() {

        stop_process("libqcd");
        stop_process("libctrl");

        String unlock_cmd[] = {"/system/xbin/su", "-c", "exec /data/data/" + packageName +
                "/lib/libctrl.so earfcn_unlock >> /data/earfcn_lock.log"};
        System.out.println("unlock_earfcn: " + unlock_cmd[2]);

        exec_cmd(unlock_cmd);
    }

    public static void lock_pci(int earfcn_2_lock, int pci_2_lock) {

        stop_process("libqcd");
        stop_process("libctrl");

        String lock_cmd[] = {"/system/xbin/su", "-c", "exec /data/data/" + packageName +
                "/lib/libctrl.so pci_lock " + String.format("%d", earfcn_2_lock) + " " +
                String.format("%d", pci_2_lock) + " >> /data/pci_lock.log"};
        System.out.println("lock_pci: " + lock_cmd[2]);

        exec_cmd(lock_cmd);
        reboot();
    }

    public static void unlock_pci() {

        stop_process("libqcd");
        stop_process("libctrl");

        String unlock_cmd[] = {"/system/xbin/su", "-c", "exec /data/data/" + packageName +
                "/lib/libctrl.so pci_unlock >> /data/pci_lock.log"};
        System.out.println("unlock_pci: " + unlock_cmd[2]);

        exec_cmd(unlock_cmd);
        reboot();
    }
}
