package com.example.caculatordemo;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	TextView mTvsub, mTvresult;
	Button mBtcaculator;
	EditText mEtresult,mEtexp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTvsub = (TextView) findViewById(R.id.main_tv_sub1);
		mTvresult = (TextView) findViewById(R.id.main_tv_result);
		mBtcaculator = (Button) findViewById(R.id.main_bt_caculator);
		mEtresult = (EditText) findViewById(R.id.main_Et_result);
		mEtexp = (EditText) findViewById(R.id.main_Et_exp);
		
		mBtcaculator.setOnClickListener(Bt_caculator);
	}
	
	/**
     * Define the operator priority
     */
	OnClickListener Bt_caculator = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String VarStrResult = mEtexp.getText().toString();
			VarStrResult = FormatExpression(VarStrResult);
			VarStrResult = ConvertInfixToPostfix(VarStrResult);
			VarStrResult = FormatExpression(VarStrResult);
			mEtresult.setText(VarStrResult);
		}
	};
	
	
	/**
     * Function to Format Expression
     */
	public static String FormatExpression(String expression)
	{
	    expression = expression.replaceAll("\\+|\\-|\\*|\\/|\\(|\\)", " $0 ");
	    expression = expression.replaceAll("\\s+", " ");
	    expression.trim();
	    expression += " ";
	    return expression;
	}
	
	/**
     * Check if a character is an operator
     */
    private static boolean IsOperator(char ch) {
        return ch == '^' || ch == '*' || ch == '/' || ch == '+' || ch == '-';
    }

    /**
     * Define the operator priority
     */
    private static int CheckPriority(char operator) {
    	if (operator == '*' || operator == '/' || operator == '^')
            return 2;
        if (operator == '+' || operator == '-')
            return 1;
        return 0;
    }
	
    /**
     * Define the operator priority
     */
	public static String ConvertInfixToPostfix(String VarExpression) {
        // Use StringBuilder to append string is faster than
        StringBuilder VarStrb = new StringBuilder();

        // Use a stack to track operations
        Stack<Character> VarStackChar = new Stack<Character>();

        // Convert expression string to char array
        char[] ArrChars = VarExpression.toCharArray();

        // The length of expression character
        int VarIndexChar = ArrChars.length;

        for (int i = 0; i < VarIndexChar; i++) {
            char VarCharLetter = ArrChars[i];

            if (Character.isDigit(VarCharLetter)) {
                // Number, simply append to the result
                while (Character.isDigit(ArrChars[i])) {
                	VarStrb.append(ArrChars[i++]);
                }
                // Use space as delimiter
                VarStrb.append(' ');
            } else if (VarCharLetter == '(') {
                // Left parenthesis, push to the stack
            	VarStackChar.push(VarCharLetter);
            } else if (VarCharLetter == ')') {
                // Right parenthesis, pop and append to the result until meet the left parenthesis
                while (VarStackChar.peek() != '(') {
                	VarStrb.append(VarStackChar.pop()).append(' ');
                }
                // Don't forget to pop the left parenthesis out
                VarStackChar.pop();
            } else if (IsOperator(VarCharLetter)) {
                // Operator, pop out all higher priority operators first and then push it to the stack
                while (!VarStackChar.isEmpty() && CheckPriority(VarStackChar.peek()) >= CheckPriority(VarCharLetter)) {
                	VarStrb.append(VarStackChar.pop()).append(' ');
                }
                VarStackChar.push(VarCharLetter);
            }
        }

        // Finally pop out whatever left in the stack and append to the result
        while(!VarStackChar.isEmpty()) {
        	VarStrb.append(VarStackChar.pop()).append(' ');
        }
        
        return VarStrb.toString();
    }
	
	/**
     * Define the operator priority
     */
	
}
