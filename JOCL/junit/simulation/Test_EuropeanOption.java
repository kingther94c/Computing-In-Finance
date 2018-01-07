package simulation;

public class Test_EuropeanOption extends junit.framework.TestCase{

	public void test_EuropeanOption() throws Exception{
		String optionName0 = "IBMEuropeanCall";
		float volatility = 0.01f;
		float interestRate = 0.0001f;
		int time = 252;
		float spotPrice = 152.35f;
		float strikePrice0 = 165;
		EuropeanOption option0 = new EuropeanOption(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, "Call");
		System.out.println(option0);
		assertEquals(option0.toString(), "EuropeanOption [_optionName=IBMEuropeanCall, _volatility=0.01, _interestRate=1.0E-4, _time=252.0, _spotPrice=152.35, _strikePrice=165.0, _type=Call]");
		assertEquals(option0.getType(),"Call");		
		
	
	}
}
