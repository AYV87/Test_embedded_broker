import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {

    private String queueName;
    private int mode;
    private boolean isTransacted;
    private String url;

    public Sender(String queueName, int mode, boolean isTransacted, String url) {
        this.queueName = queueName;
        this.mode = mode;
        this.isTransacted = isTransacted;
        this.url = url;
    }

    public void sendMessage(){

        // создаем фабрику подключений
        ConnectionFactory f = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                url);
        try{
            // создаем подключение из фабрики
            Connection connection = f.createConnection();
            // создаем сессию и устанавливаем у неё настройки хранения и доставки сообщений
            Session session = connection.createSession(isTransacted, mode);
            // создаем очередь сообщений
            Destination destination = session.createQueue(queueName);

            // создаем отправителя
            MessageProducer producer = session.createProducer(destination);

            // создаем сообщение
            TextMessage message = session.createTextMessage("Тестовое сообщение");

            // отправляем сообщение
            producer.send(message);

            System.out.println("Сообщение отправлено. Сообщение содержит - '" + message.getText() + "'");

            // тут нужно делать коммит при различных mode для сессий
            //session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println(e);
        }
    }
}
