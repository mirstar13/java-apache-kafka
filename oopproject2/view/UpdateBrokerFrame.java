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

import oopproject1.controller.KafkaClusterController;
import oopproject1.utilities.Globals;

public class UpdateBrokerFrame extends JFrame {
    JFrame callingFrame;
    KafkaClusterController controller;

    private JLabel hostLabel;
    private JLabel oldPortLabel;
    private JLabel newPortLabel;

    private JTextField hostTextField;
    private JTextField oldPortTextField;
    private JTextField newPortTextField;

    private JButton updateBrokerBttn;

    public UpdateBrokerFrame(JFrame callingFrame, KafkaClusterController controller) {
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
        oldPortLabel = new JLabel();
        newPortLabel = new JLabel();
        hostTextField = new JTextField();
        oldPortTextField = new JTextField();
        newPortTextField = new JTextField();
        updateBrokerBttn = new JButton();

        hostLabel.setFont(new Font("Segoe UI", 1, 12));
        hostLabel.setText("Host:");

        oldPortLabel.setFont(new Font("Segoe UI", 1, 12));
        oldPortLabel.setText("Old Port:");

        newPortLabel.setFont(new Font("Segoe UI", 1, 12));
        newPortLabel.setText("New Port:");

        hostTextField.setColumns(10);

        oldPortTextField.setColumns(4);

        newPortTextField.setColumns(4);

        updateBrokerBttn.setText("Update Broker");
        updateBrokerBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateBrokerBttnActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        setTitle("Update Broker Form");
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
                                                        .addComponent(oldPortLabel)
                                                        .addComponent(newPortLabel))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(hostTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(oldPortTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(newPortTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(70, 70, 70)
                                                .addComponent(updateBrokerBttn)
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
                                                        .addComponent(oldPortLabel)
                                                        .addComponent(oldPortTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(newPortLabel)
                                                        .addComponent(newPortTextField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(updateBrokerBttn))))
                                .addContainerGap(23, Short.MAX_VALUE)));

        add(gui);

        pack();
    }

    private void formWindowClosed(WindowEvent evt) {
        this.callingFrame.setEnabled(true);
    }

    private void updateBrokerBttnActionPerformed(ActionEvent evt) {
        try {
            int oldPort = Integer.parseInt(this.oldPortTextField.getText());
            int newPort = Integer.parseInt(this.newPortTextField.getText());

            boolean created = controller.updateBrokerPortHandler(this.hostTextField.getText(), oldPort, newPort);

            if (!created) {
                JOptionPane.showMessageDialog(this, Globals.errRecordNotUpdated, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, Globals.respUpdatedSuccessfully, "Info", JOptionPane.INFORMATION_MESSAGE);

            this.setEnabled(false);
            this.setAlwaysOnTop(false);
            this.setVisible(false);
            this.callingFrame.setEnabled(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, Globals.errInvalidArguments, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, Globals.errSomethingWentWrong, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
