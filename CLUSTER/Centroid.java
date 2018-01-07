package cluster;

import java.util.Collection;

public class Centroid extends Point{
	/**
	 * Construct a Centroid based on a n-dimensional double array
	 * @param x : a n-dimensional double array
	 * @throws Exception
	 */
	Centroid(double[] x) throws Exception {
		super(x);
	}

	/**
	 * Construct a Centroid based on a Point
	 * @param p
	 * @throws Exception
	 */
	Centroid(Point p) throws Exception{
		super(p._x);
	}
	/**
	 * Construct a Centroid based on a Collection<? extends Point>
	 * @param pointCol : the Collection of Point that you want to get the centroid from
	 * @throws Exception :"The Point Collection cannot be empty!" or "Must be of the same dimensionality!"
	 */
	Centroid(Collection<? extends Point> pointCol) throws Exception{
		if(pointCol.isEmpty()){
			throw new Exception("The Point Collection cannot be empty!");
		}
		_dim = -1;
		for(Point p:pointCol){
			if(_dim != p.get_dim()&&_dim != -1)
				throw new Exception("Must be of the same dimensionality!");
			_dim = p.get_dim();
		}
		double[] sum_x = new double[_dim];
		int _count = pointCol.size();
		for(Point p:pointCol)
			for(int i=0;i<_dim;i++){
				sum_x[i] += p._x[i];
			}
		_x = new double[_dim];
		for(int i=0;i<_dim;i++){
			_x[i] = sum_x[i]/_count;
		}
	}

	/**
	 * 
	 */
	public boolean equals(Object o){
		if(!(o instanceof Point))
			return false;
		Point C = (Point) o;
		if(super.equals(C))
			return true;
		else
			return false;
	}
	/**
	 * 
	 */
	public int hashCode(){
		return super.hashCode();
	}

}
