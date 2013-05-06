===== Qu'est ce qu'un projet LibGDX =====
Lorsque vous créez un nouveau projet pour LibGDX, vous récupérez en réalité 3 projets, dont chacun contient du code généré.

==== Le code source généré ====
Lors de la génération automatique de vos projet, vous remarquerez qu'une partie du code source a été générée elle aussi.
Analysons celui-ci:

 * projet desktop:
 	public class Main {
		public static void main(String[] args) {
			LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
			cfg.title = "BatailleNavale"; // Nom du projet
			cfg.useGL20 = false; // N'utilise pas la dernière version de OpenGL pour des raisons de compatibilités
			//La taille de la fenêtre et de 800 * 600 pixels
			cfg.width = 800;
			cfg.height = 600;
			
			//L'application se lance. Il va s'executer en utilisant votre code source dans le projet global.
			new LwjglApplication(new BatailleNavale(), cfg);
		}
	}
Ce code est pour le moins explicite! Nous voyons tout-de-même ici une force de LibGDX: à partir d'un code source, nous pouvons réaliser une version pour PC en 6 lignes seulement. Pour un langage aussi verbeux que Java, c'est fort apréciable!

 * android:
	public class MainActivity extends AndroidApplication {
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
	        cfg.useGL20 = false;
	        
	        initialize(new BatailleNavale(), cfg);
	    }
	}
Même constat pour la version android. Remarquez ici qu'on ne peut pas donner de longueur et largeur de la fenêtre. L'application devra prendre la taille de l'ecran automatiquement.

 * global:
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
 * create: l'initialisation de la caméra et du batch (nous expliquerons son utilité plus tard dans cette documentation TODO placer un lien).
 * dispose: la destruction du batch
 * render: affiche quelque chose (nous l'expliquerons également plus tard)
 * resize: permet de modifier le programme lorsque la taille fenêtre est modifiée. Sous Android, cette méthode est appelé une seule fois au début de l'application.
 * pause et resume, qui font partie de cycle de vie android.

La méthode la plus utile ici est create(). Celle-ci:
 * Crée une nouvelle camera: la camera permet de créer un repère orthonomé afin de placer les images indépendemment de la taille de l'écran.
Ici, la camera propose de gérer 100% de la longueur de l'ecran, et de ne prendre qu'une partie de la largeur. L'ecran se résumera alors à un carré contenu dans le rectangle de votre ecran. Autrement dit, on perd une partie de l'ecran. Assez génant non? 

Changeons cela:
	public void create() {
		camera = new OrthographicCamera(1, 1);
		//...
	}

Ici, on utilise tout l'ecran. Cependant, les images seront étirés lorsque l'ecran sera très "rectangulaire" (comme pour mon Nexus 7 par exemple). Nous verrons plus tard comment pallier à ce problème.

Enfin, il peut être génant d'utiliser une classe aussi générique, par exemple parce qu'il y aura beaucoups de choses à redéfinir. Nous proposons une implémentation de Game appelé CmmGameAdapter. Cette classe utilise les propriétés énoncées plus haut, et gère en partie le cycle de vie (que nous expliquerons bien plus tard) TODO placer un lien.

La version "finale" de cette classe est donc:
	public class BatailleNavale extends CmmGameAdapter {}

Cette version ultra simpliste fonctionne, puisque CmmGameAdapter herite de Game.
Nous verrons tout-de-même plus tard qu'elle n'est pas suffisante pour réaliser un jeu complexe.


==== Conclusion ====
Sans écrire une seule ligne de code (et en générant à peine quelques lignes), nous avons réussi à réalisé un projet porté sur ordinateur et systèmes android.
En modifiant quelque peu le projet global, nous réussisont à modifier le projet à notre guise. En modifiant le point d'entrée (en utilisant la classe CmmGameAdapter), nous aurons plus tard des outils de calculs pour simplifier le code.
Il reste maintenant à pouvoir afficher des images.