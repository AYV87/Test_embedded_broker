import javax.jms.Session;

public class Main {

    // тут выбираем в каком режиме будут храниться сообщения
    private static final boolean IS_PERSISTENT = true;
    private static final boolean IS_TRANSACTED = false;
    // адрес где находится activeMQ
    private static final String URL = "tcp://localhost:61616";
    //private static final String URL = "vm://broker";
    // название очереди
    private static final String QUEUENAME = "queue";

    //private static final int MODE = Session.SESSION_TRANSACTED;
    //private static final int MODE = Session.AUTO_ACKNOWLEDGE;
    //private static final int MODE = Session.DUPS_OK_ACKNOWLEDGE;
    private static final int MODE = Session.CLIENT_ACKNOWLEDGE;

    public static void main(String[] args) {

        System.out.println("Старт приложения jmstest");
        System.out.println("Режим = Session.CLIENT_ACKNOWLEDGE");
        System.out.println("IS_TRANSACTED = " + IS_TRANSACTED);
        System.out.println("IS_PERSISTANT = " + IS_PERSISTENT);

        // создаем отправителя
        Sender sender = new Sender(QUEUENAME, MODE, IS_TRANSACTED, URL);
        System.out.println("Отправитель создан");
        // создаем получателя
        Receiver receiver = new Receiver(QUEUENAME, MODE, IS_TRANSACTED, URL);
        System.out.println("Получатель создан");

        // создаем брокера
        Broker broker = new Broker(IS_PERSISTENT, IS_TRANSACTED, URL);
        broker.run();

        // отправляем сообщение
        sender.sendMessage();

        // получаем сообщение
        receiver.receiveMessage();
    }
}
