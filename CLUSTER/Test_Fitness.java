package cluster;

import java.util.LinkedList;

public class Test_Fitness extends junit.framework.TestCase {
	public static LinkedList<Point> simple_pointList() throws Exception{
		LinkedList<Point> pointSet = new LinkedList<Point>();
		for(int i=0;i<4;i++)
			for(int j=0;j<5;j++){
				double[] x = {i,j};
				pointSet.add(new Point(x));
			}
		return pointSet;
	}
	
	public static void test_computeFitness() throws Exception{
		LinkedList<Point> list = simple_pointList();
		System.out.println(Fitness.computeFitness(new Centroid(list),list));

	}

}
