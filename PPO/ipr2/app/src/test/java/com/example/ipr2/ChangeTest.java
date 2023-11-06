package com.example.ipr2;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;


public class ChangeTest {
    @Test
    public void initTest() {
        ChangeActivity changeActivity = Mockito.mock(ChangeActivity.class);
        changeActivity.backToList();
        verify(changeActivity).backToList();
    }
}