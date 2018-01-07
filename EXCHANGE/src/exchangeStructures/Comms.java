package exchangeStructures;

import java.util.LinkedList;
import messages.*;
import fills.Fills;

public class Comms {
	private Fills _fills;
	private LinkedList<RestingOrderConfirmation> _restingOrderConfirmations;
	private LinkedList<CancelationConfirmation> _cancelationConfirmations;
	private LinkedList<CancelRejected> _cancelRejections;
	/**
	 * 
	 */
	public Comms(){
		_fills = new Fills();
		_restingOrderConfirmations = new  LinkedList<RestingOrderConfirmation>();
		_cancelationConfirmations = new LinkedList<CancelationConfirmation>();
		_cancelRejections = new LinkedList<CancelRejected>();
		
	}
	public Fills getFills() { return _fills;}
	public LinkedList<RestingOrderConfirmation> getRestingOrderConfirmations() {
		return _restingOrderConfirmations;
	}
	public LinkedList<CancelationConfirmation> getCancelationConfirmations() {
		return _cancelationConfirmations;
	}
	public LinkedList<CancelRejected> getCancelRejections() {
		return _cancelRejections;
	}
	public void addRestingOrderConfirmation(RestingOrderConfirmation restingOrderConfirmation) {
		if(restingOrderConfirmation != null)
			_restingOrderConfirmations.add(restingOrderConfirmation);
	}
	/**
	 * 
	 * @param fills
	 */
	public void addFills(Fills fills){
//		while(!fills.isEmpty()){
//			_fills.add(fills.pollFirst());
//		}
		if(fills != null)
			_fills.addAll(fills);
	}
	public void addCancelationConfirmation(CancelationConfirmation cancelationConfirmation) {
		if(cancelationConfirmation != null)
			_cancelationConfirmations.add(cancelationConfirmation);
	}
	public void addCancelRejection(CancelRejected cancelRejection) {
		if(cancelRejection != null)
			_cancelRejections.add(cancelRejection);
	}

}
