package simulation;

public class Test_NormalRandomVectorGenerator extends junit.framework.TestCase{
	public void test_NormalRandomVectorGenerator(){
		long seed = 11086220l;
		NormalRandomVectorGenerator NRVG = new NormalRandomVectorGenerator(seed);
		double[] vector = NRVG.getNextVector(2);
		double a = vector[0];
		System.out.println(vector[0]);
		
		NRVG = new NormalRandomVectorGenerator(seed);
		vector = NRVG.getNextVector();
		System.out.println(vector[0]);
		assertEquals(a,vector[0],0.01);
		
	}
	
	public void test_NormalRandomVector(){
		long seed = 11086220l;
		NormalRandomVectorGenerator NRVG = new NormalRandomVectorGenerator(seed);
		double[] vector = NRVG.getNextVector(10000);
		double sum = 0.;
		for (int i=0;i<10000;i++){
			sum += vector[i];
			if(i%100==0){
				System.out.println(sum/i);
			}
			
		}
		assertEquals(sum/10000,0,0.01);
	
		
	}
}
