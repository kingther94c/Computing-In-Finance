package middleware;

import java.util.HashMap;


import simulation.AsianCallPayOut;
import simulation.EuropeanCallPayOut;
import simulation.PayOut;       
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 * 
 * Server send a batch simulation requests on a queue
 * Server has consumer listening to results over unique topic
 * @author Kingther
 *
 */
public class Server implements Runnable{  


   	public static HashMap<String,Option> _testOptionMap = generateOptionList();
	protected static String brokerURL = "tcp://localhost:61616";
   	protected static ConnectionFactory factory;
   	protected Connection connection;
   	protected Session session;
   	protected MessageProducer producer;
   	
    private int _numOption;
    private MapMessage[] _message;
    private String[] _optionName;
   	private boolean[] _isFinished;
   	private int[] _requestBatchCounter;
    private int _batchSize = 1000;
    private SimulatorManager[] _simulatorManager ;
    private double confidenceLevel = 0.96;
    private double errorTolerance = 0.1;

    private class PayoffMessageListener implements MessageListener {
    	public PayoffMessageListener(int i){
    		_i = i;
    	}
    	private int _i;
    	public void onMessage(Message message) { 
   			if (message instanceof TextMessage){
   				try{
   					//System.out.println(Double.parseDouble( ((TextMessage) message).getText()));
   					String[] payoutRecord = ((TextMessage) message).getText().split(",");
       				_simulatorManager[_i].update(Double.parseDouble(payoutRecord[0]),Double.parseDouble(payoutRecord[1]),_batchSize); 
       				message.acknowledge();
   				} catch (JMSException e ){
   					e.printStackTrace();
   				}
   			}
   		}
    }

    public Server(Option[] option) throws JMSException {
      	factory = new ActiveMQConnectionFactory(brokerURL);
      	connection = factory.createConnection();
      	connection.start();
      	session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      	producer = session.createProducer(null);

      	_numOption = option.length;
        _message = new MapMessage[_numOption];
        _optionName = new String[_numOption];
       	_isFinished = new boolean[_numOption];
        _simulatorManager = new SimulatorManager[_numOption];
        _requestBatchCounter = new int[_numOption];
        for(int i = 0;i<_numOption;i++){
            _message[i] = createSimulationRequest(option[i]);
            _optionName[i] = option[i].getOptionName();
           	_isFinished[i] = false;
           	_simulatorManager[i] = new SimulatorManager(confidenceLevel,errorTolerance);
           	_requestBatchCounter[i] = 0;
        }
    }
    
    public void close() throws JMSException {
    	if (connection != null) {
    		connection.close();
    	}
    }


    public void run(){


    	
    	try{
    		
        	//To create destinations
        	Destination[] payoffTopics = new Destination[_numOption];
    		Destination RequestQueue = session.createQueue("Request");
    		for(int i =0;i<_numOption;i++){
        		payoffTopics[i] = session.createTopic("Payoff:"+_optionName[i]);
        		
        		MessageConsumer consumer = session.createConsumer(payoffTopics[i] );
        		consumer.setMessageListener(new PayoffMessageListener(i));	
        	}
	
	    	
	    	producer = session.createProducer(null); 
	    	
	    	//To send simulation request
	    	for (int i =0;i<_numOption;i++){
	    		
    			producer.send(RequestQueue,_message[i]);      
	    		System.out.println("["+_optionName[i]+"]Simulation Request has been sent!"); 
	    		
	    	}
	    	
	
	    	int numFinished = 0;
	    	//repeat if not finished for all.
	    	while(numFinished<_numOption){
	    		numFinished = 0;
	        	for (int i =0;i<_numOption;i++){
	        		//if already finished, it would not check weather it is finished
	        		if(!_isFinished[i]){
	        			//if not finished, send another batch request
	        			if(_simulatorManager[i].getCount()>_requestBatchCounter[i]*_batchSize){
	        				_isFinished[i] = _simulatorManager[i].isFinished();
	        				System.out.println(_simulatorManager[i].getSummary());
	        				if(!_isFinished[i]){
	        					producer.send(RequestQueue,_message[i]);      
	                    		System.out.println("["+_optionName[i]+"]Simulation Request has been sent!"); 
	        				}
	        				else
	        					numFinished++;
	        					
	        			}
	        			
	        		}
	        		else
	        			numFinished++;
	        	}
	        	Thread.sleep(100);
	    	}
	    	for (int i =0;i<_numOption;i++)
	    		System.out.println(_optionName[i]+" Price = "+Double.toString(_simulatorManager[i].getPrice()));
	    	this.close();
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return;
    	}
    	
    	
    	
    }
    //just for test
	private static HashMap<String,Option> generateOptionList(){
		String optionName0 = "IBMEuropeanCall";
		String optionName1 = "IBMAsianCall";
		double volatility = 0.01;
		double interestRate = 0.0001;
		int time = 252;
		double spotPrice = 152.35;
		double strikePrice0 = 165;
		double strikePrice1=164;
		PayOut payOut0 = (PayOut) new EuropeanCallPayOut();
		PayOut payOut1 = (PayOut) new AsianCallPayOut();
		HashMap<String,Option> optionMap = new HashMap<String,Option>();
		optionMap.put(optionName0, new Option(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, payOut0));
		optionMap.put(optionName1, new Option(optionName1,volatility, interestRate, time, spotPrice, strikePrice1, payOut1));
		return optionMap;
	}
	/**
	 * converts a option to a MapMessage
	 * @param option
	 * @return
	 * @throws JMSException
	 */
	private MapMessage createSimulationRequest(Option option) throws JMSException{
		MapMessage message = session.createMapMessage();
		message.setInt("batchSize", _batchSize);
		message.setString("optionName", option.getOptionName());
		message.setDouble("volatility", option.getVolatility());
		message.setDouble("interestRate", option.getInterestRate());	
		message.setDouble("strikePrice", option.getStrikePrice());
		message.setDouble("spotPrice", option.getSpotPrice());	
		message.setInt("time", option.getTime());
		message.setString("type", option.getType());
		return message;
	}
	
    public static void main(String[] args) throws Exception {
    	Option[] options = {_testOptionMap.get("IBMEuropeanCall"),_testOptionMap.get("IBMAsianCall")};
    	Server server = new Server(options);
    	server.run();	
    }
	
}
