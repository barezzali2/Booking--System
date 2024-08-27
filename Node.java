package BookingSystem;

public class Node {
    public int seatNum;
    public String person;
    Node leftChild, rightChild;
    
    public Node(int seatNum, String person) {
        this.seatNum = seatNum;
        this.person = person;
        leftChild = rightChild = null;
    }
}