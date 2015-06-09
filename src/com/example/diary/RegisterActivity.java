package com.example.diary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.*;



public class RegisterActivity extends Activity {
	
	private EditText mAccount;
	private EditText mPwd;
	private EditText mPwd2;
	private EditText mEmail;
	private Button mRegisterButton;
	private Button mCancelButton;
	boolean hasRegisted=false;
	boolean isSuccess = false;
	HttpClient httpClient;
	
	
	/*Handler handler = new Handler(RegisterActivity.this.getMainLooper())
	{
		public void handleMessage(Message msg)
		{
			if(msg.what == 0x123)
			{
				// 使用response文本框显示服务器响应
				Toast.makeText(RegisterActivity.this,msg.obj.toString(),2);
			}
		}
	};*/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.register);
		
		mAccount =(EditText)findViewById(R.id.edit_text);
		mPwd = (EditText)findViewById(R.id.edit_text1);
		mPwd2 = (EditText)findViewById(R.id.edit_text3);
		mEmail = (EditText)findViewById(R.id.edit_text2);
		mRegisterButton = (Button)findViewById(R.id.button1);
		mCancelButton = (Button)findViewById(R.id.button2);
		
		httpClient = new DefaultHttpClient();
		
		mAccount.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				
				if(mAccount.hasFocus()==false){  
					if(TextUtils.isEmpty(mAccount.getText()))
						Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();  
					
				}
            }  
				
			
			
		});
		
		mPwd2.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if(!mPwd2.getText().toString().trim().equals(mPwd.getText().toString().trim())) {
						Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
			
		
		
		mRegisterButton.setOnClickListener(new OnClickListener() {
			String msg="";
			@Override
			public void onClick(View v) {
				final String userName = mAccount.getText().toString();
				final String userPwd = mPwd.getText().toString();
				final String userEmail = mEmail.getText().toString();
				if(TextUtils.isEmpty(mAccount.getText()))
					Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
				if(TextUtils.isEmpty(mPwd.getText()))
					Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				else{
					new Thread()
					{
						@SuppressWarnings("deprecation")
						@Override
						public void run()
						{
							
							try
							{
								HttpPost post = new HttpPost("http://thisisanickname."
										+ "xicp.net:14098/webbb/register.jsp");//③
								// 如果传递参数个数比较多的话可以对传递的参数进行封装
								
								List<NameValuePair> params = new
									ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair
									("name", userName));
								params.add(new BasicNameValuePair
									("pass", userPwd));		
								params.add(new BasicNameValuePair("email", userEmail));
								
								// 设置请求参数
								post.setEntity(new UrlEncodedFormEntity(
									params, HTTP.UTF_8));
								// 发送POST请求
								/*Looper.prepare();
								Toast.makeText(RegisterActivity.this, userName+userPwd+userEmail, 2).show();;
								Looper.loop();*/
								
								HttpResponse response = httpClient
									.execute(post);  //④
								// 如果服务器成功地返回响应
								
								if (response.getStatusLine()
									.getStatusCode() == 200)
								{
									msg = EntityUtils
										.toString(response.getEntity()).trim();
									//msg = response.getEntity().toString();
									// 提示登录成功
									Looper.prepare();
									if(msg.equals("exist"))
										Toast.makeText(RegisterActivity.this, "用户名已存在", 2).show();
									else if(msg.equals("success")){
										Toast.makeText(RegisterActivity.this, "注册成功，请登录", 2).show();
										
										Intent in = getIntent();
									    setResult(RESULT_OK,in);
									    finish();
									}
									else
										Toast.makeText(RegisterActivity.this, msg, 2).show();
									Looper.loop();
									
								}
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}.start();
					
					
				}
				
			}
		});
		mCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent in = getIntent();
			    setResult(RESULT_OK,in);
			    finish();
			}
		});
		
		
	}
	
}
