# game-jam-mai-2023
Mon jeu de la game jam de mai 2023 du club Pixel de l'Isima

Durant cette game jam j'ai réalisé ce jeu sur le thème "Cellule".

J'ai réalisé un jeu seul en java. La game jam a duré 2 jours et j'ai donc du aller assez vite pour
incorporer toutes les fonctionnalités que je souhaitais.

Il s'agit d'un jeu multijoueur en réseau.
L'application comporte donc 2 modes : un mode "Host" pour héberger une partie et un mode "Join" pour se connecter à un hôte.

Le jeu comporte quelques images dans le dossier 'res' qu'il faut télécharger dans le même dossier que le fichier .jar pour lancer le jeu.

Dans ce jeu, le joueur incarne un organisme qu'il va faire évoluer. Tout d'abord le joueur possède de l'énergie.
Lorsque le joueur perd lorsqu'il n'a plus d'énergie.
Pour se développer, le joueur possède des cellules souches qui apparaissent aléatoirement avec le temps.
Les cellules souches peuvent se transformer en différentes autres cellules : muscululaires, neuronales, végétales ou carnivores.
Cependant, toutes les cellules, hormis les cellules souches, consomment de l'énergie.

Tout d'abord, les cellules végétales produisent de l'énergie à partir de rien.
Ensuite, les cellules carnivores produisent de l'énergie lorsque le joueur est sur un autre joueur (il le "mange") ou sur de la nourriture
(que nous verrons après).
Les cellules neuronales permettent de débloquer des évolutions dans le panneau "Evolution".
Enfin, les cellules musculaires qui enlèvent de l'énergie aux joueurs adverses lorsque le joueur est sur les autres joueurs.

Lorsqu'on transforme les cellules souches, la taille du joueur augmente.
On peut également changer le nom du joueur en bas.

Dans l'onglet "Evolution", on peut choisir des bonus en payant avec des neurones.
L'évolution de photosynthèse permet de produire plus d'énergie avec les cellules végétales.
L'évolution de division cellulaire permet d'augmenter le nombre de cellules souches qui apparaissent.
L'évolution de digestion diminue la consommation des cellules carnivores.
L'évolution de dash permet au joueur d'accélérer en payant un peu d'énergie en appuyant sur espace.

Dans l'onglet "Carte", on peut voir la carte du monde avec la nourriture, représenté par des carrés jaunes.
On peut aussi voir les autres joueurs avec leurs surnoms.
Il n'y a pas de limites dans la carte mais comme il n'y a pas de caméra non-plus, il est préférable de rester dans la zone de départ.

Pour lancer la partie, la personne qui héberge la partie doit appuyer sur le bouton "Start"
Elle peut également contrôler le nombre de nourriture présente sur la carte.

Le jeu ne comporte pas vraiment de fin. Lorsqu'un joueur perd, il recommence du début.


