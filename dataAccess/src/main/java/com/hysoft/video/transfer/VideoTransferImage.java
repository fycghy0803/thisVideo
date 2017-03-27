package com.hysoft.video.transfer;

import java.util.List;
import java.util.Random;

/**
 * Created by yulifan on 2017/1/17.
 */
public class VideoTransferImage {
    private String ffmpegPath = "/usr/local/bin/ffmpeg";

    public String getFfmpegPath() {
        return ffmpegPath;
    }

    public void setFfmpegPath(String ffmpegPath) {
        this.ffmpegPath = ffmpegPath;
    }

    public boolean transferVideoToImage(String vidoeFile,String imageFile) {
        List<String> commands = getCommend(vidoeFile,imageFile);
        try {
           ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
           builder.start();
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean transferVideoToMp4(String oldFile,String newFile) {
        List<String> commands = getTranVideoCommend(oldFile, newFile);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getCommend(String vidoeFile,String imageFile) {
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(vidoeFile);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        Random random = new Random();
        int index = random.nextInt(6);
        commands.add(Integer.toString(index));//这个参数是设置截取视频多少秒时的画面
        commands.add("-s");
        commands.add("700x525");
        commands.add(imageFile);
        return commands;
    }

    public List<String> getTranVideoCommend(String oldFile,String newFile) {
        List<String> command = new java.util.ArrayList<String>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(oldFile);
        command.add("-vcodec");
        command.add("libx264");
        command.add(newFile);
        return command;
    }

    public String getStrType(String inputPath) {
        String type = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length()).toLowerCase();
        return type;
    }

}

