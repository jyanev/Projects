from cs1graphics import *

#determines the amount of pixels per grid
grid_pix = int(input('Enter pixels per grid cell: '))

#creates the canvas
paper = Canvas(grid_pix * 15, grid_pix * 13, 'burlywood4')

#draws a middle line separating the board into 2 sides
middle_line = Path(Point(grid_pix * 7.5, 0), Point(grid_pix * 7.5, grid_pix * 13))
middle_line.setBorderWidth(3)
paper.add(middle_line)

#draws the beige playing area
for x in range(2):
    board = Rectangle(grid_pix * 6, grid_pix * 11, Point(grid_pix * (4 + (7 * x)), grid_pix * 6.5))
    board.setFillColor('navajowhite')
    board.setBorderWidth(2)
    paper.add(board)


#creates the top half of the triangles
top = Layer()

#loops an amount of times equivalent to the number of triangles in the top row
for tri in range(grid_pix, grid_pix * 14, grid_pix):

    #determines whether the triangle belongs in the top left side
    if tri < grid_pix * 7:

        #determines whether the triangle is dark orange or tan
        if (tri / grid_pix) % 2 == 0:
            triangle = Polygon(Point(tri, grid_pix), Point(tri + grid_pix, grid_pix), Point(tri + grid_pix/2, grid_pix * 6))
            triangle.setFillColor('darkorange3')
        if (tri / grid_pix) % 2 == 1:
            triangle = Polygon(Point(tri, grid_pix), Point(tri + grid_pix, grid_pix), Point(tri + grid_pix/2, grid_pix * 6))
            triangle.setFillColor('tan')
            
        #adds in the left side text
        text = Text(str(25-(tri // grid_pix)),  grid_pix/2, Point(tri + grid_pix/2, grid_pix/2))
        paper.add(text)
        text = Text(str(0 + (tri // grid_pix)), grid_pix/2, Point(tri + grid_pix/2, grid_pix/2 + grid_pix * 12))
        paper.add(text)
            
    elif tri == grid_pix * 7:
        continue

    #determines whether the triangle belongs in the top right side
    if tri > grid_pix * 7:
        if (tri / grid_pix) % 2 == 0:
            triangle = Polygon(Point(tri, grid_pix), Point(tri + grid_pix, grid_pix), Point(tri + grid_pix/2, grid_pix * 6))
            triangle.setFillColor('tan')
        if (tri / grid_pix) % 2 == 1:
            triangle = Polygon(Point(tri, grid_pix), Point(tri + grid_pix, grid_pix), Point(tri + grid_pix/2, grid_pix * 6))
            triangle.setFillColor('darkorange3')
            
        #adds in the right side text
        text = Text(str(26 - (tri // grid_pix)),  grid_pix/2, Point(tri + grid_pix/2, grid_pix/2))
        paper.add(text)
        text = Text(str(-1 + (tri // grid_pix)), grid_pix/2, Point(tri + grid_pix/2, grid_pix/2 + grid_pix * 12))
        paper.add(text)
            
    triangle.setBorderWidth(2)
    top.add(triangle)
    
paper.add(top)


#creates the bottom half of the triangles
bottom = top.clone()
bottom.adjustReference(grid_pix * 7.5, grid_pix)
bottom.rotate(180)
bottom.move(0, grid_pix * 11)
paper.add(bottom)


#draws the playing pieces
for num,pt,whiteOnTop in [(2,1,True), (5,6,False), (3,9,False), (5,13,True)]:
    x = grid_pix * (pt + 0.5)
    y = grid_pix * 1.5 - .1 * (grid_pix/2)

    #draws the white pieces on top and black on bottom
    if whiteOnTop:

        for i in range(num):
            piece = Circle((grid_pix/2)*.9, Point(x,y + grid_pix * .9 * i))
            piece.setFillColor('white')
            paper.add(piece)
            
            piece2 = piece.clone()
            piece2.adjustReference(0, -(y+ grid_pix * .9 * i) + grid_pix * 6.5)
            piece2.flip(90)
            piece2.setFillColor('black')
            paper.add(piece2)

    #draws the black pieces on top and white on bottom        
    else:
        
        for i in range(num):
            piece = Circle((grid_pix/2)*.9, Point(x,y + grid_pix * .9 * i))
            piece.setFillColor('black')
            paper.add(piece)
            
            piece2 = piece.clone()
            piece2.adjustReference(0, -(y+ grid_pix * .9 * i) + grid_pix * 6.5)
            piece2.flip(90)
            piece2.setFillColor('white')
            paper.add(piece2)
    
