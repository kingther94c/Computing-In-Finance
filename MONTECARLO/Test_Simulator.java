package simulation;

public class Test_Simulator extends junit.framework.TestCase{
	public void test_Simulator(){
		double sigma=1;
		double r=0;
		int T=1;
		double S0=100;
		double K=100;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		System.out.println(simulator.getClass());
		assertEquals(simulator.getClass().toString(),"class simulation.Simulator");
	}
	
//	public void test_computeLimit_iter(){
//		double sigma=1;
//		double r=0;
//		int T=1;
//		double S0=100;
//		double K=100;
//		PayOut payOut = (PayOut) new EuropeanCallPayOut();
//		double confidenceLevel = 0.96;
//		double error = 0.1;
//		
//		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
//		
//		double term = S0*Math.exp(-sigma*sigma/2.*T);
//		double lambda = sigma*Math.sqrt(T);
//		double Var = term*term*(Math.exp(2*lambda*lambda)-Math.exp(lambda*lambda));
//		System.out.println(n);
//		
//		double p = 2*StandardNormalDistributionStat.N(error*Math.sqrt(n/Var)) - 1;
//		System.out.println(p);
//		assertEquals(confidenceLevel,p,0.05);
//	}
	
	public void test_solve_boundarycondition1(){
		double sigma=0.01;
		double r=0.5;
		int T=0;
		double S0=100;
		double K=100;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("boundarycondition_T=0");
		System.out.println(optionPrice);
		assertEquals(optionPrice,0,0.5);
	}
	
	public void test_solve_boundarycondition2(){
		double sigma=0;
		double r=0;
		int T=10;
		double S0=1100;
		double K=100;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("boundarycondition_sigma=0");
		System.out.println(optionPrice);
		assertEquals(optionPrice,S0-K,1);
	}

	public void test_solve_boundarycondition3(){
		double sigma=10;
		double r=0;
		int T=1;
		double S0=100;
		double K=0;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("boundarycondition_large sigma");
		System.out.println("Careful: In this situation the results from BS formula (Stock Price) and Monte Carlo (0) are different!");
		System.out.println(optionPrice);
		assertEquals(optionPrice,0,0.5);
	}


	public void test_solve_boundarycondition4(){
		double sigma=0.01;
		double r=0;
		int T=100;
		double S0=100;
		double K=0.1;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("boundarycondition: large S/K");
		System.out.println(optionPrice);
		assertEquals(optionPrice,S0,0.5);
	}
	public void test_solve_boundarycondition5(){
		double sigma=0.5;
		double r=0;
		int T=1000;
		double S0=10;
		double K=0;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("boundarycondition_large T");
		System.out.println("Careful: In this situation the results from BS formula (Stock Price) and Monte Carlo (0) are different!");
		System.out.println(optionPrice);
		assertEquals(optionPrice,0,0.5);
	}
	public void test_EuropeanCallPutParity(){
		double sigma=0.01;
		double r=0.;
		int T=200;
		double S0=150;
		double K=165;
		PayOut CallpayOut = (PayOut) new EuropeanCallPayOut();
		PayOut PutpayOut = (PayOut) new EuropeanPutPayOut();
		long seed = 11086220;
		Simulator callSimulator = new Simulator(sigma, r, T, S0, K, CallpayOut, 0.96, 0.1);
		Simulator putSimulator = new Simulator(sigma, r, T, S0, K, PutpayOut, 0.96, 0.1);
		double callOptionPrice = callSimulator.solve(seed);
		double putOptionPrice = putSimulator.solve(seed);		
		System.out.println("European Call - European Put");
		System.out.println(callOptionPrice - putOptionPrice);
		System.out.println("Stock - Strike");
		System.out.println(S0 - K);		
		assertEquals(S0 - K,callOptionPrice - putOptionPrice,0.2);
	}

	public void test_AsianCallPutParity(){
		double sigma=0.01;
		double r=0.;
		int T=200;
		double S0=165;
		double K=150;
		PayOut CallpayOut = (PayOut) new AsianCallPayOut();
		PayOut PutpayOut = (PayOut) new AsianPutPayOut();
		long seed = 11086220;
		Simulator callSimulator = new Simulator(sigma, r, T, S0, K, CallpayOut, 0.96, 0.1);
		Simulator putSimulator = new Simulator(sigma, r, T, S0, K, PutpayOut, 0.96, 0.1);
		double callOptionPrice = callSimulator.solve(seed);
		double putOptionPrice = putSimulator.solve(seed);		
		System.out.println("Asian Call - Asian Put");
		System.out.println(callOptionPrice - putOptionPrice);
		System.out.println("Stock - Strike");
		System.out.println(S0 - K);		
		assertEquals(S0 - K,callOptionPrice - putOptionPrice,0.2);
	}
	
	public void test_EuropeanCall_approximation(){
		double sigma=0.3/19.1049731745428;
		double r=0;
		int T=60;
		double S0=165;
		double K=165;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.98;
		double error = 0.1;
		long seed = 11086220;
		double approxPrice = 0.4*S0*sigma*Math.sqrt(T);
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("European Call");
		System.out.println(optionPrice);
		System.out.println("European Call_Apprx");		
		System.out.println(approxPrice);
		assertEquals(optionPrice,approxPrice,0.3);
	}
	
	public void test_EuropeanCall(){
		double sigma=0.01;
		double r=0.0001;
		int T=252;
		double S0=152.35;
		double K=165;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve();
		System.out.println("European Call");
		System.out.println(optionPrice);
	}
	public void test_AsianCall(){
		double sigma=0.01;
		double r=0.0001;
		int T=252;
		double S0=152.35;
		double K=164;
		PayOut payOut = (PayOut) new AsianCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve();
		System.out.println("Asian Call");
		System.out.println(optionPrice);
	}
}
