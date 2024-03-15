import grid
from grapher import *
from time import sleep

coordGrid = grid.drawGrid()
graph = Grapher()
coordGrid.add(graph)

graph.plot('x')
sleep(1.5)
graph.plot('0.1x ^2')
sleep(1.5)
graph.plot('3x + 5')
sleep(1.5)
graph.plot('0.05x^2 + 3x - 20')
sleep(1.5)
graph.plot('0.05x^3 - x^2 + 40')
sleep(1.5)
graph.plot('-x')
sleep(4)
graph.clearGraph()
sleep(1)
graph.plot('x')
sleep(1.5)
graph.clearGraph()
sleep(1)
graph.plot('x')
