Conception :
- rien à signaler concernant l'architecture utilisée, nous avons utilisé celle fournit (nous avons cependant ajouté un parametre ghost a la classe crate)
- Nous avons créer les classes de base : Terrain, Bike, BikeGame, Finish, Wheel
Nous avons créer, en plus : 
- des nouveaux controles dans bike pouvant etre interchangés avec les anciens en appuyant sur C
- toujours dans bike (et en utilisant wheel), la posibilité de faire un saut
- Une nouvelle classe Banane utilisé pour permettre de lancer des bananes en appuyant sur B
- Une classe Terrain Glissant qui difère du Terrain classique par une friction bien moindre

Concernant la méthode pedal(deltaTime) de Bike.java (l. 363), la position des genoux suit une diagonale plutôt qu'un arc de cercle, rendant ainsi le pédalement non parfait mais amplement suffisant.