===== Les Images en LibGDX ======
---------------------------------
==== Introduction: un monde d'images =====
------------------------------------------

Très souvent, lorsque vous voulez créer un jeu-vidéo, vous utilisez des images que vous "collez" sur l'écran.
Prenons un exemple: *mario*. Lorsque vous déplacez votre plombier préféré, en réalité le programme execute:
 * placer la bonne image de mario (par exemple: mario regarde à droite, jambe gauche et bras droit en avant)
 * placer cette image quelques pixels plus à droite que l'ancienne.
En réalité, c'est le déplacement de l'image, allié au changement d'image qui vous donne l'illusion du déplacement d'un bonhomme comme mario.

Maintenant que vous connaissez la manière de procéder, programmons là!

==== Placer une image en mémoire ====
-------------------------------------

Afin de pouvoir "coller" (on dit également "peindre") une image sur l'écran, la première étape est de la placer en mémoire.
Pour cela, la classe Texture est à votre disposition:

	Texture maTexture = new Texture("data/img/maTexture.jpg");

Cette instruction permet de charger une image en mémoire.

#### Attention: ####
Une image représente une instance **lourde** en mémoire. C'est pourquoi, il est impératif de créer la texture le moins souvent possible, et de relacher la mémoire dès que cette image n'est plus utilisé (avec la méthode *dispose()*).
La classe *CmmScreenAdapter* vous permet de faire ces instructions facilement:
 * instancier l'image (new Texture(path))
 * ajouter la Texture dans votre classe héritée de *CmmScreenAdapter* (this.textures.add(maTexture))
Cette classe s'occupe d'appeler *dispose()* sur toutes les Textures contenu dans l'ArrayList *textures*.

==== Placer une image en mémoire ====
--------------------

L'image est maintenant en mémoire. Cependant, nous n'avons pas encore choisi:
 * son abscisse et son ordonnée
 * sa taille
 * sa rotation (si vous voulez "retourner" l'image par exemple)

Pour cela, nous allons encapsuler l'image dans une autre classe: *Sprite*.
Un Sprite est la façon la plus commune d'afficher une image sous libGDX. On peut voir un Sprite comme la représentation d'une image, à une abscisse et une ordonnée défini, ayant une longueur, une hauteur et un angle définis:

	Texture cmmText = new Texture("data/img/cmm.png");
	textures.add(CMM_SPRITE, cmmText);
	TextureRegion cmmTextReg = new TextureRegion(cmmText, 278, 330);
	
	Sprite cmmSprite = new Sprite(cmmTextReg);
	cmmSprite.setSize(0.2f, 0.2f);
	cmmSprite.setPosition(-cmmSprite.getWidth()/2, -cmmSprite.getHeight()/2);
	sprites.add(cmmSprite);

Ce code permet d'afficher l'image au milieu de l'écran.
On remarque ici que le Sprite est placé dans l'ArrayList *sprites*. Cet ArrayList permet d'afficher les sprite automatiquement,et de les supprimer ``au plus tôt`` en appliquant la méthode ``dispose`` sur chacun d'eux.


==== Peindre une image =====
----------------------------

Nous venons de placer l'image en mémoire. Ici, on l'a placé dans l'ArrayList, et elle sera donc affiché automatiquement. Cependant, si vous ne voulez pas utiliser l'ArrayList, vous pouvez afficher une image vous-même:
	/* Initialisation de l'affichage */
	Gdx.gl.glClearColor(1, 1, 1, 1);
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	spriteBatch.begin();
		/* série d'affichage */
		cmmSprite.draw(spriteBatch);
		font.drawMultiLine(spriteBatch,	message, 0f, 0f);
	/* Arrêt de l'affichage */
	spriteBatch.end();

Dans ce code, vous voyez clairement trois blocs:
 * L'initialisation de l'affichage: l'écran ne se rafraichira pas si vous n'appelez pas ces 3 méthodes.
 * L'affichage en lui-même. Il s'agit du code que vous devez écrire en fonction de ce que vous voulez afficher.
 * l'arrêt de l'affichage. Pour des raisons d'optimisation, OpenGL attend que vous lui disiez explicitement qu'il peut afficher toutes les images.


 ==== Retirer une image de la mémoire ====
 -----------------------------------------

 Puisque les images (ou plutôt les Texture) sont des ressources lourdes. C'est pourquoi il convient de les supprimer de la mémoire dès que possible (plutôt que d'attendre le Garbage Collector, qui sera moins efficace que vous!). Pour celà, rien de compliqué:
 	maTexture.dispose();
 La méthode dispose supprime directement la Texture. Retenez que :
  * Un Sprite ne copie pas la Texture (ou la TextureRegion) qu'il utilise. Lorsqu'une Texture est supprimé, les Sprite utilisant cette Texture ne **doivent** pas être affiché.
  * L'ArrayList textures permet de supprimer les Texture qui la composent. Cette ArrayList permet de gérer plus facilement le cycle de vie Android, en supprimant les Texture au changement de Screen.

  
