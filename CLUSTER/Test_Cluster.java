package cluster;
import java.util.LinkedList;

public class Test_Cluster extends junit.framework.TestCase{
	/**
	 * 
	 */
	public static double eps = 1e-6;
	public void test_Cluster() throws Exception{
		LinkedList<Point> Points = new LinkedList<Point>();
		double[] a = {1,2};
		double[] b = {3,4};
		Points.add(new Point(a));
		Points.add(new Point(b));
		
		Cluster C1 = new Cluster();
		Cluster C2 = new Cluster();
		assertTrue(C1.equals(C2));
		Cluster C3 = new Cluster(Points);
		//System.out.println(C3._centroid.toString());
		assertTrue(!C1.equals(C3));
		C2.addAll(Points);
		//System.out.println(C2._centroid);
		//System.out.println(C3._centroid);
		assertTrue(C3.equals(C2));
		
	}
	/**
	 * 
	 * @throws Exception
	 */
	public void test_computeCentroid() throws Exception{
		LinkedList<Point> Points = new LinkedList<Point>();
		double[] a = {1,2};
		double[] b = {3,4};
		Points.add(new Point(a));
		Points.add(new Point(b));
		Cluster C = new Cluster(Points);
		assertTrue(C.computeCentroid().toString().equals("(2.0,3.0)"));

	}

}
