package com.postnov.android.summerschoolapp.net;

import java.util.List;

/**
 * Created by postnov on 25.03.2016.
 */
public interface Parser<T> {
    public List parse(T data);
}
