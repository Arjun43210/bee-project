# To Bee or Not To Bee

A 3D world is home to $n$ bees and $n$ bee hives, randomly distributed. Each hive can fit exactly one bee. Bees can move in any direction, unless there is an obstacle present. Find the most optimal way (shortest paths on average) for all the bees to return to their hives.

My solution implements the A* Algorithm to find the shortest path (on average) to return each bee to one hive. After running the algorithm, my code prints the path that each bee takes to travel to a hive. It then gives the total number of moves taken, as well as the average number of moves per bee.

To run this program, simply run `Main.java`. The default map used is `beesetup1.txt`, but feel free to change this by uncommenting a line at the start of `Main.java`.

This problem was given in Millburn High School's AP Computer Science class taught by David Farrell. I developed the presented solution independently in 10th grade.
