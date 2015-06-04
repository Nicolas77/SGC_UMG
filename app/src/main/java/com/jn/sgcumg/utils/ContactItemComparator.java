package com.jn.sgcumg.utils;


import java.util.Comparator;


public class ContactItemComparator implements Comparator<ClienteItemInterface> {

    /**
     * Compara los idices de la listview
     * */
    @Override
    public int compare(ClienteItemInterface lhs, ClienteItemInterface rhs) {
        if(lhs.getItemForIndex() == null || rhs.getItemForIndex() == null)
            return -1;


        return(lhs.getItemForIndex().compareTo(rhs.getItemForIndex().toUpperCase() ) );

    }

}
