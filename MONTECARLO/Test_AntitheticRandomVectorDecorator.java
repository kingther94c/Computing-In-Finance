package simulation;

import org.junit.Assert;

public class Test_AntitheticRandomVectorDecorator extends junit.framework.TestCase {
	public void test_getNextVector(){
	
		long seed = 11086220l;
		NormalRandomVectorGenerator RVG = new NormalRandomVectorGenerator(seed);
		AntitheticRandomVectorDecorator ARVG = new AntitheticRandomVectorDecorator(RVG);
		double[] v1 = ARVG.getNextVector(10);
		double[] v2 = ARVG.getNextVector(10);

		System.out.println(v1);
		System.out.println(v2);
		for(int i =0;i<v1.length;i++){
			System.out.println(v1[i]);
			System.out.println(v2[i]);
		}
		Assert.assertArrayEquals(v1, ARVG.AntitheticVector(v2), 0.001);
		
	}
	
	public void test_AntitheticVector(){
		long seed = 11086220l;
		NormalRandomVectorGenerator RVG = new NormalRandomVectorGenerator(seed);
		AntitheticRandomVectorDecorator ARVG = new AntitheticRandomVectorDecorator(RVG);
		double[] v1 = {1.,2.,34.};
		double[] v2 = {-1.,-2.,-34.};
		Assert.assertArrayEquals(ARVG.AntitheticVector(v1), v2, 0.001);

	}
}
