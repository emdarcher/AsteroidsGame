import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Part4 extends PApplet {

/* keypress stuff, button flag bit numbers */
public static final int UP_BIT    = 0;
public static final int LEFT_BIT  = 1;
public static final int DOWN_BIT  = 2;
public static final int RIGHT_BIT = 3;
public static final int BRAKE_BIT = 4;
public static final int HYPER_BIT = 5;
/* variable to store flags bits for each key, can store up to 32 flags
because an int is 32 bits */
public int key_bits = 0x00000000;

/* basic spaceship looks and stuff */
public static final int basic_corners = 3;
int basic_xCorners[] = { -8,16,-8, };
int basic_yCorners[] = { -8,0,8, };
/* stuff for the asteroid's looks */
public static final int a_corners = 6;
int a_xCorners[] = { -11,7,13,6,-11,-5, };
int a_yCorners[] = { -8,-8,0,10,8,0, };

public static final int bg_val = 24; /* stores the background value */

public static final int NUM_STARS = 64; /* Number of stars to have */
Star [] stars; /* declaration of a Star array for the stars */
SpaceShip spacey;/* a Spaceship */
//Asteroid astrid; /* an Asteroid (used in testing) */ 
public static final int NUM_ROCKS = 4; /* number of "rocks" (Asteroids) */
ArrayList <Asteroid> rocks_list; /* declare an Asteroid ArrayList */

public void setup() {
  background(bg_val);
  size(255,255);
  spacey = new SpaceShip(); /* inits the spaceship */
  rocks_list = new ArrayList <Asteroid>();
  for(int r=0;r<NUM_ROCKS;r++){
    rocks_list.add( new Asteroid() ); 
  }
  stars = new Star[NUM_STARS]; /* inits Stars */
  for(int i=0;i<stars.length;i++){
    stars[i]=new Star(); 
  }
}
public void draw() {
  scan_key_bits(); /* function checks to see which flags in 'key_bits'
                    * have been set, and then executes the proper movements */
  background(bg_val); /* set backround to clear screen */ 
  spacey.move();
  for(int i=0;i<stars.length;i++){ //show the stars (this is quite inefficient,
    stars[i].show(); }             //should only have to happen once and stay)
  for(int r=0;r<rocks_list.size();r++){
    if(rocks_list.get(r).shipDist(spacey) < 20){ rocks_list.remove(r); }
  }
  for(Asteroid ast : rocks_list){
    ast.move(); ast.show();/* move then show the Asteroids */
  }
  spacey.show();
} 
public void scan_key_bits(){
  /* checks to see if the different button flag bits are set in 'key_bits'
  then calls the functions that each button should do */
  if((key_bits & (1<<BRAKE_BIT))!=0){ spacey.brake(0.1f);}
  if((key_bits & (1<<HYPER_BIT))!=0){ spacey.hyperspace();}
  if((key_bits & (1<<UP_BIT))!=0){    spacey.accelerate((double)0.1f);}
  if((key_bits & (1<<DOWN_BIT))!=0){  spacey.accelerate((double)(-0.1f));}
  if((key_bits & (1<<LEFT_BIT))!=0){  spacey.rotate(-8);}
  if((key_bits & (1<<RIGHT_BIT))!=0){ spacey.rotate(8);}
}
public void keyPressed(){
  /* runs when key is pressed */
if(key == 'b'){     key_bits |= (1<<BRAKE_BIT);}
if(key == 'h'){     key_bits |= (1<<HYPER_BIT);}
if(keyCode == UP){  key_bits |= (1<<UP_BIT);}
if(keyCode == LEFT){key_bits |= (1<<LEFT_BIT);}
if(keyCode == RIGHT){key_bits|= (1<<RIGHT_BIT);}
if(keyCode == DOWN){key_bits |= (1<<DOWN_BIT);}
} 
public void keyReleased() {
  /* runs when key is released */
if(key == 'b'){     key_bits &= ~(1<<BRAKE_BIT);}
if(key == 'h'){     key_bits &= ~(1<<HYPER_BIT);}
if(keyCode == UP){  key_bits &= ~(1<<UP_BIT);}
if(keyCode == LEFT){key_bits &= ~(1<<LEFT_BIT);}
if(keyCode == RIGHT){key_bits&= ~(1<<RIGHT_BIT);}
if(keyCode == DOWN){key_bits &= ~(1<<DOWN_BIT);} 
} 
public class SpaceShip extends Floater  {   
    /* for the spaceship */
    public void setX(int x){myCenterX=x;}
    public int getX(){return (int)myCenterX;}
    public void setY(int y){myCenterY=y;}
    public int getY(){return (int)myCenterY;}
    public void setDirectionX(double x){myDirectionX=x;}
    public double getDirectionX(){return myDirectionX;}
    public void setDirectionY(double y){myDirectionY=y;}
    public double getDirectionY(){return myDirectionY;}
    public void setPointDirection(int degrees){myPointDirection=degrees;}
    public double getPointDirection(){return myPointDirection;}

    public SpaceShip(){ //constructor
      corners = basic_corners;
      xCorners = new int [corners];
      yCorners = new int [corners];
      myCenterX = (width>>1); myCenterY = (height>>1);
      myDirectionX = 0; myDirectionY = 0;
      myPointDirection = 0;
      myColor = 0xff239589;//stuff
      for(int i=0;i<corners;i++){ //ints arrays
        xCorners[i] = basic_xCorners[i];
        yCorners[i] = basic_yCorners[i];
      }
    }
    public void brake(double bAmount){
      if(myDirectionX != 0){
        myDirectionX += (myDirectionX>0) ? (0-bAmount) : bAmount;
      }
      if(myDirectionY != 0){
        myDirectionY += (myDirectionY>0) ? (0-bAmount) : bAmount;
      }
    }
    public void hyperspace(){
      myDirectionX=0; myDirectionY=0;
      myPointDirection = (double)((Math.random()*360)-180);
      myCenterX = (double)((Math.random()*width));
      myCenterY = (double)((Math.random()*height)); 
    }
}//end Spaceship class

public class Asteroid extends Floater {
    private int rotSpeed; /* variable to store rotation speed */
    private static final int SHIP_BUFFER = 64;
    /* finishing the abstract functions */
    public void setX(int x){myCenterX=x;}
    public int getX(){return (int)myCenterX;}
    public void setY(int y){myCenterY=y;}
    public int getY(){return (int)myCenterY;}
    public void setDirectionX(double x){myDirectionX=x;}
    public double getDirectionX(){return myDirectionX;}
    public void setDirectionY(double y){myDirectionY=y;}
    public double getDirectionY(){return myDirectionY;}
    public void setPointDirection(int degrees){myPointDirection=degrees;}
    public double getPointDirection(){return myPointDirection;}

  public Asteroid(){
    rotSpeed = (int)((Math.random()*8)-4);
    corners = a_corners;
    xCorners = new int [corners];
    yCorners = new int [corners];
    myCenterX = (double)((Math.random()*((width>>1)-SHIP_BUFFER)));
    myCenterX += ((Math.random()>=0.5f)) ? myCenterX:((width>>1)+SHIP_BUFFER); 
    myCenterY = (double)((Math.random()*((height>>1)-SHIP_BUFFER)));
    myCenterY += ((Math.random()>=0.5f)) ? myCenterY:((height>>1)+SHIP_BUFFER);
    myDirectionX = (double)((Math.random()*2)-1);
    myDirectionY = (double)((Math.random()*2)-1);
    myPointDirection = 0;
    myColor = 0xfff3d2e1;//stuff
    for(int i=0;i<corners;i++){ //inits arrays
        xCorners[i] = a_xCorners[i];
        yCorners[i]  = a_yCorners[i];
    }
  } 
  public void move(){
    rotate(rotSpeed); //rotate the Asteroid
    super.move(); //call the original 'move()'
  }
  public float shipDist(SpaceShip ship){
    return dist((float)myCenterX, (float)myCenterY, ship.getX(), ship.getY());
  }
} //end asteroid class

abstract class Floater //Do NOT modify the Floater class! Make changes in the SpaceShip class 
{   
  protected int corners;  //the number of corners, a triangular floater has 3   
  protected int[] xCorners;   
  protected int[] yCorners;   
  protected int myColor;   
  protected double myCenterX, myCenterY; //holds center coordinates   
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel   
  protected double myPointDirection; //holds current direction the ship is pointing in degrees    
  abstract public void setX(int x);  
  abstract public int getX();   
  abstract public void setY(int y);   
  abstract public int getY();   
  abstract public void setDirectionX(double x);   
  abstract public double getDirectionX();   
  abstract public void setDirectionY(double y);   
  abstract public double getDirectionY();   
  abstract public void setPointDirection(int degrees);   
  abstract public double getPointDirection(); 

  //Accelerates the floater in the direction it is pointing (myPointDirection)   
  public void accelerate (double dAmount)   {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move (){ //move the floater in the current direction of travel
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;
    //wrap around screen    
    if(myCenterX >width){     
      myCenterX = 0;    
    } else if (myCenterX<0){     
      myCenterX = width;    
    }    
    if(myCenterY >height){    
      myCenterY = 0;    
    } else if (myCenterY < 0){     
      myCenterY = height;    
    }   
  }   
  public void show (){ //Draws the floater at the current position            
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++) {     
      //rotate and translate the coordinates of the floater using current direction 
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
} 

public class Star {
  private int Sx, Sy;
  private int Scolor;
  private int Sdia;
  public Star(){
    Sx = (int)(width*(Math.random()));
    Sy = (int)(height*(Math.random()));
    Scolor= 0xffffffff;
    Sdia = 2;
  }
  public void show(){
    fill(Scolor);
    stroke(Scolor);
    ellipse(Sx, Sy, Sdia, Sdia);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Part4" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
