Conception :
- Rien à signaler concernant l'architecture utilisée, nous avons utilisé celle fournit (nous avons cependant ajouté un paramètre ghost à la classe crate)
- Nous avons créé les classes de base : Terrain, Bike, BikeGame, Finish et Wheel
Nous avons créé, en plus : 
- Des nouveaux contrôles dans Bike pouvant être interchangés avec les anciens en appuyant sur "C"
- Toujours dans Bike (et en utilisant Wheel), la possibilité de faire un saut
- Encore une fois dans Bike, de nouvelles méthodes lui permettant de lever les bras quand il gagne et de pédaler  
- Une nouvelle classe Banane utilisée pour permettre de lancer des bananes en appuyant sur "B"
- Une classe Terrain Glissant qui diffère du Terrain classique par une friction bien moindre
- Une nouvelle classe Arrow qui nous permet de lancer une flèche au depart en appuyant sur "UP"
- Une nouvelles classes BoutonCrate qui est une sorte de "GhostCrate" qui réagit aux collisions et permet d'enclencher des événements quand il est touché 

Concernant la méthode pedal(deltaTime) de Bike.java (l. 363), la position des genoux suit une diagonale plutôt qu'un arc de cercle, rendant ainsi le pédalement non parfait mais amplement suffisant.