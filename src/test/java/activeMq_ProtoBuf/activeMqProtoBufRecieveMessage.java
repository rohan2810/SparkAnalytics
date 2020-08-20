package activeMq_ProtoBuf;

import Protobuf.AddressBookProtos;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.jms.*;

public class activeMqProtoBufRecieveMessage {

    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    @BeforeTest
    public void init() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
    }

    @Test
    public void recieveMessage() throws JMSException, InvalidProtocolBufferException {

        session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        String subject = "WITH PROTOBUF";
        Destination destination = session.createQueue(subject);

        consumer = session.createConsumer(destination);

        Message message = consumer.receive(1000);

        BytesMessage bytesMessage;
        byte[] bytes = new byte[0];
        if (message instanceof BytesMessage) {
            bytesMessage = (BytesMessage) message;
            bytes = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            System.out.println("Received message '" + bytesMessage.toString() + "'");
        }
        /*
            De-serialize
         */
        ByteString b = ByteString.copyFrom(bytes);
        AddressBookProtos.AddressBook deserialized
                = AddressBookProtos.AddressBook.newBuilder().mergeFrom(b).build();


        System.out.println(deserialized.getPeople(0).getEmail());
        System.out.println(deserialized.getPeopleCount());
    }

    @AfterTest
    public void closeConnections() throws JMSException {
        consumer.close();
        session.close();
        connection.close();
    }


}
