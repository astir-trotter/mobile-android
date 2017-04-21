package com.astir_trotter.atcustom.ui.layout.filter.listener;

import com.astir_trotter.atcustom.ui.layout.filter.widget.FilterItem;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public interface FilterItemListener {

    void onItemSelected(FilterItem item);

    void onItemDeselected(FilterItem item);

    void onItemRemoved(FilterItem item);

}
