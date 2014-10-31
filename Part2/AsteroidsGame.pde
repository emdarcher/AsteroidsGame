/* keypress stuff */
public static final int UP_BIT    = 0;
public static final int LEFT_BIT  = 1;
public static final int DOWN_BIT  = 2;
public static final int RIGHT_BIT = 3;
public static final int BRAKE_BIT = 4;
public static final int HYPER_BIT = 5;
public byte key_bits = 0x00;

/**basic spaceship stuff**/
public static final int basic_corners = 3;
int basic_xCorners[] = {
  -8,16,-8,
};
int basic_yCorners[] = {
  -8,0,8,
};

public static final int a_corners = 6;
int a_xCorners[a_corners] = {
  -11,7,13,6,-11,-5,
};
int a_yCorners[a_corners] = {
  -8,-8,0,10,8,0,
};

public static final int bg_val =24;

public static final int NUM_STARS = 64;
Star [] stars;

SpaceShip spacey;

//your variable declarations here
public void setup() 
{
  //your code here
  background(bg_val);
  size(255,255);
  spacey = new SpaceShip();
  stars = new Star[NUM_STARS];
  for(int i=0;i<stars.length;i++){
    stars[i]=new Star();
  }
}
public void draw() 
{
  //your code here
  scan_key_bits();
  background(bg_val);
  spacey.move();
  for(int i=0;i<stars.length;i++){
    stars[i].show();  
  }
  spacey.show();
}
public void scan_key_bits(){
  if((key_bits & (byte)(1<<BRAKE_BIT))!=0){
    spacey.brake(0.1);
  }
  if((key_bits & (byte)(1<<HYPER_BIT))!=0){
    spacey.hyperspace();
  }
  if((key_bits & (byte)(1<<UP_BIT))!=0){
    spacey.accelerate((double)0.1);
  }
  if((key_bits & (byte)(1<<DOWN_BIT))!=0){
    spacey.accelerate((double)(-0.1));
  }
  if((key_bits & (byte)(1<<LEFT_BIT))!=0){
    spacey.rotate(-8);
  }
  if((key_bits & (byte)(1<<RIGHT_BIT))!=0){
    spacey.rotate(8);
  }
}

public void keyPressed(){
if(key == 'b'){
  key_bits |= (1<<BRAKE_BIT);
}

if(key == 'h'){
  key_bits |= (1<<HYPER_BIT);
}
if(keyCode == UP){key_bits |= (1<<UP_BIT);}
if(keyCode == LEFT){key_bits |= (1<<LEFT_BIT);}
if(keyCode == RIGHT){key_bits |= (1<<RIGHT_BIT);}
if(keyCode == DOWN){key_bits |= (1<<DOWN_BIT);}
/*if(key == 'b'){
  spacey.brake(0.1);
} else {
  if(!((keyCode ==LEFT)&&(keyCode == RIGHT))){  
    if(keyCode == LEFT){
      spacey.rotate(-8);
    } //else 
    if (keyCode == RIGHT){
      spacey.rotate(8);
    } 
  }

  if(!((keyCode==UP)&&(keyCode==DOWN))){
    //else 
    if (keyCode == UP){
      spacey.accelerate((double)0.1);
    } //else 
    if (keyCode == DOWN){
      spacey.accelerate((double)-0.1);
    }
  }
}*/
} 
public void keyReleased() {
if(key == 'b'){
  key_bits &= ~(1<<BRAKE_BIT);
}
if(key == 'h'){
  key_bits &= ~(1<<HYPER_BIT);
}
if(keyCode == UP){key_bits &= ~(1<<UP_BIT);}
if(keyCode == LEFT){key_bits &= ~(1<<LEFT_BIT);}
if(keyCode == RIGHT){key_bits &= ~(1<<RIGHT_BIT);}
if(keyCode == DOWN){key_bits &= ~(1<<DOWN_BIT);} 
} 
class SpaceShip extends Floater  
{   
    //your code here
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

    SpaceShip(){
      //constructor
      corners = basic_corners;
      xCorners = new int [corners];
      yCorners = new int [corners];
      myCenterX = width/2; myCenterY = height/2;
      myDirectionX = 0; myDirectionY = 0;
      myPointDirection = 0;
      myColor = #239589;//stuff
      for(int i=0;i<corners;i++){
        //ints arrays
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

}

class Asteroid extends Floater {
  int speed_rot;

//your code here
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


  Asteroid(){
    int rotSpeed = (int)((Math.random()*4)-2);
    corners = a_corners;
      xCorners = new int [corners];
      yCorners = new int [corners];
      myCenterX = width/2; 
      myCenterY = height/2;
      myDirectionX = 0; myDirectionY = 0;
      myPointDirection = 0;
      myColor = #239589;//stuff
      for(int i=0;i<corners;i++){
        //ints arrays
        xCorners[i] = a_xCorners[i];
        yCorners[i] = a_yCorners[i];
      }
  }
  public void move(){
    //rotate
    rotate(rotSpeed);
    super.move();
  }

}

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
  public void accelerate (double dAmount)   
  {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   
  {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move ()   //move the floater in the current direction of travel
  {      
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;     

    //wrap around screen    
    if(myCenterX >width)
    {     
      myCenterX = 0;    
    }    
    else if (myCenterX<0)
    {     
      myCenterX = width;    
    }    
    if(myCenterY >height)
    {    
      myCenterY = 0;    
    }   
    else if (myCenterY < 0)
    {     
      myCenterY = height;    
    }   
  }   
  public void show ()  //Draws the floater at the current position  
  {             
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
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
  private color Scolor;
  private int Sdia;
  Star(){
    Sx = (int)(width*(Math.random()));
    Sy = (int)(height*(Math.random()));
    Scolor= #ffffff;
    Sdia = 2;
  }
  public void show(){
    fill(Scolor);
    stroke(Scolor);
    ellipse(Sx, Sy, Sdia, Sdia);
  }
}