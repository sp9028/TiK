package ltpo.Seznami;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.HashMap;

public class SeznamiUV {

    HashMap<String, Seznam<String>> seznami;
    Seznam<String> seznam;

    public SeznamiUV() {
        seznami = new HashMap<>();
        seznami.put("pq", new PrioritetnaVrsta<String>());
        seznami.put("stack", new Sklad<String>());
        seznami.put("bst", new Bst<String>());
        seznami.put("23", new Drevo23<>());
    }

    public void addImpl(String key, Seznam<String> seznam) {
        seznami.put(key, seznam);
    }

    public String processInput(String input) {
        Scanner sc = new Scanner(input);
        String token;
        String result = "OK";

        if (sc.hasNext()) {
            token = sc.next();
        } else {
            return "Error: enter command";
        }
        if (!token.equals("use") && (null == seznam)) {
            return "Error: empty use statement";
        }
        if (token.equals("exit")) {
            return "Have a nice day.";
        }
        try {
            switch (token) {
                case "use":
                    if (sc.hasNext()) {
                        seznam = seznami.get(sc.next());
                        if (null == seznam) {
                            result = "Error: choose one of existing data structures";
                        }
                    } else {
                        result = "Error: empty use statement";
                    }
                    break;
                case "add":
                    if (sc.hasNext()) {
                        seznam.add(sc.next());
                    } else {
                        result = "Error: please specify a string";
                    }
                    break;
                case "remove_first":
                    result = seznam.removeFirst();
                    break;
                case "get_first":
                    result = seznam.getFirst();
                    if (result == null) {
                        result = "Error: data structure is empty";
                    }
                    break;
                case "size":
                    result = seznam.size() + "";
                    break;
                case "depth":
                    result = seznam.depth() + "";
                    break;
                case "is_empty":
                    result = "Data structure is " + (seznam.isEmpty() ? "" : "not ") + "empty.";
                    break;
                case "reset":
                    while (!seznam.isEmpty()) {
                        seznam.removeFirst();
                    }
                    break;
                case "exists":
                    if (sc.hasNext()) {
                        result = "Element " + (seznam.exists(sc.next()) ? "exists " : "doesn't exist ") + "in data structure.";
                    } else {
                        result = "Error: please specify a string";
                    }
                    break;
                case "remove":
                    if (sc.hasNext()) {
                        String next = sc.next();
                        if (!seznam.exists(next)) {
                            result = "Error: can't remove element that doesn't exist in data structure.";
                        }else {
                            result = seznam.remove(next);
                        }
                    } else {
                        result = "Error: please specify a string";
                    }
                    break;
                case "asList":
                    result = String.valueOf(seznam.asList());
                    break;
                case "print":
                    seznam.print();
                    break;
                case "save":
                    if (sc.hasNext()) {
                        seznam.save(new FileOutputStream(sc.next()));
                    } else {
                        result = "Error: please specify a file name";
                    }
                    break;
                case "restore":
                    if (sc.hasNext()) {
                        seznam.restore(new FileInputStream(sc.next()));
                    } else {
                        result = "Error: please specify a file name";
                    }
                    break;
                default:
                    result = "Error: invalid command";
                    break;
            }
        }  catch (IllegalArgumentException e) {
            result = "Error: Duplicated entry";
        } catch (NoSuchElementException | NullPointerException e) {
            result = "Error: data structure is empty";
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}