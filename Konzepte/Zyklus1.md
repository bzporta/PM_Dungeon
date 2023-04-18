---
title: "Konzeptskizze für Zyklus 1"
author: "Bjarne Zaremba, Lukas Landmann, David Stevic"

hidden: true
---


# Beschreibung der Aufgabe

## Aufgabe 1 - Fallen

In dieser Aufgabe sollen Fallen in den Dungeon implementiert werden. Diese sollen das Voranschreiten im Dungeon schwieriger machen. Die Fallen sollen verschiedene Wirkungen beim Helden oder beim Monster auslösen. 

## Aufgabe 2 - Game-Over



# Beschreibung der Lösung

## Aufgabe 1 - Fallen

Wir wollen zwei Arten von Fallen erstellen. Bei der einen bekommt der Hero eine zufällig generierte Anzahl an Schaden. Bei der anderen Art der Falle erscheint ein zufälliges Monster vor dem Hero. Außerdem gibt es eine gewisse Anzahl an Schaltern, die benutzt werden können, um eine Falle zu deaktivieren. Die Falle soll als bodenähnliche Druckplatte auf dem Boden zu sehen sein. 

## Aufgabe 2


# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). 


# Ansatz und Modellierung

Wir erstellen eine `Monster`-Klasse mit den grundlegenden Eigenschaften und Funktionen
eines Monsters.

Da Monster auch im Dungeon gezeichnet werden müssen, implementiert die Klasse `Monster`
das Interface `IDrawable`. Die Monster sollen vom `EntityController` verwaltet werden,
daher implementiert die Klasse auch das Interface `IEntity`.

## Monster haben zusätzlich folgende Grundeigenschaften:

-   `float lebenspunkte`: Geben die verbleibenden Lebenspunkte des Monsters an
    -   Hat das Monster 0 Lebenspunkte, wird es mit Hilfe der `deletable`-Methode
        aus dem `EntityController`entfernt.
-   `float hSpeed`: Die Geschwindigkeit, in der sich das Monster horizontal bewegt
-   `float vSpeed`: Die Geschwindigkeit, in der sich das Monster vertikal bewegt
-   `float dmg`: Den Schaden, den das Monster im Kampf macht
-   `dungeonWorld level`: Genau wie der Held müssen auch Monster das Level kennen,
    um sich darin zu bewegen. Wird im Konstruktor gesetzt.

## Monster haben zusätzlich folgende Grundfunktionen:

-   `void move()`: Bewegt das Monster in eine zufällige Richtung

    Dafür verwenden wir zwei Zufallszahlen: Die erste Zahl gibt an, ob sich das
    Monster nach rechts oder links bewegt, die zweite Zahl, ob sich das Monster
    nach oben oder unten bewegt. Zusätzlich gibt es die Chance, dass ein Monster
    sich gar nicht bewegt.

    ```java
    int linksOrechts = getRandomZahl(0,1);
    int obenOunten = getRandomZahl(0,1);

    //30% Chance, sich nicht zu bewegen
    if (getRandomZahl(0,100) > 30) {
        //horizontal
        if (linksOrechts == 0) {
            this.x += this.hSpeed;
        } else {
            this.x -= this.hSpeed;
        }

        //vertikal
        if (obenOunten == 0) {
            this.y += this.vSpeed;
        } else {
            this.y -= this.vSpeed;
        }
    }
    ```

-   `void getHit(float dmg)`: Zieht dem Monster Lebenspunkte ab
-   `float getDMG()`: Gibt Schaden zurück
-   `getRandomPosition()`: Wird im Konstruktor aufgerufen, sucht sich eine
    zufällige Position im Dungeon als Spawn-Punkt

## Daraus ergibt sich folgendes UML:

![Klassendiagramm der angedachten Lösung](images/tagebuch_uml.png)

## Beschreibung der konkreten Monster:

1.  Igel:
    -   Hat 5 Lebenspunkte
    -   Macht 0.5 Schaden
    -   Bewegt sich sowohl vertikal als auch horizontal mit 0.1f
    -   Bewegt sich in eine zufällige Richtung

2.  Schleimkugel:
    -   Hat 3 Lebenspunkte
    -   Macht 1 Schaden
    -   Bewegt sich nur horizontal
        -   hSpeed=0.2f;
        -   vSpeed=0f;

Monster werden beim Laden eines Levels im Dungeon verteilt. Dafür erstellen
wir die Funktion `spawnMonster` in unserem `MainController`, welche in der
`onLevelLoad`-Methode aufgerufen wird.

`spawnMonster` erstellt eine zufällige Anzahl an Monstern (zwischen 5 und 10),
platziert diese mit Hilfe von `getRandomPointInDungeon` (wie beim Helden) im
Dungeon und fügt Sie dem `EntityController` hinzu.

Verlässt der Spieler das Level, werden alle Monster aus dem `EntityController`
gelöscht. Dafür wird die gesamte Liste des `EntityController` gelöscht und der
Held neu hinzugefügt.