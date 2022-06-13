public class OpenList {
  private Node [] nodes;
  private int length;
  
  public OpenList(int size) {
    nodes = new Node[size*size*size];
    length = 0;
  }

  public void add(Node n) {
    nodes[length] = n;
    length++;
  }

  public Node get (int index) {
    return nodes[index];
  }

  public int find (Node n) {
    for (int i=0;i<length;i++) {
      if (nodes[i].x==n.x && nodes[i].y==n.y && nodes[i].z==n.z) {
        return i;
      }
    }
    return -1;
  }

  public Node getSmallest (boolean[][][] closedList) {
    Node min=nodes[0];
    for (int i=1;i<length;i++) {
      if (closedList[nodes[i].x][nodes[i].y][nodes[i].z]==false) {
        if (nodes[i].f<min.f) {
          min = nodes[i];
        } else if (nodes[i].f==min.f) {
          if (nodes[i].h<=min.h) {
            min = nodes[i];
          }
        }
      }
    }
    return min;
  }

  public void replace (int index, Node n) {
    nodes[index] = n;
  }
}
