package cluster;

import java.util.Collection;
import java.util.LinkedList;

public class KMeans2 extends KMeans{

	int _minNumPerCluster;
	/**
	 * Constructor Method
	 * @param pointList : a collections of Points that need to be clustered.
	 * @param numCluster : the number of clusters that you want to get.
	 * @throws Exception
	 */
	KMeans2(Collection<? extends Point> pointList, int numCluster) throws Exception {
		super(pointList, numCluster);
		_minNumPerCluster = _pointList.size()/_numCluster;
	}
	/**
	 * Computes the new list of Centroids based on present _clusterList 
	 * @throws Exception
	 */
	@Override
	protected void computeCentroid() throws Exception{
		for(int i=0;i<_numCluster;i++){
			_centroidList.set(i, new Centroid(_clusterList.get(i)));
		}
	}
	/**
	 * Computes the new list of Clusters based on present _centroidList 
	 * @throws Exception
	 */
	@Override
	protected void computeCluster() throws Exception{
		for(Cluster cluster:_clusterList){
			cluster.clear();
		}
		LinkedList<Point> pointListCopy = new LinkedList<Point>(_pointList);
		for(int i =0;i<_numCluster;i++){
			LinkedList<Point> nearestPointList = _centroidList.get(i).findNearestPoint(pointListCopy,_minNumPerCluster);
			_clusterList.get(i).addAll(nearestPointList);
			pointListCopy.removeAll(nearestPointList);
		}
		for(int i =0;i<_numCluster&&!pointListCopy.isEmpty();i++){
			Point point = _centroidList.get(i).findNearestPoint(pointListCopy);
			_clusterList.get(i).add(point);
			pointListCopy.remove(point);
		}

		double tmp = Fitness.computeFitness(_centroidList, _clusterList);
		_costDelta = Math.abs(this._cost-tmp);
		this._cost = tmp;
	}
	



}
