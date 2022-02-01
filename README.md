# BabbelTest
This is a simple game app. When it starts, the user will see a word at the top of the screen. Another word will appear below the first word and begin descending down the
screen. Ths user is to choose if the descending word is the correct translation of the first word.

## Total time taken
From start to finish, the develpmemt time was 6hrs approximately.

## Time distrubution
The time distribution for the game is as follows:\n
1) Initial planning: It was necessary to plan how the game should look and operate before coding begins. This took 1hr
2) Writing the game logic: The game logic involes classes that fecth list of words from the json and determine if the user passesd or failed a question. 
Writing and manually testing this class took 3hrs.
3) Wrtine the game's user interface. The total time spent on the user interface of the app is 2hrs.
4) Instrumented test: This project contains instrumented tests for GameWordsManagerInstrumentedTest class. This took 30min.

## Decisions made to solve certain aspects of the game
I will state two important decisions I took during the game development:
1) Keeping the UI simple: The app requires a word to fall from the top of the screen to the bottom. There are many ways to achieve this but I choose to use
the well known Android ```ObjectAnimator``` class. This class handled all the animation work and saved me lots of time.
2) Clean code to make testing easy: To make testing the app easy, I created dedicated classes for each aspect of the game. For example: the game's user interface is 
handled by ```GameUIManager``` and the logic of the game is handled by ```GameWordsManager```. This seperation of concerns helped to ensure that UI code is not
mixed with business logic.

## Decisions made because of restricted time
Becasue of restricted time, I decided to keep the UI as simple as possible. Nothing facy. Stright to the point.

## What would be the first thing to improve or add if there had been more time
I didnt have time to write UI tests. If I had more time I would have written them.
