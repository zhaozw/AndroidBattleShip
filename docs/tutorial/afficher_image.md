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


# Les bases du painting #
Nous avons réussi à lancer notre programme sur un ordinateur et sur notre tablette ou smartphone préféré.
Mais nous voulons réaliser un jeu vidéo. Comme nous l'avons déjà expliqué en introduction, un jeu vidéo peut se résumer à un ensemble d'images placées et déplacées sur l'écran afin de réaliser l'impression de mouvement.
Nous allons donc voir comment afficher une image.

## Analyse du code généré ##
Rien ne vaut un bon exemple pour comprendre. Comme exemple, nous prendrons le code généré lors de la création d'un nouveau projet:

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	@Override
	public void create() {		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		/* instanciation des textures */
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		/* création d'une région */
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		/* création d'un sprite */
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose(); // désallocation mémoire.
	}

	@Override
	public void render() {		
		/* début du painting libgx */
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		/* painting */
		sprite.draw(batch);

		/* fin du painting */
		batch.end();
	}

Ce code, un peu long il est vrai, nous montre tout ce dont nous avons besoin pour afficher des images.
Le painting se fait en plusieurs étapes:

 * Instanciation de l'image en mémoire
 * painting : affichage de l'image; appelé automatiquement tant que l'application s'exécute.
 * désallocation mémoire.

Nous verrons ces trois points en détails.

## L'instanciation des images en mémoire ##
C'est très simple: pour utiliser une image, il faut qu'elle existe pour votre application, c'est-à-dire qu'elle soit placée en mémoire.

	Texture texture = new Texture(Gdx.files.internal("data/libgdx.png"));
	
Ici, on place en mémoire l'image "data/libgdx.png" en mémoire.

Attention: les images sont des ressources particulièrement lourdes. Il faut donc:
 * n'ajouter une image que lorsqu'on en a réellement besoin. Il ne faut donc pas ajouter toutes les images du programme d'un seul coup, mais les placer en mémoire ``au plus tard``
 * n'ajouter qu'une seule fois la même image en mémoire.
 * les supprimer de la mémoire ``au plus tôt``, c'est-à-dire dès que l'on ne s'en sert plus.
Dans la partie concernant les Sprite, nous expliquerons plus en détail comment afficher plusieurs fois la même image.
Dans la partie concernant les Screen, nous expliquerons plus en détail quand créer et disposer les images.

## Le rendering: afficher les images sur l'écran ##
Il y a deux façons de peindre une image sur l'écran:

 * peindre directement la texture,
 * utiliser un Sprite pour encapsuler une texture, à un point donné, avec une taille donnée.
Dans les deux cas, le SpriteBatch nous sert, car c'est lui qui peint effectivement l'image sur l'écran. La caméra quant à elle nous donne un repère orthonormé de l'écran.

### Les repères en OpenGL ###
Par défaut, le repère de OpenGL est comme tel:

 * le point de repère (où x=0 et y=0) se trouve au centre de l'image.
 * x va de -0.5f à +0.5f
 * y va de -0.5f à +0.5f

Par défaut, le repère d'une image est comme tel:

 * le point de repère (où x=0 et y=0) se trouve sur le coin bas-gauche de l'image.
 * x commence à 0.f
 * y commence à 0.f

Il est primordial de comprendre comment fonctionnent ces repères afin de pouvoir placer les images où bon vous semble.
Par exemple, pour placer une image au centre de l'écran:

x = 0.f - longueur de l'image/2
y = 0.f - hauteur de l'image/2

Maintenant que nous comprenons mieux le système de repère (nous aurons de toute façon l'occasion de donner d'autres exemples), essayons d'afficher notre image au centre.

### peindre directement une texture ###
Comme énoncé précédemment, nous allons utiliser notre SpriteBatch.
la méthode render() est une méthode appelée automatiquement, et qui rafraichit l'écran (i.e. il repeint tout l'écran à chaque appel)
Pour afficher une image, il faut donc utiliser un code comme celui-ci:

	@Override
	public void render() {	
		/* Calcul des coordonnées et de la taille de l'image: */
		float longueur = 0.1f;
		float largeur = 0.1f;
		float x = -longueur /2.f;
		float y = -largeur / 2.f;

		/* début du painting libgx */
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		/* painting */
		texture.draw(spriteBatch, x, y longueur, largeur);

		/* fin du painting */
		batch.end();
	}

Analysons ce code en détail:

 * On calcule les coordonnées de façon à placer l'image au centre. L'image prend 1/5 de l'écran en longueur et en largeur.
 * On exécute les instructions propres à OpenGL. Elles sont nécessaires à l'exécution des méthodes suivantes mais ne seront pas expliquées ici. (voir les liens sur [developpez.com](http://nehe.developpez.com/)ou [siteduzero](http://www.siteduzero.com/informatique/tutoriels/creez-des-programmes-en-3d-avec-opengl) par exemple).
  * on peint la texture avec texture.draw, en lui donnant en paramètre la SpriteBatch qui s'occupe de peindre sur l'ecran.

C'est assez simple finalement!
Une question se pose: ici, on recalcule sans cesse x, y, longueur et largeur. Il pourrait être utile et astucieux de garder en mémoire ces informations. C'est le rôle d'un Sprite.

### Utiliser les Sprite ###
Qu'est ce qu'un Sprite ? Une encapsulation vers une ``manière`` de peindre un image. Pour être plus clair, un Sprite retient:

 * Une TextureRegion d'une image,
 * les coordonnées (x et y)où doivent être peinte l'image,
 * les tailles (width et height) de l'image
 * l'orientation de l'image,
 * ...

L'idée ici est de retenir ces informations afin de pouvoir afficher une image facilement.
Prenons un exemple plus parlant: nous allons déplacer une image de gauche à droite:

	public class Test extends Game {
		Texture text;
		Sprite sprite;
		SpriteBatch batch;
		Camera camera;
		
		@Override
		public void create() {
			camera = new OrthographicCamera(1, 1);
			batch = new SpriteBatch();
			
			/* ***** Initialisation texture ****** */
			text = new Texture("./data/maTexture.png");// On crée la texture (longueur et hauteur doivent être une puissance de 2)
			TextureRegion textReg = new TextureRegion(text, 200, 200); // On ne prend que les 200 * 200 premiers pixels
			
			/* ***** Création d'un sprite ***** */
			sprite = new Sprite(textReg);
			sprite.setSize(0.1f, 0.1f);
			sprite.setPosition(-0.5f, -0.5f - sprite.getHeight());
		}

		@Override
		public void render() {
			super.render();
			/* début du painting libgx */
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			sprite.draw(batch); /* painting */
			batch.end();/* fin du painting */
			
			sprite.translateX(0.05f); /* deplacement vers la droite */
		}

		@Override
		public void dispose() {
			super.dispose();
			text.dispose();
		}
	}

Ce code reprend les principales notions vues:

 * Tout d'abord on initialise la Texture, et on crée un Sprite
 * Ensuite, on rafraichit l'écran, et déplaçant à chaque fois le Sprite,
 * Lorsqu'on quitte l'application, on dispose la texture. Il s'agit de la libération de mémoire, que nous allons voir tout de suite.

## La libération de la mémoire ##
Comme expliqué précédemment, une Texture (c'est-à-dire une image) est une ressource lourde. Il faut donc la libérer *au plus tôt*. Pour celà, LibGDX met à notre disposition la méthode dispose(). Elle s'utilise le plus simplement du monde:
	maTexture.dispose();

Notez que les TextureRegion ne sont pas des ressources à "disposer". En effet, les TextureRegion ne recopient pas la Texture qui l'interesse. Ce n'est donc qu'une encapsulation d'un pointeur vers la Texture (bien que le terme pointeur soit abusif en Java), et de quelques attributs privés.

	@Override
	public void dispose() {
		super.dispose();
		text.dispose();
	}

C'est aussi simple que cela: appliquez cette méthode sur toutes vos Textures.