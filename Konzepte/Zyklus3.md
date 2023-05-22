#### title: "Konzeptskizze für Zyklus 3"

#### author: "Bjarne Zaremba, Lukas Landmann, David Stevic"

# Beschreibung der Aufgabe

## Aufgabe 1 - Nahkampf

Es soll ein einfaches Nahkampfsystem implementiert werden. Dazu soll der Hero eine Nahkampfwaffe bekommen, mit der er
Monster angreifen kann. Die Monster sollen den Hero ebenfalls angreifen können.

## Aufgabe 2 - Fernkampf

Es sollen Projektile für den Fernkampf implementiert werden, damit der Hero auch aus der Distanz angreifen kann. Die
Monster sollen ebenfalls Projektile bekommen, damit sie den Hero aus der Distanz angreifen können. Dabei kann ein
Boomrang als Waffe für den Hero und ein Feuerball als Waffe für die Monster implementiert werden.

## Aufgabe 3 - Bossmonster

Es soll ein einzigartiges Bossmonster implementiert werden. Diese soll eine besondere Fähigkeit haben, die sie von den
anderen Monstern unterscheidet. Der Boss soll eine zweite Phase haben, in der er stärker wird. Diese wird aktiviert,
wenn der Boss nur noch 50% seiner Lebenspunkte hat.

## Codeanalyse des ProjektilSystems

Die Klasse `ProjectileSystem.java` hat die Aufgabe, die abgefeuerten Projektile der Entitäten zu verwalten. Dabei
handelt es sich beispielsweise um Feuerbälle, die vom Held abgeschossen werden, wenn die Fernkampffunktion implementiert
ist. `ProjectileSystem.java` überschreibt die Methode `update`, da es von `System` erbt. In dieser Methode wird
überprüft, welche Entitäten im Spiel eine `ProjectileComponent` besitzen. Des Weiteren wird geprüft, ob die Projektile
ihren Endpunkt erreicht haben. Schließlich werden die Entitäten entfernt, deren Projektile den Endpunkt erreicht haben.
Die Methode `buildDataObject` legt die Position und Geschwindigkeit eines Projektils fest. Abschließend wird in der
Methode `hasReachedEndpoint` überprüft, ob ein Projektil seinen Endpunkt erreicht hat.

# Beschreibung der Lösung

## Aufgabe 1 - Nahkampf

Wir wollen ein Schwertangriff implementieren. Der Hero soll mit dem Schwert angreifen können. Wenn er ein Monster
trifft, soll dieses Schaden erleiden. Wenn das Monster den Hero trifft, soll dieser Schaden erleiden. Bei einem Treffer
wird die getroffene Entität ein kurzes Stück zurückgeworfen. Der Nahkampfangriff soll mithilfe des des Fernkampfsystems
implementiert werden.

## Aufgabe 2 - Fernkampf

Wir wollen für den Fernkampf einen Boomerang und ein Sägeblatt implementieren. Der Boomerang soll vom Hero abgeschossen
werden und soll nach einer bestimmten Zeit wieder zum Hero zurückkehren. Das Sägeblatt soll abgeschossen werden und soll
sich in einer geraden Linie bewegen. Wenn es den Hero oder ein Monster trifft, soll Blutungsschaden ausgelöst werden.
Das Sägeblatt soll über die Distanz langsam nach unten fallen. Bei einem Treffer, egal von welchem Projektil, soll der
getroffene ein kurzes Stück zurückgeworfen werden.

## Aufgabe 3 - Bossmonster

Das Bossmonster soll eine besondere Fähigkeit haben, die es von den anderen Monstern unterscheidet. Das Monster soll am
Endtile des Levels spawnen und sich von dort wiederkehrend zum Spawntile des Heros bzw. Endtile bewegen. Es soll eine
zweite Phase haben, in der es stärker wird. Diese wird aktiviert, wenn das Bossmonster nur noch 50% seiner Lebenspunkte
hat. In der zweiten Phase soll das Bossmonster schneller und stärker werden. Das Bossmonster soll einzigartig sein und
soll nicht mehrmals im Spiel vorkommen. Wir möchten, dass das Bossmonster nach 15 gespielten Leveln erscheinen.
Gleichzeitig wird die Größe des Levels erhöht. Außerdem soll das Bossmonster ein eigenes Angriffsmonster bekommen. Es
soll in der ersten Phase soll das Monster Nahkampfangriffe ausführen. In der zweiten Phase soll das Bossmonster
Fernkampfangriffe ausführen. Es bleibt dabei immer in einem gewissen Abstand zum Hero, sodass es für den Hero
schwieriger wird, das Monster zu treffen.

# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns
entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). Wenn es sich anbietet, wollen wir
Methodenreferenzen und Lambda-Ausdrücke nutzen. Des weiteren haben wir uns für den Einsatz von Logging entschieden.
Zusätzlich halten wir die PM-Coding-Rules ein.

# Ansatz und Modellierung

## Aufgabe 1 - Nahkampf

Wir implementieren eine Klasse `SwordAttack`. Diese implementiert das Interface `ISkillFunction` und überschreibt
dementsprechend die `execute()`- Methode. Die Schwertattacke wird Gegnern in einem bestimmten Radius vor dem Hero
Schaden zufügen. Die Klasse hat folgende Attribute:

- `damage` -> Schaden, den die Attacke anrichtet
- `radius` -> Radius, in dem die Attacke Schaden anrichtet
- `pathToAnimation` -> Pfad zur Animation der Attacke

Die Klasse hat folgende Components:

- `AnimationComponent`
- `HitboxComponent`
- `PositionComponent`
  Die Components werden mit `setup`-Methoden initialisiert.

Bei einem Treffer wird die Postion des Gegners über die PositionComponent leicht verändert, sodass diese ein Stück
zurückgeworfen wird.

#### Skin des Schwerts:

![Schwert](https://opengameart.org/sites/default/files/59_32_sword_0.png)

#### UML:

![UML](https://i.ibb.co/MVDxgq4/Screenshot-2023-05-17-163805.png)

## Aufgabe 2 - Fernkampf

Wir implementieren zwei Klassen `BoomerangProjectile` und `SawProjetile`. Beide implmentieren das
Interface `ISkillFunction` und überschreiben dementsprechend die `execute()`- Methode. Dort wird dann das
dementsprechende Flug-/Schadensverhalten (siehe Beschreibung der Lösung) implementiert.
Die Klassen haben jeweils folgende Attribute:

- `damage` -> Schaden, den das Projektil anrichtet
- `xSpeed` -> Geschwindigkeit in x-Richtung
- `ySpeed` -> Geschwindigkeit in y-Richtung
- `pathToAnimation` -> Pfad zur Animation des Projektils

Die Klassen haben jeweils folgende Components:

- `AnimationComponent`
- `HitboxComponent`
- `VelocityComponent`
- `PositionComponent`

Die Components werden über `setup`-Methoden gesetzt.

Der Rückstoß wird über die `PositionComponent` der getroffenen Entität realisiert. Die Postition der Entität wird um
einen bestimmten Wert in x- und y-Richtung verändert.

#### Skin des Boomerangs:

![Boomerang](https://opengameart.org/sites/default/files/styles/medium/public/bullet006_48px_8.png)

#### Skin des Sägeblatts:

![Sägeblatt](https://opengameart.org/sites/default/files/pixil-frame-0%20%282%29_0.png)

#### UML-Diagramm:

![UML](https://i.ibb.co/yqf6cYd/Screenshot-2023-05-17-161638.png)

## Aufgabe 3 - Bossmonster

Das Bossmonster soll von der Klasse `Monster` erben. Es werden dann im Konstruktor andere/stärkere Werte gesetzt.
Außerdem ist die Animation des Bossmonsters deutlich größer als bei einem normalen Monster. Das Bossmonster benötigt
folgende Components:

- `AnimationComponent`
- `HitboxComponent`
- `VelocityComponent`
- `HealthComponent`
- `PositionComponent`
- `AIComponent` -> Wir möchten eine eigene FightAI für das Bossmonster implementieren. Es soll sich, je nach Radius zum
  Hero, anders verhalten. Wenn es sich in der ersten Phase befindet, soll es Nahkampfangriffe ausführen. Wenn es sich in
  der zweiten Phase befindet, soll es Fernkampfangriffe ausführen. Es soll sich immer in einem gewissen Abstand zum Hero
  bewegen, sodass es für den Hero schwieriger wird, das Monster zu treffen.

Das Bossmonster soll folgende Attribute haben:

- `hp` -> Lebenspunkte
- `dmg` -> Schaden
- `xSpeed` -> Geschwindigkeit in x-Richtung
- `ySpeed` -> Geschwindigkeit in y-Richtung
- `pathToIdleLeft` -> Pfad zur Idle-Animation nach links
- `pathToIdleRight` -> Pfad zur Idle-Animation nach rechts

Wir benutzen einzelne `setup()`-Methoden, um die einzelnen Components zu konfigurieren. Um zwischen den 2 Phasen des
Bossmonstern wechseln zu können, erweitern wir das HealthSystem. Dort wird dann jedes Frame geschaut, ob die HP des
Monsters unter 50% gefallen sind. In dem Fall wird die zweite Phase des Bossmonsters aktiviert.

#### Skin des Bossmonstes:

![Skin des Bossmonsters](https://img.itch.zone/aW1hZ2UvNzgzMDYwLzQzODIxOTcucG5n/original/9MzdEh.png)

#### UML:

![UML](https://i.ibb.co/rvDYp8C/Screenshot-2023-05-17-155328.png)

