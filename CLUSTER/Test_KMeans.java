package cluster;

import java.util.LinkedList;
import java.util.Random;

public class Test_KMeans extends junit.framework.TestCase {
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static LinkedList<Point> simple_pointList() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<2;i++)
			for(int j=0;j<2;j++){
				double[] x = {i,j};
				pointSet.add(new Point(x));
			}
		return pointSet;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static LinkedList<Point> my_pointSet() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++){
				for(int k=0;k<100;k++){
					Random r = new Random();
					double[] x = {10*i+0.05*r.nextDouble(),10*j+0.05*r.nextDouble()};
					pointSet.add(new Point(x));
				}
			}
		return pointSet;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static LinkedList<Point> lee_pointSet() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<100;i++)
			for(int j=0;j<100;j++){
				double[] x = {i,j};
				pointSet.add(new Point(x));
			}
		return pointSet;
	}
	/**
	 * 
	 * @throws Exception
	 */
	public void test_KMeans() throws Exception{
		int input_numCluster = 20;
		LinkedList<Point> pointSet = my_pointSet();
		KMeans km = new KMeans(pointSet,input_numCluster);
		
		assertTrue(km instanceof KMeans);
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void test_initialization() throws Exception{
		int input_numCluster = 2;
		LinkedList<Point> pointSet = simple_pointList();
		KMeans km = new KMeans(pointSet,input_numCluster);
		//System.out.println(km._centroidList);
		//System.out.println(km._centroidList.size());
		assertTrue(km._centroidList.size()==km._numCluster);
		boolean repeat = false;
		for(int i=0;i<km._centroidList.size();i++)
			for(int j=0;j<i;j++){
				if(km._centroidList.get(i).equals(km._centroidList.get(j)))
				{
					repeat = true;
					break;
				}
			}
		assertTrue(repeat == false);
		
		
	}
	/**
	 * 
	 * @throws Exception
	 */
	public void test_computeCluster() throws Exception{
		LinkedList<Point> pointSet = simple_pointList();
		KMeans km = new KMeans(pointSet,2);
		km.computeCluster();
		assertTrue(km._centroidList.get(0).equals(km._clusterList.get(0).getFirst()));
		assertTrue(km._centroidList.get(1).equals(km._clusterList.get(1).getFirst()));
	}
	/**
	 * 
	 * @throws Exception
	 */
//	public void test_computeCentroid() throws Exception{
//		LinkedList<Point> pointSet = simple_pointList();
//		KMeans km = new KMeans(pointSet,2);
//		km.computeCluster();
//		km.computeCentroid();
//		double[] a = {0.5,0};
//		double[] b = {0.5,1};
//		Centroid A = new Centroid(a);
//		Centroid B = new Centroid(b);
//		assertTrue(km._centroidList.get(0).equals(A));
//		assertTrue(km._centroidList.get(1).equals(B));	
//		
//	}
	/**
	 * 
	 * @throws Exception
	 */
	public void test_solve() throws Exception{
		LinkedList<Point> pointSet = my_pointSet();
		int input_numCluster = 4;
		KMeans km = new KMeans(pointSet,input_numCluster);
		km.solve(10000,0.00000001);
//		System.out.println(km._centroidList);
//		System.out.println(km._clusterList);
//		for(int i=0;i<input_numCluster;i++){
//			System.out.println(km._clusterList.get(i).size());
//		}
		LinkedList<Point> pointSet2 = lee_pointSet();
		input_numCluster = 500;
		km = new KMeans(pointSet2,input_numCluster);
		km.solve(10000,0.000001);
//		for(int i =0;i<input_numCluster;i++){
//			System.out.println(km._clusterList.get(i).size());
//			System.out.println(km._centroidList.get(i));
//			System.out.println(km._clusterList.get(i));
//		}

		System.out.println(km.getCost());		

		
	}
	
}
