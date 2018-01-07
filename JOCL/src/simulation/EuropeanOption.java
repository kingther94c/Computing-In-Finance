package simulation;


public class EuropeanOption {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(_discountFactor);
		result = prime * result + Float.floatToIntBits(_interestRate);
		result = prime * result + ((_optionName == null) ? 0 : _optionName.hashCode());
		result = prime * result + Float.floatToIntBits(_spotPrice);
		result = prime * result + Float.floatToIntBits(_strikePrice);
		result = prime * result + Float.floatToIntBits(_time);
		result = prime * result + _type;
		result = prime * result + Float.floatToIntBits(_volatility);
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
		EuropeanOption other = (EuropeanOption) obj;
		if (Float.floatToIntBits(_discountFactor) != Float.floatToIntBits(other._discountFactor))
			return false;
		if (Float.floatToIntBits(_interestRate) != Float.floatToIntBits(other._interestRate))
			return false;
		if (_optionName == null) {
			if (other._optionName != null)
				return false;
		} else if (!_optionName.equals(other._optionName))
			return false;
		if (Float.floatToIntBits(_spotPrice) != Float.floatToIntBits(other._spotPrice))
			return false;
		if (Float.floatToIntBits(_strikePrice) != Float.floatToIntBits(other._strikePrice))
			return false;
		if (Float.floatToIntBits(_time) != Float.floatToIntBits(other._time))
			return false;
		if (_type != other._type)
			return false;
		if (Float.floatToIntBits(_volatility) != Float.floatToIntBits(other._volatility))
			return false;
		return true;
	}


	private String _optionName;
	private float _volatility;
	private float _interestRate;
	private float _time;
	private float _spotPrice;
	private float _strikePrice;
	private int _type;
	private float _discountFactor;
	/**
	 * Constructs an European Option
	 * @param optionName
	 * @param volatility
	 * @param interestRate
	 * @param time
	 * @param spotPrice
	 * @param strikePrice
	 * @param type "Call" or "Put"
	 * @throws Exception
	 */
	public EuropeanOption(String optionName, float volatility, float interestRate, float time, float spotPrice, float strikePrice, String type) throws Exception{
		_optionName=optionName;
		_volatility  = volatility;
		_interestRate = interestRate;
		_time = time;
		_spotPrice = spotPrice;
		_strikePrice = strikePrice;
		_discountFactor = getDiscountFactor();
		if(type.toLowerCase().equals("call"))
			_type = 1;
		else if(type.toLowerCase().equals("put"))
			_type = -1;
		else
			throw new Exception("Invalid Type");
	 }
	
	public String getOptionName() {
		return _optionName;
	}


	public float getTime() {
		return _time;
	}

	public float getSpotPrice() {
		return _spotPrice;
	}

	public float getStrikePrice() {
		return _strikePrice;
	}

	public float getInterestRate() {
		return _interestRate;
	}

	public float getVolatility() {
		return _volatility;
	}

	public String getType() {
		if(_type==1)
			return "Call";
		else if(_type==-1)
			return "Put";
		else
			return "Unknown";
	}
	
	public int getTypeToInt() {
		return _type;
	}
	
	public float getDiscountFactor(){
		return (float) Math.exp(-_interestRate*_time);
	}
	
	public float computePayout(float finalPrice){
		return Math.max(_type * (finalPrice - _strikePrice), 0) * _discountFactor;
	}
	

	@Override
	public String toString() {
		return "EuropeanOption [_optionName=" + _optionName + ", _volatility=" + _volatility + ", _interestRate="
				+ _interestRate + ", _time=" + _time + ", _spotPrice=" + _spotPrice + ", _strikePrice="
				+ _strikePrice + ", _type=" + getType() + "]";
	}


		

		 

}
