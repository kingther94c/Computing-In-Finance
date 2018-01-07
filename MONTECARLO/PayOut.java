package simulation;

import java.util.List;
/**
 * 
 * @author Kingther
 *
 */
public interface PayOut{
	/**
	 * returns PayOut for stockPath and strikePrice
	 * @param stockPath : List<Pair<Integer,Double>>
	 * @param strikePrice : double
	 * @return : PayOut for stockPath and strikePrice
	 */
	   public double getPayout(List<Pair<Integer,Double>> stockPath, double strikePrice);
	   
}