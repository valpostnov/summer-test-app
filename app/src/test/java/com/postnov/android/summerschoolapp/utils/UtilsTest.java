package com.postnov.android.summerschoolapp.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by platon on 14.08.2016.
 */
public class UtilsTest
{
    private List<String> strings;

    @Before
    public void init()
    {
        strings = Arrays.asList("one", "two", "three");
    }

    @Test
    public void testSubList()
    {
        Assert.assertEquals(3, Utils.subList(0, 4, strings).size());
    }
}
