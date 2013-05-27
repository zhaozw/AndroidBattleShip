import java.io.IOException;
<<<<<<< HEAD
import com.esotericsoftware.kryo.Kryo;
=======

>>>>>>> parent of 4114e84... Ajoute les entités pour le réseau. Pour l'instant, la connexino fonctionne, et on arrive à placer ces bateaux, mais la Mer n'est pas envoyée au serveur
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
<<<<<<< HEAD
		Kryo kryo = server.getKryo();
		kryo.register(Boolean.class);
		kryo.register(Coord2D.class);
		
=======
>>>>>>> parent of 4114e84... Ajoute les entités pour le réseau. Pour l'instant, la connexino fonctionne, et on arrive à placer ces bateaux, mais la Mer n'est pas envoyée au serveur
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
