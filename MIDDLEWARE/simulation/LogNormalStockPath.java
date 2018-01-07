package simulation;

import java.util.ArrayList;
import middleware.Option;


public class LogNormalStockPath implements StockPath{
	
	double _sigma;
	double _r;
	double _S0;
	int _T;
	AntitheticRandomVectorDecorator _generator;
	
	/**
	 * Construct a LogNormalStockPath Generator
	 * @param sigma : Daily volatility
	 * @param r : Daily Return
	 * @param S0 : Stock price at day 0
	 * @param T : Total Expiration
	 * @param seed : Seed for random variable generator
	 */
	public LogNormalStockPath(Option option){
		_sigma = option.getVolatility();
		_r = option.getInterestRate();
		_S0 = option.getSpotPrice();
		_T = option.getTime();
		_generator = new AntitheticRandomVectorDecorator(new NormalRandomVectorGenerator());
	}
	
	LogNormalStockPath(	double sigma, double r, double S0, int T, long seed){
		_sigma = sigma;
		_r = r;
		_S0 = S0;
		_T = T;
		_generator = new AntitheticRandomVectorDecorator(new NormalRandomVectorGenerator(seed));
	}
	
	/**
	 * Construct a LogNormalStockPath Generator
	 * @param sigma : Daily volatility
	 * @param r : Daily Return
	 * @param S0 : Stock price at day 0
	 * @param T : Total Expiration
	 */
	LogNormalStockPath(	double sigma, double r, double S0, int T){
		_sigma = sigma;
		_r = r;
		_S0 = S0;
		_T = T;
		_generator = new AntitheticRandomVectorDecorator(new NormalRandomVectorGenerator());
	}
	
	/**
	 * Get a SimulatedPath
	 */
	public ArrayList<Pair<Integer, Double>> getSimulatedPath () {
		double[] normalRandomVector = _generator.getNextVector(_T);
		ArrayList<Pair<Integer, Double>> stockPath = new ArrayList<Pair<Integer, Double>>(_T);
		double S = _S0;
		stockPath.add(new Pair<Integer, Double>(0,S));
		for(int i =1;i<=_T;i++){
			//System.out.println(normalRandomVector[i-1]);
			//System.out.println((_r-_sigma*_sigma/2)*1.+_sigma*normalRandomVector[i-1]);
			S = S*Math.exp((_r-_sigma*_sigma/2)*1.+_sigma*normalRandomVector[i-1]);
			stockPath.add(new Pair<Integer, Double>(i,S));
		}
		return stockPath;
	}

}
