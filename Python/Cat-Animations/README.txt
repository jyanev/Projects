---------------------------------------
Authors: John Yanev
---------------------------------------
Brief overview of program:

Overview

This Cat class draws a customizable cat on a canvas. It is a subclass of the Layer class, so it iherits all of the methods and attributes of that class.

Attributes of Cat
color        -  String value; sets the color of the cat, includes all the cs1graphics colors as well as a 'black and white' option
fat          -  Boolean value; makes the cat fat if True, healthy if False
spots        -  Boolean value; gives cat spots if True, no spots if False
spotColor    -  String value; sets the color of the spots on the cat, includes all the cs1graphics colors
expression   -  String value; gives the cat an expression from 'neutral', 'bored', 'angry', 'sad', and 'fed up'

Methods of Cat
makeFat          -  makes the cat fat
makeHealthy      -  makes the cat healthy
addSpots         -  adds spots to the cat
changeExpression -  changes the cat's expression
changeColor      -  changes the cat's color
changeDirection  -  changes the cat's direction so that its either facing left or right
speech           -  adds text above the cat
meow             -  cat meows loudly
stopMeow         -  stops the meow and returns the cat to a neutral expression


Methods

1. makeFat() - makes the cat fat

2. makeHealthy() - makes the cat healthy

3. addSpots(spotColor) - adds spots to the cat
	3.1 spotColor : String value; includes all regular cs1graphics colors; default value 'black'

4. changeExpression(expression) - changes the expression of the cat
	4.1 expression : String value; the expression you want to change to, choose between 'neutral', 'bored', 'angry', 'sad', and 'fed up'; default value 'neutral'

5. changeColor(color) - changes the color of the cat
	5.1 color : String value; sets the color of the cat; includes all regular cs1graphics colors as well as a 'black and white' option, default value 'white'

6. meow() - makes the cat meow

7. stopMeow() - stops the cat's meow and returns it to a neutral expression

8. changeDirection() - changes the direction of the cat sso that its facing the left or the right opposite of its current direction

9. speech(message, time) - adds text above the cat
	9.1 message : String value, the text that will appear above the cat
	9.2 time : Int value, how long the text will be displayed, default value of 2 seoncds
-----------------------------------
