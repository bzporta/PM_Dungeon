
#### title: "Konzeptskizze für Zyklus 3"

#### author: "Bjarne Zaremba, Lukas Landmann, David Stevic"



# Beschreibung der Aufgabe

## Aufgabe 1 - Nahkampf

Es soll ein einfaches Nahkampfsystem implementiert werden. Dazu soll der Hero eine Nahkampfwaffe bekommen, mit der er Monster angreifen kann. Die Monster sollen den Hero ebenfalls angreifen können.

## Aufgabe 2 - Fernkampf

Es sollen Projektile für den Fernkampf implementiert werden, damit der Hero auch aus der Distanz angreifen kann. Die Monster sollen ebenfalls Projektile bekommen, damit sie den Hero aus der Distanz angreifen können. Dabei kann ein Boomrang als Waffe für den Hero und ein Feuerball als Waffe für die Monster implementiert werden.

## Aufgabe 3 - Bossmonster

Es soll ein einzigartiges Bossmonster implementiert werden. Diese soll eine besondere Fähigkeit haben, die sie von den anderen Monstern unterscheidet. Der Boss soll eine zweite Phase haben, in der er stärker wird. Diese wird aktiviert, wenn der Boss nur noch 50% seiner Lebenspunkte hat.

## Codeanalyse des ProjektilSystems

Die Klasse `ProjectileSystem.java` hat die Aufgabe, die abgefeuerten Projektile der Entitäten zu verwalten. Dabei handelt es sich beispielsweise um Feuerbälle, die vom Held abgeschossen werden, wenn die Fernkampffunktion implementiert ist. `ProjectileSystem.java` überschreibt die Methode `update`, da es von `System` erbt. In dieser Methode wird überprüft, welche Entitäten im Spiel eine `ProjectileComponent` besitzen. Des Weiteren wird geprüft, ob die Projektile ihren Endpunkt erreicht haben. Schließlich werden die Entitäten entfernt, deren Projektile den Endpunkt erreicht haben. Die Methode `buildDataObject` legt die Position und Geschwindigkeit eines Projektils fest. Abschließend wird in der Methode `hasReachedEndpoint` überprüft, ob ein Projektil seinen Endpunkt erreicht hat.


# Beschreibung der Lösung

## Aufgabe 1 - Nahkampf

Wir wollen ein Schwertangriff implementieren. Der Hero soll mit dem Schwert angreifen können. Wenn er ein Monster trifft, soll dieses Schaden erleiden. Wenn das Monster den Hero trifft, soll dieser Schaden erleiden. Bei einem Treffer wird die getroffene Entität ein kurzes Stück zurückgeworfen. Der Nahkampfangriff soll mithilfe des des Fernkampfsystems implementiert werden.

## Aufgabe 2 - Fernkampf

Wir wollen für den Fernkampf einen Boomerang und ein Sägeblatt implementieren. Der Boomerang soll vom Hero abgeschossen werden und soll nach einer bestimmten Zeit wieder zum Hero zurückkehren. Das Sägeblatt soll abgeschossen werden und soll sich in einer geraden Linie bewegen. Wenn es den Hero oder ein Monster trifft, soll Blutungsschaden ausgelöst werden. Wenn es eine Wand trifft, soll es an dieser abprallen und sich in die andere Richtung bewegen. Bei einem Treffer, egal von welchem Projektil, soll der getroffene ein kurzes Stück zurückgeworfen werden. 

## Aufgabe 3 - Bossmonster

Das Bossmonster soll eine besondere Fähigkeit haben, die es von den anderen Monstern unterscheidet. Das Monster soll am Endtile des Levels spawnen und sich von dort wiederkehrend zum Spawntile des Heros bzw. Endtile bewegen. Es soll eine zweite Phase haben, in der es stärker wird. Diese wird aktiviert, wenn das Bossmonster nur noch 50% seiner Lebenspunkte hat. In der zweiten Phase soll das Bossmonster schneller und stärker werden. Das Bossmonster soll einzigartig sein und soll nicht mehrmals im Spiel vorkommen. Wir möchten, dass das Bossmonster nach 15 gespielten Leveln erscheinen. Gleichzeitig wird die Größe des Levels erhöht. Außerdem soll das Bossmonster ein eigenes Angriffsmonster bekommen. Es soll in der ersten Phase soll das Monster Nahkampfangriffe ausführen. In der zweiten Phase soll das Bossmonster Fernkampfangriffe ausführen. Es bleibt dabei immer in einem gewissen Abstand zum Hero, sodass es für den Hero schwieriger wird, das Monster zu treffen.

# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). Wenn es sich anbietet, wollen wir Methodenreferenzen und Lambda-Ausdrücke nutzen. Des weiteren haben wir uns für den Einsatz von Logging entschieden. Zusätzlich halten wir die PM-Coding-Rules ein.


# Ansatz und Modellierung

## Aufgabe 1 - Monster

