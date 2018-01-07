package simulation;

import java.util.List;

public class AsianPutPayOut implements PayOut {
	
	
	@Override
	/**
	 * an asian Put option will pay the maximum between zero and the strike price minus the average price during the total period.
	 */
	public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice) {
		double sumPrice = 0.;
		for (Pair<Integer,Double> pair : stockPath){
			sumPrice += pair.getRight();
		}
		return Math.max(0., strikePrice - sumPrice/stockPath.size() );
	}

}
