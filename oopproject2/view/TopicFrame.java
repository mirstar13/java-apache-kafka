package oopproject2.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import oopproject2.admin.KafkaConsumer;
import oopproject2.admin.KafkaProducer;
import oopproject2.admin.KafkaTopic;
import oopproject2.controller.KafkaClusterController;
import oopproject2.utilities.Globals;

public class TopicFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    DefaultListModel<KafkaTopic> topicListModel;
    DefaultListModel<KafkaProducer> producerListModel;
    DefaultListModel<KafkaConsumer> consumerListModel;

    private KafkaTopic inspectingTopic;

    private JScrollPane topicScrollPane;
    private JScrollPane producerScrollPane;
    private JScrollPane consumerScrollPane;

    private JList<KafkaTopic> topicList;
    private JList<KafkaProducer> producerList;
    private JList<KafkaConsumer> consumerList;

    private JLabel topicLabel;
    private JLabel producerLabel;
    private JLabel consumerLabel;

    private JButton inspectTopicBttn;
    private JButton addTopicBttn;
    private JButton addProducerBttn;
    private JButton addConsumerBttn;
    private JButton deleteTopicBttn;
    private JButton deleteProducerBttn;
    private JButton deleteConsumerBttn;
    private JButton refreshBttn;

    public TopicFrame(JFrame callingFrame, KafkaClusterController controller) {
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
        this.topicListModel = new DefaultListModel<>();
        this.topicListModel.addAll(controller.getAllTopics());
        this.producerListModel = new DefaultListModel<>();
        this.consumerListModel = new DefaultListModel<>();
        initComponents();
    }

    private void initComponents() {
        topicScrollPane = new JScrollPane();
        producerScrollPane = new JScrollPane();
        consumerScrollPane = new JScrollPane();
        topicList = new JList<>();
        producerList = new JList<>();
        consumerList = new JList<>();
        topicLabel = new JLabel();
        producerLabel = new JLabel();
        consumerLabel = new JLabel();
        inspectTopicBttn = new JButton();
        addTopicBttn = new JButton();
        addProducerBttn = new JButton();
        addConsumerBttn = new JButton();
        deleteTopicBttn = new JButton();
        deleteProducerBttn = new JButton();
        deleteConsumerBttn = new JButton();
        refreshBttn = new JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Topic Manager");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        topicList.setModel(topicListModel);
        topicScrollPane.setViewportView(topicList);

        producerList.setModel(producerListModel);
        producerScrollPane.setViewportView(producerList);

        consumerList.setModel(consumerListModel);
        consumerScrollPane.setViewportView(consumerList);

        inspectTopicBttn.setText("Inspect Topic");
        inspectTopicBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inspectTopicBttnActionPerformed();
            }
        });

        addTopicBttn.setText("Add Topic");
        addTopicBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addTopicBttnActionPerformed();
            }
        });

        addProducerBttn.setText("Add Producer");
        addProducerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addProducerBttnActionPerformed();
            }
        });

        addConsumerBttn.setText("Add Consumer");
        addConsumerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addConsumerBttnActionPerformed();
            }
        });

        deleteTopicBttn.setText("Delete Topic");
        deleteTopicBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteTopicBttnActionPerformed();
            }
        });

        deleteProducerBttn.setText("Delete Producer");
        deleteProducerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteProducerBttnActionPerformed();
            }
        });

        deleteConsumerBttn.setText("Delete Cosnumer");
        deleteConsumerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteConsumerBttnActionPerformed();
            }
        });

        refreshBttn.setText("Refresh Topics List");
        refreshBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                refreshBttnActionPerformed();
            }
        });

        topicLabel.setFont(new Font("Segoe UI", 1, 12));
        topicLabel.setText("Topics:");

        producerLabel.setFont(new Font("Segoe UI", 1, 12));
        producerLabel.setText("Producers:");

        consumerLabel.setFont(new Font("Segoe UI", 1, 12));
        consumerLabel.setText("Consumers:");

        JPanel gui = new JPanel();

        GroupLayout layout = new GroupLayout(gui);
        gui.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(topicLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(producerLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(consumerLabel)
                                                .addGap(319, 319, 319))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(topicScrollPane)
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(addTopicBttn)
                                                        .addComponent(refreshBttn)
                                                        .addComponent(deleteTopicBttn)
                                                        .addComponent(inspectTopicBttn))
                                                .addComponent(producerScrollPane)
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(addProducerBttn)
                                                        .addComponent(deleteProducerBttn))
                                                .addComponent(consumerScrollPane)
                                                .addGroup(layout.createParallelGroup()
                                                        .addComponent(addConsumerBttn)
                                                        .addComponent(deleteConsumerBttn))))));

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(topicLabel)
                                        .addComponent(producerLabel)
                                        .addComponent(consumerLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(topicScrollPane)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(addTopicBttn)
                                                .addComponent(refreshBttn)
                                                .addComponent(inspectTopicBttn)
                                                .addComponent(deleteTopicBttn))
                                        .addComponent(producerScrollPane)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(addProducerBttn)
                                                .addComponent(deleteProducerBttn))
                                        .addComponent(consumerScrollPane)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(addConsumerBttn)
                                                .addComponent(deleteConsumerBttn)))));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void inspectTopicBttnActionPerformed() {
        try {
            KafkaTopic selectedTopic = this.topicList.getSelectedValue();

            this.producerListModel.clear();
            this.consumerListModel.clear();
            this.producerListModel.addAll(Arrays.asList(selectedTopic.getProducers()));
            this.consumerListModel.addAll(Arrays.asList(selectedTopic.getConsumers()));
            this.inspectingTopic = selectedTopic;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTopicBttnActionPerformed() {
        AddTopicFrame addTopicForm = new AddTopicFrame(this, controller);
        this.setEnabled(false);
        addTopicForm.setAlwaysOnTop(true);
        addTopicForm.setVisible(true);
    }

    private void addProducerBttnActionPerformed() {
        try {
            KafkaTopic selectedTopic = this.topicList.getSelectedValue();

            KafkaProducer newProducer = selectedTopic.addProducer(new KafkaProducer(selectedTopic));

            if (newProducer == null) {
                JOptionPane.showMessageDialog(this,
                        Globals.errRecordNotAdded + Globals.messageSeperator + Globals.errMaxProducersReached,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, Globals.respAddedSuccessfully, "Info",
                    JOptionPane.INFORMATION_MESSAGE);

            if (this.inspectingTopic != null) {
                if (selectedTopic.getName().equals(this.inspectingTopic.getName())) {
                    this.producerListModel.clear();
                    this.producerListModel.addAll(Arrays.asList(selectedTopic.getProducers()));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addConsumerBttnActionPerformed() {
        try {
            KafkaTopic selectedTopic = this.topicList.getSelectedValue();

            KafkaConsumer newConsumer = selectedTopic.addConsumer(new KafkaConsumer(selectedTopic));

            if (newConsumer == null) {
                JOptionPane.showMessageDialog(this,
                        Globals.errRecordNotAdded + Globals.messageSeperator + Globals.errMaxConsumersReached,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, Globals.respAddedSuccessfully, "Info",
                    JOptionPane.INFORMATION_MESSAGE);

            if (this.inspectingTopic != null) {
                if (selectedTopic.getName().equals(this.inspectingTopic.getName())) {
                    this.consumerListModel.clear();
                    this.consumerListModel.addAll(Arrays.asList(selectedTopic.getConsumers()));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTopicBttnActionPerformed() {
        try {
            KafkaTopic selectedTopic = this.topicList.getSelectedValue();

            int resp = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the topic: " + selectedTopic.getName());

            if (resp == 0) {
                this.controller.deleteTopicHandler(selectedTopic.getName());
                JOptionPane.showMessageDialog(this, Globals.respDeletedSuccessfully, "Info",
                        JOptionPane.INFORMATION_MESSAGE);

                this.topicListModel.clear();
                this.topicListModel.addAll(controller.getAllTopics());

                if (this.inspectingTopic != null) {
                    if (selectedTopic.getName().equals(this.inspectingTopic.getName())) {
                        this.producerListModel.clear();
                        this.consumerListModel.clear();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProducerBttnActionPerformed() {
        try {
            int selectedProducerIndex = this.producerList.getSelectedIndex();

            int resp = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this producer");

            if (resp == 0) {
                this.controller.removeProducer(this.inspectingTopic.getName(), selectedProducerIndex);
                JOptionPane.showMessageDialog(this, Globals.respDeletedSuccessfully, "Info",
                        JOptionPane.INFORMATION_MESSAGE);

                this.producerListModel.clear();
                this.producerListModel.addAll(Arrays.asList(this.inspectingTopic.getProducers()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteConsumerBttnActionPerformed() {
        try {
            int selectedConsumerIndex = this.consumerList.getSelectedIndex();

            int resp = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this consumer");

            if (resp == 0) {
                this.controller.removeConsumer(this.inspectingTopic.getName(), selectedConsumerIndex);
                JOptionPane.showMessageDialog(this, Globals.respDeletedSuccessfully, "Info",
                        JOptionPane.INFORMATION_MESSAGE);

                this.consumerListModel.clear();
                this.consumerListModel.addAll(Arrays.asList(this.inspectingTopic.getConsumers()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errNothingIsSelected, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshBttnActionPerformed() {
        this.topicListModel.clear();
        this.topicListModel.addAll(this.controller.getAllTopics());
    }
}