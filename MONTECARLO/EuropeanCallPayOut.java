package simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EuropeanCallPayOut implements PayOut{

	/**
	 * Compute the payOut for European Call Option
	 * @param stockPath
	 * @param strikePrice
	 * @return
	 */
	public double getPayout(ArrayList<Pair<Integer,Double>> stockPath, double strikePrice) {
		return Math.max(0., stockPath.get(stockPath.size()-1).getRight() - strikePrice);
	}
	/**
	 * Compute the payOut for European Call Option
	 * @param stockPath
	 * @param strikePrice
	 * @return
	 */
	public double getPayout(LinkedList<Pair<Integer,Double>> stockPath, double strikePrice) {
		return Math.max(0., stockPath.getLast().getRight() - strikePrice);
	}
	/**
	 * Compute the payOut for European Call Option
	 * @param stockPath
	 * @param strikePrice
	 * @return
	 */
	public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice) {
		return Math.max(0., stockPath.get(stockPath.size()-1).getRight() - strikePrice);
	}
}
