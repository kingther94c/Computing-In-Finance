package middleware;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.MapMessage;

import simulation.*;

public class Option {
	private String _optionName;
	private double _volatility;
	private double _interestRate;
	private int _time;
	private double _spotPrice;
	private double _strikePrice;
	private PayOut _payOut;
	static HashMap<String,PayOut> _payOutMap = payOutMap();
	
	private static HashMap<String, PayOut> payOutMap(){
		HashMap<String,PayOut> payOutMap = new HashMap<String,PayOut>();
		payOutMap.put("AsianCall", new AsianCallPayOut());
		payOutMap.put("AsianPut", new AsianPutPayOut());
		payOutMap.put("EuropeanCall", new EuropeanCallPayOut());
		payOutMap.put("EuropeanPut", new EuropeanPutPayOut());
		return payOutMap;
	}
	
	public Option(String optionName, double volatility, double interestRate, int time, double spotPrice, double strikePrice, String type){
		_optionName=optionName;
		_volatility  = volatility;
		_interestRate = interestRate;
		_time = time;
		_spotPrice = spotPrice;
		_strikePrice = strikePrice;
		_payOut = _payOutMap.get(type);
	 }
	
	public Option(String optionName, double volatility, double interestRate, int time, double spotPrice, double strikePrice, PayOut payOut){
		_optionName=optionName;
		_volatility  = volatility;
		_interestRate = interestRate;
		_time = time;
		_spotPrice = spotPrice;
		_strikePrice = strikePrice;
		_payOut = payOut;
	 }
	
	public String getOptionName() {
		return _optionName;
	}


	public int getTime() {
		return _time;
	}

	public double getSpotPrice() {
		return _spotPrice;
	}

	public double getStrikePrice() {
		return _strikePrice;
	}

	public double getInterestRate() {
		return _interestRate;
	}

	public double getVolatility() {
		return _volatility;
	}

	public String getType() {
		return _payOut.getType();
	}
	
	public PayOut getPayOut() {
		return _payOut;
	}
	public double getDiscountFactor(){
		return Math.exp(-_interestRate*_time);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(_interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_spotPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(_strikePrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + _time;
		result = prime * result + ((_payOut == null) ? 0 : _payOut.hashCode());
		temp = Double.doubleToLongBits(_volatility);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (Double.doubleToLongBits(_interestRate) != Double.doubleToLongBits(other._interestRate))
			return false;
		if (_optionName != other._optionName)
			return false;
		if (Double.doubleToLongBits(_spotPrice) != Double.doubleToLongBits(other._spotPrice))
			return false;
		if (Double.doubleToLongBits(_strikePrice) != Double.doubleToLongBits(other._strikePrice))
			return false;
		if (_time != other._time)
			return false;
		if (_payOut == null) {
			if (other._payOut != null)
				return false;
		} else if (!_payOut.equals(other._payOut))
			return false;
		if (Double.doubleToLongBits(_volatility) != Double.doubleToLongBits(other._volatility))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Option [_optionName=" + _optionName + ", _volatility=" + _volatility + ", _interestRate="
				+ _interestRate + ", _time=" + _time + ", _spotPrice=" + _spotPrice + ", _strikePrice="
				+ _strikePrice + ", _type=" + _payOut.getType() + "]";
	}
	
	public static Option convertToOption(MapMessage message) throws JMSException {
		return new Option(message.getString("optionName"), 
				message.getDouble("volatility"), 
				message.getDouble("interestRate"), 
				message.getInt("time"), 
				message.getDouble("spotPrice" ), 
				message.getDouble("strikePrice" ),
				message.getString("type")
				);
	}
	public double computePayOut(StockPath stockPath){
		return _payOut.getPayout(stockPath.getSimulatedPath(), _strikePrice )*getDiscountFactor();
	}
	
	

		

		 

}
