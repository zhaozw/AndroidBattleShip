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


# Notion de base d'intelligence artificielle #


N.B. Ce document n'est en aucun cas un cours sur l'intelligence artificielle. Il ne fait qu'exprimer les notions vues ou aperçues lors de ce projet, et qui ont été utiles.  
Cette page ne prétend donc pas apprendre à réaliser une intelligence artificielle de haut niveau, mais à donner une première approche de la décision pour un programme léger.  
Ici, on veut montrer qu'il peut être simple de réaliser une Intelligence Artificielle capable de jouer, voir de battre un utilisateur humain _peu averti_

## Une Intelligence Artificielle simpliste ##
Tout d'abord, réalisons une I.A. simpliste pour nous servir de référence: Random.  
La stratégie random (exprimée ici par "je choisis au hasard une case à torpiller") a le mérite d'être particulièrement simple à implémenter. Pour trouver la case à torpiller nous utilisons:

	int x = Math.random() * NB_CASES;
	int y = Math.random() * NB_CASES;
	return new Coord2D(x, y);

Cette I.A. est en effet très mauvaise. Dans le cas d'une bataille navale, il ne tient pas compte du fait que les cases à couler qui appartiennent au même bateau sont contigües. Ainsi, l'I.A., lorsqu'elle touchera un bateau, ne cherchera pas à la couler.  

## Intelligence Artificielle à base de conditionnelle ##
Partons maintenant de ce principe: essayons de couler un bateau entier. Rappelons que lorsqu'on touche un bateau, il y a au moins 1 case qui lui est accolée (un bateau a une taille d'au moins 2).  
Cet algorithme peut se résumer de la façon suivante:
	s'il reste des cases accolées alors
		tester la prochaine case accolée
	sinon
		jouer une case au hasard, parmi les cases non essayées
	finSi

	si j'ai touché, alors
		retenir toutes les 4 cases qui lui sont accolées
		supprimer les cases déjà testées
	finSi

Ici, l'algorithme reste simple, et ne demande que peu de ressources:
 * Une List des cases à tester (car accolées à une case qui est "touchée")
 * Une List des cases déjà testées
De plus, l'algorithme ne prend que très peu de calcul.  
On voit pourtant qu'il est plus efficace qu'un algorithme Random, car il est certain de couler un bateau à partir du moment où il touche une des cases sur lesquelles il se trouve.  
Pourtant, cet algorithme reste améliorable: Si le bateau est de taille 2, l'algorithme va tester 8 cases, bien qu'il l'ait déjà potentiellement coulé (et ceci afin de vérifier qu'aucun bateau n'est accolé à un autre).

### Améliorations spécifiques ###
N'oublions pas que nous jouons à la bataille navale. dans ce jeu, il y a quelques règles dont nous pouvons tirer parti:
 * Les bateaux ont une taille de 2 à 5 cases,
 * Les bateaux sont soit à l'horizontale, soit à la verticale,
 * Il y a un bateau de 2 cases, deux de 3 cases, un de 4 cases et un de 5 cases.

Des deux premières règles, on peut conclure qu'en essayant une case sur deux, on est sûr de trouver tous les bateaux (car les bateaux sont au moins de taille 2). Il faut donc changer le choix de la case: il ne faut plus tester une case au hasard, mais une case au hasard parmi une case sur deux. Avec cet algorithme très simple, on gagne en un nombre de coups environ égal à  
nombre de cases / 2 - nombre de cases à couler.


## Intelligence Artificielle à base de connaissance ##
Passons à une autre forme d'Intelligence Artificielle: par connaissance.
Le principe est simple: se référer à des données d'une "vraie partie"; c'est-à-dire d'une partie entre deux joueurs réels.  
Pour se faire, vous devez jouer, et enregistrer les moindres faits et gestes des joueurs. Ensuite, l'algorithme se sert des probabilités pour jouer le coup "le plus probable".

Il vous semble peut-être difficile de croire que les statistiques sur d'autres joueurs permet de comprendre votre façon de jouer. Pourtant, si l'on prend des milliers de parties faites par des milliers de joueurs, il y a des chances pour que vous suiviez inconsciemment la même façon de jouer que beaucoup d'autres.  

Pour mieux vous convaincre, nous allons prendre un exemple: pierre-feuille-ciseaux (chifumi)  
Ce jeu paraît totalement aléatoire: vous choisissez au hasard parmi l'une des trois figures, et vous gagnez ou perdez en fonction du choix de votre adversaire.  
Pourtant, un algorithme à base de connaissances peut vous battre à ce jeu. [voir cette page](http://www.nytimes.com/interactive/science/rock-paper-scissors.html?_r=0).

### Comment ça marche ? ###
Tout d'abord, comprenez bien que cet algorithme ne fonctionne que si vous avez des milliers d'entrées dans votre base de données. Vous ne pouvez espérer avoir un résultat satisfaisant avec seulement quelques parties.  
Ensuite, l'ordinateur évalue la probabilité de vos actions, sachant vos actions précédentes.
Dans le cas de chifumi, l'ordinateur procéde tel quel:

 * Il prend, dans l'ordre la liste de vos actions (prenons pour exemple Pierre, Pierre, Papier)
 * Il regarde dans sa base, il fait le calcul suivant:
 	* papier = nombre de personnes qui ont joué Pierre Pierre Papier ET qui ont joué Papier ensuite / nombre de personnes qui ont joué Pierre Pierre Papier
 	* pierre = nombre de personnes qui ont joué Pierre Pierre Papier ET qui ont joué Pierre ensuite / nombre de personnes qui ont joué Pierre Pierre Papier
 	* ciseaux = nombre de personnes qui ont joué Pierre Pierre Papier ET qui ont joué Ciseaux ensuite / nombre de personnes qui ont joué Pierre Pierre Papier
 * Il prend la valeur la plus forte; si c'est ciseaux, alors il va supposer que votre prochain coup est ciseaux. 

 * Ensuite, il recommence, mais il inclut dans les actions précédentes votre dernier coup. si en fait vous aviez joué Papier, il va regarder les personnes ayant joué Pierre Pierre Papier Papier.

Plus vous jouez, et plus vous avez de chance que l'algorithme trouve votre prochaine action.


## Conclusion ##
Comme vous avez pu le constater, nous n'avons pas montrer de méthodes compliquées ou techniques sur l'Intelligence Artificielle. Nous montrons ici qu'un jeu-vidéo peut utiliser une I.A. simple à développer, mais qui va battre certains joueurs. Bien sûr, d'autres I.A. sont bien plus perfectionnées, comme ceux des jeux d'échecs par exemple.  
Pourtant, il est raisonnable de penser qu'une I.A. simple et bien pensée, c'est-à-dire qui s'adapte aux règles du jeu, peu suffir lors de l'implémentation d'un jeu vidéo tel que celui présenté.
