package com.pullenti.n2j;


public interface Func<T, TResult> {

    TResult call(T obj);
}
