
#### title: "Konzeptskizze für Zyklus 1"

#### author: "Bjarne Zaremba, Lukas Landmann, David Stevic"



# Beschreibung der Aufgabe

## Aufgabe 1 - Fallen

In dieser Aufgabe sollen Fallen in den Dungeon implementiert werden. Diese sollen das Voranschreiten im Dungeon schwieriger machen. Die Fallen sollen verschiedene Wirkungen beim Helden oder beim Monster auslösen. 

## Aufgabe 2 - Game-Over

In dieser Aufgabe soll man den Spielertod implementieren. Wenn der Held stirbt, dann soll eine Meldung "Game Over" auf dem Bildschirm erscheinen. Danach soll der Spieler eine Auswahlmöglichkeit bekommen, ob er das Spiel beenden oder neustarten möchte. 

## Aufgabe 3 - NPC-Ghost

In dieser Aufgabe soll ein NPC-Ghost implementiert werden. Dieser soll als NPC den Spieler verfolgen und zusätzlich zufällig durch das Level "fliegen". Außerdem soll ein Grabstein generiert werden. Wenn der Spieler den Grabstein findet, soll dieser dafür belohnt bzw. bestraft werden. 

# Beschreibung der Lösung

## Aufgabe 1 - Fallen

Wir wollen zwei Arten von Fallen erstellen. Bei der einen bekommt der Hero eine zufällig generierte Anzahl an Schaden. Bei der anderen Art der Falle wird der Hero an einen zufälligen Ort im Level teleportiert. Außerdem gibt es eine gewisse Anzahl an Schaltern, die benutzt werden können, um eine Falle zu deaktivieren. Die Falle soll als bodenähnliche Druckplatte auf dem Boden zu sehen sein. 

## Aufgabe 2 - Game-Over

Wenn die Health-Points des Spielers (aus welchem Grund auch immer) auf 0 fallen, stirbt der Spieler, d.h. es wird ein "Game Over"-Screen (ähnlich wie das Pause-Menu) eingeblendet. Dort erscheinen dann noch zusätzlich zwei Buttons. Mit diesen kann der Spieler auswählen, ob er das Spiel beenden oder neustarten möchte. Beim Beenden wird die Java-Applikation ordnungsgemäß nach beendet. Beim Neustart wird ein neuer Run gestartet. 

## Aufgabe 3 - NPC-Ghost

Wir wollen einen Geist in Form eines verstorbenen Heros implementieren (also ein Hero-Skin mit weißer Umrandung). Dieser kann den Spieler anhand seiner Koordinaten verfolgen. Zusätzlich kann dieser frei durch das Level wandern. Dieser soll in jedem Level spawnen. Wenn der Spieler den dazugehörigen Grabstein findet, soll er mit einem Wahrscheinlichkeitsverhältnis von 70%/30% belohnt/bestraft werden. Als Belohnung/Bestrafung werden dem Spieler HP gegeben bzw. abgezogen.

# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). 


# Ansatz und Modellierung

## Aufgabe 1 - Fallen

Wir erstellen eine `Fallen`-Klasse, die von `Entity` erbt. Diese hat die grundlegenden Komponenten einer Falle. Diese sind:
- HitboxComponent (der Hero bekommt ebenfalls eine HitBoxComponent, damit eine Kollision zwischen zwei Entities möglich ist)
- PositionComponent (wir nehmen den Konstruktor, der die Falle an eine zufällige Position setzt)

Die Fallen Klasse hat folgende Attribute:
- `int damage` : Gib die HP an, die beim Betreten der Falle abgezogen werden.
- `String pathToSkin` : Speicher den Pfad in dem das Bild für die Falle gespeichert ist. 
- `Hebel hebel`

Der Hero bekommt zusätzlich eine Methode `getHit()`, die ihm die entsprechende Anzahl an HP abzieht. 

Zusätzlich wird noch eine `Hebel`-Klasse implementiert. Wenn der Hebel benutzt wird, dann wird die zugehörige Falle deaktiviert. 
Der Hebel braucht folgende Components:
- InteractionComponent
- PositionComponent
- AnimationComponent

Der Hebel hat eine `toogle()`-Methode und ein `isToggled`-Attribut.

## Aufgabe 2 - Game-Over

Der Game-Over-Screen soll ähnlich wie das Pausen-Menu realisiert werden. Dazu legen wir eine neue `GameOver`-Klasse im Ordner Hud an. Außerdem wird die Methode `frame()` aus der `starter.game`-Klasse erweitert. Es soll bei jedem Neuladen des Screens die HP des Heros überprüft werden. Wenn diese unter 1 fallen, wird die `showGameOverMenu`-Methode aufgerufen. Der Spieler bekommt dann die Möglichkeit über 2 Tasten auf der Tastatur auszuwählen, ob er das Spiel beenden oder neustarten will. Dafür nutzen wir die `(Gdx.input.isKeyJustPressed())`-Methode.

## Aufgabe 3 - NPC-Ghost

Wir erstellen eine `Ghost`-Klasse, die von `Entity` erbt. Diese hat die grundlegenden Komponenten eines NPC-Ghosts. Diese sind:
- AnimationComponent (zur Animation des Ghosts)
- PositionComponent
- VelocityComponent
- AiComponent -> zum Verfolgen des Heros

Folgende Methoden sollen implementiert werden:
- `setupVelocityComponent()`
- `setupAnimationComponent()`
- `setupPositionComponent()`
- `setupAiComponent()`

Die Klasse `Ghost` hat folgende Attribute:
- `float xSpeed` : Die Geschwindigkeit, in der sich der Geist in x-Richtung bewegt.
- `float ySpeed` : Die Geschwindigkeit, in der sich der Geist in y-Richtung bewegt.
- `String pathToIdleLeft` : Hier wird der Pfad zu den Assets der Ghost-Animationen gespeichert.
- `String pathToIdleRight` 
- `String pathToRunLeft`
- `String pathToRunRight`

Um den Grabstein zu realisieren erstellen wir eine neue Klasse `Grabstein`, die von der Klasse `Fallen` erbt, da diese ähnliche Eigenschaften wie der Grabstein hat. Der Unterschied zu den Fallen ist, dass der Grabstein den Spieler belohnen kann. Hier beträgt die Chance einer Belohnung 60%. Eine geeignete Methode könnte wie folgt aussehen:
```java
int damage;
if (getRandomZahl(0,100) > 60){
    damage = -20; 
} 
else {
    damage = 20;
}
```
Wir arbeiten mit folgenden Components für den Grabstein:
- HitboxComponent 
- PositionComponent (wir nehmen den Konstruktor, der die Falle an eine zufällige Position setzt)

Der Grabstein hat folgende Attribute: 
- `pathToSkin`

Der Grabstein hat folgende Methoden:
- `setDamage()` -> mit dieser kann die Variable damage überschrieben werden