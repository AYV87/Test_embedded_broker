import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Receiver {

    private final String queueName;
    private final int mode;
    private final boolean isTransacted;
    private final String url;

    public Receiver(String queueName, int mode, boolean isTransacted, String url) {
        this.queueName = queueName;
        this.mode = mode;
        this.isTransacted = isTransacted;
        this.url = url;
    }

    public void Message(){
    // фабрика создания коннектов для activeMQ
        ConnectionFactory collectionFactory = new ActiveMQConnectionFactory(url);
        try{
            // из фабрики создаем подключение
            Connection connection = collectionFactory.createConnection();
            connection.start();

            // из подлючения создаем сессию
            Session session = connection.createSession(isTransacted, mode);
            // из сессии создаем очередь сообщений
            Destination destination = session.createQueue(queueName);

            // создаем получателя
            MessageConsumer consumer = session.createConsumer(destination);

            // получаем сообщение
            Message message = consumer.receive();

            // тк в примере мы отправляем только TextMessage,  проверяем на возможность его преобразования
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Сообщение отправлено. Сообщение содержит - " + textMessage.getText() + "'");
            }

            // подтверждаем что сообщение получено
            message.acknowledge();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println(e);
        }
    }

}
