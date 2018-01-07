package middleware;

import java.util.HashMap;

import simulation.AsianCallPayOut;
import simulation.EuropeanCallPayOut;
import simulation.PayOut;

public class Test_Option extends junit.framework.TestCase{

	public void test_Option(){
		String optionName0 = "IBMEuropeanCall";
		String optionName1 = "IBMAsianCall";
		double volatility = 0.01;
		double interestRate = 0.0001;
		int time = 252;
		double spotPrice = 152.35;
		double strikePrice0 = 165;
		double strikePrice1=164;
		PayOut payOut0 = (PayOut) new EuropeanCallPayOut();
		PayOut payOut1 = (PayOut) new AsianCallPayOut();
		Option option0 = new Option(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, payOut0);
		Option option1 = new Option(optionName1,volatility, interestRate, time, spotPrice, strikePrice1, payOut1);
		System.out.println(option1);
		assertEquals(option1.toString(), "Option [_optionName=IBMAsianCall, _volatility=0.01, _interestRate=1.0E-4, _time=252, _spotPrice=152.35, _strikePrice=164.0, _type=AsianCall]");
		assertTrue(!option0.equals(option1));
		assertTrue(option0.getPayOut() instanceof EuropeanCallPayOut);
		assertEquals(option0.getType(),"EuropeanCall");		
		
	
	}
}
