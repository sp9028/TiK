package ltpo.Seznami;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Character.isDigit;


class CompareEmso implements java.util.Comparator<Oseba>{
    @Override
    public int compare(Oseba o1, Oseba o2){
        String emso1 = o1.getEmso();
        String emso2 = o2.getEmso();
        return emso1.compareTo(emso2);
    }
}

class CompareName implements java.util.Comparator<Oseba>{
    @Override
    public int compare(Oseba o1, Oseba o2){
        String name1 = o1.getIme() + ", " + o1.getPriimek();
        String name2 = o2.getIme() + ", " + o2.getPriimek();
        return name1.compareTo(name2);
    }
}

public class Oseba implements Serializable, Comparable{

    @Override
    public int compareTo(Oseba o) {

        return this.emso.compareTo(((Oseba)o).emso);
    }


    protected String emso;
    protected String ime;
    protected String priimek;
    protected String kategorija;
    protected String leto_pridobitve;

    public Oseba(String emso, String ime, String priimek, String kategorija, String leto_pridobitve){
        this.emso = emso;
        this.ime = ime;
        this.priimek = priimek;
        this.kategorija = kategorija;
        this.leto_pridobitve = leto_pridobitve;
    }

    public Oseba(String[] data){
        this.emso = data[0];
        this.ime = data[1];
        this.priimek = data[2];
        this.kategorija = data[3];
        this.leto_pridobitve = data[4];
    }


    public boolean validate(){
        try {
            if (emso.length() == 0 || ime.trim().length() == 0 || priimek.trim().length() == 0 || kategorija.length() == 0 ||
                    leto_pridobitve.length() == 0) {
                return false;
            } else {
                if (emso.length() != 13) {
                    return false;
                } else if (leto_pridobitve.length() != 4) {
                    return false;

                } else if (!((2023 - Integer.parseInt(leto_pridobitve)) >= 18)) {
                    return false;
                } else {
                    ArrayList<String> kategorije = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G"));
                    if (!kategorije.contains(kategorija)) {
                        return false;
                    }
                }
            }
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public String getEmso(){
        return emso;
    }

    public String getIme(){
        return ime;
    }

    public String getPriimek(){
        return priimek;
    }

    public String getKategorija(){
        return kategorija;
    }

    public String getLeto_pridobitve(){
        return leto_pridobitve;
    }

    @Override
    public String toString(){
        return emso + " | " + priimek + ", " + ime + " | " + kategorija + " | " + leto_pridobitve;
    }


}