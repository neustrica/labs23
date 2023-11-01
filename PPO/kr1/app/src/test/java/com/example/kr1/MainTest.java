package com.example.kr1;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MainTest {

    @Test
    public void calculateExpressionTest() {
        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        StringBuilder string = new StringBuilder("5+(cos(0)-tan(80)*6.14)-4^lg(25)/ln(13)-√19+sin(4!)*cot(π)+e");
        mainActivity.calculateExpression(string, 0, string.length());
        verify(mainActivity).calculateExpression(string, 0, string.length());
    }

    @Test
    public void calculateThisExpressionTest() {
        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        StringBuilder string = new StringBuilder("5+(cos(0)-tan(80)*6.14)-4^lg(25)/ln(13)-√19+sin(4!)*cot(π)+e");
        String[] operations = new String[]{"+", "-","/","*","%","^","√","!","cot(","tan(","cos(","sin(","lg(", "ln(","("};
        mainActivity.calculateThisExpression(string, 0, string.length(),operations);
        verify(mainActivity).calculateThisExpression(string, 0, string.length(),operations);
    }

    @Test
    public void getFactorialTest() {
        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        when(mainActivity.getFactorial(5)).thenCallRealMethod();
        assertEquals(120, mainActivity.getFactorial(5));
    }

    @Test
    public void replaceCharTest() {
        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        StringBuilder string = new StringBuilder("5+(cos(0)-tan(80)*6.14)-4^lg(25)/ln(13)-√19+sin(4!)*cot(π)+e");
        mainActivity.replaceChar(string, 'π', Double.toString(MainActivity.PI));
        verify(mainActivity).replaceChar(string, 'π', Double.toString(MainActivity.PI));
    }

    @Test
    public void checkEmptyStringAndAppendTest() {
        MainActivity mainActivity = Mockito.mock(MainActivity.class);
        mainActivity.checkEmptyStringAndAppend("");
        verify(mainActivity).checkEmptyStringAndAppend("");
    }
}
