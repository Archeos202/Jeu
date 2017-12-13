# Jeu de Quentin Esteban et Michaël Tasev

1. Pour lancer le jeu, ouvrez le fichier "Program.java" en suivant "PhysicsGame [Projet2 master]" -> "src" -> "main" -> "java" -> "ch" -> "epfl" -> "cs107" -> "play" -> "Program.java". Vérifiez que la ligne 34 soit bien "Game game = new BikeGame();", et lancez (run) le programme.

2. Pour contrôler le cycliste, vous avez à disposition plusieures touches du clavier :

	"UP" 		Permet d'avancer dans le sens du regard du cycliste (à noter que seule la roue arrière est motrice).

	"DOWN"		Bloque les deux roues pour freiner.

	"LEFT" 		Permet de faire pivoter le cycliste sur la roue gauche, autrement dit de lever la roue droite (indépendemment du sens du regard du cycliste).

	"RIGHT" 	Permet de faire pivoter le cycliste sur la roue droite, autrement dit de lever la roue gauche (indépendemment du sens du regard du cycliste).

	"ESPACE" 	Change le sens du regard du cycliste.

	"SHIFT"		Permet de sauter si au moins une des roues touche le sol.

	"R" 		Recommence le jeu à la position initiale.
	
	"B"		Lance une banane derrière la cycliste, dépendemment du sens de son regard.
		
	"C" 		Change les contrôles "UP", "DOWN", "LEFT", "RIGHT" et "SPACE" comme suit :
		"UP" 	Fait lever la roue avant du cycliste, dépendemment du sens de son regard.
		"DOWN" 	Fait lever la roue arrière du cycliste, dépendemment de la direction dans laquelle il regarde.
		"LEFT"	Fait avancer le cycliste vers la gauche en changeant éventuellement le sens du regard du cycliste.
		"RIGHT" 	Fait avancer le cycliste vers la gauche en changeant éventuellement le sens du regard du cycliste.
		"ESPACE"	Bloque les deux roues pour freiner.

C'est maintenant qu'on peut s'amuser. 
Dès le début, il y aura un arc derrière le cycliste qui va lancer une flèche lorsque vous allez appuyer sur "UP", et la seule manière de ne pas mourrir est de lever sa roue arrière jusqu'à ce qu'elle soit à la hauteur de la flèche et de l'activer à ce moment, pour que la flèche rebondisse sur votre roue (attention tout de même à ne pas trop lever la roue arrière, le vélo pourrait se faire retourner).

Le premier piège passé, vous devrez passer au dessus d'une caisse fixée au sol, et vous arriverez devant un bouton rouge. En le touchant, des caisses vont vous tomber dessus, deux choix s'offrent donc à vous : soit vous continuez de rouler le plus vite possible et évitez ainsi les caisses, soit vous sautez et évitez de toucher et donc d'actionner le bouton (pour cela, vous devrez vous déplacer sur la roue arrière avant de sauter, sans quoi il n'est pas possible de sauter au dessus du bouton).

Il suffit ensuite de suivre le parcours qui ne comporte pas de difficulté apparente en vous aidant ou non du saut, à choix de style (vous pouvez tout à fait poser des back/frontflips). Lorsque vous arrivez proche du ravin, vous observez un terrain différent, c'est un terrain glissant. Le problème est dû au bouton bleu qui, au contact, fait apparaître un canard géant qui vous écrase. Il faut donc ralentir avant de descendre pour pouvoir revenir vers le drapeau et ainsi gagner la partie (le terrain n'est glissant qu'a 90%, ce qui permet de modifier sa trajectoire mais il ne faut pas arriver trop vite).

Si vous vous sentez imbattable, vous pouvez essayer de sauter par dessus le bouton bleu (un peu plus complexe que sur un sol non-glissant), et vous pourrez voir une grenouille qui vous attend. Si vous passez devant elle sans y prêter attention (ce n'est pas très sympa) vous arriverez devant un fantôme semblable à celui que vous voyez au tout début du niveau, ils sont là pour délimiter le terrain. Aussi, si vous revenez vers le drapeau et gagnez la partie, vous pourrez retourner voir la grenouille qui se sera magiquement méthamorphosée en princesse.