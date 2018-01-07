Summer Assignment
Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
How To Use
————————————————————————
Here is a example to compute the IBM European Call option price and the IBM Asian Call option price, where the stopping criteria is with probability 96% the estimation error is less than 0.1$.

//Firstly, you need to start the clients. Here for example you want to start 3 clients. You need to run 3 java applications with following main methods:

	public static void main(String[] args) throws Exception {	
		Client client = new Client();
		client.run();
	}
	public static void main(String[] args) throws Exception {	
		Client client = new Client();
		client.run();
	}
	public static void main(String[] args) throws Exception {	
		Client client = new Client();
		client.run();
	}

//Secondly, you need to start the server, by running 1 java application with following main method:

	public static void main(String[] args) throws Exception {
    		Option[] options = {_testOptionMap.get("IBMEuropeanCall"),_testOptionMap.get("IBMAsianCall")};
    		Server server = new Server(options);
    		server.run();		
    	}



Design
————————————————————————
My Server is constructed with an array of options to be simulated. There is a field _numClient that records how many clients can be sent requests to for each option. At first, my server will convert each option into a mapMessage and send a batch simulation requests through a queue (unique one for each option) to each client. Then, it will listen to results from each client over unique topics for unique option and decide whether to send another batch of simulation requests.

On Client side, it has a consumer listen to a queue of requests, which can be the request for any option as long as the stock path is the same, once it receives a message, it launches [batchSize] simulations (the number of simulations is specified in the MapMessage received). A producer will send the sum and sum of square of the payout values as textMessage back to the Server on a option name related topic. (If there are more than 1 severs, they must use different option names, therefore simulation results for different severs won’t mixed up)
