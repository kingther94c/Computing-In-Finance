package cluster;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Test_KMeans2 extends junit.framework.TestCase {
	
	public static LinkedList<Point> simple_pointList() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<2;i++)
			for(int j=0;j<2;j++){
				double[] x = {i,j};
				pointSet.add(new Point(x));
			}
		return pointSet;
	}
	
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
	
	public static LinkedList<Point> lee_pointSet() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<100;i++)
			for(int j=0;j<100;j++){
				double[] x = {i,j};
				pointSet.add(new Point(x));
			}
		return pointSet;
	}
	
	public void test_KMeans() throws Exception{
		int input_numCluster = 20;
		LinkedList<Point> pointSet = my_pointSet();
		KMeans km = new KMeans(pointSet,input_numCluster);
		
		assertTrue(km instanceof KMeans);
	}


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
	
	public void test_computeCluster() throws Exception{
		LinkedList<Point> pointSet = simple_pointList();
		KMeans2 km = new KMeans2(pointSet,2);


		km.computeCluster();
		//assertTrue(km._clusterList.get(0).contains(km._centroidList.get(0).findNearestPoint(pointSet,2).get(0)));
		//assertTrue(km._clusterList.get(0).contains(km._centroidList.get(0).findNearestPoint(pointSet,2).get(1)));
	}
	
	public void test_computeCentroid() throws Exception{
		LinkedList<Point> pointSet = simple_pointList();
		KMeans2 km = new KMeans2(pointSet,2);
		km.computeCluster();
		
		km.computeCentroid();


		assertTrue(km._centroidList.get(0).equals(new Centroid(km._centroidList.get(0))));

		
	}

	public void test_solve() throws Exception{
		LinkedList<Point> pointSet = my_pointSet();
		int input_numCluster = 4;
		KMeans2 km = new KMeans2(pointSet,input_numCluster);
		km.solve(10000,0.00000001);
		//System.out.println(km._centroidList);
		//System.out.println(km._clusterList);
		for(int i=0;i<input_numCluster;i++){
			//System.out.println(km._clusterList.get(i).size());
		}
		assertTrue(km._clusterList.get(0).size()==400);
		assertTrue(km._clusterList.get(1).size()==400);
		assertTrue(km._clusterList.get(2).size()==400);
		assertTrue(km._clusterList.get(3).size()==400);

	}
	
	public void test_lee() throws Exception{
		
		LinkedList<Point> pointSet2 = lee_pointSet();
		int input_numCluster = 500;
		KMeans2 km = new KMeans2(pointSet2,input_numCluster);
		ArrayList<Centroid> answer = km.solve(10000,0.0000001);
		System.out.println(answer);
		System.out.println(km.getCost());
		assertTrue(true);
		
		//this one is too long to show here:
		//System.out.println(km.getClusterList());
	}
	
}
