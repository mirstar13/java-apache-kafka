package oopproject1.view;

import oopproject1.controller.KafkaClusterController;
import java.awt.Toolkit;

import java.awt.Dimension;

public class MainFrame extends javax.swing.JFrame {
    KafkaClusterController controller;
    
    public MainFrame(KafkaClusterController controller){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        this.setLocation((screenSize.width - frameSize.width) / 4, (screenSize.height - frameSize.height) / 4);
        this.controller = controller;
        this.setVisible(true);
        initComponents();
    }

    private void initComponents() {
    }
}
