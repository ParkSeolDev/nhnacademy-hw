package com.nhnacademy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 1p vs 2p
 * 1p 조작 RDFG + A
 * 2p 조작 ←↑↓→ + L
 */

public class CannonGame extends JFrame implements ComponentListener {
    static final int FRAME_WIDTH = 1500;
    static final int FRAME_HEIGHT = 700;
    static final int DT = 50;

    CannonWorld world;

    public CannonGame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(this);

        setLayout(null);

        world = new CannonWorld(300, 0, FRAME_WIDTH - 300, FRAME_HEIGHT - 200);
        world.setDT(DT);
        world.setBackground(Color.WHITE);
        world.setBounds(300, 0, FRAME_WIDTH - 300, FRAME_HEIGHT - 200);
        add(world);

        JButton button2 = new JButton("Clear!");
        button2.setBounds(180, 550, 100, 70);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.clear();
                world.requestFocus();
            }
        });
        add(button2);

        addSpeedComponent();
        addGravityComponent();
        addWindComponent();

        world.init();
    }

    public void start() {
        setVisible(true);
        setEnabled(true);

        world.run();
    }

    public void addSpeedComponent() {
        JLabel label1 = new JLabel("속도");
        label1.setBounds(30, 50, 100, 100);
        add(label1);

        JSlider speed = new JSlider( 0, 1000, 500);
        speed.setBounds(50, 100, 200, 50);
        speed.setPaintTrack(true);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
        speed.setMajorTickSpacing(200);
        speed.setMinorTickSpacing(50);

        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setSpeed(speed.getValue());
                world.requestFocus();
            }
        });
        add(speed);
    }

    public void addGravityComponent() {
        JLabel label3 = new JLabel("중력");
        label3.setBounds(30, 250, 100, 100);
        add(label3);

        JSlider gravity = new JSlider( 0, 10, 0);
        gravity.setBounds(50, 300, 200, 50);
        gravity.setPaintTrack(true);
        gravity.setPaintTicks(true);
        gravity.setPaintLabels(true);

        gravity.setMajorTickSpacing(2);
        gravity.setMinorTickSpacing(1);

        gravity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setGravity(gravity.getValue());
                world.requestFocus();
            }
        });
        add(gravity);
    }

    public void addWindComponent() {
        JLabel label4 = new JLabel("바람");
        label4.setBounds(30,350, 100, 100);
        add(label4);

        JSlider windSpeed = new JSlider( -10, 10, 0);
        windSpeed.setBounds(50, 400, 200, 50);
        windSpeed.setPaintTrack(true);
        windSpeed.setPaintTicks(true);
        windSpeed.setPaintLabels(true);
        windSpeed.setMajorTickSpacing(2);
        windSpeed.setMinorTickSpacing(1);

        windSpeed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                world.setWindSpeed(windSpeed.getValue());
                world.requestFocus();
            }
        });
        add(windSpeed);
    }

    public static void main(String[] args) {
        CannonGame frame = new CannonGame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        frame.start();

    }

    @Override
    public void componentResized(ComponentEvent e) {
        
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }
    
    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
}
