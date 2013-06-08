import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class ServerListener extends Listener {
	public final static int 
	WAIT_TWO_PLAYERS = 0, 	WAIT_ONE_PLAYER = 1,
	WAIT_TWO_SEAS = 2, 		WAIT_ONE_SEA = 3,
	WAIT_PLAYER_1 = 4, 		WAIT_PLAYER_2 = 5; 
	private int state;
	private Connection[] players;

	public ServerListener() {
		state = WAIT_TWO_PLAYERS;
		players = new Connection[2];
	}


	public void received (Connection connection, Object object) {
		super.received(connection, object);
		System.out.println(object);
		switch(state) {
		case WAIT_TWO_PLAYERS:
			players[0] = connection;
			players[0].sendTCP("wait for player");
			state = WAIT_ONE_PLAYER;
			System.out.println("wait for one more player");
			break;
		case WAIT_ONE_PLAYER:
			if(!equals(connection, players[0])) {
				players[1] = connection;
				players[0].sendTCP("wait for sea");
				players[1].sendTCP("wait for sea");
				state = WAIT_TWO_SEAS;
				System.out.println("wait for seas");
			}
			break;

		case WAIT_TWO_SEAS:
			if(object instanceof String) {
				String message = (String)object;
				if(message.equals("WFP")) { // WFP: WAIT FOR PLAYING
					int p = -1;
					if(connection.equals(players[0])) {
						p = 1;
					}
					else if(connection.equals(players[1])) {
						p = 0;
					}
					players[p].sendTCP("WFP");
					state = WAIT_ONE_SEA;
					System.out.println("wait for one more sea");
				}
			}
			break;
		
		case WAIT_ONE_SEA:
			if(object instanceof String) {
				String message = (String)object;
				int beginner = (int)(Math.random() + 0.5); // Tire un chiffre entre 0 et 1
				if(message.equals("WFP")) { // WFP: WAIT FOR PLAYING
					if(beginner == 0) {
						players[0].sendTCP("play");
						players[1].sendTCP("wait");
						state = WAIT_PLAYER_1;
						System.out.println("wait for player 1");
					}
					else {
						players[0].sendTCP("wait");
						players[1].sendTCP("play");
						state = WAIT_PLAYER_2;
						System.out.println("wait for player 2");
					}
				}
			}
			break;
			
		case WAIT_PLAYER_1:
			if(object instanceof String) {
				if(connection.equals(players[0])) { // Cas où on envoi la case où on tire
					players[1].sendTCP(object);
					System.out.println("wait for player 2");
				}
				else { // Cas où on répond TRUE ou FALSE
					players[0].sendTCP(object);
					state = WAIT_PLAYER_2;
				}	
			}
			break;
		case WAIT_PLAYER_2:
			if(object instanceof String) {
				if(connection.equals(players[1])) { // Cas où on envoi la case où on tire
					players[0].sendTCP(object);
				}
				else { // Cas où on répond TRUE ou FALSE
					players[1].sendTCP(object);
					state = WAIT_PLAYER_2;
					System.out.println("wait for player 1");
				}	
			}
			break;
		}
	}
	
	@Override
	public void disconnected(Connection c) {
		super.disconnected(c);
		if(players[0] != null) {
			players[0].close();
		}
		if(players[1] != null) {
			players[1].close();
		}
		state = WAIT_TWO_PLAYERS;
	}

	/*
	private void play(Connection connection, Object message) {
		if(message instanceof Coord2D) {
			if(equals(connection, players[0]) && state == WAIT_PLAYER_1) {
				players[1].sendTCP(message);
			}
			else if(equals(connection, players[1]) && state == WAIT_PLAYER_2) {
				players[0].sendTCP(message);
			}
			else {
				connection.sendTCP("not your turn");
			}
		}
		else if (message instanceof Boolean) {
			if(equals(connection, players[1]) && state == WAIT_PLAYER_1) {
				players[1].sendTCP(message);
				state = WAIT_PLAYER_2;
			}
			else if(equals(connection, players[2]) && state == WAIT_PLAYER_2) {
				players[0].sendTCP(message);
				state = WAIT_PLAYER_1;
			}
			else {
				connection.sendTCP("not your turn");
			}
		}
	}
	*/
	
	private boolean writeMer(Connection connection, Object object) {
		boolean ok = false;
		if(equals(connection, players[0])) {
			players[1].sendTCP(object);
			ok = true;
		}
		else if(equals(connection, players[1])) {
			players[0].sendTCP(object);
			ok = true;
		}

		return ok;
	}

	// TODO: Gérer les envois
//	private void play(Connection connection, Object object) {
//		if(equals(connection, players[0]) && state == WAIT_PLAYER_1) {
//			players[1].sendTCP(object);
//			state = WAIT_PLAYER_2;
//		}
//		else if(equals(connection, players[0]) && state == WAIT_PLAYER_2) {
//			players[0].sendTCP(object);
//			state = WAIT_PLAYER_1;
//		}
//		else {
//			connection.sendTCP("not your turn");
//		}
//	}


	private boolean equals(Connection connection, Connection connection2) {

		return connection.getID() != -1 && connection.getID() == connection2.getID();
	}


	@Override
	public void connected(Connection arg0) {
		System.out.println("connected!");
	}
}
