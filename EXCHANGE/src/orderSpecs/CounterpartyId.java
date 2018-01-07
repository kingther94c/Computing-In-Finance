package orderSpecs;

public class CounterpartyId extends ClientId{

	public CounterpartyId(String counterpartyId) {
		super(counterpartyId);
	}
	
	public CounterpartyId(ClientId counterpartyId) {
		super(counterpartyId.getValue());
	}
	
	
	
}
