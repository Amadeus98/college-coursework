import java.util.ArrayList;
import java.util.Collections;

public class Node implements Comparable<Node>{

  protected int       MAX_DATA;
  protected int       MAX_CHILDREN;
  protected ArrayList data;
  protected ArrayList children;
  protected Node      parent;

  public Node() {
    MAX_DATA      = 1;
    MAX_CHILDREN  = 2;
    data          = new ArrayList(MAX_DATA);
    children      = new ArrayList(MAX_CHILDREN);
    parent        = null;
  }

  public ArrayList getData() {
    return data;
  }

  public void setData(ArrayList newData) {
    if (newData.size() <= MAX_DATA) data = newData;
  }

    public boolean hasSpace() {
      return data.size() < MAX_DATA;
    }

    public void addData(Object newData) {
      data.add(newData);
      sortData();
    }

      protected void sortData() {
        Collections.sort(data);
      }

    public void removeData(Object killData) {
      if (data.contains(killData)) {
        data.remove(killData);
        sortData();
      }
    }

  public ArrayList getChildren() {
    return children;
  }

  public void setChildren(ArrayList newChildren) {
    if (newChildren.size() <= MAX_CHILDREN) children = newChildren;
  }

    public boolean hasChildSpace() {
      return children.size() < MAX_CHILDREN;
    }

    public boolean hasChildren() {
      return children.size() > 1;
    }

    public void addChild(Node newChild) {
      children.add(newChild);
      newChild.setParent(this);
      sortChildren();
    }

      protected void sortChildren(){
        Collections.sort(children);
      }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parentNode) {
    parent = parentNode;
  }

  public int getMaxData() {
    return MAX_DATA;
  }

  public void updateMaxData(int newMax) {
    MAX_DATA = newMax;
    data.ensureCapacity(MAX_DATA);
  }

  public int getMaxChildren() {
    return MAX_CHILDREN;
  }

  public void updateMaxChildren(int newMax) {
    MAX_CHILDREN = newMax;
    children.ensureCapacity(MAX_CHILDREN);
  }

  @SuppressWarnings("unchecked")
  public int compareTo(Node compareNode){
    if (data.equals(compareNode.getData())) return 0;
    return Collections.max(data).compareTo(Collections.max(compareNode.getData()));
  }

}
