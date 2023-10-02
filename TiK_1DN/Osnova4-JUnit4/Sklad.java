package ltpo.Seznami;

import java.io.*;
import java.util.ArrayList;

class Element<Tip> {

    public Tip vrednost;
    public Element<Tip> vezava;

    public Element(Tip e) {
        this.vrednost = e;
    }
}

public class Sklad<Tip> implements Seznam<Tip>{

    private Element<Tip> vrh;

    public Sklad() {
    }

    public void push(Tip e) {
        Element<Tip> novVrh = new Element<>(e);
        novVrh.vezava = vrh;
        vrh = novVrh;
    }

    public Tip pop() {
        if (vrh == null) {
            throw new java.util.NoSuchElementException();
        }
        Tip e = vrh.vrednost;
        vrh = vrh.vezava;
        return e;
    }

    public boolean isEmpty() {
        return (vrh == null);
    }

    public Tip peek() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return vrh.vrednost;
    }

    @Override
    public Tip remove(Tip e) {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Element<Tip> before = null;
        Element<Tip> tmp = vrh;
        while (tmp != null) {
            if (tmp.vrednost.equals(e)) {
                if (before != null)
                    before.vezava = tmp.vezava;
                else
                    vrh = tmp.vezava;
                return tmp.vrednost;
            }
            before = tmp;
            tmp = tmp.vezava;
        }

        throw new java.util.NoSuchElementException();
    }

    @Override
    public boolean exists(Tip e) {
        if (isEmpty()) {
            return false;
        }

        Element<Tip> tmp = vrh;
        while (tmp != null) {
            if (tmp.vrednost.equals(e))
                return true;
            tmp = tmp.vezava;
        }
        return false;
    }

    ArrayList<Tip> seznam = new ArrayList<>();
    @Override
    public ArrayList<Tip> asList() {
        seznam.clear();
        getElements(vrh);
        return seznam;
    }

    @Override
    public void print() {
        print(vrh);
    }

    private void print(Element node) {
        if (node == null) return;
        System.out.println(node.vrednost);
        print(node.vezava);
    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeInt(this.size());
        Element<Tip> tmp = vrh;
        save(tmp, out);
    }
    private void save(Element node, ObjectOutputStream out) throws
            IOException {
        if (node == null)
            return;

        save(node.vezava, out);
        out.writeObject(node.vrednost);
    }

    @Override
    public void restore(InputStream inputStream) throws IOException,
            ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(inputStream);
        int count = in.readInt();
        vrh = null;
        restore(in, count);
    }

    private void restore(ObjectInputStream in, int count) throws
            IOException, ClassNotFoundException {
        if (count == 0) return;

        Element node = new Element((Tip) in.readObject());
        node.vezava = vrh;
        vrh = node;
        restore(in, count-1);
    }

    public void getElements(Element n){
        if (n == null)
            return;
        seznam.add((Tip) n.vrednost);
        getElements(n.vezava);

    }

    @Override
    public void add(Tip e) {
        this.push(e);
    }

    @Override
    public Tip removeFirst() {
        return this.pop();
    }

    @Override
    public Tip getFirst() {
        return peek();
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        int stElementov = 0;
        Element<Tip> tmp = vrh;
        while (tmp != null) {
            stElementov++;
            tmp = tmp.vezava;
        }
        return stElementov;
    }

    @Override
    public int depth() {
        return this.size();
    }

    public boolean isTop(Tip e) {
        if (vrh == null) {
            throw new java.util.NoSuchElementException();
        }
        return vrh.vrednost.equals(e);
    }

    public int search(Tip e) {
        Element<Tip> tmp = vrh;
        int i = 0;
        while (null != tmp) {
            if (tmp.vrednost.equals(e)) {
                return i;
            }
            i++;
            tmp = tmp.vezava;
        }
        return -1;
    }
}
