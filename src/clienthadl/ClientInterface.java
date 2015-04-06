package clienthadl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;

public class ClientInterface implements Runnable  {
		 
		private String addr;
		private String port;
		private PrintWriter out;
		private DataOutputStream out2;
		private	Socket socket;
		private Thread thd;
		private int id;
	 
	 
	public ClientInterface(String addr, String port, String id){
		this.id = Integer.parseInt(id);
		try{
			socket = new Socket (addr , Integer.parseInt(port));
			socket.setSoLinger(true , 10);
		}//try
		catch (IOException a ){
			System.out.println("erreur2 " + a.getMessage()) ; a.printStackTrace() ;
		}
	 
		/*try{
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true );
			out2 = new DataOutputStream(socket.getOutputStream());
		}//try
	 
		catch (IOException a ) {
			System.out.println("erreur3") ;
			a.printStackTrace();
		}//catch IO*/
		thd = new Thread (this) ;
		thd.start();
	 /*
			while (true){
				try {
	//-----------------------------------------------------------------------------------
					int t;
					String tmp="" ;
					do {
						t = socket.getInputStream().read();
						tmp = tmp + t;
					}while (t!=0) ;
	 
	//-----------------------------------------------------------------------------------
						System.out.println(tmp);
				}//try
				catch (IOException a ) {
					System.out.println("erreur4") ;
					a.printStackTrace() ;  }//catch IO
			}//while*/
	}//Client
	 
	public void run (){
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("nom", "Arn");
		json.put("id_article", 1);
		this.send(json.toJSONString());
		json = this.read();
		
		if(json.containsKey("error")) {
			System.out.println(json.get("error"));
		}
		else {
			System.out.println("Nom : " + json.get("nom"));
			System.out.println("Prix : " + json.get("prix"));
		}
	}
	
	
	public void send (String scor){
		PrintWriter output;
		try {
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ClientInterface.this.socket.getOutputStream())), true);
			output.println(scor) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//send
	
	public JSONObject read() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,String> map = new HashMap<String,String>();
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = input.readLine();
			
			map = mapper.readValue(message, new TypeReference<HashMap<String,String>>(){});
			JSONObject json = new JSONObject(map);
			return json;
	// 		out2.writeBytes(entree.readLine() + "\n");
		} catch( IOException e ) {
			System.out.println("Erreur lors de la lecture");
			e.printStackTrace();
		}
		return null;
	}
	 
}//class
