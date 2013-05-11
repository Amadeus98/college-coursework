import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class NodeTest {
  private Node testNode;

  @Before
  public void setUp() throws Exception{
    testNode = new Node();
  }
  
  @Test
  public void testDefaultConstructor() {
    assertNotNull("Failed to instantiate", testNode);
    assertNotNull("Data is null", testNode.getData());
    assertNotNull("Children is null", testNode.getChildren());
    assertNull("Parent not null", testNode.getParent());
    assertEquals("Max data not set", testNode.getMaxData(), 1);
    assertEquals("Max children not set", testNode.getMaxChildren(), 2);
  }

  @Test
  public void testGetAndSetData() {
    ArrayList testData  = new ArrayList();
    ArrayList wrongData = new ArrayList(); 
    testData.add(5);
    wrongData.add(6); 
    wrongData.add(7); 
    testNode.setData(testData);
    assertEquals("Data not set", testNode.getData(), testData);
    testNode.setData(wrongData); 
    assertNotSame("Data size not checked", testNode.getData(), wrongData); 
  }

  @Test
  public void testGetAndSetChildren() {
    Node testChild          = new Node();
    Node wrongChild1        = new Node(); 
    Node wrongChild2        = new Node(); 
    Node wrongChild3        = new Node(); 
    ArrayList testChildren  = new ArrayList();
    ArrayList wrongChildren = new ArrayList(); 
    testChildren.add(testChild);
    wrongChildren.add(wrongChild1); 
    wrongChildren.add(wrongChild2); 
    wrongChildren.add(wrongChild3); 
    testNode.setChildren(testChildren);
    assertEquals("Children not set", testNode.getChildren(), testChildren);
    testNode.setChildren(wrongChildren); 
    assertNotSame("Children size not set", testNode.getChildren(), wrongChildren); 
  }

  @Test
  public void testGetAndSetParent() {
    Node testParent = new Node();
    testNode.setParent(testParent);
    assertEquals("Parent not set", testNode.getParent(), testParent);
  }

  @Test
  public void testGetAndUpdateMaxData() {
    int newMax    = 3;
    testNode.updateMaxData(newMax);
    assertEquals("Max data not updated", testNode.getMaxData(), newMax);
  }

  @Test
  public void testGetAndUpdateMaxChildren() {
    int newMax    = 4;
    testNode.updateMaxChildren(newMax);
    assertEquals("Max children not updated", testNode.getMaxChildren(), newMax);
  }

  @Test
  public void testAddData() {
    Object[] testData = {5,6,7};
    testNode.addData(testData[2]);
    testNode.addData(testData[1]);
    testNode.addData(testData[0]);
    assertArrayEquals("Data not adding properly", testNode.getData().toArray(), testData);
  }

  @Test
  public void testRemoveData() {
    Object[] testData = {5,7,8};
    testNode.addData(6);
    testNode.addData(testData[2]);
    testNode.addData(testData[1]);
    testNode.addData(testData[0]);
    testNode.removeData(6);
    assertArrayEquals("Data not removed", testNode.getData().toArray(), testData);
    testNode.removeData(4); 
    assertArrayEquals("Wrong data removed", testNode.getData().toArray(), testData); 
  }

  @Test
  public void testHasSpace() {
    assertTrue("Node doesn't have space when empty", testNode.hasSpace());
    testNode.addData(5);
    assertFalse("Node has space when full", testNode.hasSpace());
  }
  
  @Test
  public void testCompareTo() { 
    Node lessNode 	 = new Node();
    Node greaterNode = new Node();
    Node equalNode 	 = new Node();
    lessNode.addData(5);
    greaterNode.addData(6);
    equalNode.addData(5);
    assertEquals("Nodes do not equal", lessNode.compareTo(equalNode), 0);
    assertEquals("Node is not less than", lessNode.compareTo(greaterNode), -1);
    assertEquals("Nodes is not greater than", greaterNode.compareTo(lessNode), 1);
  }

  @Test
  public void testAddChildren() {
    Node leftNode  = new Node();
    Node rightNode = new Node();
    ArrayList children = new ArrayList();
    children.add(leftNode);
    children.add(rightNode);
    leftNode.addData(5);
    rightNode.addData(6);
    testNode.addChild(rightNode);
    testNode.addChild(leftNode);
    assertEquals("Children not sorted", testNode.getChildren(), children);
    assertEquals("Child's parent not set", leftNode.getParent(), testNode);
  }

  @Test
  public void testHasChildSpace() {
    Node parentNode = new Node();
    Node childNode = new Node();
    assertTrue("Parent node does not have space when empty", parentNode.hasChildSpace());
    parentNode.addChild(childNode);
    parentNode.addChild(childNode);
    assertFalse("Parent node has space when full", parentNode.hasChildSpace());
  }

}
