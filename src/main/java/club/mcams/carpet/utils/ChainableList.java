package club.mcams.carpet.utils;

import java.util.ArrayList;

public class ChainableList<T> extends ArrayList<T> {
    public ChainableList<T> cAdd(T element) {
        super.add(element);
        return this;
    }
}