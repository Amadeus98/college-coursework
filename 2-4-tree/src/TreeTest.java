import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {
  private Tree testTree;
  private Object[] testData = {1,2,3,4,5,6,7,8,9};
  private Object[] actual;

  @Before
  public void setUp() throws Exception{
    testTree = new Tree();
  }

  @Test
  public void testDefaultConstructor() {
    assertNotNull("Failed to instantiate", testTree); 
    assertNotNull("Root node null", testTree.getRoot());
  }

  @Test
  public void testAddData() {
    addData(1);
    Object[] actual = {testData[0]};
    assertArrayEquals("Data not to root", testTree.getRoot().getData().toArray(), actual);
  }
  
  @Test
  public void testIncrementToThreeNode() {
    addData(2);
    assertEquals("Two node not updating to three node", testTree.getRoot().getMaxChildren(), 3); 
  }
  
  @Test 
  public void testIncrementToFourNode() {
    addData(3);
    assertEquals("Three node not updating to four node", testTree.getRoot().getMaxChildren(), 4); 
  }

  @Test
  public void testUpdateFourNode() {
    addData(4);
    Object[] actual = {testData[1]};
    assertTrue("Root node does not have children", testTree.getRoot().hasChildren());
    assertArrayEquals("Root node not set", testTree.getRoot().getData().toArray(), actual);
  }

  @Test
  public void testUpdateFourNodeChild() {
    addData(4);
    Object[] actualRoot = {testData[1]};
    assertArrayEquals("Root does not have correct values", testTree.getRoot().getData().toArray(), actualRoot);
    Node rootRightChild = (Node) testTree.getRoot().getChildren().get(1); 
    Object[] actualChild = {testData[2], testData[3]}; 
    assertArrayEquals("Root right child does not have data", rootRightChild.getData().toArray(), actualChild);
  }

  public void addData(int dataNum) {
    for (int i = 0; i < dataNum; i++)
      testTree.addData(testData[i]);
  }
}
