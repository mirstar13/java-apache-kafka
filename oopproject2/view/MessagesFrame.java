package oopproject2.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oopproject2.admin.KafkaTopic;
import oopproject2.controller.KafkaClusterController;
import oopproject2.utilities.Globals;

public class MessagesFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    private boolean isKeyed;

    private JLabel fileNameLabel;
    private JLabel topicNameLabel;
    private JLabel keyNameLabel;

    private JCheckBox isKeyedCheckBox;

    private JTextField fileNameTextField;
    private JTextField topicNameTextField;
    private JTextField keyNameTextField;

    private JButton insertFileBttn;
    private JButton startReadingMessagesBttn;

    public MessagesFrame(JFrame callingFrame, KafkaClusterController controller) {
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
        initComponents();
    }

    private void initComponents() {
        fileNameLabel = new JLabel();
        topicNameLabel = new JLabel();
        keyNameLabel = new JLabel();
        isKeyedCheckBox = new JCheckBox();
        fileNameTextField = new JTextField();
        topicNameTextField = new JTextField();
        keyNameTextField = new JTextField();
        insertFileBttn = new JButton();
        startReadingMessagesBttn = new JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Message Manager");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        fileNameLabel.setFont(new Font("Segoe UI", 1, 12));
        fileNameLabel.setText("File Name:");

        topicNameLabel.setFont(new Font("Segoe UI", 1, 12));
        topicNameLabel.setText("Topic Name:");

        keyNameLabel.setFont(new Font("Segoe UI", 1, 12));
        keyNameLabel.setText("Key Name:");

        isKeyedCheckBox.setText("Keyed");
        isKeyedCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                isKeyed = !isKeyed;
            }
        });

        fileNameTextField.setColumns(30);

        topicNameTextField.setColumns(20);

        keyNameTextField.setColumns(20);

        insertFileBttn.setText("Insert File");
        insertFileBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                insertFileActionPerformed(evt);
            }
        });

        startReadingMessagesBttn.setText("Read Messages");
        startReadingMessagesBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                startReadingMessagesBttnActionPerformed(evt);
            }
        });

        JPanel gui = new JPanel();

        GroupLayout layout = new GroupLayout(gui);
        gui.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(fileNameLabel)
                                                        .addComponent(topicNameLabel)
                                                        .addComponent(keyNameLabel))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(fileNameTextField,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout
                                                                        .createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(topicNameTextField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(keyNameTextField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(90, 90, 90)
                                                .addComponent(isKeyedCheckBox))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(insertFileBttn)
                                                .addComponent(startReadingMessagesBttn)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(layout.createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(fileNameLabel)
                                                        .addComponent(fileNameTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(topicNameLabel)
                                                        .addComponent(topicNameTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(keyNameLabel)
                                                        .addComponent(keyNameTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(isKeyedCheckBox))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(insertFileBttn)
                                                        .addComponent(startReadingMessagesBttn))))
                                .addContainerGap(23, Short.MAX_VALUE)));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void insertFileActionPerformed(ActionEvent evt) {
        String fileName = this.fileNameTextField.getText();
        String topicName = this.topicNameTextField.getText();
        String keyName = "";

        if (fileName == "" || topicName == "") {
            JOptionPane.showMessageDialog(this, Globals.errInvalidArguments, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        KafkaTopic topic = this.controller.findTopicByName(topicName);

        if (topic == null) {
            JOptionPane.showMessageDialog(this, Globals.errTopicNotFound, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (topic.isKeyed() != this.isKeyed) {
            JOptionPane.showMessageDialog(this, "Keyed/Non-Keyed messages must be inserted to Keyed/Non-Keyed topics acordingly", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (topic.isKeyed()) {
            keyName = this.keyNameTextField.getText();
        }

        boolean inserted = this.controller.insertFileHandler(fileName, topic.getName(), keyName);

        if (!inserted) {
            JOptionPane.showMessageDialog(this, Globals.errFileNotFound, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, Globals.respAddedSuccessfully, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startReadingMessagesBttnActionPerformed(ActionEvent evt) {
        controller.readMessagesHandler();
        JOptionPane.showMessageDialog(this, "Started reading Messages", "Info", JOptionPane.INFORMATION_MESSAGE);

        this.setEnabled(false);
        this.setAlwaysOnTop(false);
        this.setVisible(false);
        this.callingFrame.setEnabled(true);
    }
}
