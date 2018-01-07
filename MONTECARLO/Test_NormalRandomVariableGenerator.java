package simulation;

public class Test_NormalRandomVariableGenerator extends junit.framework.TestCase{

	
	public void test_NormalRandomVariableGenerator(){
		long seed = 11086220;
		double sum = 0;
		double count = 0;
		NormalRandomVariableGenerator RVG = new NormalRandomVariableGenerator(seed);
		for(count=1;count<=10000;count++){
			System.out.println(sum/count);
			sum += RVG.getNextNumber();
		}
		assertTrue(sum/count>-0.1 && sum/count<0.1);
	}
}
