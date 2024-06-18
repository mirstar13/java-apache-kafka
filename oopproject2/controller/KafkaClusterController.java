package oopproject1.controller;

import java.util.List;

import oopproject1.admin.KafkaBroker;
import oopproject1.admin.KafkaClient;
import oopproject1.admin.KafkaCluster;
import oopproject1.admin.KafkaConsumer;
import oopproject1.admin.KafkaProducer;
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

    public boolean insertBrokerHandler(String host, int port, int maxTopics) {
        return this.cluster.insertBroker(new KafkaBroker(host, port, maxTopics));
    }

    public boolean deleteBrokerHandler(String host, int port) {
        return this.cluster.removeBroker(host, port);
    }

    public boolean updateBrokerPortHandler(String host, int port, int newPort) {
        return this.cluster.updateBrokerPort(host, port, newPort);
    }

    public KafkaTopic insertTopicHandler(String name, int numPartitions, int maxProducers, int maxConsumers, int replicationFactor, boolean keyed) {
        return this.cluster.addTopic(name, numPartitions, maxProducers, maxConsumers, replicationFactor, keyed);
    }

    public void deleteTopicHandler(String topicName) {
        boolean deleted = this.cluster.deleteTopic(topicName);
        if (!deleted) {
            System.out.println(Globals.errGUI + Globals.errTopicNotFound);
        }
    }

    public KafkaProducer addProducer(String topicName) {
        KafkaTopic topic = this.cluster.findTopicByName(topicName);

        if (topic == null) {
            System.out.println(Globals.errGUI + Globals.errTopicNotFound);
            return null;
        }

        KafkaProducer newProducer = topic.addProducer(new KafkaProducer(topic));        
        return newProducer;
    }

    public void removeProducer(String topicName, int producerIndex) {
        KafkaTopic topic = this.cluster.findTopicByName(topicName);

        if (topic == null) {
            System.out.println(Globals.errGUI + Globals.errTopicNotFound);
            return;
        }

        topic.removeProducer(producerIndex);
    }

    public KafkaConsumer addConsumer(String topicName) {
        KafkaTopic topic = this.cluster.findTopicByName(topicName);

        if (topic == null) {
            System.out.println(Globals.errGUI + Globals.errTopicNotFound);
            return null;
        }

        KafkaConsumer newConsumer = topic.addConsumer(new KafkaConsumer(topic));
        return newConsumer;
    }

    public void removeConsumer(String topicName, int consumerIndex) {
        KafkaTopic topic = this.cluster.findTopicByName(topicName);

        if (topic == null) {
            System.out.println(Globals.errGUI + Globals.errTopicNotFound);
            return;
        }

        topic.removeConsumer(consumerIndex);
    }

    public boolean insertFileHandler(String fileName, String topicName, String keyName) {
        if (!this.cluster.checkFileExistance(fileName)) {
            return false;
        }
        
        this.cluster.insertFile(fileName, topicName, keyName);
        return true;
    }

    public void readMessagesHandler() {
        this.cluster.startReadingMessages();
    }

    public KafkaTopic findTopicByName(String topicName) {
        return this.cluster.findTopicByName(topicName);
    }

    public List<KafkaBroker> getAllBrokers() {
        return this.cluster.getBrokers();
    }

    public List<KafkaTopic> getAllTopics() {
        return this.cluster.getTopics();
    }

    public List<KafkaProducer> getAllProducers() {
        return this.cluster.getProducers();
    }

    public List<KafkaConsumer> getAllConsumers() {
        return this.cluster.getConsumers();
    }

    public List<KafkaClient> getAllClients() {
        return this.cluster.getClients();
    }
}
