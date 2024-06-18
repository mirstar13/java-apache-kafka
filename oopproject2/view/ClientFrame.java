package oopproject2.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import oopproject2.admin.KafkaClient;
import oopproject2.admin.KafkaProducer;
import oopproject2.admin.KafkaTopic;
import oopproject2.controller.KafkaClusterController;
import oopproject2.utilities.Globals;

public class ClientFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    DefaultListModel<KafkaClient> clientListModel;

    JList<KafkaClient> clientList;
    JLabel clientLabel;
    JLabel clientTypeLabel;
    JLabel topicLabel;
    JButton inspectBttn;
    JScrollPane clientScrollPane;
    JScrollPane dataScrollPane;

    public ClientFrame(JFrame callingFrame, KafkaClusterController controller) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();

        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        this.setLocation((screenSize.width - frameSize.width) / 4, (screenSize.height - frameSize.height) / 4);

        this.callingFrame = callingFrame;
        this.controller = controller;
        this.clientListModel = new DefaultListModel<>();
        this.clientListModel.addAll(controller.getAllClients());
        initComponents();
    }

    private void initComponents() {
        clientList = new JList<>();
        topicLabel = new JLabel();
        clientTypeLabel = new JLabel();
        clientLabel = new JLabel();
        inspectBttn = new JButton();
        clientScrollPane = new JScrollPane();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Clients Manager");
        getContentPane().setLayout(new GridBagLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        clientList.setModel(clientListModel);
        clientScrollPane.setViewportView(clientList);

        topicLabel.setFont(new Font("Segoe UI", 1, 12));
        topicLabel.setText("Topic:");

        clientTypeLabel.setFont(new Font("Segoe UI", 1, 12));
        clientTypeLabel.setText("Client Type:");

        clientLabel.setFont(new Font("Segoe UI", 1, 12));
        clientLabel.setText("Clients:");

        inspectBttn.setText("Inspect Client");
        inspectBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inspectBttnActionPerformed();
            }
        });

        JPanel gui = new JPanel();

        GroupLayout layout = new GroupLayout(gui);
        gui.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(clientScrollPane)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(clientLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(inspectBttn))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(topicLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup())
                                                .addComponent(clientTypeLabel).addGap(43, 43, 43)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(clientLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(clientScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        159,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(clientTypeLabel)
                                        .addComponent(topicLabel))
                                .addComponent(inspectBttn)
                                .addContainerGap()));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void inspectBttnActionPerformed() {
        try {
            KafkaClient client = this.clientList.getSelectedValue();
            KafkaTopic clientTopic = client.getTopic();
    
            this.topicLabel.setText("Topic: " + clientTopic.getName());
    
            if (client instanceof KafkaProducer) {
                this.clientTypeLabel.setText("Client type: Producer");
            } else {
                this.clientTypeLabel.setText("Client type: Consumer");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errSomethingWentWrong, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
