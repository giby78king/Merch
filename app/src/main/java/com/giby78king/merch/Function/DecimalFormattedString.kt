package com.giby78king.merch.Model

import android.annotation.SuppressLint
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.util.*

class EditAmountSetting {
    @SuppressLint("SetTextI18n")
    fun editRule(editAmount: EditText, textWatcher: TextWatcher) {
        try {
            var marks = false
            editAmount.removeTextChangedListener(textWatcher)
            if (editAmount.text.contains("-")) {
                marks = true
            }
            var value: String =
                editAmount.text.toString().replace(",", "").replace("$ ", "").replace("-", "")

            if (value.startsWith("0")&&!value.startsWith("0.")) {
                if(value.length > 1)
                {
                    value = value.drop(1)
                }
            }
            if (value.startsWith(".")) {
                value = value.replace(".", "0.")
            }

            if (value != "" || marks) {
                if (!marks) {
                    if (value.length > 7) {
                        value = value.dropLast(1)
                    }
                    editAmount.setText("$ ${DecimalFormattedString().formatted(value)}")
                }
                else
                {
                    editAmount.setText("$ -${DecimalFormattedString().formatted(value)}")
                }
                editAmount.setSelection(editAmount.text.toString().length)
            } else {
                editAmount.setText("")
            }
            editAmount.addTextChangedListener(textWatcher)
            return
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            editAmount.addTextChangedListener(textWatcher)
        }
    }
    @SuppressLint("SetTextI18n")
    fun editNoDollarRule(editAmount: EditText, textWatcher: TextWatcher) {
        try {
            var marks = false
            editAmount.removeTextChangedListener(textWatcher)
            if (editAmount.text.contains("-")) {
                marks = true
            }
            var value: String =
                editAmount.text.toString().replace(",", "").replace("$ ", "").replace("-", "")

            if (value.startsWith("0")&&!value.startsWith("0.")) {
                if(value.length > 1)
                {
                    value = value.drop(1)
                }
            }
            if (value.startsWith(".")) {
                value = value.replace(".", "0.")
            }

            if (value.contains(".")) {
                value = BigDecimal(value).setScale(2, BigDecimal.ROUND_DOWN).toDouble().toString()
                value = String.format("%.2f", (value.toDouble()))
            }

            if (value != "" || marks) {
                if (!marks) {
                    if (value.length > 7) {
                        value = value.dropLast(1)
                    }
                    editAmount.setText(DecimalFormattedString().formatted(value))
                }
                else
                {
                    editAmount.setText("-${DecimalFormattedString().formatted(value)}")
                }
                editAmount.setSelection(editAmount.text.toString().length)
            } else {
                editAmount.setText("0")
            }
            editAmount.addTextChangedListener(textWatcher)
            return
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            editAmount.addTextChangedListener(textWatcher)
        }
    }
}
class TextAmountSetting {
    @SuppressLint("SetTextI18n")
    fun formatAmount(textAmount: String) : String{
        try {
            var marks = false
            if (textAmount.contains("-")) {
                marks = true
            }
            var value: String =
                textAmount.toString().replace(",", "").replace("$ ", "").replace("-", "")

            if (value.startsWith("0")&&!value.startsWith("0.")) {
                if(value.length > 1)
                {
                    value = value.drop(1)
                }
            }
            if (value.startsWith(".")) {
                value = value.replace(".", "0.")
            }

            return if (value != "" || marks) {
                if (!marks) {
                    if (value.length > 7) {
                        value = value.dropLast(1)
                    }
                    "$ ${DecimalFormattedString().formatted(value)}"
                } else {
                    "$ -${DecimalFormattedString().formatted(value)}"
                }
            } else {
                ""
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
    }

    @SuppressLint("SetTextI18n")
    fun formatAmountNoDollar (textAmount: String) : String{
        try {
            var marks = false
            if (textAmount.contains("-")) {
                marks = true
            }
            var value: String =
                textAmount.toString().replace(",", "").replace("$ ", "").replace("-", "")

            if (value.startsWith("0")&&!value.startsWith("0.")) {
                if(value.length > 1)
                {
                    value = value.drop(1)
                }
            }
            if (value.startsWith(".")) {
                value = value.replace(".", "0.")
            }

            return if (value != "" || marks) {
                if (!marks) {
                    if (value.length > 7) {
                        value = value.dropLast(1)
                    }
                    DecimalFormattedString().formatted(value)
                } else {
                    "-${DecimalFormattedString().formatted(value)}"
                }
            } else {
                ""
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return ""
    }
}
class DecimalFormattedString {
    fun formatted(value: String): String {
        val lst = StringTokenizer(value, ".")
        var str1 = value
        var str2 = ""
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken()
            str2 = lst.nextToken()
        }
        var str3 = ""
        var i = 0
        var j = -1 + str1.length
        if (str1[-1 + str1.length] == '.') {
            j--
            str3 = "."
        }
        var k = j
        while (true) {
            if (k < 0) {
                if (str2.isNotEmpty()) str3 = "$str3.$str2"
                return str3
            }
            if (i == 3) {
                str3 = ",$str3"
                i = 0
            }
            str3 = str1[k].toString() + str3
            i++
            k--
        }
    }
}