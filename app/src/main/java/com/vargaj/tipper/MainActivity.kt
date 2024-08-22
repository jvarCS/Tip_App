package com.vargaj.tipper

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"      //Logging convention is that TAG value is same as class name
private const val TIP_DESCRP_INIT = "Tip is Naur"

class MainActivity : AppCompatActivity() {  //Activities in android dev represent screens. Main activity here means main screen
    private lateinit var etBaseAmountVar: EditText     //Convention used in tutorial video im watching is that variable associated with label has same name as label.
    private lateinit var etTipSliderVar: SeekBar     //Convention used in tutorial video im watching is that variable associated with label has same name as label.
    private lateinit var tvTipPercentVar: TextView     //Convention used in tutorial video im watching is that variable associated with label has same name as label.
    private lateinit var tvTotalVar: TextView     //Convention used in tutorial video im watching is that variable associated with label has same name as label.
    private lateinit var tvTipAmountVar: TextView     //Convention used in tutorial video im watching is that variable associated with label has same name as label.
    private lateinit var tvTipDescrVar: TextView
    private lateinit var cbSplitChkVar: CheckBox
    private lateinit var spChkSpinrVar: Spinner
    private lateinit var etCustomSplitVar: EditText


    override fun onCreate(savedInstanceState: Bundle?) {    //Android OS calls onCreate when app is ran
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)      //R = resources: This means the ui of our screen should come from the layout file defined by activity_main
        etBaseAmountVar = findViewById(R.id.etBaseAmount)
        etTipSliderVar = findViewById(R.id.etTipSlider)
        tvTipAmountVar = findViewById(R.id.tvTipAmount)
        tvTipPercentVar = findViewById(R.id.tvTipPercent)
        tvTotalVar = findViewById(R.id.tvTotal)
        tvTipDescrVar = findViewById(R.id.tvTipDescr)
        cbSplitChkVar = findViewById(R.id.cbSplitChk)
        spChkSpinrVar = findViewById(R.id.spChkSpinr)
        etCustomSplitVar = findViewById(R.id.etCustomSplit)

        // HIDE/SHOW DROP-DOWN MENU WHEN SPLIT CHECK BOX IS SELECTED HERE
        spChkSpinrVar.visibility = View.GONE
        etCustomSplitVar.visibility = View.GONE
        cbSplitChkVar.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                showDropDown()
            }
        })
        // HIDE/SHOW DROP-DOWN MENU WHEN SPLIT CHECK BOX IS SELECTED HERE

        /*
            Object is how anonymous classes are defined. Anonymous classes are one-time use classes that are used to implement interfaces like
            OnSeekBarChangeListener and TextWatcher. In java (cuz am pretty sure kotlin is based off java), interfaces are functions that need to be
            overwritten by other classes before they can be used. (Might just be in general, not just java, cuz pretty sure we used them in CS100 too)
            Instead of creating whole new classes, we just use object to declare and define an anonymous class solely to implement these and other
            interfaces.
        */
        tvTipDescrVar.text = TIP_DESCRP_INIT
        etTipSliderVar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {    //Defining an anonymous class 'object' which implements the interface "OnSeekBarChangeListener". Interface from java
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentVar.text = "$progress%"     //String interpolation (rep as string) in kotlin $var_name
                computeTipAndTotal()

                val tipDesc = when (etTipSliderVar.progress) {
                    in 0..5 -> "Dog water"
                    in 6..10 -> "Stainage"
                    in 11..15 -> "Midington"
                    in 16..20 -> "Solidification"
                    in 21..25 -> "Yippiee"
                    else -> "YAURRRRR"
                }
                tvTipDescrVar.text = tipDesc
                //Update Color of tip descr when sliding
                val color = ArgbEvaluator().evaluate(
                    etTipSliderVar.progress.toFloat() / etTipSliderVar.max,
                    ContextCompat.getColor(this@MainActivity, R.color.badTip),
                    ContextCompat.getColor(this@MainActivity, R.color.goodTip)
                ) as Int

                tvTipDescrVar.setTextColor(color)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        etBaseAmountVar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {   //s = what is being typed
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })

        etCustomSplitVar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {   //s = what is being typed
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

        })

        //Split check button

    }

    private fun computeTipAndTotal() {
        //Check if function should be ran
        if (etBaseAmountVar.text.isEmpty()) {
            tvTipAmountVar.text = ""
            tvTotalVar.text = ""
            return
        }
        //Get value of base amnt and tip percent
        val baseAmnt = etBaseAmountVar.text.toString().toDouble()
        val tipPrcnt = etTipSliderVar.progress
        // Get split number
        var splitAmnt = 1.0
        if (cbSplitChkVar.isChecked) {
            splitAmnt = if (spChkSpinrVar.selectedItem == "Custom") {
                Log.i(TAG,"HELLOSAN")
                etCustomSplitVar.text.toString().toDouble()
            } else spChkSpinrVar.selectedItem.toString().toDouble()
        }
        //Compute tip and total
        val tip = baseAmnt * tipPrcnt / 100
        val total = (baseAmnt + tip) / splitAmnt
        Log.i(TAG, "splitAmnt: $splitAmnt")
        //Update UI
        tvTipAmountVar.text = "$%.2f".format(tip)
        tvTotalVar.text = "$%.2f".format(total)
    }

    private fun showDropDown() {
        if (!(cbSplitChkVar.isChecked)) {
            spChkSpinrVar.visibility = View.GONE
            etCustomSplitVar.visibility = View.GONE
        }
        else  {
            spChkSpinrVar.visibility = View.VISIBLE
            val ddMenuItems = arrayOf("2", "3", "4", "5", "Custom");
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ddMenuItems)
            spChkSpinrVar.adapter = adapter
            spChkSpinrVar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                    val items = spChkSpinrVar.selectedItem.toString()
                    if (spChkSpinrVar.selectedItem.toString() == "Custom") etCustomSplitVar.visibility = View.VISIBLE
                    else etCustomSplitVar.visibility = View.GONE
                    computeTipAndTotal()
                }

                override fun onNothingSelected(arg0: AdapterView<*>?) {}
            }
        }
        computeTipAndTotal()
    }
}

