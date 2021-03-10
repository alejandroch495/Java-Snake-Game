package com.zetcode;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Music {
    void playSound(String filePath) {
        try {
            File mpath = new File(filePath);

            if (mpath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(mpath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

            } else {
                System.out.println(" Can not find file! ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
