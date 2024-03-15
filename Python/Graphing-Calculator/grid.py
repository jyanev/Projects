from cs1graphics import *
def drawGrid():
    grid = Canvas(400, 400)
    for x in range(0, 401, 20):
        a = Path(Point(x, 0), Point(x, 400))
        a.setBorderColor('gray63')
        b = Path(Point(0, x), Point(400, x))
        b.setBorderColor('gray63')
        grid.add(a)
        grid.add(b)
    a = Path(Point(200, 0), Point(200, 400))
    a.setBorderWidth(3)
    b = Path(Point(0, 200), Point(400, 200))
    b.setBorderWidth(3)
    grid.add(a)
    grid.add(b)
    grid.add(Text('x', 14, Point(390, 190)))
    grid.add(Text('y', 14, Point(210, 10)))
    return grid
