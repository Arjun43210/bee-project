import java.util.ArrayList;

class Sort
{
  public static void selectionSort(ArrayList<Space> arr)
  {
    int length = arr.size();

    for(int start = 0; start < length; start++)
    {
      int minIndex = start;
      
      for(int i = start; i<length; i++)
      {
        if(arr.get(minIndex).compareTo(arr.get(i)) > 0)
        {
          minIndex = i;
        }
      }
      
      //Adjust array
      Space temp = arr.get(minIndex);
      for(int i = minIndex; i > start; i--)
      {
        arr.set(i, arr.get(i-1));
      }
      arr.set(start, temp);
    }
  }
}