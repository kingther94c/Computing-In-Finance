package middleware;



public class Test_SimulatorManager extends junit.framework.TestCase{
    private double confidenceLevel = 0.96;
    private double errorTolerance = 0.1;
	public void test_SimulatorManager(){

		SimulatorManager sm = new SimulatorManager(confidenceLevel, errorTolerance);
		for (int i =0 ;i<1000;i++){
			sm.update(i%5);
			System.out.println(sm.getSummary());
			System.out.println(sm.isFinished());
			if(i >= 843)
				assertTrue(sm.isFinished());
			else 
				assertTrue(!sm.isFinished());
		}
		
		
	}
}
