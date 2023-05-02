
#### title: "Konzeptskizze für Zyklus 2"

#### author: "Bjarne Zaremba, Lukas Landmann, David Stevic"



# Beschreibung der Aufgabe

## Aufgabe 1 - Monster

Es sollen einfache Gegner für den Hero implementiert werden, die sich frei im Dungeon bewegen können.

### Codeanalyse des AI-Systems:

In dem Spiel wird eine AI-Component verwendet, um das Verhalten der Monster zu steuern. Die AI-Component nutzt verschiedene Strategien, die durch das Strategy-Pattern implementiert sind. Jede Strategie repräsentiert ein bestimmtes Verhalten, das von einer Entität ausgeführt werden kann (zum Beispiel einen Spieler verfolgen oder angreifen). Durch die Verwendung eines Interfaces können alle Strategien von der AI-Component genutzt werden, was es ermöglicht, dass unterschiedliche Arten von Monstern unterschiedliche Verhaltensweisen haben können. Die eigentliche Steuerung der AIs erfolgt durch das "systems/AISystem.java". Das AI-System kontrolliert das Verhalten aller Entities im Spiel, die eine AI-Component haben.

## Aufgabe 2 - Fähigkeiten

Es soll der Levelaufstieg implementiert werden. Dazu soll der Hero verschiedene Fähigkeiten bekommen.

### Codeanalyse des Skill-Systems:

Das Skillsystem updatet den Verlauf des Cooldowns für die entsprechenden Skills. Der FireballSkill ist eine Unterklasse von DamageProjectileSkill, weil der Feuerball eine spezielle Art eines DamageProjectiles ist. Es werden also noch weitere Sachen, wie zum Beispiel die Geschwindigkeit oder der Schaden des Feuerballs spezifiziert. Diese Klasse implementiert das Interface ISkillFunction. Infolgedessen muss die Methode `execute()` überschrieben werden. Die Methode wird immer dann aufgerufen, wenn der Skill benutzt wird (AI-Component oder Playable-Component). Im konkreten Fall des Fireballs wird diese aufgerufen, wenn der Spieler "q" auf der Tastatur drückt.  

### Codeanalyse des XP-Systems:

Die XP-Component verwaltet die Erfahrungspunkte des Heros. Das XP-System updatet die XP-Component. Wenn der Hero genug XP hat, um ein Level aufzusteigen, wird dies durch das XP-System erkannt und das Level des Heros wird erhöht, indem die `performLevelUp()`-Methode aufgerufen wird.  

# Beschreibung der Lösung

## Aufgabe 1 - Monster

Wir wollen drei verschiedene Monster implementieren. Ein Monster trägt den Namen Darkheart, ein anderes Monster heißt Imp und das letzte Monster heißt Andromalius.
Die Monster bewegen sich zufällig im Dungeon. Dazu nutzen wir eine bereits implementierte IdleAi-Component. Zusätzlich implementieren wir eine eigene AI-Component. Diese soll das Monster den Ausgang des Levels "bewachen" lassen. 

## Aufgabe 2 - Fähigkeiten

Wir wollen zwei verschiedene Zaubersprüche implementieren. Ein Zauberspruch ist ein Heilzauber, der den Hero heilt. Der andrere Zauber kann Eiszapfen verschiessen. Wenn dieser auf ein Monster trifft, wird dieses für eine gewisse Zeit eingefroren. Der Hero kann die Zaubersprüche benutzen, indem er die Tasten "q" bzw. "e" drückt. Der Hero bekommt Erfahrungspunkte, wenn er ein Level im Dungeon beendet. Wenn er genug Erfahrungspunkte hat, steigt er ein Level auf. Beim Levelaufstieg schaltet er dann einen Skill frei, den er benutzen kann. Wenn der Skill schon freigeschaltet ist, wird er verbessert.

# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). Wenn es sich anbietet, wollen wir Methodenreferenzen und Lambda-Ausdrücke nutzen.


# Ansatz und Modellierung

## Aufgabe 1 - Monster

Wir erstellen eine Oberklasse `Monster` von der dann alle drei oben genannten Monster erben sollen. Die Monster benötigen folgende Components:
 - `PositionComponent` -> Position im Dungeon
 - `AnimationComponent` -> Animation des Monsters
 - `AIComponent` -> Steuerung des Monsters
 - `VelocityComponent` -> Geschwindigkeit des Monsters
 - `HitboxComponent` -> Hitbox des Monsters (vorbereitend für weitere Aufgaben)
 - `HealthComponent` -> Lebenspunkte des Monsters


 Die Monster haben folgende Attribute:
 - `hp` -> Lebenspunkte
 - `dmg` -> Schaden
 - `xSpeed` -> Geschwindigkeit in x-Richtung
 - `ySpeed` -> Geschwindigkeit in y-Richtung
 - `pathtoIdleLeft` -> Pfad zur Idle-Animation nach links
 - `pathtoIdleRight` -> Pfad zur Idle-Animation nach rechts

 Außerdem benutzen wir seperate `setup()`-Methoden für die einzelnen Components. Diese werden dann im Konstruktor aufgerufen.

 Beschreibung der konkreten Monster:

Darkheart:

![Test](https://opengameart.org/sites/default/files/styles/medium/public/threeformsPJ2.png)

Imp:

![Imp](https://opengameart.org/sites/default/files/styles/medium/public/animated%20imp.gif)

Andromalius:

![Andromalius](https://opengameart.org/sites/default/files/styles/medium/public/minion-45x66.png)

UML:

![UML](https://i.ibb.co/K9D1Tyq/Monster-UML.png)


## Aufgabe 2 - Fähigkeiten

### Heilzauber

![Heilzauber](https://i.ibb.co/r2zBs0w/shield-Green-Edit.png)

Wir erstellen eine Klasse `HealSkill`, die das Interface `ISkillFunction` implementiert. Wir heilen den Hero, indem wir die `execute()`-Methode überschreiben und in dieser die `hp`-Attribute des Heros anpassen.
Der `HealSkill` braucht nur eine `AnimationComponent`.
Der `HealSkill` hat folgende Attribute:
- `healAmount` -> Heilung des Heros (in HP)
- `pathToAnimation` -> Pfad zur Animation des Heilzaubers

### Eiszapfen

![Eiszapfen](https://opengameart.org/sites/default/files/icicle.png)

Wir erstellen eine Klasse `EiszapfenSkill` und eine neue Klasse `SlowProjectileSkill`-Klasse, die das Interface `ISkillFunction`implementiert. Wir verlangsamen das Ziel, indem wir die `execute()`-Methode überschreiben und in dieser die Attribute `xSpeed` und `ySpeed` anpassen. Der `EiszapfenSkill` soll ähnlich wie der `FireballSkill` umgesetzt werden. Der `EiszapfenSkill` hat nur einen Konstruktor. In diesem werden die Attribute des Eiszapfens gesetzt. Diese sind:
- `xSpeed` -> Geschwindigkeit in x-Richtung
- `ySpeed` -> Geschwindigkeit in y-Richtung
- `slowAmount` -> Verlangsamung des Ziels (in Prozent)
- `slowDuration` -> Dauer der Verlangsamung (in Sekunden)
- `pathToAnimation` -> Pfad zur Animation des Eiszapfens
- `cost` -> Kosten des Eiszapfens (in HP)

Der `SlowProjectileSkill` benötigt folgende Komponenten:
- `PositionComponent` -> Position des Eiszapfens
- `AnimationComponent` -> Animation des Eiszapfens
- `VelocityComponent` -> Geschwindigkeit des Eiszapfens
- `HitboxComponent` -> Hitbox des Eiszapfens
- `ProjectileComponent` -> Projectile-Component des Eiszapfens

Desweiteren wird die `Hero`-Klasse entsprechend erweitert. 

### Levelaufstieg

Wir initialisieren in `starter.Game` eine XP-Component. Die Component wird über die `starter.Game`-Klasse organisiert. In der Methode `onLevelUp` aus dem `ILevelUp`-Interface wird ein Screen eingeblendet, in dem der Spieler auswählen kann, welche Fähigkeiten sein Hero erlernen soll. Außerdem soll beim LevelUp die Anzahl der HP auf das Maximum gesetzt werden, d.h. der Hero hat wieder volle HP. Dies soll ähnlich wie das GameOverMenu realisiert werden.  

UML:

![UML](https://i.ibb.co/Svz47yB/Screenshot-2023-05-02-140325.png)