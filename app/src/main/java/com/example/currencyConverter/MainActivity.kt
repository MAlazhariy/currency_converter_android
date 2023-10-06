package com.example.currencyConverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    private val currencyEGP = "EGP"
    private val currencyUSD = "USD"
    private val currencyEUR = "EUR"
    private val currencyGBP = "GBP"

    private val values = mapOf(
        currencyUSD to 1.0,
        currencyEGP to 30.80,
        currencyEUR to 0.9,
        currencyGBP to 0.7,
    )

    lateinit var currencyFrom: AutoCompleteTextView
    lateinit var currencyTo: AutoCompleteTextView
    lateinit var calcButton: Button
    lateinit var amountV: EditText
    lateinit var resultValue: AutoCompleteTextView
    lateinit var toolbarV: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        populateDropDownMenu()

        inflateMenu()



        amountV.addTextChangedListener {
            calculateResult()
        }

        currencyFrom.addTextChangedListener {
            calculateResult()
        }

        currencyTo.addTextChangedListener {
            calculateResult()
        }
    }

    private fun inflateMenu() {
        toolbarV.inflateMenu(R.menu.main_menu)
        toolbarV.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share_action -> {
                    // todo: handle share action intent here
                }

                R.id.contact_action -> {
                    // todo: handle contact us intent here
                }
            }
            Toast.makeText(
                this,
                "item clicked",
                Toast.LENGTH_SHORT
            ).show()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(
            R.menu.main_menu,
            menu
        )
        return true
    }


    private fun calculateResult() {
        if (amountV.text.toString().isEmpty()) {
            amountV.error =
                getString(R.string.amount_empty_err_msg)
            return
        }



        try {
            // Ration of to currency to dollar
            val amount =
                amountV.text.toString().toDouble()
            val fromRatio: Double =
                values[currencyFrom.text.toString()]?.toDouble()
                    ?: 0.0
            val toRatio: Double =
                values[currencyTo.text.toString()]?.toDouble()
                    ?: 0.0

            val result: Double =
                amount * toRatio / fromRatio
            val formattedResult =
                String.format("%.3f", result)

            Log.d(
                TAG,
                "VALUES: amount: ${amountV.text}, from: ${currencyFrom.text}, to: ${currencyTo.text}"
            )
            Log.d(TAG, "----")
            Log.d(
                TAG,
                "amount: $amount, fromRatio: $fromRatio, toRation: $toRatio" +
                        "\nresult = $amount * $toRatio / $fromRatio = $result"
            )

            // set result
            resultValue.setText(formattedResult)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.error_occurred),
                Toast.LENGTH_SHORT
            ).show()
            amountV.setText("")
        }
    }

    private fun initViews() {
        currencyFrom =
            findViewById(R.id.from_autoC)
        currencyTo =
            findViewById(R.id.to_autoCompleteTextView)
        calcButton = findViewById(R.id.calc_bu)
        amountV = findViewById(R.id.te_amount)
        resultValue =
            findViewById(R.id.result_autoCompleteTextView2)
        toolbarV = findViewById(R.id.toolbar)
    }

    private fun populateDropDownMenu() {
        val countries = listOf(
            currencyEGP,
            currencyUSD,
            currencyEUR,
            currencyGBP
        )
        val adapter = ArrayAdapter(
            this,
            R.layout.dropdown_text_item,
            countries
        )

        currencyFrom.setAdapter(adapter)
        currencyTo.setAdapter(adapter)
    }
}