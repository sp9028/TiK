package ltpo.Seznami;

import ltpo.Seznami.Seznam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SkladMock implements Seznam {
    @Override
    public void add(Object e) {

    }

    @Override
    public Object removeFirst() {
        return null;
    }

    @Override
    public Object getFirst() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Object remove(Object e) {
        return null;
    }

    @Override
    public boolean exists(Object e) {
        return false;
    }

    @Override
    public ArrayList asList() {
        return null;
    }

    @Override
    public void print() {

    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        throw new IOException("No space left on device");

    }

    @Override
    public void restore(InputStream inputStream) throws IOException, ClassNotFoundException {

    }
}