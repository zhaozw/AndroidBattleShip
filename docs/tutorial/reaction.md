[Accueil](accueil.html)  
[Cahier des charges](cahier_des_charges.html)  
[Créer un projet LibGDX](creer_projet.html)  
[Composition du projet](les_bases_du_projet.html)
[Placer une image en mémoire](Les_Images_en_LibGDX.html)  
[Afficher une image](les_bases_du_painting.html)  
[Les Screen](screens.html)  
[Les Sons](sons.html)  
[L'interaction avec l'utilisateur](reaction.html)  
[L'accélérométrie](accélérométrie.html)  
[Intelligence artificielle](intelligence_artificielle.html)  
[Réseau](reseau.html)

# La réaction de votre application #

Si l'utilisateur ne peut pas interagir avec les éléments de votre jeu, ce n'est pas un jeu!  
Nous allons donc voir comment interagir avec l'utilisateur. En réalité, il y a deux types d'interaction, La callback et le polling

## Système de callback (listener) ##
LibGDX implémente des classes de Listeners. En particulier, les InputAdapter implémentent les méthodes suivantes:

### Le listener ###

Méthode | Machine concernée | commentaire
--------`-------------------|---------
keyDown | Ordinateur | Appelée lorsqu'une touche du clavier est pressée
keyTyped | Ordinateur| Appelée lorsqu'une touche du clavier est appuyée puis relâchée
keyUp    | Ordinateur | Appelée lorsqu'une touche du clavier est relâchée
mouseMoved | Ordinateur | Appelée lorsqu'aucun clic n'est effectué, au mouvement de la souris
scrolled | Ordinateur | Appelée lors du déplacement de la molette de la souris
touchDown | Tous      | Appelée lors d'un clic, ou lorsqu'on touche l'écran pour Android
touchDragged | Tous  | Appelée lors du mouvement de la souris, clic enfoncé, ou lors d'un mouvement du doigt sur l'écran Android
touchUp | Tous | Appelée lorsque le clic est relâché, ou lorsque le doigt est retiré de l'écran Android

Comme vous pouvez le constater sur l'API, les méthodes des listeners renvoyent toutes un boolean. En fait, ils servent à dire si l'appel était pour ce listener ou non.
En effet, si vous placez des listeners sur des objets graphiques (et ce sera preque toujours le cas), il se peut que ces objets se superposent. Or, vous ne voulez sans doute pas qu'on puisse cliquer sur plusieurs objets en même temps
C'est pourquoi, vous devez renvoyer *true* si vous décidez de prendre l'appel.

L'algorithme de LibGDX sur les Listener est le suivant:
	aReponduVrai <- faux
	tant que non aReponduVrai
		Prendre le Listener suivant
		aReponduVrai <- executer listener
	fin_tant
ici, on voit donc clairement que les listener suivants ne seront pas exécutés si votre listener répond *true*

### Ajouter des listeners ###
En LibGDX, il y a une classe pour placer plusieurs listeners en même temps: InputMultiplexer.
Le InputProcessor est l'objet qui exécute l'algorithme décrit précédemment.

Pour placer les listeners, il suffit de faire:

 * Cas où on place un listener:

 	Listener list = new Listener();
	Gdx.input.setInputProcessor(list);

 * Cas où on place plusieurs listeners

	Listener list1 = new Listener();
	Listener list2 = new Listener();
	InputMultiplexer process = new InputMultiplexer(list1, list2);
	Gdx.input.setInputProcessor(multiplexer);
	
A noter que l'algorithme précédent prend en compte l'ordre dans lequel vous placez les Listeners. L'algorithme sera donc ici:

	aReponduVrai <- execution_list1;
	si NON aReponduVrai alors
		aReponduVrai <- execution_list2
		si NON aReponduVrai alors
			aReponduVrai <- execution_list3 ...
		fsi
	fsi


## Polling ##
Le callback est un système performant et simple à mettre en place. De plus, il peut être intéressant de créer des listeners dans des fichiers séparés pour respecter de design partern M.V.C.
Cependant, l'exécution de callback trop répétitif peut baisser les performances de votre application. Par exemple, une callback exécutée à chaque fois que l'accélérométrie change est une mauvaise idée, car le moindre mouvement de la part de l'utilisateur entraîne plusieurs dizaines d'exécutions.  

C'est pourquoi il existe une autre manière d'interagir avec l'utilisateur: le **Polling**  
Le polling est une manière plus bas niveau de récupérer l'information. Le polling consiste à noter tous les événements entre deux rafraîchissement de l'image.  
Ensuite, à chaque rafraîchissement (dans la méthode render), on peut demander à la machine:

 * A t-on cliqué?
 * L'accéléromètre a-t-il bougé ?
 * a-t-on déplacé la souris ?
 * ...

Le polling consiste donc en une série de conditions, afin de connaître les changements de l'application entre deux rafraichissements de l'image:

	if(Gdx.input.isKeyPressed('A')) {
		/* execution si on appuie sur A */
	}
	if(Gdx.input.isDragged()) {
		/* execution si on a deplacé la souris en cliquant */
	}
	if(isTouched(0)) {
		/* execution si au moins un doigt est placé sur l'ecran */
	}


Ainsi, on peut voir que le polling est une manière simple, et souvent plus rapide de réaliser une interaction avec l'utilisateur.  
Cependant, il ne faut pas abuser du polling, car:

 * il n'est pas M.V.C, et peut rapidement salir votre code source,
 * il peut entrainer des problèmes de concurrence sur les différents objets,
 * sans rafraichissement automatique de l'écran, pas de polling. Or le rafraichissement automatique de l'écran n'est pas toujours utile (dans les menus notamment).

Il faut donc utiliser le polling avec parcimonie, lorsqu'il est nécessaire. c'est par exemple le cas de l'accéléromètrie.


## Conclusion ##
LibGDX implémente deux façons de gérer les interactions avec l'utilisateur. Il faut connaître les différences entre ces deux méthodes, et les implémenter correctement, en tenant compte des besoins de l'application, ainsi que des spécificités de la machine.  
En particulier, tentez de toujours implémenter des listeners lorsque celui-ci n'est pas trop répétitif. En particulier, l'accéléromètrie est un bon exemple d'implémentation en polling.
