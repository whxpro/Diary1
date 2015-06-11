package com.example.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class feedback extends Activity{
	private TextView feedback_login;
	EditText femail;
	Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		
		setContentView(R.layout.feedback);
		femail = (EditText)findViewById(R.id.femail);
		submit = (Button)findViewById(R.id.submit);
		feedback_login = (TextView)findViewById(R.id.feedback_login);
		
		feedback_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(feedback.this,MainActivity.class);
				//startActivity(intent);
				finish();
			}
		});
	}

}
