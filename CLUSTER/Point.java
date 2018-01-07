package cluster;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class Point implements IDistance<Point>{
	static private double eps = 1e-4;
	protected double[] _x;
	protected int _dim;
	public void set_x(double[] x) {_x = x; _dim = x.length;}
	public double[] get_x() {return _x;}
	public int get_dim() {return _dim;}
	/**
	 * 
	 */
	public String toString() { 
		StringBuffer s = new StringBuffer("(");
		for(int i =0;i<_dim;i++){
			s.append(_x[i]);
			if(i<_dim-1)
				s.append(",");
		}
		s.append(")");
		return s.toString();
	}
	/**
	 * 
	 */
	Point(){
		_dim = -1;
	}
	/**
	 * 
	 * @param x
	 * @throws Exception 
	 */
	Point(double[] x) throws Exception{
		_dim = x.length;
		if(_dim==0)
			throw new Exception("Point cannot be 0-dimensional");
		_x = x;
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	public boolean isSameDim(Point p){
		if(p._dim!=_dim)
			return false;
		else
			return true;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Point))
			return false;
		Point P = (Point) o;
		if(P._dim!=_dim)
			return false;
		boolean equals = true;
		for(int i=0;i<_dim;i++){
			if(DoubleComparator.compare(P._x[i], _x[i], eps)!=0){
				equals = false;
				break;
			}
		}
		return equals;
	}
	/**
	 * 
	 */
	@Override
	public int hashCode(){
		int hash = 7;
		for(int i=0;i<_dim;i++)
		    hash = 71 * hash + (int)(Math.round(_x[i])*100);
		return hash;
	}
	/**
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public double computeDistance(Point p) throws Exception
	{
		if(!isSameDim(p))
			throw new Exception("computeDistance: Must be of the same dimensionality");
		double sum = 0;
		for(int i=0;i<_dim;i++){
			sum += (_x[i]-p._x[i])*(_x[i]-p._x[i]);
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * 
	 * @param centroidCol
	 * @return
	 * @throws Exception
	 */
	public Centroid findNearestCentroid(Collection<? extends Centroid> centroidCol) throws Exception{
		return new Centroid(findNearestPoint(centroidCol));
	}
	/**
	 * 
	 * @param centroidCol
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public LinkedList<Centroid> findNearestCentroid(Collection<? extends Centroid> centroidCol, int num) throws Exception{
		LinkedList<Centroid> centroidList = new LinkedList<Centroid>();
		LinkedList<Point> pointList = findNearestPoint(centroidCol,  num);
		for(int i=0;i<num;i++)
		{
			centroidList.add(new Centroid((pointList.pollFirst())));
		}
		return centroidList;
	}
	
	/**
	 * 
	 * @param pointCol
	 * @return
	 * @throws Exception
	 */
	public Point findNearestPoint(Collection<? extends Point> pointCol) throws Exception{
		Point ref_c = null;
		double recordDistance=Double.MAX_VALUE;
		double tmpDistance=0;
		for(Point c : pointCol){
			tmpDistance = computeDistance(c);
			if(DoubleComparator.compare(tmpDistance, recordDistance, eps)<0){
				recordDistance = tmpDistance;
				ref_c = c;
			}	
		}
		return ref_c;
	}
	
	/**
	 * 
	 * @param pointCol
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public LinkedList<Point> findNearestPoint(Collection<? extends Point> pointCol, int num) throws Exception{
		if(num>pointCol.size()||num<=0)
			throw new Exception("Invalid Parameter: num!");
		LinkedList<Point> nearestCentroids = new LinkedList<Point>();
		LinkedList<Pair<Point,Double>> pairList = new LinkedList<Pair<Point,Double>> ();
		for(Point p : pointCol)
			pairList.add(new Pair<Point,Double>(p,computeDistance(p)));
		Collections.sort(
				pairList,
				new Comparator<Pair<Point,Double>>(){
					@Override
					public int compare(Pair<Point,Double> o1, Pair<Point,Double> o2) {
						return o1.getRight().compareTo( o2.getRight() );
					}
				}
		);
		for(int i=0;i<num;i++){
			nearestCentroids.add(pairList.pollFirst().getLeft());
		}
		return nearestCentroids;
	}
}
