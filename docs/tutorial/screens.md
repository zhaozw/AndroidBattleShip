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


# Développer son jeu-vidéo de façon modulaire #
Jusqu'à présent, nous n'avons créé que de minuscules "jeux vidéos", et n'avons alors utilisé qu'une seule classe. Bien évidemment, nous n'allons pas faire cela pour de vrais jeux. Nous allons donc les découper en **Screen**.

## Notion de Screen ##
Qu'est ce qu'un Screen ? C'est un "morceau" de notre jeu, un "écran".
Voici plusieurs exemples de Screen:

 * Menu principal
 * Menu Options
 * L'écran dans lequel vous placez vos bateaux
 * L'écran dans lequel vous jouez contre l'adversaire à la bataille navale.
 * L'écran de fin où vous déplacez l'image grâce à l'accélérométrie.

Pour qu'un screen devienne le Screen utilisé, il faut:

 * L'instancier,
 * appeler la méthode setScreen(Screen) de l'application.

Pour cela, la classe jouant le rôle de point d'entrée va nous être fort utile. Analysons tout-de-suite son code:

	public class BatailleNavale extends CmmGameAdapter {
		public static final int MAIN_MENU = 0, CMM_FINAL = 1, SEARCH_ENNEMY = 2,PLACE_BATEAU = 3, VERSUS_COMPUTER_GAME = 4;	
		@Override
		public void create() {
			super.create();
			
			/* ***** on crée toutes les screens en mémoire pour les utiliser dans l'appli ***** */
			screens.add(MAIN_MENU, new MainMenuScreen(this));
			screens.add(CMM_FINAL,new CmmFinalScreen(this));
			screens.add(SEARCH_ENNEMY, new SearchEnnemyScreen(this));
			screens.add(PLACE_BATEAU, new PlaceBateauScreen(this));
			screens.add(VERSUS_COMPUTER_GAME, new VersusComputerGameScreen(this));
			
			/* ***** On place MAIN_MENU comme le premier screen de l'appli ***** */
			setScreen(screens.get(MAIN_MENU));
		}
	}

Ici, le point d'entrée hérite de CmmGameAdapter. Nous avons déjà vu que cette classe donne des instructions par défaut pour d'autres méthodes (dont nous reparlerons pour le cycle de vie).
Le code est composé de deux parties:

 * l'instanciation de tous les Screen que nous utiliserons dans le jeu,
 * le Screen d'entrée (c'est-à-dire le Screen qui va s'afficher au lancement de l'application) : setScreen(screens.get(MAIN_MENU));

Ici, on remarque que CmmGameAdapter ne réimplémente pas la méthode ``render``. C'est tout simplement qu'elle ne peut pas être utilisée: c'est la méthode ``render`` du Screen qui sera utilisée.

On remarque également qu'on instancie tous les Screen dès le début du jeu, pour des raisons d'efficacité et de simplicité du code. Or, rappelez vous de la méthode ``au plus tard``. Ne placez donc **surtout pas** vos ressources dans le constructeur, mais bien dans la méthode *initialize*. Ainsi, vous pourrez changer de screen facilement, sans prendre beaucoup de mémoire. Au changement, les anciennes images seront supprimées, les nouvelles automatiquement ajoutées, et l'affichage raffraîchi avec le nouveau Screen.


## Ecrire un Screen #
Ecrivons dès à présent un Screen. Nous allons directement utiliser l'adapter ``CmmScreenAdapter``.

	public abstract class MonScreen extends CmmScreenAdapter {
		protected GraphicMer graphicJoueur;
		protected GraphicMer graphicAdversaire;
		private Sound missedSound, touchedSound;
		protected boolean playerTurn;
		protected boolean isOverGame;
		private BitmapFont font;
		private String message;

		/* ***** Très peu de chose dans le constructeur, une petite configuration, et quelques boolean ****** */
		public GameScreen(BatailleNavale app) {
			super(app, false);
		}

		@Override
		/* ***** Initialisation des ressources lourdes, plus ce dont vous avez besoin ***** */
		public void initialize() {
			super.initialize();
			isOverGame = false;

			/* ***** Création de la mer de l'adversaire ***** */
			Mer computerMer = new Mer();
			graphicAdversaire = new GraphicMer(this, computerMer, false);

			/* ***** Place les images en mémoire ***** */
			Texture wallText = app.getTransversaleTexture("wallpaper");
			setWallpaper(new TextureRegion(wallText, 349, 496));
			textures.add(wallText);

			/* ***** place les sons en mémoire ***** */
			touchedSound = Gdx.audio.newSound(Gdx.files
					.internal("data/sounds/touched.mp3"));
			missedSound = Gdx.audio.newSound(Gdx.files
					.internal("data/sounds/missed.mp3"));
			sounds.add(touchedSound);
			sounds.add(missedSound);

			/* ***** place la police en mémoire ***** */
			font = new BitmapFont(Gdx.files.internal("data/fonts/mainMenu.fnt"),
					Gdx.files.internal("data/fonts/mainMenu.png"), false);
			font.setUseIntegerPositions(false);
			font.setScale(1.4f / Gdx.graphics.getHeight());
			font.setColor(1, 1, 1, 1);
		}

		@Override
		/* ***** On rafraichit l'écran par une série d'appels OpenGL (ou LibGDX) ***** */
		public void render(float delta) {
			super.render(delta);
			spriteBatch.begin();
			font.drawMultiLine(spriteBatch, message, x, y);
			spriteBatch.end();
		}

		@Override
		/* ***** Et enfin on supprime les ressources lourdes ***** */
		public void dispose() {
			super.dispose();
			GraphicMer.dispose();
			font.dispose(); 
		}
	}

Ici, on respecte donc bien la méthode *j'initialise mes ressources lourdes seulement lorsque j'en ai besoin, je les supprime dès que je ne m'en sers plus*.  

Ici, on s'est servi du helper CmmScreenAdapter. Ce helper permet simplement de supprimer automatiquement les ressources placées dans son ArrayList. Il réimplémente également des méthodes du cycle de vie Android, dont nous allons parler tout de suite.


## Gérer le cycle de vie ##

Le cycle de vie est une notion fondamentale dans toute application Android. Gérer convenablement le cycle de vie de votre application vous assurera de:

 * ne pas perdre de données,
 * rendre le jeu vidéo moins gourmand en batterie et en ressources (mémoire, CPU ...)
 * éviter des bugs difficiles à reproduire.

 LibGDX nous permet naturellement de gérer ce cycle de vie de manière agréable, puisqu'il implémente des méthodes qui seront appelées au bon moment, en fonction du cycle de vie. Ainsi, il est important de redéfinir les méthodes d'un Screen:

 | Méthode LibGDX | état du cycle de vie | Commentaire|
 =====================================================================================
 | show()         | Created   | Placez vos initialisations|
 | hide()         | Stoped    | Placez vos sauvegardes temporaires, et vos dispose |
 | resume()       | Resumed   | Placez vos chargements temporaires, et vos initialisations |
 | pause()        | Paused    | Placez vos sauvegardes temporaires, et vos dispose |
 | dispose()      | Destroyed | Placez vos sauvegardes temporaires, et vos dispose |
 | resize()       | N/A       | Surtout utile pour la version PC.

 Pour vous donner une idée, au changement de Screen, si on passe de ScreenA à ScreenB, les méthodes seront lancées dans cet ordre:
 
  * screenA.hide()
  * screenA.dispose()
  * screenB.show()
  * screenB.resize()

Bien évidemment, le cycle de vie est un peu plus capricieux que cela. Cependant, son utilisation est bien entendu similaire à celle des applications plus classiques. 


## Conclusion ##
Les ressources utilisées pour un jeu vidéo sont lourdes, voire parfois très lourdes. Essayez de les utiliser de manière utile, afin de ne pas surcharger la machine.  
Ayez toujours à l'esprit le cycle de vie Android. Celui-ci pose des contraintes liées justement à la faible puissance de certaines machines. N'oubliez jamais que les ressources de votre application peuvent être placées en cache par la machine. il faudra donc recharger les images dès que le jeu est passé en Pause (c'est-à-dire, chaque fois que votre application n'est plus au premier plan).  
N'hésitez pas à utiliser les facilités apportées par LibGDX: réimplémentez les méthodes des Screen. Le helper CmmScreenAdapter donne un comportement par défaut, qui sera peut-être le bon comportement la plupart du temps. Cependant, il est important que vous compreniez comment le cycle de vie fonctionne, et à programmer un comportement adapté à ce que vous voulez faire.
