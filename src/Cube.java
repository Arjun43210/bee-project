import java.util.*;
public class Cube
{
  //INSTANCE VARIABLES

  private Space[][][] grid;
  private int length;
  private Space[] hives;
  private Space[] availableHives;
  private Bee[] bees;
  private Space[] obstacles;


  //Temporary - for each bee
  private ArrayList<Space> expandedPoints = new ArrayList<>(); //PRIORITY QUEUE
  private Space end; //BEE DESTINATION
    

  //CONSTRUCTOR
  public Cube(int size)
  {
    grid = new Space[size][size][size];
    length = size;

    for(int i = 0; i<size; i++)
    {
      for(int j = 0; j<size; j++)
      {
        for(int k = 0; k<size; k++)
        {
          grid[i][j][k] = new Space();
          grid[i][j][k].setX(i);
          grid[i][j][k].setY(j);
          grid[i][j][k].setZ(k);
        }
      }
    } 
  }

  //GETTERS AND SETTERS

  public void setHive(Space c)
  {
    grid[c.getX()][c.getY()][c.getZ()].set('h');
  }

  public void setBee(Space c)
  {
    grid[c.getX()][c.getY()][c.getZ()].set('b');
  }

  public void setObstacle(Space c)
  {
    grid[c.getX()][c.getY()][c.getZ()].set('o');
  }

  public void setEmpty(Space c)
  {
    grid[c.getX()][c.getY()][c.getZ()].set('e');
  }

  public void setHive(int x, int y, int z)
  {
    grid[x][y][z].set('h');
  }

  public void setBee(int x, int y, int z)
  {
    grid[x][y][z].set('b');
  }

  public void setObstacle(int x, int y, int z)
  {
    grid[x][y][z].set('o');
  }

  public void setEmpty(int x, int y, int z)
  {
    grid[x][y][z].set('e');
  }

  public char getType(Space c)
  {
    return grid[c.getX()][c.getY()][c.getZ()].getType();
  }

  public char getType(int x, int y, int z)
  {
    return grid[x][y][z].getType();
  }

  public void setHives(Space[] theHives)
  {
    hives = new Space[theHives.length];
    availableHives = new Space[theHives.length];

    for(int i = 0; i<theHives.length; i++)
    {
      hives[i] = theHives[i];
      availableHives[i] = theHives[i];
    }
    
    for(Space hive : hives)
    {
      hive.set('h');
      grid[hive.getX()][hive.getY()][hive.getZ()] = hive;
    }
  }

  public void setBees(Bee[] theBees)
  {
    bees = theBees;
    for(Bee bee : bees)
    {
      bee.set('b');
      grid[bee.getX()][bee.getY()][bee.getZ()] = bee;
    }
  }

  public void setObstacles(Space[] theObstacles)
  {
    obstacles = theObstacles;
    for(Space o : obstacles)
    {
      o.set('o');
      grid[o.getX()][o.getY()][o.getZ()] = o;
    }
  }

  //STARTS PROCESS
  public void setShortestRoutes()
  {
    for(Bee bee : bees)
    {
      findShortestRoute(bee);
    }
  }

  public Bee[] getBees()
  {
    return bees;
  }

  public Space[] getHives()
  {
    return hives;
  }

  public Space[] getObstacles()
  {
    return obstacles;
  }

  private void removeHive(Space h)
  {
    boolean hiveFound = false;
    Space[] newHiveList = new Space[hives.length-1];
    
    for(int i = 0; i<availableHives.length; i++)
    {
      if(hiveFound == false && availableHives[i].equals(h) == false)
      {
        newHiveList[i] = availableHives[i];
      }
      else if(availableHives[i].equals(h))
      {
        hiveFound = true;
      }
      else if(hiveFound == true && availableHives[i].equals(h) == false)
      {
        newHiveList[i-1] = availableHives[i];
      }
    }
    
    availableHives = newHiveList;
    end = null;
  }

  public void resetNodeValues()
  {
    for(Space[][] s1 : grid)
    {
      for(Space[] s2: s1)
      {
        for(Space s : s2)
        {
          s.resetNodeValues();
        }
      }
    }
  }

  


  //IMPORTANT METHODS


  /**
    Finds the shortest route from a given start (bee) to the end (hive)
    Stores shortest route in bee

    @param bee (starting point)
  */
  private void findShortestRoute(Bee bee)
  {     
    //Choose Hive (end target) - compares heuristic values of each hive

    int[] hValues = new int[availableHives.length];
    for(int i = 0; i<availableHives.length; i++)
    {
      hValues[i] = getHeuristic(bee, availableHives[i]);
    }
    int min = hValues[0];
    int minIndex = 0;
    for(int i = 0; i<hValues.length; i++) //find closest hive
    {
      if(hValues[i] > min)
      {
        min = hValues[i];
        minIndex = i;
      }
    }
    this.end = hives[minIndex];

    //Node values set up
    bee.setG(0);
    bee.setH(getHeuristic(bee));
    bee.setF();
    expandedPoints.add(bee); //the starting point is the first to be added to our priority queue


    //Start expanding points & making priority queue
    while(!(expandedPoints.get(0).equals(this.end)))
    {
      expandCoordinate(); //see method below
    }
    //The while loop above runs until the hive is at the front of our queue


    ArrayList<Space> route = new ArrayList<>();

    //Populate it backwards
    route.add(end);

    while(true)
    {
      Space p = route.get(route.size()-1);

      if(p.equals(bee))
        break;
      else
        route.add(p.getPrevious());
    }

    Space[] route2 = new Space[route.size()];
    for(int i = route.size()-1; i>=0; i--)
    {
      route2[route.size()-1-i] = route.get(i);
    }

    bee.setShortestRoute(route2); //Store the route in the bee
    //end.set('x');
    //removeHive(end);
    
    //Clear priority queue for next bee
    resetNodeValues();
    clearExpandedPoints();
  }










  /**
    Looks at every node touching the given node (top of the priority queue) and, if valid, calculates their f-values and adds them to the priority queue

    Precondition: expandedPoints (priority queue) is sorted
  */
  private void expandCoordinate()
  {
    Space root = expandedPoints.get(0);
    for(int a = -1; a <= 1; a++) //Change to x-coordinate
    {
      for(int b = -1; b <= 1; b++) //Change to y-coordinate
      {
        for(int c = -1; c <= 1; c++) //Change to z-coordinate
        {
          boolean notStartingPoint = a != 0 || b != 0 || c != 0;
          
          boolean xIsValid = (root.getX()+a >= 0 && root.getX()+a < length);
          
          boolean yIsValid = (root.getY()+b >= 0 && root.getY()+b < length);
          
          boolean zIsValid = (root.getZ()+c >= 0 && root.getZ()+c < length);
          
          if(notStartingPoint && xIsValid && yIsValid && zIsValid)
          {
            Space s = grid[root.getX()+a][root.getY()+b][root.getZ()+c];
            
            if(s.isObstacle() == false) //&& s.isBeeInHive() == false)
            {
              s.setG(root.getF()-root.getH() + 1); //For any given node, h is always the same, while g is subject to change if a shorter route is found.
              s.setH(getHeuristic(s));
              if(s.setF() == true) //This will be true only if (1) this node has not been reached before or (2) this node has been reached, but the current route is a shorter one.
              {
                s.setPrevious(root); //link the previous node to this one
                expandedPoints.add(s); //add it to the priority queue
              }
            }
          }
        }
      }
    }
    expandedPoints.remove(0); //once all neighbors have been checked, the root may be removed
    
    sortExpandedPoints();
  }

  private void clearExpandedPoints()
  {
    expandedPoints = new ArrayList<>();
  }

  private int getHeuristic(Space start)
  {
    int dx = Math.abs(start.getX() - end.getX());
    int dy = Math.abs(start.getY() - end.getY());
    int dz = Math.abs(start.getZ() - end.getZ());
    
    int min = Math.min(dx, dy);
    min = Math.min(min, dz);
    
    return (dx + dy + dz) + (-1) * min;
  }

  private int getHeuristic(Space start, Space end)
  {
    int dx = Math.abs(start.getX() - end.getX());
    int dy = Math.abs(start.getY() - end.getY());
    int dz = Math.abs(start.getZ() - end.getZ());
    
    int min = Math.min(dx, dy);
    min = Math.min(min, dz);
    
    return (dx + dy + dz) + (-1) * min;
  }

  //Updates the Priority Queue
  private void sortExpandedPoints()
  {
    Sort.selectionSort(expandedPoints);
    /*
    Space[] temp = new Space[expandedPoints.size()];
    temp = expandedPoints.toArray(temp);
    
    //Sort.selectionSort(temp);
    Arrays.sort(temp);
    
    expandedPoints.clear();
    for(int i = 0; i<temp.length; i++)
    {
      expandedPoints.add(temp[i]);
    }
    */
  }
}