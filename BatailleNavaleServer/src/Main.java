import java.io.IOException;

import com.esotericsoftware.kryonet.Server;


public class Main {
	public static void main(String args[]) {
		/* ***** Récupération du port ***** */
		int port = 54722;
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		
		/* ***** lancement du serveur ***** */
		Server server = new Server();
		server.start();
		try {
			server.bind(port, port);
		} catch (IOException e) {
			System.out.println("Impossible de placer le serveur sur " + port);
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("server started");
		server.addListener(new ServerListener());
	}
}
