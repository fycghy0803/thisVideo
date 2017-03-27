package com.hysoft.videoTransfter;

import com.hysoft.video.transfer.VideoTransferImage;
import org.junit.Test;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import java.util.Random;

/**
 * Created by yulifan on 2017/1/17.
 */
public class TestVideoTransfter {
    @Test
    public void testVideoTrans() {
        String videoFile = "/Users/yulifan/Workspaces/myvideo/webapp/src/main/webapp/videos/ff.mp4";
        String imageFile = "/Users/yulifan/Workspaces/myvideo/webapp/src/main/webapp/videoPics/ff.jpg";

        VideoTransferImage videoTransferImage = new VideoTransferImage();
        videoTransferImage.transferVideoToImage(videoFile,imageFile);
    }

    @Test
    public void testNextLong() {
        Long ll = new Random().nextLong();
        System.out.println(ll.toString());
    }
}
