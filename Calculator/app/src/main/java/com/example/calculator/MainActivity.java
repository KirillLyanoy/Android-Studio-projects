package com.example.calculator;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String number_1 = "0";
    String number_2 = "0";
    String current_operation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button button_0 = findViewById(R.id.button_0);
        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);
        Button button_4 = findViewById(R.id.button_4);
        Button button_5 = findViewById(R.id.button_5);
        Button button_6 = findViewById(R.id.button_6);
        Button button_7 = findViewById(R.id.button_7);
        Button button_8 = findViewById(R.id.button_8);
        Button button_9 = findViewById(R.id.button_9);
        Button button_negative_number = findViewById(R.id.button_negative_number);
        Button button_comma = findViewById(R.id.button_comma);
        Button button_plus = findViewById(R.id.button_plus);
        Button button_minus = findViewById(R.id.button_minus);
        Button button_multiply = findViewById(R.id.button_multiply);
        Button button_divide = findViewById(R.id.button_divide);
        Button button_equals = findViewById(R.id.button_equals);
        Button button_square_root = findViewById(R.id.button_square_root);
        Button button_1_divide_x = findViewById(R.id.button_1_divide_x);
        Button button_log = findViewById(R.id.button_log);
        Button button_fact = findViewById(R.id.button_fact);
        Button button_square_degree = findViewById(R.id.button_square_degree);
        Button button_degree = findViewById(R.id.button_degree);
        Button button_ln = findViewById(R.id.button_ln);
        Button button_delete = findViewById(R.id.button_delete);
        Button button_clear = findViewById(R.id.button_clear);
        Button button_mod = findViewById(R.id.button_mod);
        Button button_exp = findViewById(R.id.button_exp);

        TextView currentOperationView = findViewById(R.id.currentOperationView);
        TextView editTextNumber = findViewById(R.id.editTextNumber);

        View.OnClickListener buildFirstNumber = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_0:
                        if (number_1.equals("0")) number_1 = "0";
                        else number_1 += '0';
                        break;
                    case R.id.button_1:
                        if (number_1.equals("0")) number_1 = "1";
                        else number_1 += '1';
                        break;
                    case R.id.button_2:
                        if (number_1.equals("0")) number_1 = "2";
                        else number_1 += '2';
                        break;
                    case R.id.button_3:
                        if (number_1.equals("0")) number_1 = "3";
                        else number_1 += '3';
                        break;
                    case R.id.button_4:
                        if (number_1.equals("0")) number_1 = "4";
                        else number_1 += '4';
                        break;
                    case R.id.button_5:
                        if (number_1.equals("0")) number_1 = "5";
                        else number_1 += '5';
                        break;
                    case R.id.button_6:
                        if (number_1.equals("0")) number_1 = "6";
                        else number_1 += '6';
                        break;
                    case R.id.button_7:
                        if (number_1.equals("0")) number_1 = "7";
                        else number_1 += '7';
                        break;
                    case R.id.button_8:
                        if (number_1.equals("0")) number_1 = "8";
                        else number_1 += '8';
                        break;
                    case R.id.button_9:
                        if (number_1.equals("0")) number_1 = "9";
                        else number_1 += '9';
                        break;
                    case R.id.button_comma:
                        if (number_1.contains(",")) break;
                        else {
                            if (number_1.equals("0")) number_1 = "0,";
                            else number_1 += ',';
                        }
                        break;
                    case R.id.button_negative_number:
                        if (number_1.equals("0")) break;
                        else {
                            if (number_1.charAt(0) == '-') {
                                number_1 = number_1.substring(1);
                            } else number_1 = '-' + number_1;
                        }
                        break;
                    case R.id.button_delete:
                        if (number_1.equals("0")) break;
                        else {
                            if (number_1.length() == 1) number_1 = "0";
                            else number_1 = number_1.substring(0, number_1.length() - 1);
                        }
                        break;
                    case R.id.button_clear:
                        number_1 = "0";
                        number_2 = "0";
                        current_operation = null;
                        editTextNumber.setText("");
                        currentOperationView.setText("");
                        break;
                }
                editTextNumber.setText(number_1);
            }
        };

        button_0.setOnClickListener(buildFirstNumber);
        button_1.setOnClickListener(buildFirstNumber);
        button_2.setOnClickListener(buildFirstNumber);
        button_3.setOnClickListener(buildFirstNumber);
        button_4.setOnClickListener(buildFirstNumber);
        button_5.setOnClickListener(buildFirstNumber);
        button_6.setOnClickListener(buildFirstNumber);
        button_7.setOnClickListener(buildFirstNumber);
        button_8.setOnClickListener(buildFirstNumber);
        button_9.setOnClickListener(buildFirstNumber);
        button_comma.setOnClickListener(buildFirstNumber);
        button_negative_number.setOnClickListener(buildFirstNumber);
        button_delete.setOnClickListener(buildFirstNumber);
        button_clear.setOnClickListener(buildFirstNumber);

        View.OnClickListener calculationWithOneVariable = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                number_2 = number_1;

                if (!current_operation.isEmpty()) {
                    button_equals.callOnClick();
                }

                switch (view.getId()) {
                    case R.id.button_square_root:
                        number_1 = String.valueOf((sqrt(Float.parseFloat(number_1))));
                        current_operation = "sqrt ";
                        break;
                    case R.id.button_1_divide_x:
                        number_1 = String.valueOf(1 / Float.parseFloat(number_1));
                        current_operation = "1/";
                        break;
                    case R.id.button_log:
                        number_1 = String.valueOf((Math.log10(Float.parseFloat(number_1))));
                        current_operation = "log ";
                        break;
                    case R.id.button_fact:
                        long temp = 1;
                        for (int i = 1; i <= Float.parseFloat(number_1); i++)
                        {
                            temp *= i;
                        }
                        number_1 = String.valueOf(temp);
                        current_operation = "fact ";
                        break;
                    case R.id.button_square_degree:
                        number_1 = String.valueOf(Float.parseFloat(number_1) * Float.parseFloat(number_1));
                        current_operation = "sqr ";
                        break;
                    case R.id.button_ln:
                        number_1 = String.valueOf((Math.log(Float.parseFloat(number_1))));
                        current_operation = "ln ";
                        break;
                    case R.id.button_exp:
                        number_1 = String.valueOf((Math.exp(Float.parseFloat(number_1))));
                        current_operation = "exp";
                        break;
                }
                currentOperationView.setText(current_operation + number_2);
                editTextNumber.setText(number_1);
            }
        };

        button_square_root.setOnClickListener(calculationWithOneVariable);
        button_1_divide_x.setOnClickListener(calculationWithOneVariable);
        button_log.setOnClickListener(calculationWithOneVariable);
        button_fact.setOnClickListener(calculationWithOneVariable);
        button_square_degree.setOnClickListener(calculationWithOneVariable);
        button_ln.setOnClickListener(calculationWithOneVariable);
        button_exp.setOnClickListener(calculationWithOneVariable);

        View.OnClickListener calculationWithTwoVariable = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber.setText(number_1);

                if (!current_operation.isEmpty()) {
                    button_equals.callOnClick();
                } else number_2 = number_1;

                number_1 = "0";
                switch (view.getId()) {
                    case R.id.button_plus:
                        current_operation = "+";
                        break;
                    case R.id.button_minus:
                        current_operation = "-";
                        break;
                    case R.id.button_multiply:
                        current_operation = "*";
                        break;
                    case R.id.button_divide:
                        current_operation = "/";
                        break;
                    case R.id.button_degree:
                        current_operation = "^";
                        break;
                    case R.id.button_mod:
                        current_operation = "mod";
                        break;
                }
                currentOperationView.setText(number_2 + " " + current_operation);
            }
        };
        button_plus.setOnClickListener(calculationWithTwoVariable);
        button_minus.setOnClickListener(calculationWithTwoVariable);
        button_multiply.setOnClickListener(calculationWithTwoVariable);
        button_divide.setOnClickListener(calculationWithTwoVariable);
        button_degree.setOnClickListener(calculationWithTwoVariable);
        button_mod.setOnClickListener(calculationWithTwoVariable);

        button_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (current_operation){
                    case "":
                        break;
                    case "+":
                        number_2 = String.valueOf(Float.parseFloat(number_2) + Float.parseFloat(number_1));
                        break;
                    case "-":
                        number_2 = String.valueOf(Float.parseFloat(number_2) - Float.parseFloat(number_1));
                        break;
                    case "*":
                        number_2 = String.valueOf(Float.parseFloat(number_2) * Float.parseFloat(number_1));
                        break;
                    case "/":
                        number_2 = String.valueOf(Float.parseFloat(number_2) / Float.parseFloat(number_1));
                        break;
                    case "^":
                        number_2 = String.valueOf(Math.pow( Float.parseFloat(number_2), Float.parseFloat(number_1)));
                        break;
                    case "mod":
                        number_2 = String.valueOf(Float.parseFloat(number_2) % Float.parseFloat(number_1));
                        break;
                }
                currentOperationView.setText(number_2);
            }
        });
    }
}