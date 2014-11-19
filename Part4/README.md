# Asteroids Part 4: Finishing Asteroids

To finish our Asteroids game, we need to write a new class that represents Bullets. We will store the Bullets in an ArrayList much like we did with the Asteroids. Once we can shoot the bullets and destroy Asteroids, we will have a working game. Your Asteroids game doesn't have to look like any other. Feel free to modify it in any way you wish.

###Suggested steps to completing this assignment

    1. Write a Bullet class that extends Floater. You will need to:
        * Write a constructor that takes one ship argument: Bullet(SpaceShip theShip)
            1. Intialize myCenterX and myCenterY of the bullet to be the same as the ship.
            2. Initialize myPointDirection of the bullet to be the same as myPointDirection of the ship
            3. convert myPointDirection to radians with the following code: double dRadians =myPointDirection*(Math.PI/180);
            4. Initialize myDirectionX as 5 * Math.cos(dRadians) + the myDirectionX of the ship
            5. Initialize myDirectionY as 5 * Math.sin(dRadians) + the myDirectionY of the ship
        * Override the show method of the Floater class so that you can use circular bullets
    2. Now, add just one bullet to your program. First, just draw it to the screen. Make sure you can see it before continuing to the next step.
    3. Now, move the bullet.
    4. Now create an ArrayList of Bullets. The list should be empty to start with. Everytime you press the key to "shoot", add a new Bullet to the ArrayList. Modify the program with loops that draw and move all the bullets in the ArrayList
    5. One way to check for collisions between the bullets and the Asteroids is to write a loop within a loop (see below for another way). Everytime you move one asteroid you will need:
        * a loop that goes through all the bullets to see if there is a collision between that bullet and the asteroid
        * if there is a collision remove both the asteroid and the bullet from their ArrayLists
    6. Alternatively, you might be able to use processing's get() to check for collisions.
    7. (Note: I'm not sure if this is still true in the current version of Processing)If your finished program is running slowly, try changing size() to use P2D. For example, size(600,600,P2D); creates an applet 600 x 600 that uses processing's fast 2D renderer (which is not as accurate as the default renderer).

