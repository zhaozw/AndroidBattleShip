import java.io.IOException;
import java.util.ArrayList;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

public class Main {
	public static int UDP_PORT = 54723, TCP_PORT = 54722;
	public static void main(String args[]) {
		/* ***** Récupération du port ***** */		
		if(args.length > 0) {
			TCP_PORT = Integer.parseInt(args[0]);
			UDP_PORT = TCP_PORT + 1;
		}
		System.out.println("" + UDP_PORT + " - " + TCP_PORT);
		
		/* ***** lancement du serveur ***** */
		Server server = new Server();
		Kryo kryo = server.getKryo();
		kryo.register(int[][].class);
		kryo.register(int[].class);
		kryo.register(ArrayList.class);
		kryo.register(Bateau.class);
		kryo.register(Mer.class);
		
		server.start();
		
		try {
			server.bind(TCP_PORT, UDP_PORT);
		} catch (IOException e) {
			System.out.println("Impossible de placer le serveur sur " + TCP_PORT);
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("server started");
		server.addListener(new ServerListener());
	}
}
