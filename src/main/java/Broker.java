import org.apache.activemq.broker.BrokerService;

public class Broker {

    public final boolean isPersistent;
    public final boolean isTransacted;
    public final String url;

    public Broker(boolean isPersistent, boolean isTransacted, String url) {
        this.isPersistent = isPersistent;
        this.isTransacted = isTransacted;
        this.url = url;
    }
    public void run(){
        //создаем брокера
        BrokerService brokerService = new BrokerService();
        try{
            //настраиваем его
            brokerService.setPersistent(isPersistent);
            brokerService.setUseJmx(false);

            brokerService.setBrokerName("broker");

            brokerService.addConnector(url);
            //начинаем его работу
            brokerService.start();
            System.out.println("Broker start");
            //brokerService.stop();
            System.out.println("Broker stop");
            } catch (Exception e) {
            System.out.println(e);

        }
    }

}
