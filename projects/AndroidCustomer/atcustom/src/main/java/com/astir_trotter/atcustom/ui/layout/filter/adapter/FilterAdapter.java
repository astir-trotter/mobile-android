package com.astir_trotter.atcustom.ui.layout.filter.adapter;

import java.util.List;

/**
 * @author - Saori Sugiyama
 * @contact - sugiyama.saori.biz@gmail.com
 * @date - 4/21/17
 */

public interface FilterAdapter<T> extends List<T> {

    FilterItem createView(int position, T item);

}
