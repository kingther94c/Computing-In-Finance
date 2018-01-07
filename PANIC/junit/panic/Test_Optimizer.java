package panic;


public class Test_Optimizer extends junit.framework.TestCase{


	public void test_Optimizer() throws Exception {
		int parameterStart =  20;
		int parameterStep = 5;
		int parameterEnd = 365;
		QuotesReader reader =  new QuotesReader("/Users/kingther/Documents/workspace/Panic/panicData.csv");
		Optimizer op = new Optimizer(reader, parameterStart,parameterStep,parameterEnd);
		op.optimize();
		
	}
	
	public void test_Optimizer2() throws Exception {
		int parameterStart =  20;
		int parameterStep = -1;
		int parameterEnd = 365;
		QuotesReader reader =  new QuotesReader("/Users/kingther/Documents/workspace/Panic/panicData.csv");
		boolean flag = false;
		try{Optimizer op = new Optimizer(reader, parameterStart,parameterStep,parameterEnd);}
		catch(Exception e){
			flag = true;
		}
		assertTrue(flag);
	}
	
	
	public void test_Optimizer3() throws Exception {
		int parameterStart =  365;
		int parameterStep = 5;
		int parameterEnd = 20;
		QuotesReader reader =  new QuotesReader("/Users/kingther/Documents/workspace/Panic/panicData.csv");
		boolean flag = false;
		try{Optimizer op = new Optimizer(reader, parameterStart,parameterStep,parameterEnd);}
		catch(Exception e){
			flag = true;
		}
		assertTrue(flag);
		
	}
	
	public void test_Optimizer4() throws Exception {
		int parameterStart =  20;
		int parameterStep = 0;
		int parameterEnd = 20;
		QuotesReader reader =  new QuotesReader("/Users/kingther/Documents/workspace/Panic/panicData.csv");
		Optimizer op = new Optimizer(reader, parameterStart,parameterStep,parameterEnd);
		assertEquals(op.optimize(), 20);
	}

}
