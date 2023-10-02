package ltpo.Seznami;


import java.io.*;
import java.util.ArrayList;

public class PrioritetnaVrsta<Tip extends Comparable> implements Seznam<Tip> {

    private Object[] heap;
    private int end = 0;

    public PrioritetnaVrsta() {
        this(100);
    }

    public PrioritetnaVrsta(int maxSize) {
        heap = new Object[maxSize];
    }

    @Override
    public void print() {
        String list = "";
        for (int i = 0; i < end; i++) {
            if (heap[i] != null)
                list +=  heap[i] + " ";
        }
        if (list.length() > 0) {
            System.out.println(list.substring(0, list.length()-1));
        }
    }


    @Override
    public void save(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeInt(this.size());

        for (int i = 0; i < this.size(); i++) {
            out.writeObject(heap[i]);

        }

    }

    @Override
    public void restore(InputStream inputStream) throws IOException,
            ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        int count = in.readInt();
        end = count;
        for (int i = 0; i < this.size(); i++) {
            heap[i] = in.readObject();
        }
    }

    private void bubbleUp() {
        int eltIdx = end - 1;
        while (eltIdx >= 0) {
            Tip elt = (Tip) heap[eltIdx];
            int childIdx = eltIdx * 2 + 1;
            if (childIdx < end) {
                Tip child = (Tip) heap[childIdx];
                if (childIdx + 1 < end && child.compareTo(heap[childIdx + 1]) < 0) {
                    child = (Tip) heap[++childIdx];
                }
                if (elt.compareTo(child) >= 0) {
                    return;
                }
                swap(eltIdx, childIdx);
            }
            eltIdx = eltIdx % 2 == 1 ? (eltIdx - 1) / 2 : (eltIdx - 2) / 2;
        }
    }

    private void bubbleUp(int eltIdx) {
        while (eltIdx >= 0) {
            Tip elt = (Tip) heap[eltIdx];
            int childIdx = eltIdx * 2 + 1;
            if (childIdx < end) {
                Tip child = (Tip) heap[childIdx];
                if (childIdx + 1 < end && child.compareTo(heap[childIdx + 1]) < 0) {
                    child = (Tip) heap[++childIdx];
                }
                if (elt.compareTo(child) >= 0) {
                    return;
                }
                swap(eltIdx, childIdx);
            }
            eltIdx = eltIdx % 2 == 1 ? (eltIdx - 1) / 2 : (eltIdx - 2) / 2;
        }
    }

    @Override
    public void add(Tip e) {
        if (end == heap.length) {
            resize();
        }
        heap[end++] = e;
        bubbleUp();
    }

    private void resize() {
        Object[] tmp = heap;
        heap = new Object[2 * heap.length];
        for (int i = 0; i < end; i++) {
            heap[i] = tmp[i];
        }
    }

    private void bubbleDown(int start) {
        int eltIdx = start;
        int childIdx = eltIdx * 2 + 1;
        while (childIdx < end) {
            Tip elt = (Tip) heap[eltIdx];
            Tip child = (Tip) heap[childIdx];
            if (childIdx + 1 < end && child.compareTo(heap[childIdx + 1]) < 0) {
                child = (Tip) heap[++childIdx];
            }
            if (elt.compareTo(child) >= 0) {
                return;
            }
            swap(eltIdx, childIdx);
            eltIdx = childIdx;
            childIdx = eltIdx * 2 + 1;
        }
    }

    @Override
    public Tip removeFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Tip elt = (Tip) heap[0];
        swap(0, --end);
        bubbleDown(0);
        return elt;
    }

    private void swap(int a, int b) {
        Object tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    @Override
    public Tip getFirst() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return (Tip) heap[0];
    }

    @Override
    public int depth() {
        if (end == 0) {
            return 0;
        }
        return (int) (Math.log(end) / Math.log(2)) + 1;
    }

    @Override
    public boolean isEmpty() {
        return end == 0;
    }

    @Override
    public int size() {
        return end;
    }

    @Override
    public Tip remove(Tip e) {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Tip del = null;
        int index = 0;
        for (int i = 0; i < end; i++) {
            if (heap[i].equals(e)) {
                del = (Tip) heap[i];
                index = i;
            }
        }

        if (del == null) throw new java.util.NoSuchElementException();

        heap[index] = heap[0];
        bubbleUp(index);
        removeFirst();

        return (Tip) del;

    }

    @Override
    public boolean exists(Tip e) {
        if (this.isEmpty()) {
            return false;
        }
        for (int i = 0; i < end; i++) {
            if (heap[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    ArrayList<Tip> seznam = new ArrayList<>();

    @Override
    public ArrayList<Tip> asList() {
        seznam.clear();
        for (int i = 0; i < end; i++) {
            if (heap[i] != null){
                seznam.add((Tip) heap[i]);
            }
        }
        return seznam;
    }


}
