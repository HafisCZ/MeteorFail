/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.util;

public interface QuadConsumer<T, U, V, W> {

    void accept(T t, U u, V v, W w);
}
