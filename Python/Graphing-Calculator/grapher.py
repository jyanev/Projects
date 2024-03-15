from cs1graphics import *

class Grapher(Layer):
    def __init__(self):
        '''
        Grapher class is a subclass of Layer. Given a string representation of a function,
        it will graph that function. Only works for linear, quadratic, and cubic functions
        in the form ax^3 + bx^2 + cx + d.


        Constructor:
    
        Positions the graph in the center of the coordinate grid.
        self._listOfColors - stores all the color variants for the graph
        self._currentColor - stores the current color that will be used for the next graph
        self._count - stores how many graphs are on the grid in total
        '''
        super().__init__()
        self.move(200,200)
        self._listOfColors = ['red', 'blue', 'green', 'orange', 'violet']
        self._currentColor = 'red'
        self._count = -1

    def _createFunction(self, function):
        '''
        __createFunction__ takes in a string representation of a function
        and returns a tuple of all of the coefficients of the function as float values
        '''
        function = function.replace(' ', '')
        self.fSplitString = []
        fSplitPlus = function.split('+')
        for i in fSplitPlus:
            fSplitMinus = i.split('-')
            for i2 in fSplitMinus:
                if function[function.find(i2)-1] == '-':
                    self.fSplitString.append('-' + i2)
                else:
                    self.fSplitString.append(i2)

        for i in self.fSplitString:
            if i == '':
                self.fSplitString.remove(i)

        if 'x^3' not in function:
            self.fSplitString.insert(0, '0')
        if 'x^2' not in function:
            self.fSplitString.insert(1, '0')
          
        flag = True
        for k in range(len(function)-1):
            if function[k] == 'x':
                if function[k+1] != '^':
                    flag = False
                    
        if function[-1] == 'x':
            flag = False
        if flag:
            self.fSplitString.insert(2, '0')
            
        if len(self.fSplitString) == 3:
            self.fSplitString.append('0')
        
        
        fSplit = [0, 0, 0, 0]
        for i in range(1, 5):

            
            if 'x' in self.fSplitString[-i]:
                if self.fSplitString[-i][0] == 'x':
                    fSplit[-i] = 1.0
                elif self.fSplitString[-i][:2] == '-x':
                    fSplit[-i] = -1.0
                else:
                    fSplit[-i] = float(self.fSplitString[-i][0 : self.fSplitString[-i].find('x')])
            else:
                fSplit[-i] = float(self.fSplitString[-i])

        return tuple(fSplit)


    def plot(self, function):
        '''
        plot draws a graph of the function on the coordinate grid.
        '''
        self._count += 1
        self._changeColor()
        coef = self._createFunction(function)
        
        for x in range(-200, 200):
            segment = Path(Point(x, -(coef[0]*x**3 + coef[1]*x**2 + coef[2]*x + coef[3])),
                           Point((x+1), -(coef[0]*(x+1)**3 + coef[1]*(x+1)**2 + coef[2]*(x+1) + coef[3])))
            segment.setBorderColor(self._currentColor)
            self.add(segment)


    def _changeColor(self):
        '''
        __changeColor__ changes the color of the graph
        Used every time a new graph is plotted
        '''
        self._currentColor = self._listOfColors[self._count % len(self._listOfColors)]

    def clearGraph(self):
        '''
        clears the coordinate grid and resets the graph count
        '''
        self.clear()
        self._count = -1
        



    
    

    
