package gui;

//Les imports habituels

import channel.Client;
import model.Peer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.NumberFormat;

public class Fenetre extends JFrame {
    Peer peer;
    private JPanel container = new JPanel();
    private JTextField jTextField = new JTextField();
    private JTextField jTextField1 = new JTextField();
    private JTextField jTextField2 = new JTextField();
    private JTextField jTextField3 = new JTextField();
    private JTextField jTextField4 = new JTextField();
    private JTextField jTextField5 = new JTextField();
    private JLabel label = new JLabel("Un JTextField");
    private JButton b = new JButton ("Thread Listen");
    private JButton b1 = new JButton ("Thread connect");
    private JButton b2 = new JButton ("Send message");

    public Fenetre(){
        System.out.println("fenetre va souvrir");
        this.setTitle("Animation");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        Font police = new Font("Arial", Font.BOLD, 14);
        jTextField.setFont(police);
        jTextField.setPreferredSize(new Dimension(150,30));
        jTextField1.setFont(police);
        jTextField1.setPreferredSize(new Dimension(150,30));
        jTextField2.setFont(police);
        jTextField2.setPreferredSize(new Dimension(150,30));
        jTextField3.setFont(police);
        jTextField3.setPreferredSize(new Dimension(150,30));
        jTextField4.setFont(police);
        jTextField4.setPreferredSize(new Dimension(150,30));
        jTextField5.setPreferredSize(new Dimension(150,30));
        b.addActionListener(new BoutonListener());
        top.add(label);
        top.add(jTextField);
        top.add(jTextField1);
        top.add(jTextField2);
        top.add(jTextField3);
        top.add(jTextField4);
        top.add(jTextField5);
        top.add(b);
        top.add(b1);
        top.add(b2);
        b1.addActionListener(e -> {
            if (!(peer.equals(null))){
                this.peer.addConnection(jTextField3.getText(), Integer.valueOf(jTextField2.getText()));
            }
        });
        b2.addActionListener(e -> {
            if (!(peer.equals(null))){
                peer.askFileList(jTextField5.getText());
            }
        });

        this.setContentPane(top);
        this.setVisible(true);
    }

    class BoutonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int port = Integer.valueOf(jTextField.getText());
            String ip = jTextField1.getText();
            System.out.println("Port "+port);
            System.out.println("Ip : "+ip);
            peer = new Peer (ip,port);


        }
    }
}