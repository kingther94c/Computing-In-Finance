package simulation;

import java.util.ArrayList;

public class Test_LogNormalStockPath extends junit.framework.TestCase{
	public void test_LogNormalStockPath1(){
		System.out.println("test_LogNormalStockPath1");
		long seed = 11086220l;
		double sigma = 0.;
		double r = 0;
		double S0 = 1.;
		int T = 10;
		LogNormalStockPath stockPath = new LogNormalStockPath(sigma, r, S0, T,seed);
		ArrayList<Pair<Integer, Double>> stockPathList = stockPath.getSimulatedPath();
		
		System.out.println(stockPathList);
		assertEquals(stockPathList.get(T).getRight(),1.0,0.001);
	}
	public void test_LogNormalStockPath2(){
		System.out.println("test_LogNormalStockPath1");
		long seed = 11086220l;
		double sigma = 1.;
		double r = 0;
		double S0 = 1.;
		int T = 1;
		LogNormalStockPath stockPath = new LogNormalStockPath(sigma, r, S0, T,seed);
		NormalRandomVariableGenerator RVG = new NormalRandomVariableGenerator();
		double Z0 = RVG.getNextNumber();
		//System.out.println(Z0);
		double S1 = S0*Math.exp(-sigma*sigma/2.+Z0);
		ArrayList<Pair<Integer, Double>> stockPathList = stockPath.getSimulatedPath();
		
		System.out.println(stockPathList);
		System.out.println(Math.exp(-sigma*sigma/2.+Z0));
		assertEquals(stockPathList.get(1).getRight(),S1,0.001);
	}
}
