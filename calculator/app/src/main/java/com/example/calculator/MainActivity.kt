package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    var number_1: String = "0"
    var number_2: String = "0"
    var current_operation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Добавляем обработчик WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets  // Возвращаем insets
        }

        // Объявляем все кнопки
        val button_0 = findViewById<Button>(R.id.button_0)
        val button_1 = findViewById<Button>(R.id.button_1)
        val button_2 = findViewById<Button>(R.id.button_2)
        val button_3 = findViewById<Button>(R.id.button_3)
        val button_4 = findViewById<Button>(R.id.button_4)
        val button_5 = findViewById<Button>(R.id.button_5)
        val button_6 = findViewById<Button>(R.id.button_6)
        val button_7 = findViewById<Button>(R.id.button_7)
        val button_8 = findViewById<Button>(R.id.button_8)
        val button_9 = findViewById<Button>(R.id.button_9)
        val button_negative_number = findViewById<Button>(R.id.button_negative_number)
        val button_comma = findViewById<Button>(R.id.button_comma)
        val button_plus = findViewById<Button>(R.id.button_plus)
        val button_minus = findViewById<Button>(R.id.button_minus)
        val button_multiply = findViewById<Button>(R.id.button_multiply)
        val button_divide = findViewById<Button>(R.id.button_divide)
        val button_equals = findViewById<Button>(R.id.button_equals)
        val button_square_root = findViewById<Button>(R.id.button_square_root)
        val button_1_divide_x = findViewById<Button>(R.id.button_1_divide_x)
        val button_log = findViewById<Button>(R.id.button_log)
        val button_fact = findViewById<Button>(R.id.button_fact)
        val button_square_degree = findViewById<Button>(R.id.button_square_degree)
        val button_degree = findViewById<Button>(R.id.button_degree)
        val button_ln = findViewById<Button>(R.id.button_ln)
        val button_delete = findViewById<Button>(R.id.button_delete)
        val button_clear = findViewById<Button>(R.id.button_clear)
        val button_mod = findViewById<Button>(R.id.button_mod)
        val button_exp = findViewById<Button>(R.id.button_exp)

        val currentOperationView = findViewById<TextView>(R.id.currentOperationView)
        val editTextNumber = findViewById<TextView>(R.id.editTextNumber)

        button_equals.setOnClickListener {
            when (current_operation) {
                "" -> {}
                "+" -> number_2 = (number_2.toFloat() + number_1.toFloat()).toString()
                "-" -> number_2 = (number_2.toFloat() - number_1.toFloat()).toString()
                "*" -> number_2 = (number_2.toFloat() * number_1.toFloat()).toString()
                "/" -> number_2 = (number_2.toFloat() / number_1.toFloat()).toString()
                "^" -> number_2 = number_2.toFloat().pow(number_1.toFloat()).toString()
                "mod" -> number_2 = (number_2.toFloat() % number_1.toFloat()).toString()
            }
            currentOperationView.text = number_2
        }

        val buildFirstNumber =
            View.OnClickListener { view ->
                when (view.id) {
                    R.id.button_0 -> if (number_1 == "0") number_1 = "0"
                    else number_1 += '0'

                    R.id.button_1 -> if (number_1 == "0") number_1 = "1"
                    else number_1 += '1'

                    R.id.button_2 -> if (number_1 == "0") number_1 = "2"
                    else number_1 += '2'

                    R.id.button_3 -> if (number_1 == "0") number_1 = "3"
                    else number_1 += '3'

                    R.id.button_4 -> if (number_1 == "0") number_1 = "4"
                    else number_1 += '4'

                    R.id.button_5 -> if (number_1 == "0") number_1 = "5"
                    else number_1 += '5'

                    R.id.button_6 -> if (number_1 == "0") number_1 = "6"
                    else number_1 += '6'

                    R.id.button_7 -> if (number_1 == "0") number_1 = "7"
                    else number_1 += '7'

                    R.id.button_8 -> if (number_1 == "0") number_1 = "8"
                    else number_1 += '8'

                    R.id.button_9 -> if (number_1 == "0") number_1 = "9"
                    else number_1 += '9'

                    R.id.button_comma -> if (number_1.contains(",")) else {
                        if (number_1 == "0") number_1 = "0,"
                        else number_1 += ','
                    }

                    R.id.button_negative_number -> if (number_1 == "0") else {
                        number_1 = if (number_1[0] == '-') {
                            number_1.substring(1)
                        } else "-$number_1"
                    }

                    R.id.button_delete -> if (number_1 == "0") else {
                        number_1 = if (number_1.length == 1) "0"
                        else number_1.substring(0, number_1.length - 1)
                    }

                    R.id.button_clear -> {
                        number_1 = "0"
                        number_2 = "0"
                        current_operation = ""
                        editTextNumber.text = ""
                        currentOperationView.text = ""
                    }
                }
                editTextNumber.text = number_1
            }

        button_0.setOnClickListener(buildFirstNumber)
        button_1.setOnClickListener(buildFirstNumber)
        button_2.setOnClickListener(buildFirstNumber)
        button_3.setOnClickListener(buildFirstNumber)
        button_4.setOnClickListener(buildFirstNumber)
        button_5.setOnClickListener(buildFirstNumber)
        button_6.setOnClickListener(buildFirstNumber)
        button_7.setOnClickListener(buildFirstNumber)
        button_8.setOnClickListener(buildFirstNumber)
        button_9.setOnClickListener(buildFirstNumber)
        button_comma.setOnClickListener(buildFirstNumber)
        button_negative_number.setOnClickListener(buildFirstNumber)
        button_delete.setOnClickListener(buildFirstNumber)
        button_clear.setOnClickListener(buildFirstNumber)

        val calculationWithOneVariable =
            View.OnClickListener { view ->
                number_2 = number_1
                if (!current_operation.isEmpty()) {
                    button_equals.callOnClick()
                }

                when (view.id) {
                    R.id.button_square_root -> {
                        number_1 = java.lang.String.valueOf((sqrt(number_1.toFloat())))
                        current_operation = "sqrt "
                    }

                    R.id.button_1_divide_x -> {
                        number_1 = (1 / number_1.toFloat()).toString()
                        current_operation = "1/"
                    }

                    R.id.button_log -> {
                        number_1 = (log10(number_1.toFloat())).toString()
                        current_operation = "log "
                    }

                    R.id.button_fact -> {
                        var temp: Long = 1
                        var i = 1
                        while (i <= number_1.toFloat()) {
                            temp *= i.toLong()
                            i++
                        }
                        number_1 = temp.toString()
                        current_operation = "fact "
                    }

                    R.id.button_square_degree -> {
                        number_1 = (number_1.toFloat() * number_1.toFloat()).toString()
                        current_operation = "sqr "
                    }

                    R.id.button_ln -> {
                        number_1 = (ln(number_1.toFloat())).toString()
                        current_operation = "ln "
                    }

                    R.id.button_exp -> {
                        number_1 = (exp(number_1.toFloat())).toString()
                        current_operation = "exp"
                    }
                }
                currentOperationView.text = current_operation + number_2
                editTextNumber.text = number_1
            }

        button_square_root.setOnClickListener(calculationWithOneVariable)
        button_1_divide_x.setOnClickListener(calculationWithOneVariable)
        button_log.setOnClickListener(calculationWithOneVariable)
        button_fact.setOnClickListener(calculationWithOneVariable)
        button_square_degree.setOnClickListener(calculationWithOneVariable)
        button_ln.setOnClickListener(calculationWithOneVariable)
        button_exp.setOnClickListener(calculationWithOneVariable)

        val calculationWithTwoVariable =
            View.OnClickListener { view ->
                editTextNumber.text = number_1
                if (!current_operation.isEmpty()) {
                    button_equals.callOnClick()
                } else number_2 = number_1

                number_1 = "0"
                when (view.id) {
                    R.id.button_plus -> current_operation = "+"
                    R.id.button_minus -> current_operation = "-"
                    R.id.button_multiply -> current_operation = "*"
                    R.id.button_divide -> current_operation = "/"
                    R.id.button_degree -> current_operation = "^"
                    R.id.button_mod -> current_operation = "mod"
                }
                currentOperationView.text = "$number_2 $current_operation"
            }
        button_plus.setOnClickListener(calculationWithTwoVariable)
        button_minus.setOnClickListener(calculationWithTwoVariable)
        button_multiply.setOnClickListener(calculationWithTwoVariable)
        button_divide.setOnClickListener(calculationWithTwoVariable)
        button_degree.setOnClickListener(calculationWithTwoVariable)
        button_mod.setOnClickListener(calculationWithTwoVariable)
    }
}
