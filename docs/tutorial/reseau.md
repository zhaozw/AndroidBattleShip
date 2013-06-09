[Accueil](accueil.html)  
[cahier des charges](cahier_des_charges.html)  
[créer un projet LibGDX](creer_projet.html)  
[Placer une image en mémoire](Les_Images_en_LibGDX.html)  
[Afficher une image](les_bases_du_painting.html)  
[Les Screen](screens.html)  
[les Sons](sons.html)  
[L'interaction avec l'utilisateur](reaction.html)  
[L'accélérométrie](accélérométrie.html)  
[Intelligence artificielle](intelligence_artificielle.html)  
[Réseau](reseau.html)


# Réseau #

Pour la partie réseau de cette application, nous utiliserons [Kryonet](http://code.google.com/p/kryonet/). Cette librairie possède plusieurs qualités:

 * Elle fonctionne sous Android et PC,
 * Elle est multi-thread,
 * Elle permet l'envoi d'objets Serialisés à travers le réseau.

Cette librairie de réseau Client/Serveur, est très simple à utiliser. Il faut:

 * Créer un serveur
 * Créer un client
 * Ajouter un listener pour chaque partie.

N.B. les exemples sont directement sortis de la documentation Kryonet: [ici](http://code.google.com/p/kryonet/)
## Création client/serveur ##
### Création du serveur ###
	Server server = new Server();
	server.start();
	server.bind(54555, 54777);
Ici, on décide d'utiliser le port 54555 pour TCP, 54777 pour UDP.
Globalement, le serveur utillise TCP. Cependant, il peut envoyer des requêtes UDP en broadcast pour découvrir ou répondre à une découverte du réseau (discoverHost)

### Création du client ###

	Client client = new Client();
	client.start();
	client.connect(5000, "192.168.0.4", 54555, 54777);

Même principe: pour connecter le client au serveur, il suffit de donner:

 * une adresse IP; notez que "www.google.fr" fonctionne également: la libraire s'occupe de trouver l'adresse IP grâce au DNS.
 * un port TCP où envoyer,
 * un port UDP où envoyer.

Si vous ne connaissez pas l'adresse du serveur, vous pouvez utiliser la découverte du réseau.

### Le DiscoverHost ###
Il s'agit d'envoyer un message en broadcast sur le réseau local. Pour cela, il suffit de donner le port sur lequel envoyer en broadcast:

	InetAddress address = client.discoverHost(54777, 5000);
	client.connect(5000, address, 54555, 54777);

A noter que ceci ne fonctionne que pour un réseau local.


## L'envoi de données Serialisé ##
### Le Registering ###
Pour envoyer des objets, il faut auparavant **enregistrer** votre Objet (i.e. la classe que vous allez envoyer).

	Kryo kryo = server.getKryo(); // également pour le client!
	kryo.register(MaClassAEnvoyer.class);

### L'envoi de trames ###
Pour envoyer une trame, c'est très simple:

	MaClassAEnvoyer object = new MaClassAEnvoyer();
	server.sendTCP(object);

Rappelons que l'envoi est effectué en TCP: votre message est donc certain d'arriver, en entier, dans le bon ordre.

### Listener ###
Afin de répondre aux demandes, vous pouvez écrire des listeners. Il s'agit d'un listener classique qui implémente des méthodes:

 * connected à la connexion
 * disconnected à la déconnexion
 * idle à la création d'un nouveau thread
 * received à chaque trame reçue.  



	server.addListener(new Listener() {

		public void received (Connection connection, Object object) {
		
		  if (object instanceof SomeRequest) {
		     SomeRequest request = (SomeRequest)object;
		     System.out.println(request.text);

		     SomeResponse response = new SomeResponse();
		     response.text = "Thanks!";
		     connection.sendTCP(response);
		  }
		}
	});
