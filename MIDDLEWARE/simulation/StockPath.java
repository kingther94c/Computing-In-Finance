package simulation;

import java.util.List;

public interface StockPath{
	List<Pair<Integer,Double>> getSimulatedPath();
}