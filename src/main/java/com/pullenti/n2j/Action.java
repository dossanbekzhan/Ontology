package com.pullenti.n2j;


public interface Action<T> {

    void call(T obj);
}
