package simulation;

public class Test_SimulationManager extends junit.framework.TestCase {
	static EuropeanOption opt = getOption();

	public static EuropeanOption getOption() {
		String optionName0 = "IBMEuropeanCall";
		float volatility = (float) (0.01f * Math.sqrt(252));
		float interestRate = 0.0001f * 252;
		float time = 1f;
		float spotPrice = 152.35f;
		float strikePrice0 = 165f;
		try{
			return new EuropeanOption(optionName0,volatility, interestRate, time, spotPrice, strikePrice0, "Call");
		}
		catch(Exception e){
			return null;
		}
		
	}
	public void test_SimulationManager(){
		SimulationManager sm = new SimulationManager( opt,.99, 0.005);
		System.out.println(sm.simulate());
	}
}
