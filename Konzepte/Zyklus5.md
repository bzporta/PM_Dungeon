#### title: "Konzeptskizze für Zyklus 5"

#### author: "Bjarne Zaremba, Lukas Landmann, David Stevic"

# Beschreibung der Aufgabe

## Aufgabe 1 - JUnit (Gruppe Loot)

Es soll eine gründliche Testabdeckung für die in der Aufgabe Quests implementierten Klassen und Methoden erstellt werden. Dies geschieht mithilfe von JUnit.

## Aufgabe 2 - JUnit (Gruppe Basics)

In der Aufgabe soll eine gründliche Testabdeckung für die in der Aufgabe Fallen implementierten Klassen und Methoden erstellt werden. Dies geschieht mithilfe von JUnit.

## Aufgabe 3 - JUnit (Gruppe Monster)

In der Aufgabe soll eine gründliche Testabdeckung für die in der Aufgabe Monster implementierten Klassen und Methoden erstellt werden. Dies geschieht mithilfe von JUnit.

# Beschreibung der Lösung

## Aufgabe 1 - JUnit (Gruppe Loot)

Wir wollen jede neu implementierte Klasse und Methode, die für die Aufgabe ```Quest``` benötigt wird, testen. für jede Klasse/Methode gibt es folgende Tests:

- Positiven Test (das Ergebnis ist wie erwartet)
- Negativen Test (das Ergebnis ist nicht wie erwartet)
- Grenzwert Test (das Ergebnis ist an der Grenze des erwarteten)

Außerdem werden wir an geeigneten Stellen prüfen, ob entsprechende Exceptions geworfen werden.

Folgende Methoden und Klassen sollen getestet werden (genauere Beschreibung der Tests erfolgt im Punkt ```Ansatz und Modellierung```):

- Die Unterklassen der abstrakten ```Quest```-Klasse, sowie die entsprechenden Methoden:

    - ```activateQuest```-Methode von Grave- und KillQuest
    - ```checkIfDone```-Methode von Grave- und KillQuest
    - ```countKilledMonsters```-Methode
    - ```countActivatedGraves```-Methode

- In der ```starter.Game```-Klasse:
    - sind die Quests in der ```quests```-Liste enthalten?


- ```QuestSystem```-Klasse:
    - ```update()```-Methode	
    - ```setReward()```-Methode
    - ```refreshQuestMenu()```-Methode


- ```QuestMenu```-Klasse:
    - ```showMenu()```-Methode
    - ```hideMenu()```-Methode
    - ```addActiveQuest()```-Methode-
    - ```decreaseQuestCounter()```-Methode


## Aufgabe 2 - JUnit (Gruppe Basics)

Wir wollen jede Klasse/Methode, die für die Aufgabe ```Fallen``` benötigt wird, testen. Wenn möglich, gibt es für jede Klasse/Methode folgende Tests:

- Positiven Test (das Ergebnis ist wie erwartet)
- Negativen Test (das Ergebnis ist nicht wie erwartet)
- Grenzwert Test (das Ergebnis ist an der Grenze des erwarteten)

Außerdem werden wir an geeigneten Stellen prüfen, ob entsprechende Exceptions geworfen werden.

Folgende Methoden und Klassen sollen getestet werden (genauere Beschreibung der Tests erfolgt im Punkt ```Ansatz und Modellierung```):

- Die ```Trap```-Klasse:
    - ```setTrapTile()```-Methode

- Die ```TrapDmg```-Klasse:
    - ```deactivateTrap()```-Methode

- Die ```TrapTeleport```-Klasse:
    - ```deactivateTrap()```-Methode
    - ```teleport()```-Methode

- Die ```Lever```-Klasse:
    - ```pullLever()```-Methode

- Die ```TrapTeleportCreator```-Klasse und die ```TrapDmgCreator```-Klasse:
    - jeweils ```creator()```-Methode

## Aufgabe 3 - JUnit (Gruppe Monster)

Wir wollen jede neu implementierte Klasse und Methode, die für die Aufgabe ```Monster``` benötigt wird, testen. für jede Klasse/Methode gibt es folgende Tests:

- Positiven Test (das Ergebnis ist wie erwartet)
- Negativen Test (das Ergebnis ist nicht wie erwartet)
- Grenzwert Test (das Ergebnis ist an der Grenze des erwarteten)

Außerdem werden wir an geeigneten Stellen prüfen, ob entsprechende Exceptions geworfen werden.

Folgende Methoden und Klassen sollen getestet werden (genauere Beschreibung der Tests erfolgt im Punkt ```Ansatz und Modellierung```):

Die ```Monster```-Klasse:
- ```knockback()```-Methode
- ```Monster```-Konstruktor
- ```setFrozen()```-Methode
- ```setupVelocityComponent()```-Methode
- ```setupHealthComponent()```-Methode
- ```setupAiComponent()```-Methode
- ```onDeath()```-Methode
- ```setPositon()```-Methode
- ```setHitDamage()```-Methode

Die ```ProtectTileRadiusWalk```-Klasse (IdleAI-Unterklasse):
- ```ProtectTileRadiusWalk```-Konstruktor
- ```idle()```-Methode


# Methoden und Techniken

## Für alle Aufgaben

Der Code wird mit JavaDoc dokumentiert. Dabei werden die entsprechenden Regeln eingehalten. Außerdem haben wir uns
entschieden für jedes Feature einen eigenen Branch zu machen (siehe GitHub-Flow). Des weiteren haben wir uns für den Einsatz von Logging entschieden.
Zusätzlich halten wir die PM-Coding-Rules ein. Als Testbibliothek verwenden wir JUnit. 

# Ansatz und Modellierung

## Aufgabe 1 - JUnit (Gruppe Loot)

Für jede Klasse wird eine eigene Testklasse in der bereits vorhandenen Ordnerstruktur für Tests erstellt. Diese Klassen haben alle eine ```setup()```-Methode, in der die grundlegend benötigten Objekte (Game, Hero, Klassenkonstruktor) erstellt werden.  

### Folgende Methoden werden in der ```QuestSystemTest```-Klasse erstellt:
- ```testUpdateRefresh()```-Methode: 
    - Testet, ob der Screentext auf den aktuellen Status aktualisiert wird. Hierfür wird das Statusattribut der Quest mit dem Text des Screens verglichen. 
- ```testUpdateFinishQuest()```-Methode:
    - Testet, ob der Hero seine Belohnung erhält, wenn er eine Quest abgeschlossen hat, ob die Quest ```setActive```-Boolean auf ```false``` gesetzt wird und ob die ```testSetFinished```-Boolean auf ```true``` gesetzt wird. Wir vergleichen hierfür die Werte der Attribute mit den erwarteten Werten, d.h. wir vergleichen die XP des Heros und die beiden Boolean-Werte der Quest vor dem Methodenaufruf mit den Werten nach dem Methodenaufruf.

### Folgende Methoden werden in der ```QuestMenuTest```-Klasse erstellt:
- ```testShowMenu()```-Methode:
    - Testet, ob die ```Visible```-Boolean im Actor auf ```true``` gesetzt wird, wenn die ```showMenu()```-Methode aufgerufen wird. Wir vergleichen hierfür den Wert der ```Visible```-Boolean vor dem Methodenaufruf mit dem Wert nach dem Methodenaufruf.	
- ```testHideMenu()```-Methode:
    - Testet, ob die ```Visible```-Boolean im Actor auf ```false``` gesetzt wird, wenn die ```hideMenu()```-Methode aufgerufen wird. Wir vergleichen hierfür den Wert der ```Visible```-Boolean vor dem Methodenaufruf mit dem Wert nach dem Methodenaufruf.
- ```testAddActiveQuest()```-Methode:
    - Testet, ob der ```ScreenText``` initialisiert wurde und nicht ```null``` ist. Außerdem werden die Attribute ```name``` und ```status``` der Quest mit den erwarteten Werten verglichen.
- ```testDecreaseQuestCounter()```-Methode:
    - Testet, ob die ```questCounter```-Variable um 1 verringert wird, wenn die ```decreaseQuestCounter()```-Methode aufgerufen wird. Wir vergleichen hierfür den Wert der ```questCounter```-Variable vor dem Methodenaufruf mit dem Wert nach dem Methodenaufruf.

### Folgende Methoden werden in der ```QuestTest```-Klasse erstellt:
- ```testActivateQuest()```-Methode:
    - Testet, ob die ```setActive```-Boolean auf ```true``` gesetzt wird, wenn die ```activateQuest()```-Methode aufgerufen wird. Wir vergleichen hierfür den Wert der ```setActive```-Boolean vor dem Methodenaufruf mit dem Wert nach dem Methodenaufruf. Außerdem wird getestet, ob der Screentext mit Status und Name der Quest aktualisiert wird. Hierfür wird der Text des Screens mit dem erwarteten Text verglichen.
- ```testCheckIfDone()```-Methode:
    - Testet, ob das Ziel der Aktivierten Grabsteine / getöteten Monster mit dem aktuellen Wert übereinstimmt. 
- ```testCountKilledMonsters()```-Methode:
    - Testet, ob der Wert der ```killedMonsters```-Variable um 1 erhöht wird, wenn die ```countKilledMonsters()```-Methode aufgerufen wird. Wir vergleichen hierfür den Wert der ```killedMonsters```-Variable vor dem Methodenaufruf mit dem Wert nach dem Methodenaufruf. Außerdem wird getestet, ob der Screentext mit Status und Name der Quest aktualisiert wird. Hierfür wird der Text des Screens mit dem erwarteten Text verglichen.
- ```testCountActivatedGraves()```-Methode:
    - Testet, ob der Wert der ```activatedGraves```-Variable um 1 erhöht wird, wenn die ```countActivatedGraves()```-Methode aufgerufen wird. Wir vergleichen hierfür den Wert der ```activatedGraves```-Variable vor dem Methodenaufruf mit dem Wert nach dem Methodenaufruf. Außerdem wird getestet, ob der Screentext mit Status und Name der Quest aktualisiert wird. Hierfür wird der Text des Screens mit dem erwarteten Text verglichen. 
- ```testQuestsInList()```-Methode:
    - Testet, ob die Questsobjekte in der ```quests```-Liste enthalten sind. Hierfür wird die ```quests```-Liste mit den erwarteten Questobjekten verglichen.

#### UML-Diagramm:

![UML-Diagramm](https://i.ibb.co/PmfqtVm/TP5-D3e8m48-Nt-FKMNkl02h-Y0-Gb-IWBHG-Gj4-De-BAtfj1-XZl-Bi5-Gum-LTf-Dv-Bz-Rdm-GTiywt-YX2k628z-Ua1-NFT.png)

## Aufgabe 2 - JUnit (Gruppe Basics)

Für jede Klasse wird eine eigene Testklasse in der bereits vorhandenen Ordnerstruktur für Tests erstellt. Diese Klassen haben alle eine ```setup()```-Methode, in der die grundlegend benötigten Objekte (TrapDmgCreator, TrapTeleportCreator) erstellt werden.

### Folgende Methoden werden in der ```TrapTest```-Klasse erstellt:

- ```testCreatorNegativeAmount()```-Methode:
    - Testet, ob eine ```IllegalArgumentException``` geworfen wird, wenn die ```creator()```-Methode mit einem negativen Wert für den Parameter ```Anzahl``` aufgerufen wird. Hierfür wird die ```creator()```-Methode mit einem negativen Wert aufgerufen und es wird getestet, ob eine ```IllegalArgumentException``` geworfen wird.
- ```testCreatorZeroAmount()```-Methode:
    - Testet, ob bei Übergabe von 0 beim Parameter ```Anzahl``` eine Falle im Game erstellt wird. Dies geschieht durch Prüfen der Entities im Game.
- ```testCreatorPositiveAmount()```-Methode:
    - Testet, ob bei Übergabe von 1 beim Parameter ```Anzahl``` eine Falle im Game erstellt wird. Dies geschieht durch Prüfen der Entities im Game.
- ```testCreatorHighAmount()```-Methode:
    - Testet, ob bei Übergabe von 1000 beim Parameter ```Anzahl``` ein ```StackOverflowError``` geworfen wird aufgrund der while-Schleife zum bestimmen der Positionen der Falle. 
- ```testCheckPositionList()```-Methode:
    - Testet, ob die Positionen der Fallen in der ```positionList``` enthalten sind. Hierfür wird die ```positionList``` mit den erwarteten Positionen verglichen. Außerdem wird getestet, ob eine Falle doppelt in der ```positionList``` vorkommt. 
- ```testPullLeverDmgTrap()```-Methode:
    - Testet, ob die ```damage```-Variable der Damage-Trap auf 0 gesetzt wird. Hierfür wird die ```pullLever()```-Methode aufgerufen und die ```damage```-Variable der Damage-Trap mit dem erwarteten Wert verglichen.
- ```testPullLeverTeleportTrap()```-Methode:
    - Testet, ob die ```activate```-Variable der Teleport-Trap auf ```false``` gesetzt wird. Hierfür wird die ```pullLever()```-Methode aufgerufen und die ```activate```-Variable der Teleport-Trap mit dem erwarteten Wert verglichen.
- ```testTeleport()```-Methode:
    - Testet, ob die Position des Heros mit der erwarteten, vorher generierten, Position übereinstimmt. Hierfür wird die ```teleport()```-Methode aufgerufen und die Position des Heros mit der erwarteten Position verglichen. 
- ```testSetTrapTile()```-Methode:
    - Testet, ob die gesetze Postition der Trap mit der erwarteten Position übereinstimmt. Hierfür wird die ```setTrapTile()```-Methode aufgerufen und die Position der Trap mit der erwarteten Position verglichen.

#### UML-Diagramm:

![UML-Diagramm](https://i.ibb.co/dK1Wpf3/XP2n3e9044-Jx-ueh-DVw15-KOB2s98l-Chk0-XEu-UB1a-Tu-6-Ey-AHXj4strl9fd-DH3rh-Qg-Cn-PVGYrh7l7-KJjp-LYxi.png)

## Aufgabe 3 - JUnit (Gruppe Monster)

Für jede Klasse wird eine eigene Testklasse in der bereits vorhandenen Ordnerstruktur für Tests erstellt. Diese Klassen haben alle eine ```setup()```-Methode, in der die grundlegend benötigten Objekte (Alle Monster, Game) erstellt werden.

### Folgende Methoden werden in der ```MonsterTest```-Klasse erstellt:
- ```Collection<Object[]> values()```-Variable:
    - Speichert die Parameter für die Konstruktoren.
- ```testMonsterParamters```-Methode:
    - Testet, ob alle übergebenen Parameter (Alle Components) korrekt gesetzt werden. Hierfür wird der ```Monster()```-Konstruktur aufgerufen und die Parameter mit den erwarteten Werten verglichen.
- ```testMonsterAdded()```-Methode:
    - Testet, ob ein erstelltes Monster in der EntityList von ```starter.Game``` enthalten ist.
- ```testNegativeKnockbackAmount()```:
    - Testet, ob eine ```IllegalArgumentException``` geworfen wird, wenn die ```knockback()```-Methode mit einem negativen Wert für den Parameter ```knockbackamount``` aufgerufen wird. Beim Werfen einer Exception wird die Exception mit try-catch gecatched und ausgegeben. Beim "Nicht-Werfen" einer Exception wird die ```fail()```-Methode aufgerufen.
- ```testZeroKnockbackAmount()```:
    - Testet, ob eine ```IllegalArgumentException``` geworfen wird, wenn die ```knockback()```-Methode mit einem Wert von 0 für den Parameter ```knockbackamount``` aufgerufen wird. Beim Werfen einer Exception wird die Exception mit try-catch gecatched und ausgegeben. Beim "Nicht-Werfen" einer Exception wird die ```fail()```-Methode aufgerufen.
- ```testPositiveKnockbackAmount()```:
    - Testet, ob die korrekte Position für die Entität berechnet wird. Hierfür wird die ```knockback()```-Methode mit einem Wert von 1 aufgerufen und die Position der Entität mit der erwarteten Position verglichen. 
- ```testMaxKnockbackAmount()```:
    - Testet, ob eine ```NullPointerException``` geworfen wird, wenn die ```knockback()```-Methode mit einem Wert von 1000 für den Parameter ```knockbackamount``` aufgerufen wird, da die berechnete Position außerhalb der Map liegt. Beim Werfen einer Exception wird die Exception mit try-catch gecatched und ausgegeben. Beim "Nicht-Werfen" einer Exception wird die ```fail()```-Methode aufgerufen.
- ```testSetFrozen()```:
    - Testet, ob die Animation entsprechend gewechselt wird und ob die Velocity der Entität auf 0 gesetzt wird. Hierfür wird die ```setFrozen()```-Methode aufgerufen und die Animation und die Velocity der Entität mit den erwarteten Werten verglichen.
- ```testOnDeath()```:
    - Die ```onDeath()```-Methode wird aufgerufen, wenn das Monster 0 HP erreicht. Wir testen, ob das Monster dann dementsprechend aus der EntityList in ```starter.Game``` entfernt wird. Außerdem wird getestet, ob der ```killedMonsters```-Counter um 1 erhöht wird.
- ```testSetHitDamagePositive()```:
    - Testet, ob die ```hitDamage```-Variable korrekt gesetzt wird. Hierfür wird die ```setHitDamage()```-Methode aufgerufen und die ```hitDamage```-Variable mit dem erwarteten Wert verglichen. Außerdem wird getestet, ob das Monster den entsprechenden Schaden beim Hero verursacht.
- ```testSetHitDamageNegative()```:
    - Testet, ob die ```hitDamage```-Variable korrekt gesetzt wird. Hierfür wird die ```setHitDamage()```-Methode aufgerufen und die ```hitDamage```-Variable mit dem erwarteten Wert verglichen. Außerdem wird getestet, ob das Monster trotz eines negativen Wertes dem Helden den entsprechenden Schaden zufügt.
- ```testSetHitDamageZero()```:
    - Testet, ob die ```hitDamage```-Variable korrekt gesetzt wird. Hierfür wird die ```setHitDamage()```-Methode aufgerufen und die ```hitDamage```-Variable mit dem erwarteten Wert verglichen. Außerdem wird getestet, ob das Monster dem Hero keinen Schaden zufügt.
- ```testSetHitDamageMax()```:
    - Testet, ob die ```hitDamage```-Variable korrekt gesetzt wird. Hierfür wird die ```setHitDamage()```-Methode aufgerufen und die ```hitDamage```-Variable mit dem erwarteten Wert verglichen. Außerdem wird getestet, ob das Monster den entsprechenden Schaden beim Hero verursacht.

### Folgende Methoden werden in der ```ProtectTileRadiusWalkTest```-Klasse erstellt:
- ```Collection<Object[]> values()```-Variable:
    - Speichert die Parameter für die Konstruktoren.
- ```testNegativeBreakTime()```:
    - Testet, ob die Position des Monsters bei einer negativen Breaktime korrekt geändert wird. Wenn eine Exception geworfen wird, wird diese mit try-catch gecatched und ausgegeben. Außerdem wird der Test durch Aufruf von ```fail()``` fehlschlagen gelassen.
- ```testZeroBreakTime()```:
    - Testet, ob eine Exception aufgrund des nicht vorhandenen Cooldowns geworfen wird. Wenn eine Exception geworfen wird, wird diese mit try-catch gecatched und ausgegeben.
- ```testPositiveBreakTime()```:
    - Testet, ob die Position des Monsters bei einer positiven Breaktime korrekt geändert wird. Dies wird mit einer for-Schleife, die die Frames simuliert, geprüft. 
- ```testMaxBreakTime()```:
    - Testet, ob die Position des Monsters bei einer maximalen Breaktime von 20 Sekunden korrekt geändert wird. Dies wird mit einer for-Schleife, die die Frames simuliert, geprüft. 
- ```testNegativeRadius()```:
    - Testet, ob sich die Position des Monsters trotzdem ändert. Bei einer geworfenen Exception wird diese mit try-catch gecatched und ausgegeben.
- ```testZeroRadius()```:
    - Testet, ob sich die Position des Monsters trotzdem ändert. Bei einer geworfenen Exception wird diese mit try-catch gecatched und ausgegeben.
- ```testPositiveRadius()```:
    - Testet, ob sich die Position des Monsters ändert. 
- ```testMaxRadius()```:
    - Testet, ob sich die Position des Monsters ändert. Auch bei einem großen Radius sollte sich die Position des Monsters ändern und keine ```OutOfBoundException``` geworfen werden. Bei einer geworfenen Exception wird diese mit try-catch gecatched und ausgegeben. Außerdem wird der Test durch Aufruf von ```fail()``` fehlschlagen gelassen.
- ```testNotAccessibleTile()```: 
    - Testet, ob sich die Position des Monsters ändert, wenn sich die Position des "protected Tiles" auf einem nicht begehbaren Tile befindet. Hierzu wählen wir ein Walltile. Wenn eine Exception geworfen wird, wird diese mit try-catch gecatched und ausgegeben.
- ```testAccessibleTile()```:
    - Testet, ob sich die Position des Monsters ändert, wenn sich die Position des "protected Tiles" auf einem begehbaren Tile befindet. Hierfür wählen wir das Endtile. Wenn eine Exception geworfen wird, wird diese mit try-catch gecatched und ausgegeben. Außerdem wird der Test durch Aufruf von ```fail()``` fehlschlagen gelassen.

#### UML-Diagramm

![UML-Diagramm](https://i.ibb.co/zxwSvtJ/ZPF1-IWCn48-Rl-n-Gv-Ahw1fsuf8a-ZRn-Iu8tg-R9-KCCc6-Sd-C5b5y-Tx-Dgl-AJPz-HRo-VSdy-Co4h5e-Wo-Uj-Sqs-W6p.png)