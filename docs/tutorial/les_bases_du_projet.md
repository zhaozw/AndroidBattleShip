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


# Qu'est ce qu'un projet LibGDX #
Lorsque vous créez un nouveau projet pour LibGDX, vous récupérez en réalité 3 projets, dont chacun contient du code généré.

## Le code source généré ##
Lors de la génération automatique de vos projet, vous remarquerez qu'une partie du code source a été généréee elle aussi.
Analysons celle-ci:

### Projet Desktop

 	 public class Main {
		public static void main(String[] args) {
			LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
			cfg.title = "BatailleNavale"; // Nom du projet
			cfg.useGL20 = false; // N'utilise pas la dernière version de OpenGL pour des raisons de compatibilité
			//La taille de la fenêtre est de 800 * 600 pixels
			cfg.width = 800;
			cfg.height = 600;
			
			//L'application se lance. Elle va s'exécuter en utilisant votre code source dans le projet global.
			new LwjglApplication(new BatailleNavale(), cfg);
		}
	}

Ce code est pour le moins explicite! Nous voyons tout de même ici une force de LibGDX: à partir d'un code source, nous pouvons réaliser une version pour PC en 6 lignes seulement. Pour un langage aussi verbeux que Java, c'est fort appréciable!

### Projet Android ###

	public class MainActivity extends AndroidApplication {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
	        cfg.useGL20 = false;
	        
	        initialize(new BatailleNavale(), cfg);
	    }
	}

Même constat pour la version android. Remarquez ici qu'on ne peut pas donner de longueur et largeur de la fenêtre. L'application devra prendre la taille de l'écran automatiquement.

### projet Globlal ###

	public class MyGdxGame implements ApplicationListener {
		private OrthographicCamera camera;
		private SpriteBatch batch;
		private BitmapFont font;
		
		@Override
		public void create() {		
			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();
			
			camera = new OrthographicCamera(1, h/w);
			batch = new SpriteBatch();			
		}

		@Override
		public void dispose() {
			batch.dispose();
		}

		@Override
		public void render() {		
			//Expliqué plus tard
		}

		@Override
		public void resize(int width, int height) {}

		@Override
		public void pause() {}

		@Override
		public void resume() {}
	}

En revanche, ce code-ci est plus compliqué. Il contient:

 * create: l'initialisation de la caméra et du batch 
 * dispose: la destruction du batch
 * render: affiche quelque chose (nous l'expliquerons également plus tard)
 * resize: permet de modifier le programme lorsque la taille fenêtre est modifiée. Sous Android, cette méthode est appelée une seule fois au début de l'application.
 * pause et resume, qui font partie de cycle de vie android.

La méthode la plus utile ici est create(). Celle-ci:
 * Crée une nouvelle caméra: la caméra permet de créer un repère orthonormé afin de placer les images indépendamment de la taille de l'écran.
Ici, la camera propose de gérer 100% de la longueur de l'écran, et de ne prendre qu'une partie de la largeur. L'écran se résumera alors à un carré contenu dans le rectangle de votre ecran. Autrement dit, on perd une partie de l'écran. Assez gênant non? 

Changeons cela:

	public void create() {
		camera = new OrthographicCamera(1, 1);
		//...
	}

Ici, on utilise tout l'écran. Cependant, les images seront étirées lorsque l'écran sera très "rectangulaire" (comme pour un Nexus 7 par exemple). Nous verrons plus tard comment pallier à ce problème.

Enfin, il peut être gênant d'utiliser une classe aussi générique, par exemple parce qu'il y aura beaucoup de choses à redéfinir. Nous proposons une implémentation de Game appelée CmmGameAdapter. Cette classe utilise les propriétés énoncées plus haut, et gère en partie le cycle de vie (que nous expliquerons bien plus tard).

La version "finale" de cette classe est donc:
	public class BatailleNavale extends CmmGameAdapter {}

Cette version ultra simpliste fonctionne, puisque CmmGameAdapter hérite de Game.
Nous verrons tout de même plus tard qu'elle n'est pas suffisante pour réaliser un jeu complexe.
[Plus d'information sur la notion de Screen](screens.html)


## Conclusion ##
Sans écrire une seule ligne de code (et en générant à peine quelques lignes), nous avons réussi à réaliser un projet porté sur ordinateur et systèmes android.
En modifiant quelque peu le projet global, nous réussissons à modifier le projet à notre guise. En modifiant le point d'entrée (en utilisant la classe CmmGameAdapter), nous aurons plus tard des outils de calculs pour simplifier le code.
