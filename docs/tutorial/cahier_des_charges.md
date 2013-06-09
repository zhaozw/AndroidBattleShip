##### Cahier des Charges ##### 
=================
Le but de ce projet est de réaliser un jeu-vidéo 2D fonctionnant sous système d'exploitation Android.
Plus précisément, il s'agit de créer un jeu de bataille navale en réseau, P2P, utilisant le framework LibGDX pour le compte du Club-Média-Mobile.
Le but de cette réalisation est non seulement de créer un jeu vidéo, mais aussi et surtout de montrer le principe du framework LibGDX par le biais d'un développement clair, largement commenté, ainsi qu’une documentation explicitant les fonctionnalités utilisées.

#### Déroulement du Jeu #####
Au lancement d’une partie, les deux joueurs placent leurs bateaux. Lorsque les deux joueurs ont terminés, un tirage au sort est effectué pour déterminer lequel des deux joueurs commence la partie.
S’en suit alors une bataille navale, avec les règles qui lui sont connues :
Tour à tour, chaque joueur choisit une case. L'interface graphique permet de montrer si ces cases contiennent un bateau ou non.
La partie est terminé lorsque tous les navires d'un joueur sont coulés, c'est-à-dire lorsque toutes les cases contenant un morceau de navire ont été touchées.
L’application doit permettre de jouer une partie de bataille navale classique:
Placer ses bateaux sur le plateau de jeux, représentant la mer.
Tirer pour essayer de couler les bateaux de l’adversaire.
Chaque joueur joue tour à tour jusqu’à ce que la flotte adversaire soit réduite à néant.

#### Mode de jeu: ####
Le jeu comporte un mode “1 Joueur”, c’est-à-dire un mode où le joueur joue contre la machine.
Le joueur pourra choisir entre 3 modes de difficultés (facile, moyen, difficile). Ces modes devront suivre un algorithme laissé au choix des développeurs du projet.
Le jeu comporte un mode “2 Joueurs”. Ce mode nécessite que les machines soient connectés sur un serveur pour jouer l’un contre l’autre. Le but d’une partie en réseau et similaire à celle d’une partie contre la machine. Le serveur (au sens logiciel du terme) doit également être développé dans ce projet.

#### Documentation ####
Le projet devra contenir plusieurs formes de documentations:
Une documentation Markdown résumant les fonctionnalités du framework LibGDX utilisé pour le développement de cette application.
La javadoc de chaque fonction du projet.
Tout commentaire utile à la bonne compréhension du projet, en particulier concernant les parties liées à la bibliothèque LibGDX.
La documentation devra donc couvrir:
La création d'un projet LibGDX.
Le principe de cycle de vie Android.
L'aspect graphique (menu, images...).
Le système d'interaction utilisateur.
L'aspect réseau.
L'accéléromètre.
