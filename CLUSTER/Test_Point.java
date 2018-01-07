package cluster;

import java.util.LinkedList;

public class Test_Point  extends junit.framework.TestCase{
	static private double eps = 1e-4;
	public void test_Point() throws Exception{
		double[] x ={1,2};
		Point p = new Point(x);
		Point p2 = new Point(x);
		assertTrue(p.equals(p2));
	}
	
	public void test_computeDistance() throws Exception{
		double[] a = {1,2};
		double[] b = {4,6};		
		Point A = new Point(a);
		Point B = new Point(b);
		//System.out.println(A.computeDistance(B));
		double d = A.computeDistance(B);
		assertTrue(Math.abs(d-5)<eps);
	}
	
	public void test_toString() throws Exception {
		double[] a = {1,2};
		Point A = new Point(a);
		assertTrue(A.toString().equals("(1.0,2.0)"));	
	}
	public void test_isSameDim() throws Exception{
		//double[] a = {};
		double[] a = {2};
		double[] b = {1,2,4};
		double[] c = {1,45,4};
		Point A = new Point(a);
		Point B = new Point(b);
		Point C = new Point(c);
		assertTrue(!A.isSameDim(B));
		assertTrue(B.isSameDim(C));
	}
	public void test_findNearestPoint() throws Exception{
		double[] a = {1,2,4};
		double[] b = {1,4,1};
		double[] c = {1,1,1};
		Point A = new Point(a);
		Point B = new Point(b);
		Point C = new Point(c);
		
		LinkedList<Point> pointList = new LinkedList<Point>();
		pointList.add(A);
		pointList.add(B);
		//System.out.println(C.findNearestPoint(pointList));
		assertTrue(B.equals(C.findNearestPoint(pointList)));
		for(int i=7;i<1000;i++){
			double[] x = {1,1,i};
			pointList.add(new Point(x));
		}
		assertTrue(B.equals(C.findNearestPoint(pointList)));
		LinkedList<Point> pointList2 = new LinkedList<Point>();
		pointList2.add(B);
		pointList2.add(A);
		assertTrue(pointList2.equals(C.findNearestPoint(pointList,2)));
		//System.out.println(C.findNearestPoint(pointList,12));
		
	}
	
	
}
