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

public class AddTopicFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    private boolean isKeyed;

    private JLabel topicNameLabel;
    private JLabel numPartitionsLabel;
    private JLabel maxConsumersLabel;
    private JLabel maxProducersLabel;
    private JLabel replicationsFactorLabel;

    private JCheckBox isKeyedCheckBox;

    private JTextField topicNameTxtField;
    private JTextField numPartitionsTextField;
    private JTextField maxConsumersTextField;
    private JTextField maxProducersTextField;
    private JTextField replicationFactorTextField;

    private JButton addTopicBttn;

    public AddTopicFrame(JFrame callingFrame, KafkaClusterController controller) {
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
        topicNameLabel = new JLabel();
        numPartitionsLabel = new JLabel();
        maxConsumersLabel = new JLabel();
        maxProducersLabel = new JLabel();
        replicationsFactorLabel = new JLabel();
        isKeyedCheckBox = new JCheckBox();
        topicNameTxtField = new JTextField();
        numPartitionsTextField = new JTextField();
        maxConsumersTextField = new JTextField();
        maxProducersTextField = new JTextField();
        replicationFactorTextField = new JTextField();
        addTopicBttn = new JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Add Topic Form");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        topicNameLabel.setFont(new Font("Segoe UI", 1, 12));
        topicNameLabel.setText("Topic Name:");

        numPartitionsLabel.setFont(new Font("Segoe UI", 1, 12));
        numPartitionsLabel.setText("Partitions Number:");

        maxConsumersLabel.setFont(new Font("Segoe UI", 1, 12));
        maxConsumersLabel.setText("Max Consumers:");

        maxProducersLabel.setFont(new Font("Segoe UI", 1, 12));
        maxProducersLabel.setText("Max Producers:");

        replicationsFactorLabel.setFont(new Font("Segoe UI", 1, 12));
        replicationsFactorLabel.setText("Replication Factor:");

        isKeyedCheckBox.setText("Keyed");
        isKeyedCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                isKeyed = !isKeyed;
            }
        });

        topicNameTxtField.setColumns(10);

        numPartitionsTextField.setColumns(2);

        maxConsumersTextField.setColumns(2);

        maxProducersTextField.setColumns(2);

        replicationFactorTextField.setColumns(2);

        addTopicBttn.setText("Add Topic");
        addTopicBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addTopicBttnActionPerformed();
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
                                                        .addComponent(topicNameLabel)
                                                        .addComponent(numPartitionsLabel)
                                                        .addComponent(maxConsumersLabel)
                                                        .addComponent(maxProducersLabel)
                                                        .addComponent(replicationsFactorLabel)
                                                        .addComponent(isKeyedCheckBox))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(topicNameTxtField,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(numPartitionsTextField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(maxConsumersTextField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(maxProducersTextField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(replicationFactorTextField,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(isKeyedCheckBox))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(100, 100, 100)
                                                .addComponent(addTopicBttn)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(topicNameLabel)
                                                        .addComponent(topicNameTxtField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(numPartitionsLabel)
                                                        .addComponent(numPartitionsTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(maxConsumersLabel)
                                                        .addComponent(maxConsumersTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(maxProducersLabel)
                                                        .addComponent(maxProducersTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(replicationsFactorLabel)
                                                        .addComponent(replicationFactorTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(isKeyedCheckBox))
                                                .addGap(57, 57, 57)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(addTopicBttn))))
                                .addContainerGap(23, Short.MAX_VALUE)));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void addTopicBttnActionPerformed() {
        try {
            int numPartitions = Integer.parseInt(this.numPartitionsTextField.getText());
            int maxConsumers = Integer.parseInt(this.maxConsumersTextField.getText());
            int maxProducers = Integer.parseInt(this.maxProducersTextField.getText());
            int replicationsFactor = Integer.parseInt(this.replicationFactorTextField.getText());

            if (numPartitions > Globals.maxNumPartitions) {
                JOptionPane.showMessageDialog(this, "Partitions Number" + Globals.messageSeperator + Globals.errExceedsMaxRange, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (maxConsumers > Globals.maxConsumers) {
                JOptionPane.showMessageDialog(this, "Max Consumers" + Globals.messageSeperator + Globals.errExceedsMaxRange, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (maxProducers > Globals.maxProducers) {
                JOptionPane.showMessageDialog(this, "Max Producers" + Globals.messageSeperator + Globals.errExceedsMaxRange, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (replicationsFactor > Globals.maxReplicationFactor) {
                JOptionPane.showMessageDialog(this, "Replication Factor" + Globals.messageSeperator + Globals.errExceedsMaxRange, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KafkaTopic newTopic = controller.insertTopicHandler(this.topicNameLabel.getText(), numPartitions,
                    maxProducers, maxConsumers,
                    replicationsFactor, this.isKeyed);

            if (newTopic == null) {
                JOptionPane.showMessageDialog(this, Globals.errRecordNotAdded, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, Globals.respAddedSuccessfully, "Ifno", JOptionPane.INFORMATION_MESSAGE);
            }

            callingFrame.setEnabled(true);
            this.setAlwaysOnTop(false);
            this.setEnabled(false);
            this.setVisible(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, Globals.errInvalidArguments, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errSomethingWentWrong, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
