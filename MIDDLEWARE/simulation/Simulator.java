package simulation;

import middleware.Option;

public class Simulator {
	double _sigma;
	double _r;
	int _T;
	double _S0;
	double _K;
	PayOut _payOut;
	double _discountFactor;
	double _confidenceLevel;
	double _error;
	
	
	
	/**
	 * Constructs a Simulator
	 * @param sigma  Daily volatility
	 * @param r  Daily Return
	 * @param T  Total Expiration
	 * @param S0 Stock price at day 0
	 * @param K Strike price
	 * @param payOut type of option
	 * @param confidenceLevel confidence Level
	 * @param error error tolerance
	 */
	public Simulator(Option option, double confidenceLevel, double error){
		_sigma = option.getVolatility();
		_r = option.getInterestRate();
		_T = option.getTime();
		_S0 = option.getSpotPrice();
		_K = option.getStrikePrice();
		_payOut = option.getPayOut();
		_discountFactor = Math.exp(-_r*_T);
		_confidenceLevel = confidenceLevel;
		_error = error;
	}
	/**
	 * Constructs a Simulator
	 * @param sigma  Daily volatility
	 * @param r  Daily Return
	 * @param T  Total Expiration
	 * @param S0 Stock price at day 0
	 * @param K Strike price
	 * @param payOut type of option
	 * @param confidenceLevel confidence Level
	 * @param error error tolerance
	 */
	public Simulator(double sigma, double r, int T, double S0, double K, PayOut payOut, double confidenceLevel, double error){
		_sigma = sigma;
		_r = r;
		_T = T;
		_S0 = S0;
		_K = K;
		_payOut = payOut;
		_discountFactor = Math.exp(-r*T);
		_confidenceLevel = confidenceLevel;
		_error = error;
	}
	

	/**
	 * estimates the Max_iter before computing
	 * @param confidenceLevel
	 * @param error
	 * @return
	 */
	protected int estimateMax_iter(){
		double lambda = _sigma*Math.sqrt(_T);
		double term = _S0*Math.exp(-_sigma*_sigma/2.*_T);
		double Var = term*term*(Math.exp(2*lambda*lambda)-Math.exp(lambda*lambda));
		double sqrtN = StandardNormalDistributionStat.inverseCumulativeProbability((_confidenceLevel+1.)/2.)*Math.sqrt(Var)/(_error+1e-6);
		return (int) Math.ceil(sqrtN*sqrtN);
	}
	/**
	 * computes the Max_iter with sample variance
	 * @param Var sample variance
	 * @return
	 */
	protected int computeLimit_iter(double Var){

		double sqrtN = StandardNormalDistributionStat.inverseCumulativeProbability((_confidenceLevel+1.)/2.)*Math.sqrt(Var)/(_error+1e-6);
		return (int) Math.ceil(sqrtN*sqrtN);
	}
	
	/**
	 * 
	 * @param seed
	 * @return option price
	 */
	public double solve(long seed) {
		LogNormalStockPath stockPath = new LogNormalStockPath(_sigma, _r, _S0, _T,seed);
		return solve(stockPath );
	}
	
	
	/**
	 * solves the price	
	 * @return option price
	 */
	public double solve(){
		LogNormalStockPath stockPath = new LogNormalStockPath(_sigma, _r, _S0, _T);
		return solve(stockPath );
	}
	/**
	 * solves the price	for stockPath
	 * @param stockPath
	 * @return option price
	 */
	private double solve(LogNormalStockPath stockPath ){
		//double sum = 0.;
		//double sum_sqr = 0.;
		double optionValue = 0.;
		int estmax_iter = estimateMax_iter();
//		System.out.println(estmax_iter);
		int num_iter = 0;
		int est_iter = Math.max(Math.min(10000, estmax_iter), 100);
		StatsRecorder statsRecorder = new StatsRecorder();
		double sampleVariance = 1.;
		//This calculations needs to be done where the stopping criteria is with probability confidenceLevel the estimation error is less than error.
		while(num_iter<est_iter){
//			System.out.println("num_iter");
//			System.out.println(num_iter);
//			System.out.println("sampleVariance");
//			System.out.println(sampleVariance);
//			System.out.println("optionValue");
//			System.out.println(sum/num_iter);
			for(int i=num_iter;i<est_iter;i++){
				optionValue = _payOut.getPayout(stockPath.getSimulatedPath(), _K)*_discountFactor;
//				if(i%1000==0){
//					System.out.println(optionValue);
//				}
				statsRecorder.add(optionValue);
				//sum += optionValue;
				//sum_sqr += optionValue*optionValue;
			}
			num_iter = est_iter;
			sampleVariance = statsRecorder.getSampleVariance();
			est_iter = computeLimit_iter(sampleVariance);

		}
//		System.out.println(sum/num_iter);
		return statsRecorder.getMean();
	}
	


}