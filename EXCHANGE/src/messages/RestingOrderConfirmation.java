package messages;

import orderSpecs.Quantity;
import orderTypes.RestingOrder;

public class RestingOrderConfirmation {
	private RestingOrder _restingOrder;
	public RestingOrderConfirmation(RestingOrder restingOrder){
		if(restingOrder==null){
			_restingOrder = null;
			return;
		}
		else{
			_restingOrder = new RestingOrder(restingOrder);
			_restingOrder.setQuantity(new Quantity(restingOrder.getQuantity().getValue()));
			
		}	
	}
	public RestingOrder getRestingOrder(){return _restingOrder;}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_restingOrder == null) ? 0 : _restingOrder.hashCode());
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
		RestingOrderConfirmation other = (RestingOrderConfirmation) obj;
		if (_restingOrder == null) {
			if (other._restingOrder != null)
				return false;
		} else if (!_restingOrder.equals(other._restingOrder))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RestingOrderConfirmation [" + _restingOrder.toString() + "]";
	}
	
}
