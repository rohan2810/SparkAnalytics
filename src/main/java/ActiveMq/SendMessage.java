package ActiveMq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class SendMessage {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        //Creating a non transactional session to send/receive JMS message.
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        //Destination represents here our queue 'JCG_QUEUE' on the JMS server.
        //The queue will be created automatically on the server.
        String subject = "JCG_QUEUE";
        Destination destination = session.createQueue(subject);

        // MessageProducer is used for sending messages to the queue.
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // We will send a small text message saying 'Hello World!!!'
        TextMessage message = session
                .createTextMessage("mess");
        message.setStringProperty("analysis", "A");
        TextMessage message1 = session.createTextMessage("mess");
        message1.setStringProperty("analysis", "B");

        // Here we are sending our message!
        producer.send(message);
        producer.send(message1);
        connection.close();
        System.out.println("Sent");
    }
}
