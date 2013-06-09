===== Les Sons et Musiques =====

Avouez qu'un jeu sans musique est un jeu plutôt fade. L'ambiance d'un jeu-vidéo est primordiale pour être apprécié de ces utilisateurs.
C'est pourquoi LibGDX met à disposition deux classes très simples d'utilisation : Sound pour les sons, et Music pour la musique d'ambiance.
Si ces deux classes s'utilisent de la même manière, il est important de comprendre la différence entre les deux modes: ce n'est pas par hasard que LibGDX (ainsi que toutes les librairies de sons utilisées) fonctionnent différemment avec les sons et les musiques 

==== Une question de place mémoire ====

En effet, les sons représentent une ressource très lourde: plus lourde encore que les images et leur Texture.
Les sons (classe Sound) sont par définition un bruit court. Par exemple lorsque Mario attrape une pièce, lorsque vous perdez une vie dans la plupart des jeux, ou encore lorsque vous touchez un bateau dans notre jeu.
Ce sont des ressources lourdes mais nous pouvons tout-de-même les placer entièrement en mémoire, tout comme nous le faisons avec les Textures. 
Il faut simplement veiller à les ajouter ``au plus tard`` et les supprimer ``au plus tôt``.


Les musiques (class Music) sont non seulement des ressources lourdes, mais également très longues (plusieurs minutes). Si vous placez directement une musique de 5 minutes en mémoire, vous allez placer plusieurs Mo en mémoire. Pourtant, vous n'allez lire que quelques octets par seconde. C'est donc une méthode trop gourmande pour être viable dans un contexte où les ressources sont primordiales.
Il faut donc utiliser le Streaming: un thread va s'occuper de lire le fichier de musique et récupèrer seulement ce dont il a réellement besoin.
Rassurez-vous, grâce à LibGDX, c'est bien plus simple qu'il n'y parait.


==== Jouer un Son ====
Commençons par jouer un son.

Il faut:
 * Initialiser le son,
 * Le jouer au bon moment,
 * Le supprimer lorsqu'il n'est plus utilisé.

Pour l'initialiser, nous n'allons pas utiliser directement le constructeur (bien que nous pourrions) car le manager de LibGDX ne fonctionne que si on utilise la méthode ``GDX.Audio.newSound()``. Ce manager permet de faire dispose automatiquement lorsque le jeu est en pause (cycle de vie: handler on_pause).
	Sound sound = Gdx.Audio.newSound(Gdx.file.internal(...)); //initialisation au début
	sound.play(); //joue le son
	sound.dispose(); //Supprime le son de la mémoire.


==== Jouer une Musique ====
Jouer une musique est tout-à-fait similaire à jouer un son. LibGDX s'occupe lui-même de faire le streaming pour la musique. Le code est donc très similaire :
	Music music = Gdx.Audio.newMusic(Gdx.file.internal(...));
	music.play();
	music.setLoop(true); //Facultatif, vrai par défaut
	music.dispose();
