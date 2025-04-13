import java.util.*;

public class Space implements Comparable<Space>
{
  //INSTANCE VARIABLES

  private char type;
  // h = hive, o = obstacle, b = bee, e = empty, x = bee in hive
  
  private int f; //Value of the node
  private int g; //Distance to node
  private int h; //Distance to end (heuristic)

  //Coordinates
  private int x;
  private int y;
  private int z;

  //Previous space in path
  private Space previous;
  
  
  //CONSTRUCTORS
  
  public Space()
  {
    this('e');
  }

  public Space(char t)
  {
    type = t;
    f = 0;
    g = 0;
    h = 0;
    x = -1;
    y = -1;
    z = -1;
  }
  
  public char getType()
  {
    return type;
  }
  
  public boolean isHive()
  {
    return type == 'h';
  }

  public boolean isObstacle()
  {
    return type == 'o';
  }

  public boolean isBee()
  {
    return type == 'b';
  }

  public boolean isEmpty()
  {
    return type == 'e';
  }

  public boolean isBeeInHive()
  {
    return type == 'x';
  }

  public void set(char t)
  {
    type = t;
  }

  public boolean setF()
  {
    if(g+h < f || f == 0)
    {
      this.f = g + h;
      return true;
    }
    return false;
  }

  public void setG(int g)
  {
    this.g = g;
  }

  public void setH(int h)
  {
    this.h = h;
  }

  public int getF()
  {
    return f;
  }

  public int getG()
  {
    return g;
  }

  public int getH()
  {
    return h;
  }

  public void resetNodeValues() //Resets f, g, h
  {
    f = 0;
    g = 0;
    h = 0;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public void setY(int y)
  {
    this.y = y;
  }

  public void setZ(int z)
  {
    this.z = z;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public int getZ()
  {
    return z;
  }

  public void setCoordinates(String str)
  {
    String temp = "";
    ArrayList<String> tempList = new ArrayList<>(3);
    for(char c : str.toCharArray())
    {
      if(c == ',')
      {
        tempList.add(temp);
        temp = "";
      }
      else
      {
        temp += c;
      }
    }
    tempList.add(temp);
    x = Integer.parseInt(tempList.get(0));
    y = Integer.parseInt(tempList.get(1));
    z = Integer.parseInt(tempList.get(2));
  }

  public void setPrevious(Space p)
  {
    previous = p;
  }

  public Space getPrevious()
  {
    return previous;
  }

  //GENERAL METHODS
  
  @Override
  public String toString()
  {
    return "("+getX()+", "+getY()+", "+getZ()+")";
  }

  @Override
  public int compareTo(Space other)
  {
    return this.f-other.f;
  }

  public boolean equals(Space other)
  {
    boolean xEqual = (this.x == other.x);
    boolean yEqual = (this.y == other.y);
    boolean zEqual = (this.z == other.z);
    return xEqual && yEqual && zEqual;
  }

}