====== Créer un projet LibGdx ======

Dans cette page, vous trouverez tout ce qu'il vous faut pour créer un projet libgdx.
En réalité, un projet LibGDX est constitué d'au maximum 4 projets:
 * un projet ``global`` dans lequel vous écrirez 99% de votre code source
 * un projet ``android`` dans lequel figure tous les fichiers propres à une application Android (notamment le Manifest)
 * un projet ``desktop`` qui lancera votre application sur votre ordinateur (dans une fenêtre, comme n'importe quel autre programme desktop)
 * un projet ``html`` qui fonctionnera sous navigateur.

libGDX est donc une abstraction des modèles PC, Android, et HTML. Cela signifie que le framework gère pour vous les différences entre les systèmes et compile convenablement à votre place. Les projets android, desktop et html utilisent tous les fichiers compilés du projet global.
C'est donc dans ce projet global qu'il faut écrire quasiment tout le code. La seule chose à placer dans les autres projets sont les quelques lignes qui permettent de différencier les différents projets. Par exemple, il faudra parfois modifier le Manifest pour le projet Android.


Attention: nous déconseillons (comme certains tutoriels et documentations parcourus par nos soins sur le WEB) la création du projet HTML. en effet celui-ci comporte de nombreuses différences dérangeantes avec les autres projets:
 * Il est ``stateless`` de par son implémentation
 * Il ne permet pas la création et l'ecriture de données dans des fichiers.
 * Il utilise des notions et un langage différents. On parle par exemple de Javascript pour ne citer que celui-ci. Ce n'est pas problèmatique en soi, mais il semble ralentir l'application.

 Pour les raisons évoquées ci-dessus, le projet ne comporte pas de version HTML, et aucun code source spécifique à HTML ne sera expliqué ou utilisé pour la suite. Dans l'explication ci-dessous (comment créer un nouveau projet), un seul point facultatif sera donné dans le cas où vous souhaiteriez tout-de-même créer une version HTML.


==== Créer l'ensemble des projets ====
Une application Java (sous forme JAR) a été développée par les concepteurs de LibGDX afin de réaliser facilement un nouveau projet. Pour l'utiliser, il suffit de taper la ligne de commande:
	java -jar gdx-setup-ui.jar
Le programme se lance alors. Cliquez sur ``create``, puis remplissez les champs:
 * Name: le nom de l'application. Notre projet s'appelle ``BatailleNavale``, c'est pourquoi nous avons obtenu les projets:
 	* BatailleNavale
 	* BatailleNavale-android
 	* BatailleNavale-desktop
 * Package: le package ``par défaut``. Pour des raisons de portabilité sous android, il faut réspecter la règle d'au moins 3 packages. Dans notre exemple il s'agit de ``cmm.android.bataillenavale``
 * Game Class: le nom de la classe jouant le rôle de point d'entrée. Nous l'avons appelé ``BatailleNavale``.
 * Destination: par défaut, il s'agit du chemin où se trouve le fichier jar. Vous pouvez le modifier à votre gré.
 * les cases à cocher vous permettant de générer ou non les projets pour desktop, android et html. Comme expliqué précédemment, nous avons décider de ne pas créer de projet HTML et l'avons donc décoché.
Enfin, vérifiez que le chemin vers LibGDX est bien remplit dans la section 2. Si ce n'est pas le cas, il faut simplement le lui donner.

Lorsque tous les champs sont correctement remplis (vérifiez qu'il n'y a pas d'erreur dans le menu de droite), cliquez sur ``open the generation screen``. Cliquez ensuite sur ``generate``. Votre projet est alors créé!
Reste tout-de-même à l'importer dans votre IDE préféré!


Note: il peut être astucieux de mettre les trois projets dans un même dossier parent et non directement dans un dossier où vous rangeriez tous vos projets. Une arborescence simple et pratique, que nous utilisons pour ce projet est celle-ci:
 * BatailleNavale/ # le seul dossier placé directement dans mon dossier de projets en tout genre.
 	* BatailleNavale/ # le projet ``global`` généré automatiquement
 	* BatailleNavale-android/ # le projet ``android``
 	* BatailleNavale-desktop/ # le projet ``desktop``
 	* data/ # les ressources utiles à tous les projets (images, polices ...)
 	* docs/ # la documentation du projet.
 	* ...
Comme on peut le constater, cette arborescence permet de stocker sa documentation, commune aux 3 projets, dans le dossier parent de ceux-ci. De plus, les ressources seront toujours placées dans un dossier ``data`` commun aux 3 projets. On peut ensuite recopier les ressources dans le bon dossier pour chaque projet en utilisant un script shell très simple (voir fichier ``putDatas.bs``).


==== Importer les projets dans un IDE ====
Tout d'abord, il est à noter qu'il parait difficile, voire impossible de programmer un jeu-vidéo Android sans Environnement de Développement Integré (IDE). En effet, celui-ci se chargera pour vous de compiler d'abord le projet global, puis d'utiliser ces fichiers de compilation pour les autres projets, et d'installer sur votre smartphone/tablette l'application. Bien que nous utilisons Eclipse poru le développement de notre jeu de Bataille Navale, il est à priori possible d'utiliser n'importe quel IDE supportant les projets Android. Vous pourrez donc utiliser NetBeans par exemple.

=== Importer les projets dans Eclipse ===
 * Executez Eclipse,
 * file -> import
 * Dans la fenêtre qui s'ouvre, choisissez ``Existing Project Into Workspace``
 * Donner le chemin vers votre projet, puis cliquez sur ``Finish``
 * Appliquer cette méthode pour les trois projets.

Note: si vous utilisez l'astuce décrite plus haut, vous pouvez donner le chemin vers le dossier parent; eclipse importera directement les 3 projets.

==== Executer le projet ====
Parmi les trois projets, seuls deux sont executables (le projet global ne servant que de "dépôt" commun pour le code source).
 * le projet desktop peut être executé directement en utilisant la classe Main. Une fenêtre s'ouvre alors, et vos programme s'executent.
 * le projet android doit être installé sur une machine Android pour être executé. Comme pour les autres projets android, vous devez installer un emulateur, ou branchez une machine en USB sur votre ordinateur, et executer votre programme sous eclipse, afin que celui-ci installe tout ce qui est nécessaire.