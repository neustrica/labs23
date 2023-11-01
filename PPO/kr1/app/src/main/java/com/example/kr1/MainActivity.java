package com.example.kr1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewInput;
    private TextView textViewResult;
    static final double PI = 3.14159265;
    static final double EXP = 2.71828183;
    DecimalFormat decimalFormat = new DecimalFormat("#.#########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInput = findViewById(R.id.textViewInput);
        textViewResult = findViewById(R.id.textViewResult);
        textViewInput.setText("0");
        textViewResult.setText("0");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void imageButtonCalculateClick(View view) {
        StringBuilder string = new StringBuilder(textViewInput.getText().toString());
        replaceChar(string, 'π', Double.toString(PI));
        replaceChar(string, 'e', Double.toString(EXP));
        calculateExpression(string, 0, string.length());
        textViewResult.setText(string);
    }

    public StringBuilder calculateExpression(StringBuilder getString, int start, int finish) {
        StringBuilder string = new StringBuilder(getString);
        try {
            calculateThisExpression(string, start, finish, new String[]{"("});
            calculateThisExpression(string, start, finish, new String[]{"lg(", "ln("});
            calculateThisExpression(string, start, finish, new String[]{"sin("});
            calculateThisExpression(string, start, finish, new String[]{"cos("});
            calculateThisExpression(string, start, finish, new String[]{"tan("});
            calculateThisExpression(string, start, finish, new String[]{"cot("});
            calculateThisExpression(string, start, finish, new String[]{"!"});
            calculateThisExpression(string, start, finish, new String[]{"√"});
            calculateThisExpression(string, start, finish, new String[]{"^"});
            calculateThisExpression(string, start, finish, new String[]{"%"});
            calculateThisExpression(string, start, finish, new String[]{"*"});
            calculateThisExpression(string, start, finish, new String[]{"/"});
            calculateThisExpression(string, start, finish, new String[]{"+", "-"});
        } catch (Exception e) {
            string.setLength(0);
            string.append(R.string.error);
        }
        return string;
    }

    public void calculateThisExpression(StringBuilder string, int start, int finish, String[] operations) {
        for (int i = start; i < string.length() && i < finish; i++) {
            for (String operation : operations) {
                if (!operation.equals("(") && string.charAt(i) == operation.charAt(0)
                        || (i > 0 && operation.equals("(") && string.charAt(i - 1) != 'n' && string.charAt(i - 1) != 's'
                        && string.charAt(i - 1) != 't' && string.charAt(i - 1) != 'g')
                        || i == 0 && operation.equals("(")) {
                    for (int l = 0; l < operation.length(); l++) {
                        if (string.charAt(i + l) != operation.charAt(l)) {
                            break;
                        }
                        if (l == operation.length() - 1) {

                            StringBuilder value1 = new StringBuilder();
                            StringBuilder value2 = new StringBuilder();
                            StringBuilder valueResult = new StringBuilder();
                            double valueDouble1 = 0;
                            double valueDouble2 = 0;
                            double valueDoubleResult;
                            BigDecimal valueBigDecimal1 = new BigDecimal("0");
                            BigDecimal valueBigDecimal2 = new BigDecimal("0");

                            StringBuilder newString = new StringBuilder();
                            int i_ = 0;

                            int j = i - 1;
                            int k = i + operation.length();

                            while (j >= 0 && (Character.isDigit(string.charAt(j)) || string.charAt(j) == '.' ||
                                    (j == 0 && string.charAt(j) == '-'))) {
                                value1.append(string.charAt(j));
                                j--;
                            }
                            while (k < string.length() && (Character.isDigit(string.charAt(k)) || string.charAt(k) == '.'
                                    || string.charAt(i + operation.length()) == '-')) {
                                value2.append(string.charAt(k));
                                k++;
                            }

                            j++;
                            value1.reverse();

                            if (value1.length() != 0
                                    && !operation.equals("√")
                                    && !operation.equals("sin(") && !operation.equals("cos(")
                                    && !operation.equals("tan(") && !operation.equals("cot(")
                                    && !operation.equals("(")
                                    && !operation.equals("lg(") && !operation.equals("ln(")) {
                                valueDouble1 = Double.parseDouble(value1.toString());
                                valueBigDecimal1 = BigDecimal.valueOf(valueDouble1);

                            }
                            if (!operation.equals("%") && !operation.equals("!")
                                    && !operation.equals("sin(") && !operation.equals("cos(")
                                    && !operation.equals("tan(") && !operation.equals("cot(")
                                    && !operation.equals("(")
                                    && !operation.equals("lg(") && !operation.equals("ln(")) {
                                valueDouble2 = Double.parseDouble(value2.toString());
                                valueBigDecimal2 = BigDecimal.valueOf(valueDouble2);
                            }

                            switch (operation) {
                                case "sin(":
                                case "cos(":
                                case "tan(":
                                case "cot(":
                                case "lg(":
                                case "ln(":
                                case "(":
                                    i_ = i + operation.length();
                                    while (i_ < string.length() && string.charAt(i_) != ')') {
                                        i_++;
                                    }
                                    newString.append(calculateExpression(string, i + operation.length(), i_));
                                    i_ = i + operation.length();
                                    while (i_ < newString.length() && newString.charAt(i_) != ')') {
                                        i_++;
                                    }
                                    k = i_ + 1;
                                    break;
                            }

                            switch (operation) {
                                case "+":
                                    valueDoubleResult = valueBigDecimal1.add(valueBigDecimal2).doubleValue();
                                    break;
                                case "-":
                                    valueDoubleResult = valueBigDecimal1.subtract(valueBigDecimal2).doubleValue();
                                    break;
                                case "*":
                                    valueDoubleResult = valueBigDecimal1.multiply(valueBigDecimal2).doubleValue();
                                    break;
                                case "/":
                                    valueDoubleResult = valueBigDecimal1.divide(valueBigDecimal2).doubleValue();
                                    break;
                                case "^":
                                    valueDoubleResult = Math.pow(valueDouble1, valueDouble2);
                                    break;
                                case "√":
                                    valueDoubleResult = Math.sqrt(valueDouble2);
                                    break;
                                case "!":
                                    int tmp = (int) valueDouble1;
                                    if (((double) tmp) == valueDouble1) {
                                        valueDoubleResult = getFactorial(tmp);
                                    } else {
                                        valueDoubleResult = Math.exp(Math.log(getFactorial(tmp)) + (valueDouble1 - tmp) * Math.log(tmp + 1));
                                    }
                                    break;
                                case "%":
                                    StringBuilder value0 = new StringBuilder();
                                    if(j > 1) {
                                        int j0 = j - 2;
                                        while (j0 >= 0 && (Character.isDigit(string.charAt(j0)) || string.charAt(j0) == '.' ||
                                                (j0 == 0 && string.charAt(j0) == '-'))) {
                                            value0.append(string.charAt(j0));
                                            j0--;
                                        }
                                        value0.reverse();
                                        double valueDouble0 = Double.parseDouble(value0.toString());
                                        valueDoubleResult = valueDouble0 * (valueDouble1 / 100);
                                    } else {
                                        valueDoubleResult = valueDouble1 / 100;
                                    }
                                    break;
                                case "sin(":
                                    valueDoubleResult = Math.sin(Double.parseDouble(newString.substring(i + operation.length(), i_)));
                                    break;
                                case "cos(":
                                    valueDoubleResult = Math.cos(Double.parseDouble(newString.substring(i + operation.length(), i_)));
                                    break;
                                case "tan(":
                                    valueDoubleResult = Math.tan(Double.parseDouble(newString.substring(i + operation.length(), i_)));
                                    break;
                                case "cot(":
                                    valueDoubleResult = 1 / Math.tan(Double.parseDouble(newString.substring(i + operation.length(), i_)));
                                    break;
                                case "(":
                                    valueDoubleResult = Double.parseDouble(newString.substring(i + operation.length(), i_));
                                    break;
                                case "lg(":
                                    valueDoubleResult = Math.log10(Double.parseDouble(newString.substring(i + operation.length(), i_)));
                                    break;
                                case "ln(":
                                    valueDoubleResult = Math.log(Double.parseDouble(newString.substring(i + operation.length(), i_)));
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + operation);
                            }

                            valueResult.append(decimalFormat.format(valueDoubleResult));

                            if (valueResult.charAt(0) == '-' && j > 0 &&
                                    string.charAt(j - 1) == '+') {
                                j--;
                            }

                            string.delete(j, k);
                            string.insert(j, valueResult);
                            i = j;
                            finish = string.length();
                        }
                    }
                }
            }
        }
    }

    public void imageButtonPercentClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
                textViewInput.setText(textViewInput.getText().toString().substring(0, textViewInput.getText().toString().length() - 1));
                textViewInput.append("%");
                break;
            default:
                textViewInput.append("%");
                break;
        }
    }

    public void imageButtonDivideClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
                textViewInput.setText(textViewInput.getText().toString().substring(0, textViewInput.getText().toString().length() - 1));
                textViewInput.append("/");
                break;
            default:
                textViewInput.append("/");
                break;
        }
    }

    public void imageButtonDotClick(View view) {
        for (int i = textViewInput.getText().toString().length() - 1; i > 0; i--) {
            if (!Character.isDigit(textViewInput.getText().toString().charAt(i))) {
                if (textViewInput.getText().toString().charAt(i) != '.') {
                    textViewInput.append(".");
                }
                break;
            }
            if (i == 1) {
                textViewInput.append(".");
            }
        }
        if (textViewInput.getText().toString().length() == 1) {
            textViewInput.append(".");
        }
    }

    public void imageButtonMultiplyClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
                textViewInput.setText(textViewInput.getText().toString().substring(0,
                        textViewInput.getText().toString().length() - 1));
                textViewInput.append("*");
                break;
            default:
                textViewInput.append("*");
                break;
        }
    }

    public void imageButtonMinusClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
            case '!':
            case '(':
                textViewInput.append("(-");
                break;
            default:
                checkEmptyStringAndAppend("-");
                //textViewInput.append("-");
                break;
        }
    }

    public void imageButtonXExtentClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
                textViewInput.setText(textViewInput.getText().toString().substring(0,
                        textViewInput.getText().toString().length() - 1));
                textViewInput.append("^");
                break;
            default:
                textViewInput.append("^");
                break;
        }
    }

    public void imageButtonPlusClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
                textViewInput.setText(textViewInput.getText().toString().substring(0,
                        textViewInput.getText().toString().length() - 1));
                textViewInput.append("+");
                break;
            default:
                textViewInput.append("+");
                break;
        }
    }

    public void imageButtonXSqrtClick(View view) {
        checkEmptyStringAndAppend("√");
    }

    public void imageButtonXFactorialClick(View view) {
        switch (textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1)) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '√':
            case '^':
                textViewInput.setText(textViewInput.getText().toString().substring(0,
                        textViewInput.getText().toString().length() - 1));
                textViewInput.append("!");
                break;
            default:
                textViewInput.append("!");
                break;
        }
    }

    public void imageButtonEraseClick(View view) {
        if (textViewInput.getText().toString().length() > 1) {
            textViewInput.setText(textViewInput.getText().toString().substring(0,
                    textViewInput.getText().toString().length() - 1));
        } else {
            textViewInput.setText("0");
        }
    }

    public void imageButton1DivXClick(View view) {
        textViewInput.append("^(-1)");
    }

    public void imageButtonCClick(View view) {
        textViewInput.setText("0");
        textViewResult.setText("0");
    }

    public void imageButtonPiClick(View view) {
        checkEmptyStringAndAppend("π");
    }

    public void imageButtonExitClick(View view) {
        finish();
    }

    public void imageButton1Click(View view) {
        checkEmptyStringAndAppend("1");
    }

    public void imageButton2Click(View view) {
        checkEmptyStringAndAppend("2");
    }

    public void imageButton3Click(View view) {
        checkEmptyStringAndAppend("3");
    }

    public void imageButton4Click(View view) {
        checkEmptyStringAndAppend("4");
    }

    public void imageButton5Click(View view) {
        checkEmptyStringAndAppend("5");
    }

    public void imageButton6Click(View view) {
        checkEmptyStringAndAppend("6");
    }

    public void imageButton7Click(View view) {
        checkEmptyStringAndAppend("7");
    }

    public void imageButton8Click(View view) {
        checkEmptyStringAndAppend("8");
    }

    public void imageButton9Click(View view) {
        checkEmptyStringAndAppend("9");
    }

    public void imageButton0Click(View view) {
        if (!textViewInput.getText().toString().equals("0")
                && !(textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 1) == '0'
                && !Character.isDigit(textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 2))
                && textViewInput.getText().toString().charAt(textViewInput.getText().toString().length() - 2) != '.')) {
            textViewInput.append("0");
        }
    }

    public void imageButtonEClick(View view) {
        checkEmptyStringAndAppend("e");
    }

    public void imageButtonLgClick(View view) {
        checkEmptyStringAndAppend("lg(");
    }

    public void imageButtonLnClick(View view) {
        checkEmptyStringAndAppend("ln(");
    }

    public void imageButtonOpenBraceClick(View view) {
        checkEmptyStringAndAppend("(");
    }

    public void imageButtonClosedBraceClick(View view) {
        checkEmptyStringAndAppend(")");
    }

    public void imageButtonSinClick(View view) {
        checkEmptyStringAndAppend("sin(");
    }

    public void imageButtonCosClick(View view) {
        checkEmptyStringAndAppend("cos(");
    }

    public void imageButtonTanClick(View view) {
        checkEmptyStringAndAppend("tan(");
    }

    public void imageButtonCotClick(View view) {
        checkEmptyStringAndAppend("cot(");
    }

    public int getFactorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; ++i) {
            result *= i;
        }
        return result;
    }

    public void replaceChar(StringBuilder string, char oldChar, String newString) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == oldChar) {
                string.deleteCharAt(i);
                if (i + newString.length() < string.length()) {
                    string.setLength(string.length() + newString.length());
                }
                string.insert(i, newString);
            }
        }
    }

    public void checkEmptyStringAndAppend(String string) {
        if (textViewInput.getText().toString().equals("0")) {
            textViewInput.setText(string);
        } else {
            textViewInput.append(string);
        }
    }
}