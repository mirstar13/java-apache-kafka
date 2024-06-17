package oopproject1.controller;

import oopproject1.admin.KafkaBroker;
import oopproject1.admin.KafkaCluster;
import oopproject1.admin.KafkaTopic;
import oopproject1.utilities.Globals;
import oopproject1.view.MainFrame;

public class KafkaClusterController {
    KafkaCluster cluster;
    MainFrame view;

    public KafkaClusterController(KafkaCluster cluster) {
        this.cluster = cluster;
    }

    public void start() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame(this).setVisible(true);
        });
    }

    public void insertBrokerHandler(String host, int port, int maxTopics) {
        this.cluster.insertBroker(new KafkaBroker(host, port, maxTopics));
    }

    public void deleteBrokerHandler(String host, int port) {
        this.cluster.removeBroker(host, port);
    }

    public void updateBrokerPortHandler(String host, int port, int newPort) {
        this.cluster.updateBrokerPort(host, port, newPort);
    }

    public void insertTopicHandler(String name, int numPartitions, int maxProducers, int maxConsumers, int replicationFactor, boolean keyed) {
        this.cluster.addTopic(name, numPartitions, maxProducers, maxConsumers, replicationFactor, keyed);
    }
}
