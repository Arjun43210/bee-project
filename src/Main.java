import java.io.*;
import java.util.*;
class Main 
{

  /*Uncomment map to use */
  public static final String BEE_MAP = "resources/beesetup1.txt";
  //public static final String BEE_MAP = "resources/beesetup2.txt";
  //public static final String BEE_MAP = "resources/beesetup3.txt";
  
  public static void main(String[] args) 
  {
    try
    {
      File f = new File(BEE_MAP);
      Scanner s = new Scanner(f);

      Cube cube = new Cube(getSize(s));
      
      cube.setHives(getNext(s, 15));
      cube.setBees(getNextAsBees(s, 15));
      cube.setObstacles(getRest(s));

      cube.setShortestRoutes();

      System.out.println("To Bee or not to Bee");
      System.out.println("--------------------");
      System.out.println();
      
      Bee[] bees = cube.getBees();
      
      for(int i = 0; i<15; i++)
      {
        System.out.println("Bee "+ (i+1) + " at " + bees[i]);
      }
      
      System.out.println();

      int totalMoves = 0;

      for(int i = 0; i<15; i++)
      {
        System.out.println();
        System.out.println("Bee "+ (i+1) + " at " + bees[i]);
        System.out.println("Path: " + bees[i].getShortestRouteAsString());
        System.out.println("Hive: " + bees[i].getHive());
        System.out.println("Moves: " + bees[i].getShortestRouteLength());
        totalMoves += bees[i].getShortestRouteLength();
      }

      System.out.println();
      System.out.println();

      System.out.println("Algorithm: A*");
      System.out.println("Total Moves: " + totalMoves);

      double avg = totalMoves / 15.0;

      System.out.println("Average Moves per bee: " + avg);
      
      
    }
    catch(FileNotFoundException e)
    {
      System.out.println(e);
    }
  }


  //Extract contents from text files

  private static int getSize(Scanner s)
  {
    s.nextLine();
    return Integer.parseInt((s.nextLine().substring(0,2)));
  }

  private static Space[] getNext(Scanner s, int num)
  {
    Space[] spaces = new Space[num];
    for(int i = 0; i<num; i++)
    {
      Space temp = new Space();
      temp.setCoordinates(s.nextLine());
      spaces[i] = temp;
    }
    return spaces;
  }

  private static Bee[] getNextAsBees(Scanner s, int num)
  {
    Bee[] spaces = new Bee[num];
    for(int i = 0; i<num; i++)
    {
      Bee temp = new Bee();
      temp.setCoordinates(s.nextLine());
      spaces[i] = temp;
    }
    return spaces;
  }

  private static Space[] getRest(Scanner s)
  { 
    int num = Integer.parseInt(s.nextLine());

    Space[] spaces = new Space[num];
    for(int i = 0; i<num; i++)
    {
      Space temp = new Space();
      temp.setCoordinates(s.nextLine());
      spaces[i] = temp;
    }

    return spaces;
  }
}
