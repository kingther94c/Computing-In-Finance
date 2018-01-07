package cluster;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Fitness {
	private static double computeDeviationSum(Point Y,Collection<? extends Point> X) throws Exception{
		double sum = 0;
		for(Point x : X){
			sum += x.computeDistance(Y);
		}
		return sum;
	}
	private static double computeDeviationAvg(Point Y,Collection<? extends Point> X) throws Exception{
		return computeDeviationSum(Y,X)/X.size();
	}
	public static double computeFitness(Point Y,Collection<? extends Point> X) throws Exception{
		return computeDeviationSum(Y,X)/X.size();
	}
	public static double computeFitness(Pair<? extends Point, Collection<? extends Point>> pair) throws Exception{
		return computeDeviationAvg(pair.getLeft(),pair.getRight());
	} 
	public static double computeFitness(List<Pair<? extends Point, Collection<? extends Point>>> pairList) throws Exception{
		double sum = 0;
		int count =0;
		for(Pair<? extends Point, Collection<? extends Point>> pair : pairList){
			sum += computeDeviationSum(pair.getLeft(),pair.getRight());
			count += pair.getRight().size();
		}
		return sum/count;
	} 	
	public static double computeFitness(List<? extends Point> Ylist, Collection<? extends List<? extends Point>> Xlist) throws Exception{
		double sum = 0.;
		int count =0;
		if(Ylist.size()!=Xlist.size())
			throw new Exception("XlisPoint and YlisPoint musPoint be of the same size!");
		Iterator<? extends Point> itrY = Ylist.iterator();
		Iterator<? extends List<? extends Point>> itrX = Xlist.iterator();
		while(itrX.hasNext()){
			Point Y = itrY.next();
			Collection<? extends Point> X = itrX.next();
			count += X.size();
			sum += computeDeviationSum(Y,X);
		}
		return sum/count;
	} 	
}
