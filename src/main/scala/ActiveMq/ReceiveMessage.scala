package ActiveMq

import javax.jms._
import org.apache.activemq.{ActiveMQConnection, ActiveMQConnectionFactory}

object ReceiveMessage {
  val url: String = ActiveMQConnection.DEFAULT_BROKER_URL
  val subject = "JCG_QUEUE"
  var conn: Connection = _
  var session: Session = _
  var destination: Destination = _
  var consumer: MessageConsumer = _
  var message: Message = _


  def main(args: Array[String]): Unit = {
    while (true) {
      run()
    }
  }

  private def run(): Unit = {
    createConnection()
    message match {
      case text: TextMessage =>
        if (text.getStringProperty("header") != null) {
          text.getStringProperty("header") match {
            case "A" => println("Got A")
            case "B" => println("Got B")
          }
        }
      case _ =>
    }
    closeConnection()

  }

  private def createConnection(): Unit = {
    conn = new ActiveMQConnectionFactory(url).createConnection()
    conn.start()
    session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE)
    destination = session.createQueue(subject)
    consumer = session.createConsumer(destination)
    message = consumer.receive(1000)
  }

  private def closeConnection(): Unit = {
    consumer.close()
    session.close()
    conn.close()
  }

}
