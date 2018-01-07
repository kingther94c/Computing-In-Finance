package cluster;
import java.util.LinkedList;
import java.util.Collection;

public class Cluster extends LinkedList<Point>{
	private static final long serialVersionUID = 1L;
	Cluster(){
		super();
	}
	/**
	 * 
	 * @param points
	 */
	Cluster(Collection<? extends Point> points){
		super(points);
	}
	/**
	 * 
	 */
	public boolean equals(Object o){
		if (!(o instanceof Cluster))
			return false;
		Cluster C = (Cluster) o;
		if(this.containsAll(C) && this.size()==C.size())
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
	/**
	 * 
	 * @return the Centroid of this Cluster
	 * @throws Exception
	 */
	public Centroid computeCentroid() throws Exception{
		return(new Centroid(this));
	}
	
}
