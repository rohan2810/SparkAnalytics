package activeMq_ProtoBuf;

import Protobuf.AddressBookProtos;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import javax.jms.*;
import java.util.Random;

import static org.testng.Assert.assertEquals;

public class ActiveMq_Protobuf_SendMessage {
    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    @Test
    public void basicFlow() throws JMSException {
        String email = "tes@working.com";
        int id = new Random().nextInt();
        String name = "Michael Program";
        String number = "343423434q234";
        AddressBookProtos.Person person =
                AddressBookProtos.Person.newBuilder()
                        .setId(id)
                        .setName(name)
                        .setEmail(email)
                        .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                                .setNumber(number)
                                .setType(AddressBookProtos.Person.PhoneType.HOME))
                        .build();

        assertEquals(person.getEmail(), email);
        assertEquals(person.getId(), id);
        assertEquals(person.getName(), name);
        System.out.println(person.getPhonesList());

        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();

        byte[] byte1 = addressBook.toByteArray();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        String subject = "WITH PROTOBUF";
        
        Destination destination = session.createQueue(subject);
        producer = session.createProducer(destination);

        BytesMessage message = session.createBytesMessage();
        message.writeBytes(byte1);

        producer.send(message);

        System.out.println("' " + message.toString() + " '");

//
//        AddressBookProtos.AddressBook deserialized
//                = AddressBookProtos.AddressBook.newBuilder()
//                .mergeFrom(new FileInputStream("/Users/rohansurana/IdeaProjects/work/SparkAnalytics/src/test/resources/protoSaved")).build();
//
//        System.out.println(deserialized.getPeople(0).getEmail());
//        System.out.println(deserialized.getPeopleCount());
    }

    @AfterTest
    public void closeConnections() throws JMSException {
        session.close();
        connection.close();
        producer.close();
    }
}
