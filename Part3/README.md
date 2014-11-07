 Asteroids Part 3: An ArrayList of Asteroids
=======
Background: ArrayList
------
An array probably isn't the best way to keep track of a bunch of asteroids. Arrays have fixed size. You can't easily add or remove asteroids from an array. A better choice might be an ArrayList. TheArrayList class has a number of useful member methods:
'''
    boolean add(Object x)
    void add(int index, Object element)
    Object get(int index)
    Object remove(int index)
    Object set(int index, Object x)
    int size()
'''
Steps to completing this assignment
----------
  - Modify it so that you have an ArrayList of Asteroids.
  - Now we'll modify the program so that when our space ship strikes an asteroid, the asteroid is removed from the ArrayList
  - Everytime an asteroid moves find the distance between that asteroid and the ship.
  - Use processing's dist() function to find the distance between that asteroid and the ship.
      * If the distance is less than 20 remove the asteroid from the ArrayList.
        Otherwise, move and rotate the asteroid normally

Submit the same URL for your AsteroidsGame that you submitted for the two previous assignments to the school loop drop box.
