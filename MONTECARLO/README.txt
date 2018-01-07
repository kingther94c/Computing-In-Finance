Summer Assignment
Written in MAC (Unicode-utf-8)
by Kelvin (Jinze) Chen, Financial Mathematics, Courant Institute
========================
How To Use
————————————————————————
Here is a example to compute the European call option price, where the stopping criteria is with probability 96% the estimation error is less than 0.1$.
	
	//Set the parameter for computing
	double sigma=0.01;
	double r=0.0001;
	int T=252;
	double S0=152.35;
	double K=165;
	PayOut payOut = (PayOut) new EuropeanCallPayOut();
	double confidenceLevel = 0.96;
	double error = 0.1;
	long seed = 11086220;
	
	//Compute the European call option price
	Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
	double optionPrice = simulator.solve(seed);
	System.out.println(optionPrice);


Structure
————————————————————————
>Interface 

>>PayOut
	public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice);

>>RandomVariableGenerator
	public double getNextNumber();

>>RandomVectorGenerator
	public double[] getNextVector();
	public double[] getNextVector(int dim);

>>StockPath
	List<Pair<Integer,Double>> getSimulatedPath();


>util

>>Pair<Type1,Type2>

>>StandardNormalDistributionStat
	public static double inverseCumulativeProbability(double p_)
	public static double cumulativeProbability(double x)


>Generator
>>UniformRandomVariableGenerator implements RandomVariableGenerator
	public UniformRandomVariableGenerator	
	public UniformRandomVariableGenerator(long seed)
	public double getNextNumber()

>>NormalRandomVariableGenerator implements RandomVariableGenerator
    	public NormalRandomVariableGenerator(long seed)
    	public NormalRandomVariableGenerator()
    	public double getNextNumber()

>>NormalRandomVectorGenerator implements RandomVectorGenerator
	public NormalRandomVectorGenerator(long seed)
	public NormalRandomVectorGenerator()
	public double[] getNextVector()
	public double[] getNextVector(int dim)

>>AntitheticRandomVectorDecorator implements RandomVectorGenerator
	public double[] getNextVector()
	public double[] getNextVector(int dim)
	public AntitheticRandomVectorDecorator(RandomVectorGenerator generator) 
	protected double[] AntitheticVector(double[] vector)

>>LogNormalStockPath implements StockPath
	LogNormalStockPath(	double sigma, double r, double S0, int T, long seed)
	LogNormalStockPath(	double sigma, double r, double S0, int T)
	public ArrayList<Pair<Integer, Double>> getSimulatedPath () 


>PayOut
>>AsianCallPayOut implements PayOut 
	public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice)

>>EuropeanCallPayOut implements PayOut
	public double getPayout(ArrayList<Pair<Integer,Double>> stockPath, double strikePrice)
	public double getPayout(LinkedList<Pair<Integer,Double>> stockPath, double strikePrice)
	public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice)

>Solution
>>Simulator
	public Simulator(double sigma, double r, int T, double S0, double K, PayOut payOut, double confidenceLevel, double error)
	protected int estimateMax_iter()
	protected int computeLimit_iter(double Var)
	public double solve(long seed)
	public double solve()
	private double solve(LogNormalStockPath stockPath)


Solution
————————————————————————

	public void test_EuropeanCall(){
		double sigma=0.01;
		double r=0.0001;
		int T=252;
		double S0=152.35;
		double K=165;
		PayOut payOut = (PayOut) new EuropeanCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("European Call");
		System.out.println(optionPrice);
	}
	//European Call
	//6.133898575348116

	public void test_AsianCall(){
		double sigma=0.01;
		double r=0.0001;
		int T=252;
		double S0=152.35;
		double K=164;
		PayOut payOut = (PayOut) new AsianCallPayOut();
		double confidenceLevel = 0.96;
		double error = 0.1;
		long seed = 11086220;
		
		Simulator simulator = new Simulator(sigma, r, T, S0, K, payOut, confidenceLevel, error);
		double optionPrice = simulator.solve(seed);
		System.out.println("Asian Call");
		System.out.println(optionPrice);
	}
	//Asian Call
	//2.2205140825630343