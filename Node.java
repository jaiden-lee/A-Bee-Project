public class Node {
  int x, y, z;
  int g, h, f;

  public Node (int x, int y, int z) {
    this.x = x;
    this.y = y;
    this. z = z;
  }
  
  public String toString () {
    return "(" + x + "," + y+ "," + z + ")";
  }

  public boolean equals (Node n) {
    if ((this.x == n.x) && (this.y == n.y) && (this.z == n.z)) { 
      return true;
    }
    return false;
    //determines if node is equal to another node
  }
}
