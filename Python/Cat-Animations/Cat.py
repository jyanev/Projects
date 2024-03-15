from cs1graphics import *
from random import randint
from time import sleep

class Cat(Layer):
    def __init__(self, color = 'white', spots = False, spotColor = 'black', fat = False, expression = 'neutral'):
        '''
        Creates a cat object that can be drawn on a canvas.
        Inherits all the same methods from the Layer class.

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
        '''

        super().__init__()
        self._color = color
        self._fat = fat
            
        if fat:
            self._body = Ellipse(100, 50, Point(0,  0))
            self._body.setDepth(75)
            if self._color == 'black and white':
                self._body.setFillColor('black')
            else:
                self._body.setFillColor(self._color)
            self._leftLeg = Path(Point(-28,20), Point(-31, 24))
            self._leftLeg.setBorderWidth(5)
            self._rightLeg = self._leftLeg.clone()
            self._rightLeg.flip(-2)
            self._rightLeg.move(60,0)
            self._tail = Spline(Point(-47, -6), Point(-58, -14), Point(-64, -12),
                                    Point(-72, -20), Point(-70, -22), Point(-64, -14),
                                    Point(-58, -16), Point(-45, -9))
        else:
            self._body = Ellipse(80, 40, Point(0, 0))
            self._body.setDepth(75)
            if self._color == 'black and white':
                self._body.setFillColor('black')
            else:
                self._body.setFillColor(self._color)
            self._leftLeg = Path(Point(-23,16), Point(-25, 27))
            self._leftLeg.setBorderWidth(5)
            self._rightLeg = self._leftLeg.clone()
            self._rightLeg.flip(-2)
            self._rightLeg.move(44,0)
            self._tail = Spline(Point(-37, -6), Point(-48, -14), Point(-54, -12),
                                    Point(-62, -20), Point(-60, -22), Point(-54, -14),
                                    Point(-48, -16), Point(-35, -9))
         
        self._body.setDepth(75)
        self._head = Circle(16, Point(33, -20))
        if self._color == 'black and white':
            self._head.setFillColor('black')
        else:
            self._head.setFillColor(self._color)

        self._leftEar = Path(Point(21,-29), Point(21, -38), Point(29, -36))
        self._leftEar.setBorderWidth(3)
        self._rightEar = self._leftEar.clone()
        self._rightEar.flip()
        self._rightEar.move(26,1)
        self._leftEye = Circle(4, Point(26, -24))
        self._leftEye.setFillColor('white')
        self._rightEye = self._leftEye.clone()
        self._rightEye.move(13,0)
        self._leftPupil = Circle(1, Point(26, -24))
        self._leftPupil.setFillColor('black')
        self._rightPupil = self._leftPupil.clone()
        self._rightPupil.move(13,0)
        self._mouth = Spline(Point(27, -15), Point(33, -15), Point(39, -15))
        self._text = ''
        self._direction = 'right'
        
        self.add(self._body)
        self.add(self._head)
        self.add(self._leftLeg)
        self.add(self._rightLeg)
        self.add(self._leftEar)
        self.add(self._rightEar)
        self.add(self._leftEye)
        self.add(self._rightEye)
        self.add(self._leftPupil)
        self.add(self._rightPupil)
        self.add(self._mouth)
        self.add(self._tail)
        
        if spots:
            self.addSpots(spotColor)
        self._expression = 'neutral'    
        self.changeExpression(expression)
        self.changeColor(color)
        

    def makeFat(self):
        '''
        makes the cat fat
        '''
        self.remove(self._body)
        self.remove(self._leftLeg)
        self.remove(self._rightLeg)
        self.remove(self._tail)
            
        self._body = Ellipse(100, 50, Point(0, 0))
        self._body.setDepth(75)
        self._body.setFillColor(self._color)
        self._leftLeg = Path(Point(-28,20), Point(-31, 24))
        self._leftLeg.setBorderWidth(5)
        self._rightLeg = self._leftLeg.clone()
        self._rightLeg.flip(-2)
        self._rightLeg.move(60,0)
        self._tail = Spline(Point(-47, -6), Point(-58, -14), Point(-64, -12),
                                Point(-72, -20), Point(-70, -22), Point(-64, -14),
                                Point(-58, -16), Point(-45, -9))
        if self._direction == 'left':
            self._tail.flip()
            self._tail.move(96, 0)
        self.add(self._body)
        self.add(self._leftLeg)
        self.add(self._rightLeg)
        self.add(self._tail)

            
    def makeHealthy(self):
        '''
        makes the cat healthy
        '''
        self.remove(self._body)
        self.remove(self._leftLeg)
        self.remove(self._rightLeg)
        self.remove(self._tail)
            
        self._body = Ellipse(80, 40, Point(0, 0))
        self._body.setDepth(75)
        self._body.setFillColor(self._color)
        self._leftLeg = Path(Point(-23,16), Point(-25, 27))
        self._leftLeg.setBorderWidth(5)
        self._rightLeg = self._leftLeg.clone()
        self._rightLeg.flip(-2)
        self._rightLeg.move(44,0)
        self._tail = Spline(Point(-37, -6), Point(-48, -14), Point(-54, -12),
                                Point(-62, -20), Point(-60, -22), Point(-54, -14),
                                Point(-48, -16), Point(-35, -9))
        if self._direction == 'left':
            self._tail.flip()
            self._tail.move(74, 0)
        self.add(self._body)
        self.add(self._leftLeg)
        self.add(self._rightLeg)
        self.add(self._tail)


    def addSpots(self, spotColor = 'black'):
        '''
        adds spots to the cat

        Attributes
        spotColor  -  String value; includes all regular cs1graphics colors; default value 'black'
        '''
        for spot in range(randint(1,8)):
            spot = Circle(randint(3, 6), Point(randint(-26, 26), randint(-12, 12)))
            spot.setFillColor(spotColor)
            spot.setBorderColor(spotColor)
            spot.setDepth(74)
            self.add(spot)


    def changeExpression(self, expression = 'neutral'):
        '''
        changes the expression of the cat

        Attributes
        expression  -  String value; the expression you want to change to, choose between 'neutral', 'bored', 'angry', 'sad', and 'fed up'; default value 'neutral'
        '''            
        if expression == 'neutral':
            if self._expression == 'bored':
                self.remove(self._leftBoredBrow)
                self.remove(self._rightBoredBrow)
            elif self._expression == 'angry':
                self.remove(self._leftAngryBrow)
                self.remove(self._rightAngryBrow)
            elif self._expression == 'sad':
                self.remove(self._leftSadBrow)
                self.remove(self._rightSadBrow)

        if expression == 'fed up':
            if self._expression == 'bored':
                self.remove(self._leftBoredBrow)
                self.remove(self._rightBoredBrow)
            if self._expression == 'angry':
                self.remove(self._leftAngryBrow)
                self.remove(self._rightAngryBrow)
            if self._expression == 'sad':
                self.remove(self._leftSadBrow)
                self.remove(self._rightSadBrow)
        
            self._leftFedUpBrow = Path(Point(21, -27), Point(32, -27))
            self._rightFedUpBrow = Path(Point(34, -27), Point(45, -27))
            self._leftFedUpBrow.setBorderWidth(2)
            self._rightFedUpBrow.setBorderWidth(2)
            if self._direction == 'left':
                self._leftFedUpBrow.move(-66, 0)
                self._rightFedUpBrow.move(-66, 0)
            self.add(self._leftFedUpBrow)
            self.add(self._rightFedUpBrow)
            
        if expression == 'bored':
            if self._expression == 'angry':
                self.remove(self._leftAngryBrow)
                self.remove(self._rightAngryBrow)
            if self._expression == 'fed up':
                self.remove(self._leftFedUpBrow)
                self.remove(self._rightFedUpBrow)
            if self._expression == 'sad':
                self.remove(self._leftSadBrow)
                self.remove(self._rightSadBrow)
            self._leftBoredBrow = Path(Point(23, -26), Point(30, -26))
            self._rightBoredBrow = Path(Point(36, -26), Point(43, -26))
            self._leftBoredBrow.setBorderWidth(2)
            self._rightBoredBrow.setBorderWidth(2)
            if self._direction == 'left':
                self._leftBoredBrow.move(-66, 0)
                self._rightBoredBrow.move(-66, 0)
            self.add(self._leftBoredBrow)
            self.add(self._rightBoredBrow)

        if expression == 'angry':
            if self._expression == 'bored':
                self.remove(self._leftBoredBrow)
                self.remove(self._rightBoredBrow)
            if self._expression == 'fed up':
                self.remove(self._leftFedUpBrow)
                self.remove(self._rightFedUpBrow)
            if self._expression == 'sad':
                self.remove(self._leftSadBrow)
                self.remove(self._rightSadBrow)
            self._leftAngryBrow = Path(Point(21, -29), Point(31, -25))
            self._rightAngryBrow = Path(Point(34, -25), Point(44, -29))
            self._leftAngryBrow.setBorderWidth(2)
            self._rightAngryBrow.setBorderWidth(2)
            if self._direction == 'left':
                self._leftAngryBrow.move(-66, 0)
                self._rightAngryBrow.move(-66, 0)                         
            self.add(self._leftAngryBrow)
            self.add(self._rightAngryBrow)

        if expression == 'sad':
            if self._expression == 'bored':
                self.remove(self._leftBoredBrow)
                self.remove(self._rightBoredBrow)
            if self._expression == 'fed up':
                self.remove(self._leftFedUpBrow)
                self.remove(self._rightFedUpBrow)
            if self._expression == 'angry':
                self.remove(self._leftAngryBrow)
                self.remove(self._rightAngryBrow)
            self._leftSadBrow = Path(Point(21, -25), Point(31, -29))
            self._rightSadBrow = Path(Point(34, -29), Point(44, -25))
            self._leftSadBrow.setBorderWidth(2)
            self._rightSadBrow.setBorderWidth(2)
            if self._direction == 'left':
                self._leftSadBrow.move(-66, 0)
                self._rightSadBrow.move(-66, 0)
            self.add(self._leftSadBrow)
            self.add(self._rightSadBrow)
        self._expression = expression


    def changeColor(self, color = 'white'):
        '''
        changes the color of the cat

        Attributes
        color  - String value; sets the color of the cat; includes all regular cs1graphics colors as well as a 'black and white' option, default value 'white'

        '''
        self._color = color
        if color == 'black and white':
            self._head.setFillColor('white')
            self._body.setFillColor('black')
        else:
            self._head.setFillColor(color)
            self._body.setFillColor(color)


    def meow(self):
        '''
        make the cat meow
        '''
        if self._expression != 'neutral':
            if self._expression == 'bored':
                self.remove(self._leftBoredBrow)
                self.remove(self._rightBoredBrow)
            if self._expression == 'fed up':
                self.remove(self._leftFedUpBrow)
                self.remove(self._rightFedUpBrow)
            if self._expression == 'angry':
                self.remove(self._leftAngryBrow)
            if self._expression == 'sad':
                self.remove(self._leftSadBrow)
                self.remove(self._rightSadBrow)
        self._expression = 'neutral'
        self.remove(self._head)
        self.remove(self._leftEye)
        self.remove(self._rightEye)
        self.remove(self._leftPupil)
        self.remove(self._rightPupil)
        self.remove(self._mouth)
        self.remove(self._leftEar)
        self.remove(self._rightEar)

        self._meowHead = Ellipse(32, 20, Point(33, -18))
        if self._color == 'black and white':
            self._meowHead.setFillColor(('white'))
        else:
            self._meowHead.setFillColor(self._color)
        self._meowLeftEye = Ellipse(8, 4, Point(26, -22))
        self._meowLeftEye.setFillColor('white')
        self._meowRightEye = self._meowLeftEye.clone()
        self._meowRightEye.move(13, 0)
        self._meowLeftPupil = Circle(1, Point(26, -22))
        self._meowLeftPupil.setFillColor('black')
        self._meowRightPupil = Circle(1, Point(39, -22))
        self._meowRightPupil.setFillColor('black')
        self._meowLeftEar = Path(Point(21,-24), Point(21, -30), Point(29, -29))
        self._meowLeftEar.setBorderWidth(3)
        self._meowRightEar = self._meowLeftEar.clone()
        self._meowRightEar.flip()
        self._meowRightEar.move(26,1)
        self._meowMouth = Ellipse(12, 6, Point(33, -13))
        if self._color == 'black':
            self._meowMouth.setFillColor('darkgray')
        else:
            self._meowMouth.setFillColor('black')
        self._meowText = Text('MEEOWOOOW', 20, Point(30, -42))

        if self._direction == 'left':
            self._meowHead.move(-66, 0)
            self._meowLeftEye.move(-66, 0)
            self._meowRightEye.move(-66, 0)
            self._meowLeftEar.move(-66, 0)
            self._meowRightEar.move(-66, 0)
            self._meowLeftPupil.move(-66, 0)
            self._meowRightPupil.move(-66, 0)
            self._meowMouth.move(-66, 0)

        self.add(self._meowHead)
        self.add(self._meowLeftEye)
        self.add(self._meowRightEye)
        self.add(self._meowLeftEar)
        self.add(self._meowRightEar)
        self.add(self._meowLeftPupil)
        self.add(self._meowRightPupil)
        self.add(self._meowMouth)
        self.add(self._meowText)


    def stopMeow(self):
        '''
        stops the cat's meow and returns it to a neutral expression
        '''
        self.remove(self._meowHead)
        self.remove(self._meowLeftEye)
        self.remove(self._meowRightEye)
        self.remove(self._meowLeftEar)
        self.remove(self._meowRightEar)
        self.remove(self._meowLeftPupil)
        self.remove(self._meowRightPupil)
        self.remove(self._meowMouth)
        self.remove(self._meowText)

        self.add(self._head)
        self.add(self._leftEye)
        self.add(self._rightEye)
        self.add(self._leftPupil)
        self.add(self._rightPupil)
        self.add(self._mouth)
        self.add(self._leftEar)
        self.add(self._rightEar)


    def changeDirection(self):
        '''
        changes the direction of the cat so that its facing the left or the right; changes the direction to the opposite of what it is currently
        '''        
        if self._direction == 'right':
            self._head.move(-66, 0)
            self._leftEye.move(-66, 0)
            self._rightEye.move(-66, 0)
            self._leftPupil.move(-66, 0)
            self._rightPupil.move(-66, 0)
            self._leftEar.move(-66, 0)
            self._rightEar.move(-66, 0)
            self._mouth.move(-66, 0)
            self._tail.flip()
            if self._fat:
                self._tail.move(96, 0)
            else:
                self._tail.move(74, 0)
            self._direction = 'left'
        else:
            self._head.move(66, 0)
            self._leftEye.move(66, 0)
            self._rightEye.move(66, 0)
            self._leftPupil.move(66, 0)
            self._rightPupil.move(66, 0)
            self._leftEar.move(66, 0)
            self._rightEar.move(66, 0)
            self._mouth.move(66, 0)
            self._tail.flip()
            if not self._fat:
                self._tail.move(-96, 0)
            else:
                self._tail.move(-74, 0)
            self._direction = 'right'            

    def speech(self, message, time = 2):
        '''
        adds text above the cat

        Attributes
        message  -  String value, the text that will appear above the cat
        time     -  Int value, how long the text will be displayed, default value of 2 seoncds
        '''        
        self._text = Text(message, 14, Point(30, -50))
        self._text
        self.add(self._text)
        sleep(time)
        self.remove(self._text)



if __name__ == "__main__":
    paper = Canvas(500,300)
    
    cat1 = Cat('white', True, 'black', True, 'bored')
    cat1.move(120, 220)

    cat2 = Cat('darkgray')
    cat2.move(550,220)
    cat2.changeDirection()

    cat3 = Cat('black', True, 'gray', False)
    cat3.move(90,110)
    cat3.changeDirection()
    cat4 = Cat('red', True, 'green', True)
    cat4.move(270,130)
    cat4.changeDirection()
    cat5 = Cat()
    cat5.move(400,90)
    cat6 = Cat('black and white', False, 'black', True)
    cat6.move(430,260)
    
    paper.add(cat1)
    paper.add(cat2)
    for i in range(50):
        cat2.move(-5, 0)
        sleep(0.01)

    cat2.speech('wanna play?')
    sleep(0.5)
    cat1.speech('no go away')
    sleep(0.5)
    cat2.speech('cmon, get up lets play')
    sleep(0.5)
    cat1.changeExpression('fed up')
    cat1.speech('i said go away')
    sleep(0.5)
    cat2.changeExpression('sad')
    cat2.speech('pleaase i wanna play')
    sleep(0.5)
    cat1.changeExpression('angry')
    cat1.speech('**** OFF')
    cat2.speech('pleeaaase')
    cat1.meow()
    sleep(0.7)
    cat2.meow()
    sleep(0.7)
    paper.add(cat3)
    cat3.meow()
    sleep(0.5)
    paper.add(cat4)
    cat4.meow()
    sleep(0.3)
    paper.add(cat5)
    cat5.meow()
    sleep(0.2)
    paper.add(cat6)
    cat6.meow()
    sleep(3)
    cat1.stopMeow()
    cat2.stopMeow()
    cat3.stopMeow()
    cat4.stopMeow()
    cat5.stopMeow()
    cat6.stopMeow()
    sleep(0.5)

    cat1.makeHealthy()
    cat2.makeFat()
    cat4.addSpots()
    cat5.changeExpression('angry')
    cat6.changeDirection()
    cat3.changeExpression('bored')
    cat5.changeColor('green')


    
