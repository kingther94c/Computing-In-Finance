package simulation;

public class Test_UniformRandomVariableGenerator extends junit.framework.TestCase {

	
	public void test_NormalRandomVariableGenerator(){
		long seed = 11086220;
		double sum = 0;
		double count = 0;

		UniformRandomVariableGenerator RVG = new UniformRandomVariableGenerator(seed);
		for(count=1;count<=100;count++){
			sum += RVG.getNextNumber();
			System.out.println(sum/count);

		}
		assertTrue(sum/count<0.55&&sum/count>0.45);
	}
	
}
