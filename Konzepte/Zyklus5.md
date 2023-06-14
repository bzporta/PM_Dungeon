#### title: "Konzeptskizze für Zyklus 4"

#### author: "Bjarne Zaremba, Lukas Landmann, David Stevic"

# Beschreibung der Aufgabe

## Aufgabe 1 - Dialogsystem

In der Aufgabe soll ein einfaches Dialogsystem implementiert werden. Dabei sollen RegExp zum Einsatz kommen. Außerdem soll der Spieler die Möglichkeit bekommen, Texte einzugeben. Das Dialogsystem soll als eine Art Rätsel implementiert werden.

## Aufgabe 2 - Quests

In der Aufgabe sollen verschiedene Quests implementiert werden. Dies sind Aufgaben, die der Spieler lösen kann. Dafür bekommt er dann eine Belohnung. Außerdem soll der Spieler die Möglichkeit bekommen, diese Quests abzulehnen.

## Aufgabe 3 - Speichern und Laden

In der Aufgabe soll ein Speichern und Laden des Spielstandes ermöglicht werden. Dabei soll der Spielstand in einer Datei gespeichert werden, die bei jedem Start des Spiels geladen wird. Dies soll mit Hilfe von Serializable geschehen.


# Beschreibung der Lösung

## Aufgabe 1 - Dialogsystem

Der Spieler soll in der Lage sein, mit dem Ghost-NPC zu interagieren. Dabei soll der Spieler eine Frage gestellt bekommen. Diese Frage soll mit Hilfe von RegExp überprüft werden. Wenn der Spieler die Frage richtig beantwortet, soll er eine Belohnung bekommen. Außerdem soll der Spieler die Möglichkeit bekommen, den Dialog abzubrechen. Außerdem soll das Diaglogsystem dazu verwendet werden, um Quests des Geistes anzunehmen oder abzulehnen.

## Aufgabe 2 - Quests

Wir möchten zwei verschiedene Quests implementieren. Die Quests werden über den Geist gestartet. Der Spieler soll die Möglichkeit bekommen, die Quests anzunehmen oder abzulehnen. Die erste Quest ist das Aktivieren einer bestimmten Anzahl von Grabsteinen. Die zweite Quest ist das Töten einer bestimmten Anzahl von Monstern. Aktive Quests sollen oben rechts als kleiner Screen angezeigt werden. Bei der erfolgreichen Beendigung einer Quest soll der Spieler eine Belohnung in Form von XP bekommen.

## Aufgabe 3 - Speichern und Laden

Im Pausemenu des Spiels soll der Spieler die Möglichkeit bekommen, seinen Spielstand zu speichern. Außerdem wird ein "Beenden-Button" im Pausemenu hinzugefügt. Beim Speichern wird der aktuelle Spielstand in einer Datei gespeichert. Beim Laden wird der Spielstand aus der Datei geladen.

# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns
entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). Wenn es sich anbietet, wollen wir
Methodenreferenzen und Lambda-Ausdrücke nutzen. Des weiteren haben wir uns für den Einsatz von Logging entschieden.
Zusätzlich halten wir die PM-Coding-Rules ein.

# Ansatz und Modellierung

## Aufgabe 1 - Dialogsystem

Wir erstellen eine ```DialogMenu```-Klasse. Diese ist ähnlich wie das ```Pause-Menu``` oder das ```GameOverMenu``` implementiert. Das ```DialogMenu``` soll bei Interaktion mit dem Geist unten links in der Ecke erscheinen. Aus diesem Grund fügen wir dem Geist noch eine ```InteractionComponent``` hinzu. In der ```DialogMenu```-Klasse wird die Antwort des Spielers auf eine Frage des Geistes überprüft und mithilfe von RegExp ausgewertet. Ist diese korrekt, bekommt der Spieler eine Belohnung in Form eines LevelUp`s.

#### UML:

![UML](https://i.ibb.co/bsj3wGS/Screenshot-2023-05-31-185218.png)

## Aufgabe 2 - Quests

Wir benutzen für das Vorschlagen der Quests durch den Geist das bereits implementierte Dialogsystem. Der Spieler kann die Quests annehmen/ablehnen, indem er Ja/Nein in das Texteingabefeld schreibt.
Um die Quests zu implementieren, erstellen wir eine abstrakte ```Quest```-Klasse. Diese dient als Superklasse für die beiden Quest-Klassen. Es gibt eine ```KillQuest```-Klasse und eine ```GraveQuest```-Klasse, die beide von ```Quest``` erben. In der ```KillQuest```-Klasse wird die Anzahl der getöteten Monster gespeichert. In der ```GraveQuest```-Klasse wird die Anzahl der aktivierten Grabsteine gespeichert. In beiden Klassen gibt es eine ```getReward()```- Methode, die bei erfolgreicher Beendigung der jeweiligen Quest XP an den Hero verteilt.
Attribute der ```Quest```-Klasse:
- ```name```: Name der Quest
- ```description```: Beschreibung der Quest
- ```reward```: Belohnung der Quest
- ```status```: Status der Quest: Wie viele Monster wurden getötet? Wie viele Grabsteine wurden aktiviert?
  Außerdem werden getter und setter für die Attribute erstellt.

Desweiteren erstellen wir ein ```QuestSystem```. Dieses ist äquivalent zu den anderen Systems aufgebaut. Es hat eine ```update()```- Methode. In dieser wird überprüft, ob eine Quest aktiv ist. Wenn ja, wird überprüft, ob die Quest abgeschlossen ist. Wenn ja, wird die ```getReward()```- Methode aufgerufen. Außerdem wird die Quest aus der Liste der aktiven Quests entfernt.

#### UML-Diagramm:

![UML](https://i.ibb.co/DrVXkMM/Screenshot-2023-05-31-193232.png)

## Aufgabe 3 - Speichern und Laden

Da viele Variablen in der ```starter.Game```- Klasse statisch sind, ist es nicht möglich, diese zu serialisieren. Deshalb haben wir uns entschieden, die ```Game```- Klasse nicht zu serialisieren. Deshalb erstellen wir eine neue Klasse ```SaveData```, die alle wichtigen Variablen enthält. Diese Klasse wird dann serialisiert. Dazu erstellen wir eine zweite Klasse ```SaveGame```. Diese Klasse enthält die Methoden ```saveGame()``` und ```loadGame()```. In der ```saveGame()```- Methode wird ein Objekt der ```SaveData```- Klasse erstellt und die Variablen der ```Game```- Klasse in die ```SaveData```- Klasse kopiert. Dieses Objekt wird dann serialisiert. In der ```loadGame()```- Methode wird das Objekt aus der Datei geladen und die Variablen der ```Game```- Klasse werden mit den Variablen des Objekts überschrieben.

#### UML:

![UML](https://i.ibb.co/TBxWg9Y/Screenshot-2023-05-31-182507.png)


