package com.astir_trotter.atcustom.ui.layout.filter.listener;

import java.util.ArrayList;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public interface FilterListener<T> {

    void onFiltersSelected(ArrayList<T> filters);

    void onNothingSelected();

    void onFilterSelected(T item);

    void onFilterDeselected(T item);
        
}
