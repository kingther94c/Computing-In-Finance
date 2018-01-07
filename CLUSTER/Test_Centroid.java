package cluster;

import java.util.LinkedList;

public class Test_Centroid extends junit.framework.TestCase{
	public void test_Centroid() throws Exception{
		double[] a = {1,1,0};
		Point A = new Point(a);
		Centroid C = new Centroid(A);
		Centroid C2 = new Centroid(a);	
		LinkedList<Point> pointList = new LinkedList<Point>();
		pointList.add(A);
		assertTrue(C.equals(C2));
		assertTrue(C.equals(new Centroid(pointList)));
		for(int i=-10;i<1000;i++){
			double[] x = {1,1,i};
			double[] y = {1,1,-i};
			pointList.add(new Point(x));
			pointList.add(new Point(y));
		}
		C2 = new Centroid(pointList);
		//System.out.println(C2);
		assertTrue(C.equals(C2));
	}

	
}
