import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class ServerListener extends Listener {
	public final static int 
		WAIT_TWO_PLAYERS = 0, 	WAIT_ONE_PLAYER = 1,
		WAIT_TWO_SEAS = 2, 		WAIT_ONE_SEA = 3,
		WAIT_PLAYER_1 = 4, 	WAIT_PLAYER_2 = 5; 
	private int state;
	private Connection[] players;
	
	public ServerListener() {
		state = WAIT_TWO_PLAYERS;
		players = new Connection[2];
	}
	
	
	public void received (Connection connection, Object object) {
			switch(state) {
			case WAIT_TWO_PLAYERS:
				players[0] = connection;
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
				if(writeMer(connection, object)) {
					state = WAIT_ONE_SEA;
					System.out.println("wait for one more sea");
				}
				break;
			case WAIT_ONE_SEA:
				if(writeMer(connection, object)) {
					int random = (int)(Math.random() * 2);
					if(random == 0) { 
						state = WAIT_PLAYER_1;
						System.out.println("wait for player 1");
						players[0].sendTCP("you play");
						players[1].sendTCP("wait");
					}
					else {
						state = WAIT_PLAYER_2;
						System.out.println("wait for player 2");
						players[1].sendTCP("you play");
						players[0].sendTCP("wait");
					}
				}
				break;
				
			case WAIT_PLAYER_1:
			case WAIT_PLAYER_2:
				play(connection, object);
			}
	}

	@Override
	public void disconnected(Connection c) {
		super.disconnected(c);
		players[0].close();
		players[1].close();
		state = WAIT_TWO_PLAYERS;
	}
	
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
	
	private void play(Connection connection, Object object) {
		if(equals(connection, players[0]) && state == WAIT_PLAYER_1) {
			players[1].sendTCP(object);
			state = WAIT_PLAYER_2;
		}
		else if(equals(connection, players[0]) && state == WAIT_PLAYER_2) {
			players[0].sendTCP(object);
			state = WAIT_PLAYER_1;
		}
		else {
			connection.sendTCP("not your turn");
		}
	}


	private boolean equals(Connection connection, Connection connection2) {
		
		return connection.getID() != -1 && connection.getID() == connection2.getID();
	}
}
