package ltpo.Seznami;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.HashMap;

public class SeznamiUV {

    Seznam<Oseba> seznamById;
    Seznam<Oseba> seznamByName;

    String[] osebaBuffer = new String[5];
    int addPhase = -1;
    int resetPhase = -1;
    int removePhase = -1;
    int searchPhase = -1;


    public SeznamiUV() {
        seznamById = new Bst<Oseba>(new CompareEmso());
        seznamByName = new Bst<Oseba>(new CompareName());
    }

    public void setMockObj(Seznam<Oseba> mock) {
        seznamById = mock;
        seznamByName = mock;
    }

    public String processInput(String input) {
        Scanner sc = new Scanner(input);
        String token;
        String result = ">> Napačen ukaz";
        if (sc.hasNext()) {
            token = sc.next();
        } else {
            if (addPhase < 0 && removePhase < 0 && searchPhase < 0)
                return ">> Napačen ukaz";
            else
                token = "";
        }
        try {
            if (addPhase >= 0) {
                while (sc.hasNextLine()) {
                    token += sc.nextLine();
                }
                osebaBuffer[addPhase++] = token;
                switch (addPhase) {
                    case 1:
                        result = "dodaj> IME: ";
                        break;
                    case 2:
                        result = "dodaj> PRIIMEK: ";
                        break;
                    case 3:
                        result = "dodaj> KATEGORIJA: ";
                        break;
                    case 4:
                        result = "dodaj> LETO PRIDOBITVE: ";
                    default:
                        break;
                }
                if (addPhase >= 5) {
                    Oseba st = new Oseba(osebaBuffer);
                    if(st.validate()) {
                        if (seznamById.exists(st) || seznamByName.exists(st)) {
                            result = ">> Oseba že obstaja";

                        } else {
                            seznamById.add(st);
                            seznamByName.add(st);
                            result = ">> V redu";
                        }
                    } else {
                        result = ">> Napačni podatki";
                    }
                    addPhase = -1;
                }
            }
            if (resetPhase > 0){
                if (token.equals("d")){
                    while (!seznamByName.isEmpty()) {
                        seznamByName.removeFirst();
                    }
                    while (!seznamById.isEmpty()) {
                        seznamById.removeFirst();
                    }
                    result = ">> V redu";
                } else if (token.equals("n")) {
                    result = ">> Ukaz je bil preklican";
                } else {
                    result = ">> Napačen ukaz";
                }
                resetPhase = -1;
            }
            else if (removePhase > 0) {
                while (sc.hasNextLine()) {
                    token += sc.nextLine();
                }
                osebaBuffer[removePhase++] = token;
                if (removePhase == 2) {
                    return "odstrani> PRIIMEK: ";
                }

                osebaBuffer[0] = "0980980981234";
                osebaBuffer[3] = "B";
                osebaBuffer[4] = "2002";
                removePhase = -1;
                Oseba toRemove = new Oseba(osebaBuffer);

                if (!toRemove.validate()) {
                    return ">> Napačni podatki";
                } else if(seznamByName.exists(toRemove)) {
                    for (Oseba o : seznamByName.asList()){
                        if (Objects.equals(o.ime, osebaBuffer[1]) && Objects.equals(o.priimek, osebaBuffer[2])){
                            Oseba newToRemove = new Oseba(o.emso, o.ime, o.priimek, o.kategorija, o.leto_pridobitve);
                            seznamByName.remove(newToRemove);
                            seznamById.remove(newToRemove);
                            break;
                        }
                    }
                    return ">> V redu";
                } else {
                    return ">> Oseba ne obstaja";
                }
            }
            else if (searchPhase > 0) {
                while (sc.hasNextLine()) {
                    token += sc.nextLine();
                }
                osebaBuffer[searchPhase++] = token;
                if (searchPhase == 2) {
                    return "poišči> PRIIMEK: ";
                }
                osebaBuffer[0] = "0980980981234";
                osebaBuffer[3] = "B";
                osebaBuffer[4] = "2002";
                searchPhase = -1;
                Oseba toFind = new Oseba(osebaBuffer);

                if (!toFind.validate()) {
                    return ">> Napačni podatki";
                } else if (seznamByName.exists(toFind)) {
                    for (Oseba o : seznamById.asList()) {
                        if (Objects.equals(o.ime, osebaBuffer[1]) && Objects.equals(o.priimek, osebaBuffer[2])) {
                            Oseba newToFind = new Oseba(o.emso, o.ime, o.priimek, o.kategorija, o.leto_pridobitve);
                            return ">> " + newToFind.toString();
                        }
                    }
                } else {
                    return ">> Oseba ne obstaja";
                }

            }
            if (token.equals("dodaj")) {
                if (sc.hasNext()) {
                    return ">> Napačen argument";
                }
                addPhase = 0;
                osebaBuffer = new String[5];
                result = "dodaj> EMŠO: ";
            }
            else if (token.equals("preštej")) {
                if (sc.hasNext()) {
                    return ">> Napačen argument";
                }
                result = ">> Število oseb: " + seznamByName.size();
            }
            else if (token.equals("izprazni")) {
                if (sc.hasNext()) {
                    return ">> Napačen argument";
                }
                result = "izprazni> Ste prepričani? (d|n): ";
                resetPhase = 1;
            }
            else if (token.equals("odstrani")) {
                if (sc.hasNext()) {
                    String id = sc.next();
                    Oseba toRemove = new Oseba(id, "fn", "ln", "B", "2002");
                    if (!toRemove.validate()) {
                        return ">> Napačni podatki";
                    } else if(seznamById.exists(toRemove)) {
                        for (Oseba o : seznamById.asList()){
                            if (Objects.equals(o.emso, id)){
                                Oseba newToRemove = new Oseba(o.emso, o.ime, o.priimek, o.kategorija, o.leto_pridobitve);
                                seznamById.remove(newToRemove);
                                seznamByName.remove(newToRemove);
                                break;
                            }
                        }
                        return ">> V redu";
                    } else {
                        return ">> Oseba ne obstaja";
                    }
                } else {
                    removePhase = 1;
                    return "odstrani> IME: ";
                }
            }
            else if (token.equals("poišči")) {
                if (sc.hasNext()) {
                    String id = sc.next();
                    Oseba toFind = new Oseba(id, "fn", "ln", "B", "2002");
                    if (!toFind.validate()) {
                        return ">> Napačni podatki";
                    }
                    if (seznamById.exists(toFind)) {
                        for (Oseba o : seznamById.asList()) {
                            if (Objects.equals(o.emso, id)) {
                                Oseba newToFind = new Oseba(o.emso, o.ime, o.priimek, o.kategorija, o.leto_pridobitve);
                                return ">> " + newToFind.toString();
                            }
                        }
                    } else {
                        return ">> Oseba ne obstaja";
                    }
                } else {
                    searchPhase = 1;
                    return "poišči> IME: ";
                }
            }
            else if (token.equals("izpiši")) {
                if (sc.hasNext()) {
                    return ">> Napačen argument";
                }
                result = ">> Število oseb: " + seznamByName.size();
                for(Oseba o : seznamByName.asList()){
                    result += "\n\t" + o.emso + " | " + o.priimek + ", " + o.ime + " | " +
                            o.kategorija + " | " + o.leto_pridobitve;
                }

            }
            else if (token.equals("shrani")) {
                if (sc.hasNext()) {
                    seznamByName.save(new FileOutputStream(sc.next()));
                    result = ">> V redu";
                }
            }
            else if (token.equals("povrni")) {
                if (sc.hasNext()) {
                    String filename = sc.next();
                    seznamByName.restore(new FileInputStream(filename));
                    seznamById.restore(new FileInputStream(filename));
                    result = ">> V redu";
                }
            }
        }
         catch (IOException e) {
            result = ">> I/O Napaka: " + e.getMessage();
        } catch (ClassNotFoundException e) {
            result = "Error: " + e.getMessage();
        }
        return result;
    }
}