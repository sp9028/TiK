package ltpo.Seznami;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Drevo23 <Tip extends Comparable<Tip>> implements Seznam<Tip> {

    private Element23 koren;
    private int size;
    private boolean check_if_added;

    //Drevo23 set up
    Drevo23(){
        this.koren = new Element23<>();
        this.size = 0;
    }

    @Override
    public ArrayList<Tip> asList() {
        seznam.clear();
        preOrder();
        return seznam;
    }

    @Override
    public void print() {

    }

    @Override
    public void save(OutputStream outputStream) throws IOException {

    }

    @Override
    public void restore(InputStream inputStream) throws IOException, ClassNotFoundException {

    }

    @Override
    public void add(Tip e) {
        check_if_added = false;
        if (koren == null || koren.getLeftElement() == null){
            check_if_added = true;
            koren = new Element23();
            koren.setLeftElement(e);
        } else{
            Element23 novKoren = add(koren, e);
            if (novKoren != null){
                koren = novKoren;
            }
        }
        if (check_if_added){
            size++;
        }
    }

    private Element23 add(Element23 current, Tip element){
        Element23 newParent = null;
        if (!current.jeList()) {
            Element23 newNode;
            if (current.leftElement.compareTo(element) == 0 || (current.Node3() && current.rightElement.compareTo(element) == 0)) {
            }
            else if (current.leftElement.compareTo(element) > 0) {
                newNode = add(current.leftChild, element);
                if (newNode != null) {
                    if (current.Node2()) {
                        current.rightElement = current.leftElement; // Move the current left element to the right
                        current.leftElement = newNode.leftElement;
                        current.rightChild = current.middleChild;
                        current.middleChild = newNode.middleChild;
                        current.leftChild = newNode.leftChild;
                    }
                    else {
                        Element23 rightCopy = new Element23(current.rightElement, null, current.middleChild, current.rightChild);
                        newParent = new Element23(current.leftElement, null, newNode, rightCopy);
                    }
                }
            }
            else if (current.Node2() || (current.Node3() && current.rightElement.compareTo(element) > 0)) {
                newNode = add(current.middleChild, element);
                if (newNode != null) {
                    if (current.Node2()) {
                        current.rightElement = newNode.leftElement;
                        current.rightChild = newNode.middleChild;
                        current.middleChild = newNode.leftChild;
                    }
                    else {
                        Element23 left = new Element23(current.leftElement, null, current.leftChild, newNode.leftChild);
                        Element23 mid = new Element23(current.rightElement, null, newNode.middleChild, current.rightChild);
                        newParent = new Element23(newNode.leftElement, null, left, mid);
                    }
                }
            }
            else if (current.Node3() && current.rightElement.compareTo(element) < 0) {
                newNode = add(current.rightChild, element);
                if (newNode != null) {
                    Element23 leftCopy = new Element23(current.leftElement, null, current.leftChild, current.middleChild);
                    newParent = new Element23(current.rightElement, null, leftCopy, newNode);
                }
            }
        }
        else {
            check_if_added = true;
            if (current.leftElement.compareTo(element) == 0 || (current.Node3() && current.rightElement.compareTo(element) == 0)) {
                check_if_added = false;
            }
            else if (current.Node2()) {
                if (current.leftElement.compareTo(element) > 0) {
                    current.rightElement = current.leftElement;
                    current.leftElement = element;
                }
                else if (current.leftElement.compareTo(element) < 0) current.rightElement = element;
            }
            else newParent = split(current, element);
        }

        return newParent;
    }

    private Element23 split(Element23 current, Tip element) {

        Element23 newParent = null;

        if (current.leftElement.compareTo(element) > 0) {
            Element23<Tip> left = new Element23<>(element, null);
            Element23 right = new Element23(current.rightElement, null);
            newParent = new Element23(current.leftElement, null, left, right);

        } else if (current.leftElement.compareTo(element) < 0) {
            Element23 left = new Element23(current.leftElement, null);
            if (current.rightElement.compareTo(element) > 0) {
                Element23 right = new Element23(current.rightElement, null);
                newParent = new Element23(element, null, left, right);
            }
            else {
                Element23<Tip> right = new Element23<>(element, null);
                newParent = new Element23(current.rightElement, null, left, right);
            }
        }

        return newParent;
    }

    public void preOrder() {
        if (!isEmpty()) {
            preOrder(koren);
        }
    }

    ArrayList<Tip> seznam = new ArrayList<>();
    private void preOrder(Element23 current) {
        if (current != null) {
            seznam.add((Tip) current.leftElement.toString());
            preOrder(current.leftChild);
            preOrder(current.middleChild);

            if (current.rightElement != null) {
                seznam.add((Tip) current.rightElement.toString());
                preOrder(current.rightChild);
            }
        }
    }

    @Override
    public Tip removeFirst() {
        return remove(getFirst());
    }

    @Override
    public Tip getFirst() {
        return findMin(koren);
    }

    public Tip findMin() {
        if (isEmpty()) return null;
        return findMin(koren);
    }

    private Tip findMin(Element23 current) {

        // Get the minimum element
        if (current.getLeftNode() == null) {
            return (Tip) current.leftElement;
        }

        // Otherwise -> recursive calls
        else {
            return (Tip) findMin(current.getLeftNode());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        if (size == 0){
            return 0;
        } else if (size == 1){
            return 1;
        }
        int depth = 1;
        Element23 el = koren;
        while (el.getClass() == Element23.class){
            el = el.getLeftNode();
            if (el == null){
                break;
            }
            depth++;
        }
        return depth;
    }

    @Override
    public boolean isEmpty() {
        if (koren == null) return true;
        return koren.getLeftElement() == null;
    }

    @Override
    public Tip remove(Tip element) {
        this.size--;
        boolean ifRemoved = remove(koren, element);
        koren.rebalance23();

        if (koren.getLeftElement() == null) {
            koren = null;
        }
        if (!ifRemoved) this.size++;
        return element;
    }

    private boolean remove(Element23 current, Tip element) {
        boolean ifRemoved = true;

        if (current == null) {

        }
        else {

            if (!current.getLeftElement().equals(element)) {
                if (current.getRightElement() == null || current.getRightElement().compareTo(element) > 0) {
                    if (current.getLeftElement().compareTo(element) > 0) {
                        ifRemoved = remove(current.leftChild, element);
                    }
                    else {
                        ifRemoved = remove(current.middleChild, element);
                    }

                } else {
                    if (!current.getRightElement().equals(element)) {
                        ifRemoved = remove(current.rightChild, element);
                    }
                    else {
                        if (current.jeList()) {
                            current.setRightElement(null);
                        }
                        else {
                            Tip replacement = (Tip) current.getRightNode().replaceMin();
                            current.setRightElement(replacement);
                        }
                    }
                }
            }
            else {
                if (current.jeList()) {
                    if (current.getRightElement() != null) {
                        current.setLeftElement(current.getRightElement());
                        current.setRightElement(null);

                    }
                    else {
                        current.setLeftElement(null); // Release the node
                        return true;
                    }
                }
                else {
                    Tip replacement = (Tip) current.getLeftNode().replaceMax();
                    current.setLeftElement(replacement);
                }
            }
        }
        if (!current.is23Balanced()) {
            current.rebalance23();

        } else if (!current.jeList()) {
            boolean isBalanced = false;

            while (!isBalanced) {
                if (current.getRightNode() == null) {
                    if (current.getLeftNode().jeList() && !current.getMidNode().jeList()) {
                        Tip replacement = (Tip) current.getMidNode().replaceMin();
                        Tip tempLeft = (Tip) current.getLeftElement();
                        current.setLeftElement(replacement);

                        add(tempLeft);
                    }
                    else if (!current.getLeftNode().jeList() && current.getMidNode().jeList()) {
                        if (current.getRightElement() == null) {
                            Tip replacement = (Tip) current.getLeftNode().replaceMax();
                            Tip tempLeft = (Tip) current.getLeftElement();
                            current.setLeftElement(replacement);

                            add(tempLeft);
                        }
                    }
                }
                if (current.getRightNode() != null) {
                    if (current.getMidNode().jeList() && !current.getRightNode().jeList()) {
                        current.getRightNode().rebalance23();
                    }
                    if (current.getMidNode().jeList() && !current.getRightNode().jeList()) {
                        Tip replacement = (Tip) current.getRightNode().replaceMin();
                        Tip tempRight = (Tip) current.getRightElement();
                        current.setRightElement(replacement);

                        add(tempRight);
                    } else {
                        isBalanced = true;
                    }
                }
                if (current.is23Balanced()) isBalanced = true;
            }
        }

        return ifRemoved;
    }

    @Override
    public boolean exists(Tip e) {
        return search23(koren, e);
    }

    private boolean search23(Element23 curr, Tip element){
        boolean found = false;
        if (curr != null){
            if (curr.leftElement != null && curr.leftElement.equals(element)){
                found = true;
            } else{
                if (curr.rightElement != null && curr.rightElement.equals(element)) {
                    found = true;
                } else{
                    if (curr.leftElement.compareTo(element) > 0){
                        found = search23(curr.leftChild, element);
                    } else if (curr.rightChild == null || curr.rightElement.compareTo(element) > 0) {
                        found = search23(curr.middleChild, element);
                    } else if (curr.rightElement.compareTo(element) < 0) {
                        found = search23(curr.rightChild, element);
                    } else {
                        return false;
                    }
                }
            }
        }
        return found;
    }

    public class Element23<Tip extends Comparable<Tip>> {

        Element23 leftChild;
        Element23 middleChild;
        Element23 rightChild;
        Tip leftElement;
        Tip rightElement;

        public Element23() {
            this.leftChild = null;
            this.middleChild = null;
            this.rightChild = null;
            this.leftElement = null;
            this.rightElement = null;
        }

        public Element23(Tip leftElement, Tip rightElement) {
            this.leftElement = leftElement;
            this.rightElement = rightElement;
            leftChild = null;
            middleChild = null;
            rightChild = null;
        }

        public Element23(Tip leftElement, Tip rightElement, Element23 leftChild, Element23 middleChild) {
            this.leftElement = leftElement;
            this.rightElement = rightElement;
            this.leftChild = leftChild;
            this.middleChild = middleChild;
        }

        public Tip getLeftElement() {
            return leftElement;
        }

        public void setLeftElement(Tip element) {
            this.leftElement = element;
        }

        public Tip getRightElement() {
            return rightElement;
        }

        public void setRightElement(Tip element) {
            this.rightElement = element;
        }

        private void setLeftNode(Element23 left) {
            this.leftChild = left;
        }

        public Element23 getLeftNode() {
            return leftChild;
        }

        private void setMidNode(Element23 mid) {
            this.middleChild = mid;
        }

        public Element23 getMidNode() {
            return middleChild;
        }

        private void setRightNode(Element23 right) {
            this.rightChild = right;
        }

        public Element23 getRightNode() {
            return rightChild;
        }

        public boolean jeList() {
            return leftChild == null && middleChild == null && rightChild == null;
        }

        public boolean Node2() {
            return rightElement == null;
        }

        public boolean Node3() {
            return rightElement != null;
        }

        public boolean is23Balanced() {
            boolean balanced = false;

            if (jeList()) {
                balanced = true;
            } else if (leftChild.getLeftElement() != null && middleChild.getLeftElement() != null) {
                if (rightElement != null) {
                    if (rightChild.getLeftElement() != null) {
                        balanced = true;
                    }
                } else {
                    balanced = true;
                }
            }
            return balanced;
        }

        public void rebalance23() {
            while (!is23Balanced()) {
                
                //Levi otrok ni na pravem nivoju
                if (getLeftNode().getLeftElement() == null) {
                    getLeftNode().setLeftElement(getLeftElement());
                    setLeftElement((Tip) getMidNode().getLeftElement());

                    if (getMidNode().getRightElement() != null) {
                        getMidNode().setLeftElement(getMidNode().getRightElement());
                        getMidNode().setRightElement(null);
                    }

                    else {
                        getMidNode().setLeftElement(null);
                    }
                }
                //Desni otrok ni na pravem nivoju
                else if (getMidNode().getLeftElement() == null) {
                    if (getRightElement() == null) {

                        if (getLeftNode().getLeftElement() != null && getLeftNode().getRightElement() == null && getMidNode().getLeftElement() == null) {
                            setRightElement(getLeftElement());
                            setLeftElement((Tip) getLeftNode().getLeftElement());

                            // We delete current descendants
                            setLeftNode(null);
                            setMidNode(null);
                            setRightNode(null);

                        } else {
                            getMidNode().setLeftElement(getLeftElement());
                            if (getLeftNode().getRightElement() == null) {
                                setLeftElement((Tip) getLeftNode().getLeftElement());
                                getLeftNode().setLeftElement(null);

                            } else {
                                setLeftElement((Tip) getLeftNode().getRightElement());
                                getLeftNode().setRightElement(null);
                            }

                            if (getLeftNode().getLeftElement() == null && getMidNode().getLeftElement() == null) {
                                setLeftNode(null);
                                setMidNode(null);
                                setRightNode(null);
                            }
                        }
                    } else {
                        getMidNode().setLeftElement(getRightElement());
                        setRightElement((Tip) getRightNode().getLeftElement());
                        if (getRightNode().getRightElement() != null) {
                            getRightNode().setLeftElement(getRightNode().getRightElement());
                            getRightNode().setRightElement(null);

                        }
                        else {
                            getRightNode().setLeftElement(null);
                        }
                    }
                }
                else if (getRightElement() != null && getRightNode().getLeftElement() == null) {
                    if (getMidNode().getRightElement() != null) {
                        getRightNode().setLeftElement(getRightElement());
                        setRightElement((Tip) getMidNode().getRightElement());
                        getMidNode().setRightElement(null);
                    }
                    else {
                        getMidNode().setRightElement(getRightElement());
                        setRightElement(null);
                    }
                }
            }
        }

        public Tip replaceMax() {
            Tip max;
            if (jeList()) {
                if (getRightElement() != null) {
                    max = getRightElement();
                    setRightElement(null);
                } else {
                    max = getLeftElement();
                    setLeftElement(null);
                }
            }
            else {
                if (getRightElement() != null) {
                    max = (Tip) rightChild.replaceMax();
                }
                else {
                    max = (Tip) middleChild.replaceMax();
                }
            }
            if (!is23Balanced()) {
                rebalance23();
            }
            return max;
        }

        Tip replaceMin() {
            Tip min;
            if (jeList()) {
                min = leftElement;
                leftElement = null;
                if (rightElement != null) {
                    leftElement = rightElement;
                    rightElement = null;
                }
            }
            else {
                min = (Tip) leftChild.replaceMin();
            }
            if (!is23Balanced()) {
                rebalance23();
            }
            return min;
        }
    }
}
