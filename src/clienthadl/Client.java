package clienthadl;

import java.util.List;

public class Client {

	private List<ClientInterface> clientInterfaces;
	
	public Client() {
		
	}

	public List<ClientInterface> getClientInterfaces() {
		return clientInterfaces;
	}

	public void setClientInterfaces(List<ClientInterface> clientInterfaces) {
		this.clientInterfaces = clientInterfaces;
	}
	
	public void addClientInterface(ClientInterface c) {
		this.clientInterfaces.add(c);
	}
	
	public void connect() {
		System.out.println("Ip : ");
		
		
		this.clientInterfaces.get(1).connect("127.0.0.1", 540);
	}
}
