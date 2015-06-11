package com.example.diary;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.text.Editable;
import android.text.TextUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	private Button login;
	private EditText username;
	private EditText password;
	private TextView feedback;
	HttpClient httpClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.login);
		
		login = (Button)this.findViewById(R.id.login);
		username = (EditText)this.findViewById(R.id.lusername);
		password =(EditText)this.findViewById(R.id.lpassword);
		feedback = (TextView)this.findViewById(R.id.feedback);
		httpClient = new DefaultHttpClient();
		
		login.setOnClickListener(new View.OnClickListener() {
			String msg;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String name = username.getText().toString();
				final String pwd = password.getText().toString();
				//在此之前應該添加邏輯判斷，如果需要調用數據庫，則需要判斷用戶名密碼是否和數據庫的數據相對應
				if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
					Toast.makeText(Login.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
				}else{
					new Thread()
					{
						@SuppressWarnings("deprecation")
						@Override
						public void run()
						{
							
							try
							{
								HttpPost post = new HttpPost("http://thisisanickname."
										+ "xicp.net:14098/webbb/login.jsp");//③
								// 如果传递参数个数比较多的话可以对传递的参数进行封装
								
								List<NameValuePair> params = new
									ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair
									("name", name));
								params.add(new BasicNameValuePair
									("pass", pwd));		
								
								
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
									if(msg.equals("success")){
										Toast.makeText(Login.this, "登录成功", 2).show();
										MainActivity.islogin = true;
										MainActivity.username = name;
										
										Intent in = getIntent();
									    setResult(123,in);
									    finish();
									}
									else
										Toast.makeText(Login.this, msg, 2).show();
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

		feedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this,feedback.class);
				startActivity(intent);
			}
		});
	}
	/*static Bitmap bmp;
	static Bitmap Head(){
		new Thread(){
			HttpClient httpClient2 = new DefaultHttpClient();
			public void run() {
				String ffile;
				try
				{
					HttpPost post1 = new HttpPost("http://thisisanickname."
							+ "xicp.net:14098/webbb/showhead.jsp");//③
					// 如果传递参数个数比较多的话可以对传递的参数进行封装
					
					List<NameValuePair> params = new
						ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair
						("name",MainActivity.username));
					
					// 设置请求参数
					post1.setEntity(new UrlEncodedFormEntity(
						params, HTTP.UTF_8));
					// 发送POST请求
					
					HttpResponse response = httpClient2
						.execute(post1);  //④
					// 如果服务器成功地返回响应
					
					if (response.getStatusLine()
						.getStatusCode() == 200)
					{
						ffile = EntityUtils
							.toString(response.getEntity());
						//info = msg.split("\n");
						// 
						byte[] b = Base64Coder.decodeLines(ffile);
						//Bitmap bmp = null;   
						bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
						
						
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			};
		}.start();
		return bmp;
	}*/
}

