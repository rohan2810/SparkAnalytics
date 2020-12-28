package ProtoBuf;

import Protobuf.AddressBookProtos;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.testng.Assert.assertEquals;

public class protoTest {

    @Test
    public void basicFlow() throws IOException {
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


        /*
            Serialize
         */

//        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder().addPeople(person).build();
//        FileOutputStream fos = new FileOutputStream("/Users/rohansurana/IdeaProjects/work/SparkAnalytics/src/test/resources/protoSaved");
//        addressBook.writeTo(fos);


        /*
            De-serialize
         */
        AddressBookProtos.AddressBook deserialized
                = AddressBookProtos.AddressBook.newBuilder()
                .mergeFrom(new FileInputStream("/Users/rohansurana/IdeaProjects/work/SparkAnalytics/src/test/resources/protoSaved")).build();

        System.out.println(deserialized.getPeople(0).getEmail());
        System.out.println(deserialized.getPeopleCount());
    }
}
