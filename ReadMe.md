# <p align="center">Projet programmation mobile</p>

Notre projet de programmation est une application regroupant 6 mini-jeux utilisant différents capteurs. Ce projet a été conçu dans le cadre du module de programmation mobile à l'ESIR. 

Ce projet a été crée en Java avec Android Studio. Il est possible de jouer 
- en mode entraînement solo : s'entrainer aux mini-jeux sans conséquence sur le score
- en mode défi solo : enchaînement de trois mini-jeux aléatoires écran de fin signifiant la victoire ou l'échec avec une musique 
- en mode défi multijoueur : enchaînement de trois mini-jeux aléatoires écran de fin signifiant la victoire ou l'échec avec une musique en fonction du score de l'autre 

# Participants 
### LE DET Noémie
### SENE Albin

# Mini-jeux 
Le score max pour chaque jeu est de 2000 points. Si le joueur est en mode solo il doit alors obtenir plus de 2/3 des points soit : 4000 points. 
## Light_sensor : (capteur de luminosité) 
Le joueur doit cacher son capteur de luminosité le plus vite possible pour gagner un maximum de points. 
#### Calcul score : 2000*mTimeLeftInMillis/START_TIME_IN_MILLIS

## Mega_ball : (accéléromètre)
Le joueur doit atteindre le point rouge le plus vite possible en inclinant son téléphone. 

#### Calcul score : 1000 + 1000*mTimeLeftInMillis/START_TIME_IN_MILLISestant

## Questionnaire : (questions-réponses) 
Le joueur doit répondre à des questions à choix multiples où la bonne réponse n'est pas toujours au même endroit. 
#### Calcul score : 2000*questionCorrecte/totalQuestion

## Speed_Questionnaire : (questions-réponses)
Le joueur doit répondre à des questions où seules deux réponses sont possibles en étant le plus rapide possible.  
#### Calcul score : 1000*questioncorrect/totalQuestion+1000*mTimeLeftInMillis/START_TIME_IN_MILLIS

## Decompte : (timer + affichage) 
 Le joueur doit estimer la bonne valeur du décompte qui est caché au bout de 5 secondes. 
#### Calcul score : 2000*( 1.0 - ((double) Math.abs(playerTime - condVictoire) / marge))
 
## Swipegame : (mouvement sur l'écran)
Le joueur doit réaliser la bonne série de mouvement sur l'écran le plus vite possible pour maximiser ces points.
#### Calcul score : 2000*mTimeLeftInMillis/START_TIME_IN_MILLIS
