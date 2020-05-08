/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author ercan
 */
public class CustomComparator implements Comparator<ArrayList<String>> {

    private final int index;

    public CustomComparator(int index) {
        this.index = index;
    }

    @Override
    public int compare(ArrayList<String> first, ArrayList<String> second) {
        return second.get(index).compareTo(first.get(index));
    }
}
