package com.bayviewglen.zork;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FlashingImages {


    public FlashingImages(String fileLocation, int time) { //time is in milliseconds btw
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                try {
                    BufferedImage img = ImageIO.read(new File(fileLocation));
                    JLabel label = new JLabel(new ImageIcon(img));

                    JFrame frame = new JFrame("Flashing Images");
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowOpened(WindowEvent e) {
                            Timer timer = new Timer(time, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    frame.remove(label);
                                    frame.revalidate();
                                    frame.repaint();
                                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                                }
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    });
                    frame.add(label);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (IOException exp) {
                    exp.printStackTrace();
                    
                }
            }
        });
    }
    

}