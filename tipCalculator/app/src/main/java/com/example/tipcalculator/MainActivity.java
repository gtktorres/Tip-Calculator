package com.example.calculusgratuitus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }

    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener(){
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                   boolean fromUser){
                    percent = progress / 100.0;
                    calculate();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            };
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double percent = 0.15;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;

    private void calculate() {

        Log.d("MainActivity", "inside calculate method");
        percentTextView.setText(percentFormat.format(percent));

        double tip = billAmount * percent;
        double total = billAmount + tip;

        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }

    private final TextWatcher amountEditTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count){
            Log.d("MainActivity", "inside onTextChanged method: charSequence="+ s );
            try{
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                Log.d("MainActivity", "Bill Amount = "+billAmount);
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch(NumberFormatException e) {
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate();
        }
        @Override
        public void afterTextChanged(Editable s){}

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };
}
