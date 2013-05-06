===== Gérer le cycle de vie =====
Le cycle de vie est quelque chose de fondamental dans toute application Android. Gére convenablement le cycle de vie de votre application vous assurera de:
 * ne pas perdre de données,
 * rendre le jeu-vidéo moins gourmand en batterie et en ressources (mémoire, CPU ...)
 * éviter des bugs difficiles à comprendre.

Naturellement, LibGDX est fait pour gérer le cycle de vie de manière pratique. Regardons les méthodes à réimplémenter dans la classe Screen :

TODO.

On se rend compte que les méthodes suivent le cycle de vie Android:
 * show: simule le handler on_pause
 * hide: simule le handler on_hide
 * resume: simule le handler on_resume
 * pause: simule le handler on_pause
 * render: Permet le rafraichissement de l'ecran
 * dispose: Permet la libération de la mémoire pour les ressources lourdes

Comme nous pouvons constaté, les handler du cycle de vie sont repris par les différentes méthodes pour chaque Screen. Il est important de placer les bonnes instructions dans les bonnes méthodes, sans quoi vous rencontrerez des problèmes difficiles à résoudre (comme par exemple la perte de données lorsque l'utilisateur décide de masquer votre jeu-vidéo pour lancer une autre application).
Un adapter à été écrit pour vous prémunir contre ce type de problème : CMMScreenAdapter. Pour utiliser CMMScreenAdapter, vous devez:
 * Initialiser les ressources lourdes dans initialize()
 * disposer ces même ressources lourdes dans dispose()
 * écrire la méthode render() pour gérer l'affichage.
 * écrire les méthodes tmpSave() et tmpLoad(), respectivement pour sauvegarder les données (la partie Modele du patern MVC) dans le cache, et charger ensuite ces mêmes données.

Concrétement, lorsque l'utilisateur demande à afficher le jeu (on_show, on_resume) les ressources sont chargées. Dans le cas de on_resume, le modèle peut être chargé grâce à tmpLoad()
Lorsque l'utilisateur demandee à masquer le jeu (on_hide, on_pause), les ressources sont libérés, et les données sont placées en cache grâce à tmp_load.
Par défaut, LibGDX tente de gérer le problème de la perte de données. Cependant, si vous pouvez éviter de tout sauvegarder puis tout charger, il est préférable de réimplémenter les méthodes tmpSave et tmpLoad.

De plus, l'utilisation des sprites et des ressources étant particulièrement fréquente, CmmScreenAdapter vous propose de gérer celles-ci automatiquement:
 * ajouter un Sprite dans l'ArrayList sprites et celle-ci s'affichera automatiquement
 * ajouter une Texture dans l'ArrayList textures et celle-ci sera libérée automatiquement (ce qui ne vous dispense par de l'initialiser dans la fonction initialize)
Bien sûr vous pouvez laisser CmmScreenAdapter afficher vos sprites tout en affichant d'autres choses. Il suffit de réimplémenter la méthode et d'utiliser ``super`` :

public void render(float time) {
	super.render(time);
	/* début de votre propre affichage */
	...
	/* fin de votre propre affichage */


}
