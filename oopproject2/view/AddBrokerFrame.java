package oopproject1.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import oopproject1.admin.KafkaCluster;
import oopproject1.controller.KafkaClusterController;
import oopproject1.utilities.Globals;

public class AddBrokerFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    private JLabel hostLabel;
    private JLabel portLabel;
    private JLabel maxTopicsLabel;

    private JTextField hostTextField;
    private JTextField portTextField;
    private JTextField maxTopicsTextField;

    private JButton addBrokerBttn;

    public AddBrokerFrame(JFrame callingFrame, KafkaClusterController controller) {
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
        hostLabel = new JLabel();
        portLabel = new JLabel();
        maxTopicsLabel = new JLabel();
        hostTextField = new JTextField();
        portTextField = new JTextField();
        maxTopicsTextField = new JTextField();
        addBrokerBttn = new JButton();

        hostLabel.setFont(new Font("Segoe UI", 1, 12));
        hostLabel.setText("Host:");

        portLabel.setFont(new Font("Segoe UI", 1, 12));
        portLabel.setText("Port:");

        maxTopicsLabel.setFont(new Font("Segoe UI", 1, 12));
        maxTopicsLabel.setText("Max Topics:");

        hostTextField.setColumns(10);

        portTextField.setColumns(4);

        maxTopicsTextField.setColumns(2);

        addBrokerBttn.setText("Add Broker");
        addBrokerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addBrokerBttnActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Add Broker Form");
        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
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
                                                        .addComponent(hostLabel)
                                                        .addComponent(portLabel)
                                                        .addComponent(maxTopicsLabel))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(hostTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(portTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(maxTopicsTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(70, 70, 70)
                                                .addComponent(addBrokerBttn)
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
                                                        .addComponent(hostLabel)
                                                        .addComponent(hostTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(portLabel)
                                                        .addComponent(portTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(maxTopicsLabel)
                                                        .addComponent(maxTopicsTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(addBrokerBttn))))
                                .addContainerGap(23, Short.MAX_VALUE)));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void addBrokerBttnActionPerformed(ActionEvent evt) {
        try {
            int port = Integer.parseInt(this.portTextField.getText());
            int maxTopics = Integer.parseInt(this.maxTopicsTextField.getText());

            if (maxTopics > Globals.maxTopics) {
                maxTopics = KafkaCluster.DEFAULT_MAX_TOPICS_PER_BROKER;
            }

            boolean created = controller.insertBrokerHandler(this.hostTextField.getText(), port, maxTopics);

            if (!created) {
                JOptionPane.showMessageDialog(this, Globals.errRecordNotAdded, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, Globals.respAddedSuccessfully, "Ifno", JOptionPane.INFORMATION_MESSAGE);

            this.setEnabled(false);
            this.setAlwaysOnTop(false);
            this.setVisible(false);
            callingFrame.setEnabled(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, Globals.errInvalidArguments, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errSomethingWentWrong, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
