package com.predic8.workshop.stock.event;

import com.predic8.workshop.stock.dto.Stock;
import org.junit.Before;

import static org.junit.Assert.*;

public class NullAwareBeanUtilsBeanTest {

    NullAwareBeanUtilsBean beanUtils;

    @Before
    public void setup() {
        beanUtils = new NullAwareBeanUtilsBean();
    }

    @org.junit.Test
    public void copyProperty() {

        Stock s1 = new Stock();
    }
}