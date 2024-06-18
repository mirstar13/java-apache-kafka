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

import oopproject2.admin.KafkaBroker;
import oopproject2.admin.KafkaTopic;
import oopproject2.controller.KafkaClusterController;
import oopproject2.utilities.Globals;

public class BrokerFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    DefaultListModel<KafkaBroker> brokerListModel;
    DefaultListModel<KafkaTopic> topicListModel;

    private KafkaBroker inspectingBroker;

    private JScrollPane brokerScrollPane;
    private JScrollPane topicScrollPane;

    private JList<KafkaBroker> brokerList;
    private JList<KafkaTopic> topicList;

    private JLabel brokerLabel;
    private JLabel topicLabel;

    private JButton inspectBttn;
    private JButton addBrokerBttn;
    private JButton updateBrokerBttn;
    private JButton deleteBrokerBttn;

    public BrokerFrame(JFrame callingFrame, KafkaClusterController controller) {
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
        this.brokerListModel = new DefaultListModel<>();
        this.brokerListModel.addAll(controller.getAllBrokers());
        this.topicListModel = new DefaultListModel<>();
        initComponents();
    }

    private void initComponents() {
        brokerScrollPane = new JScrollPane();
        topicScrollPane = new JScrollPane();
        brokerList = new JList<>();
        topicList = new JList<>();
        brokerLabel = new JLabel();
        topicLabel = new JLabel();
        inspectBttn = new JButton();
        addBrokerBttn = new JButton();
        updateBrokerBttn = new JButton();
        deleteBrokerBttn = new JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Broker Manager");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        brokerList.setModel(brokerListModel);
        brokerScrollPane.setViewportView(brokerList);

        topicList.setModel(topicListModel);
        topicScrollPane.setViewportView(topicList);

        brokerLabel.setFont(new Font("Segoe UI", 1, 12));
        brokerLabel.setText("Brokers:");

        topicLabel.setFont(new Font("Segoe UI", 1, 12));
        topicLabel.setText("Topics:");

        inspectBttn.setText("Inspect Broker");
        inspectBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                inspectBttnActionPerformed(evt);
            }
        });

        addBrokerBttn.setText("Add Broker");
        addBrokerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addBrokerBttnActionPerformed(evt);
            }
        });

        updateBrokerBttn.setText("Update Broker");
        updateBrokerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateBrokerBttnActionPerformed(evt);
            }
        });

        deleteBrokerBttn.setText("Delete Broker");
        deleteBrokerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteBrokerBttnActionPerformed(evt);
            }
        });

        JPanel gui = new JPanel();

        GroupLayout layout = new GroupLayout(gui);
        gui.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(brokerLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(topicLabel)
                                                .addGap(100, 100, 100))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(brokerScrollPane)
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(inspectBttn)
                                                        .addComponent(addBrokerBttn)
                                                        .addComponent(updateBrokerBttn)
                                                        .addComponent(deleteBrokerBttn))
                                                .addComponent(topicScrollPane)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(brokerLabel)
                                        .addComponent(topicLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(brokerScrollPane)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(inspectBttn)
                                                .addComponent(addBrokerBttn)
                                                .addComponent(updateBrokerBttn)
                                                .addComponent(deleteBrokerBttn))
                                        .addComponent(topicScrollPane))));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void inspectBttnActionPerformed(ActionEvent evt) {
        try {
            KafkaBroker selectedBroker = this.brokerList.getSelectedValue();

            this.inspectingBroker = selectedBroker;
            this.topicListModel.clear();
            this.topicListModel.addAll(selectedBroker.getTopics());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBrokerBttnActionPerformed(ActionEvent evt) {
        AddBrokerFrame addBrokerForm = new AddBrokerFrame(this, this.controller);
        this.setEnabled(false);
        addBrokerForm.setAlwaysOnTop(true);
        addBrokerForm.setVisible(true);
    }

    private void updateBrokerBttnActionPerformed(ActionEvent evt) {
        UpdateBrokerFrame updateBrokerForm = new UpdateBrokerFrame(this, this.controller);
        this.setEnabled(false);
        updateBrokerForm.setAlwaysOnTop(true);
        updateBrokerForm.setVisible(true);
    }

    private void deleteBrokerBttnActionPerformed(ActionEvent evt) {
        try {
            KafkaBroker selectedBroker = this.brokerList.getSelectedValue();

            int resp = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this broker");

            if (resp != 0) {
                return;
            }

            if (this.inspectingBroker != null) {
                if (selectedBroker.toString().equals(this.inspectingBroker.toString())) {
                    this.topicListModel.clear();
                }
            }

            this.controller.deleteBrokerHandler(selectedBroker.getHost(), selectedBroker.getPort());

            JOptionPane.showMessageDialog(this, Globals.respDeletedSuccessfully, "Info", JOptionPane.INFORMATION_MESSAGE);

            this.brokerListModel.clear();
            this.brokerListModel.addAll(controller.getAllBrokers());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
