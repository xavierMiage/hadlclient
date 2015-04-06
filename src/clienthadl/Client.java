package clienthadl;

import java.util.List; 

public class Client {

	public static void main (String [] args) {
		if(args.length == 0){
			System.out.println("Tentative de connection à localhost:5163");
			new ClientInterface("localhost","5163", "1");
		}else{
			new ClientInterface(args[0], args[1], args[2]);
		}
	}
}
