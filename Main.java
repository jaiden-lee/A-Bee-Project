import java.util.Scanner;
import java.io.File;

class Main {
  public static void main(String[] args) throws Exception {
    File beesetup = new File("beesetup3.txt");
    Scanner sc = new Scanner(beesetup);

    int cubeSize = Integer.parseInt(sc.nextLine().substring(0, 2));

    Node[] hive = new Node[15];

    for (int i = 0; i < 15; i++) {
      String line = sc.nextLine();
      String values[] = line.split(",");
      Node n = new Node(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
      hive[i] = n;
    }
    System.out.println("The beehive is from " + hive[0] + " to " + hive[hive.length - 1]);

    OpenList openList = new OpenList(cubeSize);

    Node[] bees = new Node[15];
    for (int i = 0; i < 15; i++) {
      String line = sc.nextLine();
      String values[] = line.split(",");
      Node n = new Node(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
      n.h = calculateHCost(n, hive);
      n.f = n.h;
      bees[i] = n;
      System.out.println("Bee " + (i + 1) + " started at " + bees[i]);
    }

    boolean closedList[][][] = new boolean[cubeSize][cubeSize][cubeSize];
    boolean obstacleArr[][][] = new boolean[cubeSize][cubeSize][cubeSize];

    while (sc.hasNextLine()) {
      String temp = sc.nextLine();
      String arr[] = temp.split(",");
      int a = Integer.parseInt(arr[0]);
      int b = Integer.parseInt(arr[1]);
      int c = Integer.parseInt(arr[2]);
      obstacleArr[a][b][c] = true;
    }

    int sumMoves = 0;
    int i = 1;
    for (Node bee : bees) {
      openList = new OpenList(cubeSize);
      closedList = new boolean[cubeSize][cubeSize][cubeSize];
      openList.add(bee);

      while (true) {
        Node min = openList.getSmallest(closedList);
        if (min.h == 0) {
          for (int hi=0;hi<15;hi++) {
            if (hive[hi]!=null) {
              if (hive[hi].equals(min)) {
              hive[hi] = null;
              }
            }
          }
          System.out.println("Bee " + i + " moved to "+min+"in " + min.g + " moves!");

          sumMoves += min.g;
          break;
        }
        findNeighbors(min, openList, closedList, obstacleArr, cubeSize, hive);
      }
      i++;
    }

    System.out.println("The total moves is " + sumMoves);

  }

  public static int calculateHCost(Node n, Node[] hive) {
    int minInt = Integer.MAX_VALUE;

    for (int i = 0; i < hive.length; i++) {
      if (hive[i] !=null) {
      int temp = Math.abs(n.x - hive[i].x) + Math.abs(n.y - hive[i].y) + Math.abs(n.z - hive[i].z); 
      if (temp < minInt) { 
        minInt = temp;
      }
    }
    }
    return minInt;
  }

  public static void findNeighbors(Node min, OpenList openList, boolean[][][] closedList, boolean[][][] obstacles, int cubeSize, Node[] hive) {

    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        for (int z = -1; z <= 1; z++) {

          Node neighbor = new Node(min.x + x, min.y + y, min.z + z);
          if ((neighbor.x >= 0 && neighbor.x < cubeSize) && (neighbor.y >= 0 && neighbor.y < cubeSize) && (neighbor.z >= 0 && neighbor.z < cubeSize)) {

            neighbor.g = min.g + 1;

            neighbor.h = calculateHCost(neighbor, hive);

            neighbor.f = neighbor.g + neighbor.h;

            if (closedList[min.x][min.y][min.z] == false && obstacles[min.x][min.y][min.z] == false) {

              int index = openList.find(neighbor);
              if (index != -1) {

                openList.replace(index, neighbor);

              } else {
                openList.add(neighbor);
              }
            }
          }
        }
      }
    }
    closedList[min.x][min.y][min.z] = true;
  }
}
