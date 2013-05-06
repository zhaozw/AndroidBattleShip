===== Développer son jeu-vidéo de façon modulaire ======
Jusqu'à présent, nous n'avons créé que de minuscule "jeu-vidéo", et n'avons alors utilisé qu'une seule classe. Bien évidemment, nous n'allons pas faire celà pour de vrais jeux. Nous allons donc les découper en **Screen**.

==== Notion de Screen ====
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

Pour celà, la classe jouant le rôle de point d'entrée va nous être fort utile. Analysons tout-de-suite son code:

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
Le code est composé de deux partie:
 * l'instanciation de tous les Screen que nous utiliserons dans le jeu,
 * le Screen d'entrée (c'est-à-dire le screen qui va s'afficher au lancement de l'application) : setScreen(setScreen(screens.get(MAIN_MENU)))

Ici, on remarque que CmmGameAdapter ne réimplémente pas la méthode ``render``. C'est tout simplement qu'elle ne peut pas être utilisé: c'est la méthode ``render`` du Screen qui sera utilisé.


==== Ecrire une Screen =====
Ecrivons dès à présent un Screen. Nous allons directement utiliser l'adapter ``CmmScreenAdapter``.

TODO


==== Gérer le cycle de vie ====
Le cycle de vie est quelque chose de fondamental dans toute application Android. Gére convenablement le cycle de vie de votre application vous assurera de:
 * ne pas perdre de données,
 * rendre le jeu-vidéo moins gourmand en batterie et en ressources (mémoire, CPU ...)
 * éviter des bugs difficiles à comprendre.

 LibGDX nous permet naturellement de gérer ce cycle de vie de manière agréable. Voyez plutôt 