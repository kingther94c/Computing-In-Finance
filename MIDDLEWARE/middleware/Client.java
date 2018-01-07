package middleware;
//     /usr/local/Cellar/activemq/apache-activemq-5.14.4/bin/macosx/activemq start

import org.apache.activemq.ActiveMQConnectionFactory;
import simulation.LogNormalStockPath;
import javax.jms.*;

/**
 * Client side has a consumer listen to the server's queue, once it receives a message, it launches [batchSize] simulations according to the simulations request
 * A producer will send each payoff in a textMessage back to server over an unique topic
 * @author Kingther
 *
 */
public class Client implements Runnable{

	
	//private class used to create MessageListener
	private class ClientMessageListener implements MessageListener{
		@Override
		public void onMessage(Message message) {
			if (message instanceof MapMessage){
				try{
					//Upon hearing message, simulate payout for the specified option and send back
					Option option= Option.convertToOption((MapMessage)message);
					int batchSize = ((MapMessage) message).getInt("batchSize");
					LogNormalStockPath stockPath = new LogNormalStockPath(option);
					double payoutSum = 0.;
					double payoutSumSqr = 0.;
					double payout = 0;
					for(int i =0;i< batchSize;i++){
						payout = option.computePayOut(stockPath);
						payoutSum += payout;
						payoutSumSqr += payout*payout;
					}						
					System.out.println(option.getOptionName()+":"+Double.toString(payoutSum));
					TextMessage returnMessage = session.createTextMessage(Double.toString(payoutSum)+","+Double.toString(payoutSumSqr));
					Destination payoffTopic = session.createTopic("Payoff:"+option.getOptionName());
					producer = session.createProducer(payoffTopic);
					producer.send(returnMessage);	
					
					message.acknowledge();

				} catch (JMSException e ){
					e.printStackTrace();
				}
			}
		}
		
	}
	private static String brokerURL = "tcp://localhost:61616";
	private static ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;	
	private ClientMessageListener clientMessageListener = new ClientMessageListener();
	/**
	 * constructs a client who focus on a specific option
	 * @param option 
	 * @throws Exception
	 */
	public Client() throws Exception {
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void main(String[] args) throws Exception {	
		//each client has a separate console. the name can be duplicated 
		Client client = new Client();
		client.run();
	}

	public void run(){
		try{
			
			Destination RequestQueue = session.createQueue("Request");
			MessageConsumer messageConsumer =session.createConsumer(RequestQueue);
			
			//start to listen to a queue
			messageConsumer.setMessageListener(clientMessageListener);
			System.out.println("Ready to receive simulation requests!");
		}
		catch(Exception e){
    		e.printStackTrace();
    		return;
		}

		
		
		
		
		
	}

  public Session getSession() {
     return session;
  }

}
