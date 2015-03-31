package clienthadl;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;

public class ClientInterface {

	private Client client;
	
	private ExecutorService threadPool;
	
	public ClientInterface() {

		//Create a thread pool
		this.threadPool = Executors.newFixedThreadPool(1);
	}
	
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void connect(String host, int port) {

		// Create channel
		RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories
		    .createRpcConnectionFactory(host, port);
		RpcChannels channel = RpcChannels.newRpcChannel(connectionFactory, threadPool);
	
		this.run(channel);
	}
	
	private void run(RpcChannels channel) {
		// Call service
		MyService myService = MyService.newStub(channel);
		RpcController controller = new SocketRpcController();
		myService.myMethod(rpcController, myRequest,
		    new RpcCallback<MyResponse>() {
		      public void run(MyResponse myResponse) {
		        System.out.println("Received Response: " + myResponse);
		      }
		    });

		// Check success
		if (rpcController.failed()) {
		  System.err.println(String.format("Rpc failed %s : %s", rpcController.errorReason(),
		      rpcController.errorText()));
		}
	}
}
