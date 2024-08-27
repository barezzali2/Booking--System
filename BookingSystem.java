package BookingSystem;

public class BookingSystem {
    Node root; // The root of the tree

    // Constructor
    public BookingSystem() {
        root = null;
    }

    public void bookSeat(int seatNum, String person) { // To record the seats

        if(seatNum <= 0 || seatNum > 155) {
            System.out.println("Invalid Input! The seat numbers should be between 1 and 155");
            return;
        }
        
        if (countSeats() >= 155) { // check the available seats
            System.out.println("Sorry, all seats are booked.");
            return;
        }

        if (findSeat(root, seatNum) != null) {
            System.out.println("Seat " + seatNum + " is already booked.");
            return;
        }
        
        Node node = new Node(seatNum, person);
        if(root == null) {
            root = node;
            return;
        }
        Node current = root;
        Node parent;
        while(true) {
            parent = current;
            if(seatNum < current.seatNum) { // left part
                current = current.leftChild;
                if(current == null) {
                    parent.leftChild = node;
                    return;
                }
            } else { // right part
                current = current.rightChild;
                if(current == null) {
                    parent.rightChild = node;
                    return;
                }
            }
        }
    }
    
    public void preOrder(Node root) { // Showing all the booked seats.
        if(root != null) {
            System.out.println(root.seatNum + " " + root.person);
            preOrder(root.leftChild);
            preOrder(root.rightChild);
        }
    }

    public void preOrderAfter(Node root, int seatNum) { // Showing the booked seats after a specific booked seat.
        if (root != null) {
            if (root.seatNum > seatNum) {
                System.out.println("The booked seat after " + seatNum + " is " + root.seatNum + " " + root.person);
            }
            preOrderAfter(root.leftChild, seatNum);
            preOrderAfter(root.rightChild, seatNum);
        }
    }


    public int countSeats() {
        return count(root);
    }
    private int count(Node root) {
        if (root == null)
            return 0;
        else
            return 1 + count(root.leftChild) + count(root.rightChild);  
    }


    public void delete(int seatNum) { // To delete a specific seat
        Node parent = null;
        Node curr = root;
    
        while(curr != null && curr.seatNum != seatNum) {
            parent = curr;
            if (seatNum < curr.seatNum) {
                curr = curr.leftChild; // to the left
            } else {
                curr = curr.rightChild; // to the right
            }
        }
    
        if (curr == null) {
            System.out.println("Seat not found to be Deleted.");
            return;
        }
    
        // Case 1: Deleting leaf
        if (curr.leftChild == null && curr.rightChild == null) {
            if (curr != root) {
                if (parent.leftChild == curr) {
                    parent.leftChild = null;
                } else {
                    parent.rightChild = null;
                }
            } else {
                root = null; // Delete the root node
            }
        }
        // Case 2: Node to be deleted with one child
        else if (curr.leftChild == null || curr.rightChild == null) {
            Node child = (curr.leftChild != null) ? curr.leftChild : curr.rightChild;
            if (curr != root) {
                if (parent.leftChild == curr) {
                    parent.leftChild = child;
                } else {
                    parent.rightChild = child;
                }
            } else {
                root = child; // Delete root node
            }
        }
        // Case 3: Node to be deleted with two children
        else {
            Node successor = getSuccessor(curr);
            if (curr != root) {
                if (parent.leftChild == curr) {
                    parent.leftChild = successor;
                } else {
                    parent.rightChild = successor;
                }
            } else {
                root = successor; // If deleting root node
            }
            successor.leftChild = curr.leftChild;
        }
    }
    
    public Node getSuccessor(Node node) {
        Node succParent = node;
        Node successor = node;
        Node current = node.rightChild;
    
        while(current != null) {
            succParent = successor;
            successor = current;
            current = current.leftChild;
        }
    
        // If successor is not the right child of node, move the successor's right child to its parent's left child
        if (successor != node.rightChild) {
            succParent.leftChild = successor.rightChild;
            successor.rightChild = node.rightChild;
        }
        return successor;
    }

    public Node findSeat(Node root, int seatNum) { // A method to find a specific seat
        if (root == null || root.seatNum == seatNum) {
            return root;
        }
        
        if (seatNum < root.seatNum) {
            return findSeat(root.leftChild, seatNum); // Recursive
        } else {
            return findSeat(root.rightChild, seatNum);
        }
    }
    
    
    public static void main(String[] args) {
        BookingSystem bk = new BookingSystem();
        bk.bookSeat(133, "Barez");
        bk.bookSeat(120, "Mama Vandam");
        bk.bookSeat(144, "Yad");
        bk.bookSeat(150, "Alan");
        
        bk.bookSeat(170, "Amanj");
        bk.bookSeat(133, "ahmad");
        bk.bookSeat(90, "Zhwan");
        
        bk.preOrder(bk.root);
        System.out.println("Available seats: " + (155-bk.countSeats()));

        bk.delete(90);
        bk.delete(30);
        System.out.println("---------------------");
        bk.preOrder(bk.root);
        System.out.println("Available seats: " + (155-bk.countSeats()));

        System.out.println("---------------------");
        bk.preOrderAfter(bk.root, 120);
    }
}