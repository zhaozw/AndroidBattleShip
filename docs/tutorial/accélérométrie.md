===== Android et les particularités de la machine =====

Nous sommes tous d'accord pour dire que Android est une technologie nouvelle, et qui apporte son lot de spécificités.
Outre son cycle de vie particulier, il y a également son matériel tout-à-fait différent d'un ordinateur classique:
 * Accélérométrie
 * GPS
 * bousole
 * etc.

Le framework LibGDX instancie ces divers materiels afin de nous permettre de les utiliser rapidement.  
Nous prendrons ici un exemple: l'accélérométrie.

==== Accélérométrie ====
Pour rappel, l'accélérométre est ce qui permet de connaître __l'inclinaison__ de la machine.  
Le but est de connaître cette inclinaison, afin de faire réagir le programme en fonction de cette accéléromètre.

==== LibGDX et l'accélérométrie ====
Comme expliqué précédemment, LibGDX permet de réaliser facilement un travail avec l'accélérométrie:

    float x = Gdx.input.getAccelerometerX();
    float y = Gdx.input.getAccelerometerY();
    float z = Gdx.input.getAccelerometerZ();

On s'aperçoit ici que LibGDX nous offre une version 3 dimensions de l'inclinaison de la machine.  
Observons maintenant les résultats:
 * les données sont des float (nombres décimaux, positif ou négatifs)
 * les valeurs sont comprises entre -2.f et 2.f
 * 0 est la valeur tel que la tablette est posée à plat (sur une table par exemple)

Nous pouvons donc connaître rapidement l'inclinaison de la tablette:
    if(x > 0) {
    	System.out.println("vous êtes un peu à gauche");
    }
    else if(x < 0) {
    	System.out.println("vous êtes un peu à droite");
	}
	else {
		System.out.println("Vous êtes tout-à-fait au centre");
	}
A noter que le else ("Vous êtes tout-à-fait au centre") ne s'appliquera probablement jamais: les valeurs étant en float, il se peut que l'approximation vous donne 0.000001 plutôt que 0 !

==== LibGDX et la gestion pour PC ====
Rappelons-nous que LibGDX permet de développer pour systèmes Android ainsi que pour système PC. 
Que se passe t-il si nous tentons de connaître la valeur de l'accéléromètre sur un système qui n'en possède pas?  
Tout simplement: la méthode retourne 0!
Ainsi, nous pouvons reprendre notre exemple, en l'améliorant un peu:

	if(x < 0.0001 && x > - 0.0001) {
    	System.out.println("vous êtes parfaitement à gauche");
    }
    else if(x < 0) {
    	System.out.println("vous êtes un peu à droite");
	}
	else if(x > 0) {
		System.out.println("vous êtes un peu à gauche");
	}
	else {
		System.out.println("Vous n'avez pas d'accéléromètre ...");
	}


==== Listening VS Polling ====
Remarquez ici que l'événement n'est pas un listener. Ainsi, aucun mécanisme ne se déclenche lorsque l'accéléromètre change (heureusement, ou il se déclencherait tout le temps !)  
Ici, nous utilisons la technique de polling: nous obtenons le résultat lors d'un appel explicite grâce à une méthode.
Afin de connaître ce résultat à chaque étape, il convient donc de l'utiliser dans la méthode _render_

    public void render(float delta) {
    	float x = Gdx.input.getAccelerometerX();
		if(x < 0.0001 && x > - 0.0001) {
	    	System.out.println("vous êtes parfaitement à gauche");
	    }
	    else if(x < 0) {
	    	System.out.println("vous êtes un peu à droite");
		}
		else if(x > 0) {
			System.out.println("vous êtes un peu à gauche");
		}
		else {
			System.out.println("Vous n'avez pas d'accéléromètre ...");
		}
	}
