package oopproject2.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import oopproject2.controller.KafkaClusterController;

import java.awt.Dimension;
import java.awt.GridBagLayout;

public class MainFrame extends javax.swing.JFrame {
    KafkaClusterController controller;
    JButton brokerBttn;
    JButton topicBttn;
    JButton clientBttn;
    JButton messagesBttn;

    public MainFrame(KafkaClusterController control) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 4, (screenSize.height - frameSize.height) / 4);
        controller = control;
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        initComponents();
    }

    private void initComponents() {
        brokerBttn = new JButton();
        topicBttn = new JButton();
        clientBttn = new JButton();
        messagesBttn = new JButton();

        this.setTitle("Apache Kafka");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        brokerBttn.setText("Manage Brokers");
        brokerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                brokerBttnActionPerformed(evt);
            }
        });

        topicBttn.setText("Manage Topics");
        topicBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                topicBttnActionPerformed(evt);
            }
        });

        clientBttn.setText("Manage Clients");
        clientBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clientBttnActionPerformed(evt);
            }
        });

        messagesBttn.setText("Manage Messages");
        messagesBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                messagesBttnActionPerformed(evt);
            }
        });

        JPanel gui = new JPanel();

        GroupLayout layout = new GroupLayout(gui);
        gui.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(brokerBttn)
                                .addGap(21, 21, 21)
                                .addComponent(clientBttn)
                                .addGap(21, 21, 21)
                                .addComponent(messagesBttn)
                                .addGap(21, 21, 21)
                                .addComponent(topicBttn)
                                .addGap(21, 21, 21)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(45, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(brokerBttn)
                                        .addComponent(clientBttn)
                                        .addComponent(messagesBttn)
                                        .addComponent(topicBttn))
                                .addGap(43, 43, 43)));

        add(gui);

        pack();
    }

    private void brokerBttnActionPerformed(ActionEvent evt) {
        BrokerFrame brokerFrame = new BrokerFrame(this, this.controller);
        this.setEnabled(false);
        brokerFrame.setVisible(true);
        brokerFrame.setAlwaysOnTop(true);
    }

    private void topicBttnActionPerformed(ActionEvent evt) {
        TopicFrame topicFrame = new TopicFrame(this, this.controller);
        this.setEnabled(false);
        topicFrame.setVisible(true);
        topicFrame.setAlwaysOnTop(true);
    }

    private void clientBttnActionPerformed(ActionEvent evt) {
        ClientFrame producerConsuerFrame = new ClientFrame(this, this.controller);
        this.setEnabled(false);
        producerConsuerFrame.setVisible(true);
        producerConsuerFrame.setAlwaysOnTop(true);
    }

    private void messagesBttnActionPerformed(ActionEvent evt) {
        MessagesFrame messagesFrame = new MessagesFrame(this, this.controller);
        this.setEnabled(false);
        messagesFrame.setVisible(true);
        messagesFrame.setAlwaysOnTop(true);
    }
}
