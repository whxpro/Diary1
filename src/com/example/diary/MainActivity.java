package com.example.diary;

import java.io.File;
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

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageView imageView;
	ImageButton calendar,photo,note;
	Button login,register;
	static boolean islogin=false;
	static String username;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			
			if(msg.what==0x123){
				//Toast.makeText(UserInfo.this, msg.getData().getString("head").trim(), 2).show();;
				byte[] b = Base64Coder.decodeLines(msg.getData().getString("head"));
				Bitmap bmp = null;   
				bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
				imageView.setImageBitmap(bmp);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		
		imageView = (ImageView)findViewById(R.id.profile_image);
		
		File destDir = new File(Environment.getExternalStorageDirectory().toString()+"/Diarytemp");
		if (!destDir.exists()) {
			  destDir.mkdirs();
			  Toast.makeText(this, "success", 2).show();
		}else{
				
		}
		//if(islogin){
		//	
		//}
		calendar = (ImageButton)findViewById(R.id.calendar);
		photo = (ImageButton)findViewById(R.id.photo);
		note = (ImageButton)findViewById(R.id.note);
		
		login = (Button)findViewById(R.id.login);
		register = (Button)findViewById(R.id.register);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(MainActivity.this,Login.class);
				startActivityForResult(intent, 1);
			}
		});
		calendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				
			}
		});
		photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				
			}
		});
		note.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(MainActivity.this,Note.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflator = new MenuInflater(this);
		// ״̬R.menu.context��Ӧ�Ĳ˵�������ӵ�menu��
		inflator.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	// ѡ��˵��Ĳ˵��������Ļص�����
	public boolean onOptionsItemSelected(MenuItem mi){
		if(mi.isCheckable()){
			mi.setChecked(true);  //��
		}
		if(mi.getItemId()==R.id.view){
			Toast.makeText(this, "chakan", Toast.LENGTH_LONG).show();
		}
		return true;
	}
	public void ShowInfo(View v){
		if(islogin){
			Intent intent = new Intent(MainActivity.this,UserInfo.class);
			
		startActivity(intent);
		}else{
			Toast.makeText(this, "����δ��¼", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO �Զ����ɵķ������
		//super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==123){
			
			new Thread(){
				HttpClient httpClient2 = new DefaultHttpClient();
				@SuppressWarnings("deprecation")
				public void run() {
					String ffile;
					try
					{
						HttpPost post1 = new HttpPost("http://thisisanickname."
								+ "xicp.net:14098/webbb/showhead.jsp");//��
						// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
						
						List<NameValuePair> params = new
							ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair
							("name",MainActivity.username));
						
						// �����������
						post1.setEntity(new UrlEncodedFormEntity(
							params, HTTP.UTF_8));
						// ����POST����
						
						HttpResponse response = httpClient2
							.execute(post1);  //��
						// ����������ɹ��ط�����Ӧ
						
						if (response.getStatusLine()
							.getStatusCode() == 200)
						{
							ffile = EntityUtils
								.toString(response.getEntity());
							//info = msg.split("\n");
							
							Message msg = new Message();
							msg.what = 0x123;
							Bundle data = new Bundle();
							data.putString("head", ffile);
							msg.setData(data);
							handler.sendMessage(msg);
							
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				};
			}.start();
			
			//Toast.makeText(this,"test", 2).show();
		}
		
	}
}
