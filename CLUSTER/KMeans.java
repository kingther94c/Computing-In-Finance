package cluster;

import java.util.Iterator;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class KMeans {
	protected Collection<? extends Point> _pointList;
	protected int _numCluster;
	protected ArrayList<Cluster> _clusterList;
	protected ArrayList<Centroid> _centroidList;
	protected double _cost = Double.MAX_VALUE;
	protected double _costDelta = Double.MAX_VALUE;
	
	public Collection<? extends Point> getPointList(){return _pointList;}
	public ArrayList<Cluster> getClusterList(){return _clusterList;}
	public ArrayList<Centroid> getCentroidList(){return _centroidList;}	
	public int getNumCluster(){return _numCluster;}
	/**
	 * Constructor Method
	 * @param pointList : a collections of Points that need to be clustered.
	 * @param numCluster : the number of clusters that you want to get.
	 * @throws Exception
	 */
	KMeans(Collection<? extends Point> pointList, int numCluster) throws Exception{
		this._pointList = new ArrayList<Point>(pointList);
		Collections.shuffle((List<?  extends Point>) this._pointList);
		
		this._numCluster = numCluster;
		int size = this._pointList.size();
		if(numCluster<=0 || size<numCluster)
			throw new Exception("Invalid Parameter: numCluster");
		this._clusterList = new ArrayList<Cluster>(this._numCluster);
		for(int i =0;i<_numCluster;i++){
			this._clusterList.add(new Cluster());
		}
		this._centroidList = new ArrayList<Centroid>(this._numCluster);

		this.initialization();
	}
/**
 * Initialize the _centroidList
 * @throws Exception
 */
	protected void initialization() throws Exception{
		int count = 0;
		Iterator<? extends Point> itr = _pointList.iterator();
		while(itr.hasNext()&&count<_numCluster){
			_centroidList.add(new Centroid(itr.next()));
			count++;
		}
	}
/**
* Computes the new list of Centroids based on present _clusterList 
 * @throws Exception
 */
	protected void computeCentroid() throws Exception{
		for(int i=0;i<_numCluster;i++){
			_centroidList.set(i, new Centroid(_clusterList.get(i)));
		}
	}
/**
 * Computes the new list of Clusters based on present _centroidList 
 * @throws Exception
 */
	protected void computeCluster() throws Exception{
		for(Cluster cluster:_clusterList){
			cluster.clear();
		}
		for(Point point:_pointList){
			int record_num = -1;
			double record_distance = Double.MAX_VALUE;
			double tmp=-0;
			for(int i=0;i<this._numCluster;i++){
				tmp = point.computeDistance(_centroidList.get(i));
				if(tmp<record_distance){
					record_num = i;
					record_distance = tmp;
				}
			}
			//System.out.println(record_num);
			_clusterList.get(record_num).add(point);
		}
		double tmp = Fitness.computeFitness(_centroidList, _clusterList);
		_costDelta = Math.abs(this._cost-tmp);
		this._cost = tmp;
	}
	/**
	 * To measure whether the solution is good enough. The smaller the value is, the better the solution is.
	 * @return The cost value of the current solution. The cost value is defined as the average distance between the point and the centroid of the cluster that the point belongs to.
	 */
	public double getCost() {return _cost;}
	/**
	 * a method used directly to solve the problem
	 * @param itr_limit : the maximal times of iteration for this algorithm
	 * @param eps : the level of tolerance of convergence. When the relative difference of cost is less than the eps, the algorithm can be considered as convergence and will return the result.
	 * @return an ArrayList<Centroid> which is a list of the Centroids of the final clusters.
	 * @throws Exception
	 */
	public ArrayList<Centroid> solve(int itr_limit, double eps) throws Exception{
		for(int i=0;i<itr_limit;i++){
			this.computeCluster();
			this.computeCentroid();
			if(_costDelta/_cost<eps)
				break;
		}
		return _centroidList;
	}
	/**
	 * a method used directly to solve the problem while printing the cost every steps
	 * @param itr_limit : the maximal times of iteration for this algorithm
	 * @param eps : the level of tolerance of convergence. When the relative difference of cost is less than the eps, the algorithm can be considered as convergence and will return the result.
	 * @return an ArrayList<Centroid> which is a list of the Centroids of the final clusters.
	 * @throws Exception
	 */
	public ArrayList<Centroid> solve_showcost(int itr_limit, double eps) throws Exception{
		for(int i=0;i<itr_limit;i++){
			this.computeCluster();
			this.computeCentroid();
			System.out.println(_cost);
			if(_costDelta/_cost<eps)
				break;
		}
		return _centroidList;
	}


}
