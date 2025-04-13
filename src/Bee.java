class Bee extends Space
{
  //INSTANCE VARIABLES
  
  private Space[] shortestRoute;
  private Space hive;

  //CONSTRUCTORS

  public Bee()
  {
    super('b');
  }

  //GETTERS AND SETTERS (SORT OF)

  public int getShortestRouteLength()
  {
    return shortestRoute.length;
  }

  public Space[] getShortestRoute()
  {
    return shortestRoute;
  }

  public String getShortestRouteAsString()
  {
    String str = "";
    for(int i = 0; i<shortestRoute.length; i++)
    {
      if(i == shortestRoute.length -1)
      {
        str += shortestRoute[i];
      }
      else
      {
        str += shortestRoute[i] + " --> ";
      }
    }
    return str;
  }

  public void setShortestRoute(Space[] s)
  {
    shortestRoute = s;
    hive = s[s.length-1];
  }

  public Space getHive()
  {
    return hive;
  }
}