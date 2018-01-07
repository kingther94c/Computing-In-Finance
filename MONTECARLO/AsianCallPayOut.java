package simulation;

import java.util.List;

public class AsianCallPayOut implements PayOut {
	
	
	@Override
	/**
	 * an asian call option will pay the maximum between zero and the average price during the total period minus the strike price.
	 */
	public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice) {
		double sumPrice = 0.;
		for (Pair<Integer,Double> pair : stockPath){
			sumPrice += pair.getRight();
		}
		return Math.max(0., sumPrice/stockPath.size() - strikePrice);
	}

}
