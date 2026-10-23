package control;

public final class North2000 {
	private North2000() {}
	
	public static void main(String[] args) {
//		CustomerLogic.getInstance().exportCustomersToXML();
//		CustomerLogic.getInstance().importCustomersFromXML("xml/customers.xml");
		CustomerLogic.getInstance().exportCustomersToJSON();
//		CustomerLogic.getInstance().importCustomersFromJSON("json/customers.json");
	}
}
