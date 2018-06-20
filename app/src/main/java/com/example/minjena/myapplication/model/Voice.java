package com.example.minjena.myapplication.model;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.Vector;

public class Voice {

    Bundle arg;
    private String midx;
    private String content;
    private String File_Name;
    private String File_extends = "txt";

    String user = "hci1";
    String passwd = "8608760";
    String URL="165.246.34.138";
    String fileURL = "165.246.34.138/home/hci1/unknown/tacotron_kr/Tacotron-2-en";
    String Save_Path;
    String Save_folder = "/mydown";
    String total_path;
    private Session session = null;
    private com.jcraft.jsch.Channel channel = null;
    private ChannelSftp channelSftp = null;
    DownloadThread dThread;

    public Voice(Bundle arg, String msg, String chat){
        this.arg = arg;
        midx = msg;
        content = chat;
    }

    public void downAndPlay()
    {
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)){
            Save_Path = Environment.getExternalStorageDirectory().getAbsolutePath() + Save_folder;
        }
        File dir = new File(Save_Path);
        if(!dir.exists())
            dir.mkdir();
        File_Name = midx + "." + File_extends;
        total_path = Save_Path + "/" + File_Name;
        File file = new File(Save_Path,File_Name);
        File voice = new File(Save_Path,midx + ".wav");
        if(!file.exists()){
            try {
                file.createNewFile();
                voice.createNewFile();
                FileOutputStream fos = new FileOutputStream(total_path,true);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
                writer.write(content);
                writer.flush();
                writer.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dThread = new DownloadThread(fileURL, Save_Path);
            dThread.start();
        }
        else{
            dThread = new DownloadThread(fileURL, Save_Path);
            dThread.start();
        }
    }

    public boolean fileCheck(ChannelSftp channelSftp, String path)
    {
        Vector res = null;
        try {
            res = channelSftp.ls(path);
        } catch (SftpException e) {
            if(e.id == channelSftp.SSH_FX_NO_SUCH_FILE)
                return false;
        }
        return res != null && !res.isEmpty();
    }



    private class DownloadThread extends Thread{
        String ServerUrl;
        String LocalPath;
        String UFile_Name;
        String DFile_Name;
        DownloadThread(String serverPath, String localPath)
        {
            ServerUrl = serverPath;
            LocalPath = localPath;
            UFile_Name = "text_4_" + midx + ".txt";
            DFile_Name = "text_4_" + midx + ".wav";
        }

        public void run()
        {
            JSch jsch = new JSch();
            try {
                session = jsch.getSession(user,URL);
                session.setPassword(passwd);
                Properties config = new Properties();
                config.put("StrictHostKeyChecking","no");
                session.setConfig(config);
                session.connect();
                channel=session.openChannel("sftp");
                channel.connect();
                channelSftp = (ChannelSftp) channel;
                channelSftp.cd("/home/hci1/unknown/tacotron_kr/Tacotron-2-en");

                //파일 업로드
                File file = new File(LocalPath + "/" + midx + ".txt");
                FileInputStream fis = new FileInputStream(file);
                channelSftp.put(fis,UFile_Name);
                fis.close();
                //파일 다운로드
                channelSftp.cd("/home/hci1/unknown/tacotron_kr/Tacotron-2-en/tacotron_output/logs-eval/wavs");
                while(fileCheck(channelSftp,DFile_Name) == false){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                FileOutputStream out = new FileOutputStream(LocalPath + "/" + midx + ".wav");
                InputStream bis = channelSftp.get(DFile_Name);
                byte[] buffer = new byte[1024];
                int len;
                while((len = bis.read(buffer)) != -1){
                    out.write(buffer,0,len);
                }

                //파일 재생
                File vFile = new File(LocalPath+"/"+midx+".wav");
                if(file.exists()) {
                    MediaPlayer mp = new MediaPlayer();
                    mp.setDataSource(LocalPath+"/"+midx+".wav");
                    mp.prepare();
                    mp.start();
                }

            } catch (JSchException e) {
                e.printStackTrace();
            } catch (SftpException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
