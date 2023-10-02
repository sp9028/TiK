package ltpo.Seznami;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public interface Seznam<Tip> {
    void add(Tip e);
    Tip removeFirst();
    Tip getFirst();
    int size();
    int depth();
    boolean isEmpty();
    Tip remove(Tip e);
    boolean exists(Tip e);
    ArrayList<Tip> asList();
    void save(OutputStream outputStream) throws IOException;

    void restore(InputStream inputStream) throws IOException, ClassNotFoundException;

    void print();
}
