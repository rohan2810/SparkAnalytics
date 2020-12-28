package ActiveMq

import javax.jms._
import org.apache.activemq.{ActiveMQConnection, ActiveMQConnectionFactory}

object ReceiveMessage {
  val url: String = ActiveMQConnection.DEFAULT_BROKER_URL
  val subject = "outgoingReplyQueue"
  var connFactory: ActiveMQConnectionFactory = _
  var conn: Connection = _
  var session: Session = _
  var destination: Destination = _
  var consumer: MessageConsumer = _
  var message1: Message = _


  def main(args: Array[String]): Unit = {
    try {
      createConnection()
      run()
      Thread.sleep(5000)
    }
    finally {
      closeConnection()
    }
  }

  private def run(): Unit = {
    consumer.setMessageListener {
      case text: TextMessage =>
        if (text.getStringProperty("analysis") != null) {
          text.getStringProperty("analysis") match {
            case "A" => println("Got A")
            case "B" => println("Got B")
          }
        }
      case bytesMessage: BytesMessage =>
        val byteMessage = bytesMessage.asInstanceOf[BytesMessage]
        val byteData = new Array[Byte](byteMessage.getBodyLength.toInt)
        byteMessage.readBytes(byteData)
        byteMessage.reset()
        val stringMessage = new String(byteData)
        println(stringMessage)

    }
  }

  private def createConnection(): Unit = {
    connFactory = new ActiveMQConnectionFactory(url)
    connFactory.setAlwaysSessionAsync(false) //This allows messages to be passed directly from the transport to the message consumer
    //connFactory.setOptimizeAcknowledge(true) // sends a range of messages consumed to the broker
    /*
    The downside to not acknowledging every message individually is that if the message consumer were to lose its connection with the ActiveMQ broker for any reason,
    then your messaging application could receive duplicate messages.
    But for applications that require fast throughput (such as real-time data feeds) and are less concerned about duplicates,
    using optimizeAcknowledge is the recommended approach.
     */
    conn = connFactory.createConnection()
    conn.start()
    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
    destination = session.createQueue(subject)
    consumer = session.createConsumer(destination)
    //    message = consumer.receive()
  }

  private def closeConnection(): Unit = {
    consumer.close()
    session.close()
    conn.close()
  }

}
