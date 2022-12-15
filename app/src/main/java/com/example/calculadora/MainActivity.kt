package com.example.calculadora

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import com.example.calculadora.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var infoPantalla = ""
    var operacion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            onDigitPressed(dig1)
            onDigitPressed(dig2)
            onDigitPressed(dig3)
            onDigitPressed(dig4)
            onDigitPressed(dig5)
            onDigitPressed(dig6)
            onDigitPressed(dig7)
            onDigitPressed(dig8)
            onDigitPressed(dig9)
            onDigitPressed(dig0)
            onExecuteOperation(digmult)
            onExecuteOperation(digresta)
            onExecuteOperation(digsuma)
            onExecuteOperation(digdivision)
            onExecuteOperation(digexponecial)
            digitNumberState(true)
            digitOperationState(false)
            digclear.setOnClickListener {
                digitOperationState(false)
                pantalla.text = ""
                pantalla2.text = ""
                digitNumberState(true)
            }
            digposNeg.setOnClickListener {
                val digCambioSigno = pantalla.text.substring(0, 1)
                if (digCambioSigno == "-") {
                    val digPos = pantalla.text.substring(1, pantalla.length())
                    pantalla.text = digPos
                } else {
                    val digNeg = "-${pantalla.text}"
                    pantalla.text = digNeg
                }
            }
            digigual.setOnClickListener {
                val digitUno =
                    infoPantalla.substring(0, infoPantalla.length - 1)
                val oper1 = digitUno.toLong()
                val digitDos = pantalla.text.toString()
                val oper2 = digitDos.toLong()
                val digitoOperacion =
                    infoPantalla.substring(infoPantalla.length - 1, infoPantalla.length)
                if (digitoOperacion == "+") {
                    val resultadoSuma = oper1 + oper2
                    validateResult(resultadoSuma)
                } else if (digitoOperacion == "-") {
                    val resultadoResta = oper1 - oper2
                    validateResult(resultadoResta)
                } else if (digitoOperacion == "*") {
                    val resultadoMultiplicacion = oper1 * oper2
                    validateResult(resultadoMultiplicacion)
                } else if (digitoOperacion == "/") {
                    val resultadoDivision = oper1 / oper2
                    validateResult(resultadoDivision)
                } else if (digitoOperacion == "^") {
                    var resultadoExponencial = 1L
                    for (i in 1..oper2) {
                        resultadoExponencial *= oper1
                        if (resultadoExponencial.toString().length < 12) {
                            pantalla.text = resultadoExponencial.toString()
                            pantalla2.text = ""
                            digitNumberState(false)
                        } else {
                            pantalla.text = getString(R.string.error)
                            pantalla2.text = ""
                            break
                        }
                    }
                }
            }
        }
    }

    fun onDigitPressed(digitButton: Button) {
        binding.apply {
            digitOperationState(false)
            digitButton.setOnClickListener {
                if (pantalla.length() >= 1) {
                    val ultimoDigito =
                        pantalla.text.substring(pantalla.length() - 1, pantalla.length())
                    if (ultimoDigito == "+" || ultimoDigito == "-" || ultimoDigito == "*"
                        || ultimoDigito == "/" || ultimoDigito == "^" || ultimoDigito == "√"
                    ) {
                        digitOperationState(false)
                        operacion = true
                        infoPantalla = pantalla.text.toString()
                        pantalla2.text = infoPantalla
                        val spannableString = SpannableString(infoPantalla)
                        val colorSigno = ForegroundColorSpan(Color.RED)
                        spannableString.setSpan(
                            colorSigno,
                            pantalla.length() - 1,
                            pantalla.length(),
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                        )
                        pantalla.text = digitButton.text
                        pantalla2.text = spannableString

                    } else {
                        if (pantalla.text.length < 12) {
                            val acumulado = "${pantalla.text}${digitButton.text}"
                            pantalla.text = acumulado
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Error",
                                BaseTransientBottomBar.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    pantalla.text = digitButton.text
                    digitOperationState(true)
                    digclear.isEnabled = true
                }
            }
        }
    }

    fun onExecuteOperation(digitButton: Button) {
        binding.apply {
            digitButton.setOnClickListener {
                if (pantalla.length() >= 1) {
                    val ultimoDigito =
                        pantalla.text.substring(pantalla.length() - 1, pantalla.length())
                    if (ultimoDigito == "+" || ultimoDigito == "-" || ultimoDigito == "*"
                        || ultimoDigito == "/" || ultimoDigito == "^") {
                        pantalla.text =
                            pantalla.text.toString()
                                .replace(ultimoDigito, digitButton.text.toString())

                    } else {
                        val acumulado = "${pantalla.text}${digitButton.text}"
                        pantalla.text = acumulado
                    }
                } else {
                    pantalla.text = digitButton.text
                    digitOperationState(true)
                }
            }
        }
    }

    fun digitNumberState(state: Boolean) {
        binding.apply {
            dig1.isEnabled = state
            dig2.isEnabled = state
            dig3.isEnabled = state
            dig4.isEnabled = state
            dig5.isEnabled = state
            dig6.isEnabled = state
            dig7.isEnabled = state
            dig8.isEnabled = state
            dig9.isEnabled = state
            dig0.isEnabled = state
        }
    }

    fun digitOperationState(state: Boolean) {
        binding.apply {
            digsuma.isEnabled = state
            digmult.isEnabled = state
            digresta.isEnabled = state
            digdivision.isEnabled = state
            digexponecial.isEnabled = state
            digposNeg.isEnabled = state
        }
    }

    fun validateResult(result: Long) {
        binding.apply {
            if (result.toString().length < 12) {
                pantalla.text = result.toString()
                pantalla2.text = ""
                digitNumberState(false)
            } else {
                pantalla.text = getString(R.string.error)
                pantalla2.text = ""
            }
        }
    }
}
