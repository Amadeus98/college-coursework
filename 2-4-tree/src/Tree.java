import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Tree {

  private Node rootNode; 
  
  public Tree() { 
    rootNode = new Node();
  }
  
  public Node getRoot() { 
    return rootNode; 
  }

  public void addData(Object newData) {
    updateTree(newData, rootNode);
  }

    private void updateTree(Object newData, Node currentNode) {
      if (currentNode.getMaxChildren() == 4) updateFourNode(newData, currentNode);
      else { if (currentNode.hasChildren()) updateTree(newData, findChild(newData, currentNode));
             else incrementNode(newData, currentNode);
      }
    }

      private Node findChild(Object newData, Node currentNode) {
        switch (currentNode.getMaxChildren()) {
          case 2: return twoNodeInterval(newData, currentNode);
          case 3: return threeNodeInterval(newData, currentNode);
          case 4: return fourNodeInterval(newData, currentNode);
        }
        return null;
      }

        private Node twoNodeInterval(Object newData, Node currentNode) {
          ArrayList<Node> children = currentNode.getChildren();
          int comparison = Collections.max(currentNode.getData()).compareTo(newData);
          if (comparison > 0) return children.get(0);
          else return children.get(1);
        }

        private Node threeNodeInterval(Object newData, Node currentNode) {
          ArrayList<Node> children = currentNode.getChildren();
          ArrayList currentData = currentNode.getData();
          if (Collections.max(currentData).compareTo(newData) < 0) return children.get(2);
          if (Collections.min(currentData).compareTo(newData) > 0) return children.get(0);
          else return (Node)currentNode.getChildren().get(1);
        }

        private Node fourNodeInterval(Object newData, Node currentNode) {
          ArrayList<Node> children = currentNode.getChildren();
          List leftData = currentNode.getData().subList(0,1);
          List rightData = currentNode.getData().subList(1,2);
          if (Collections.max(rightData).compareTo(newData) < 0) return children.get(3);
          if (Collections.min(leftData).compareTo(newData) > 0) return children.get(0);
          if (Collections.max(leftData).compareTo(newData) < 0) return children.get(2);
          else return children.get(1);
        }

        private void incrementNode(Object newData, Node currentNode) {
          if (!currentNode.hasSpace()) incrementNodeMaximums(currentNode);
          currentNode.addData(newData);
        }

          private void incrementNodeMaximums(Node currentNode) {
            currentNode.updateMaxChildren(currentNode.getMaxChildren() + 1);
            currentNode.updateMaxData(currentNode.getMaxData() + 1);
          }

        private void decrementNode(Object removeData, Node currentNode) {
          currentNode.removeData(removeData);
          decrementNodeMaximums(currentNode);
        }

          private void decrementNodeMaximums(Node currentNode) {
            currentNode.updateMaxChildren(currentNode.getMaxChildren() - 1);
            currentNode.updateMaxData(currentNode.getMaxData() - 1);
          }

        private void updateFourNode(Object newData, Node currentNode) {
          Object middleValue = getMiddleValue(currentNode);
          Object newRightValue = Collections.max(currentNode.getData());

          Node newRightNode = makeNewNode(newRightValue);
          decrementNode(newRightValue, currentNode);

          if (currentNode == rootNode) {
            updateRootNode(middleValue, currentNode, newRightNode);
            updateTree(newData, rootNode);
          }
          else updateTree(middleValue, currentNode.getParent());
        }

          private Object getMiddleValue(Node currentNode) {
            Object middleValue = currentNode.getData().get(1);
            currentNode.removeData(middleValue);
            decrementNodeMaximums(currentNode);
            return middleValue;
          }

          private void updateRootNode(Object newRootData, Node leftChild, Node rightChild) {
            Node newRootNode = makeNewNode(newRootData);
            newRootNode.addChild(leftChild);
            newRootNode.addChild(rightChild);
            rootNode = newRootNode;
          }

          private Node makeNewNode(Object newNodeData) {
            Node newNode = new Node();
            newNode.addData(newNodeData);
            return newNode;
          }
  
}
