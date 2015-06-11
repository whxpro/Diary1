package com.example.diary;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UserInfo extends Activity{
	
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	
	ImageView portrait;
	EditText username_1,password_1,email_1;
	Button change,back;
	HttpClient httpClient;
	//boolean isLogin;
	final String name=MainActivity.username;
	
	static Bitmap upbitmap;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == 0x123){
				password_1.setText(msg.getData().getStringArray("info")[0]);
				email_1.setText(msg.getData().getStringArray("info")[1]);
				//Toast.makeText(UserInfo.this, msg.getData().getStringArray("info")[2], 2).show();
			}
			if(msg.what==0x12){
				//Toast.makeText(UserInfo.this, msg.getData().getString("head").trim(), 2).show();;
				byte[] b = Base64Coder.decodeLines(msg.getData().getString("head"));
				Bitmap bmp = null;   
				bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
				portrait.setImageBitmap(bmp);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.userinfo);
		
		portrait = (ImageView)findViewById(R.id.profile_image_1);
		username_1 = (EditText)findViewById(R.id.username_1);
		password_1 = (EditText)findViewById(R.id.password_1);
		email_1 = (EditText)findViewById(R.id.email_1);
		
		httpClient = new DefaultHttpClient();
		
		change = (Button)findViewById(R.id.change);
		back = (Button)findViewById(R.id.back);
		
		showInfo();
		change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				final String userName = username_1.getText().toString();
				final String userPwd = password_1.getText().toString();
				final String userEmail = email_1.getText().toString();
				if(!userName.equals(name))
					Toast.makeText(UserInfo.this, "�������޸��û���Ӵ", Toast.LENGTH_SHORT).show();
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
										+ "xicp.net:14098/webbb/changeinfo.jsp");//��
								// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
								String msg;
								List<NameValuePair> params = new
									ArrayList<NameValuePair>();
								params.add(new BasicNameValuePair
									("name", userName));
								params.add(new BasicNameValuePair
									("pass", userPwd));		
								params.add(new BasicNameValuePair("email", userEmail));
								
								// �����������
								post.setEntity(new UrlEncodedFormEntity(
									params, HTTP.UTF_8));
								// ����POST����
								/*Looper.prepare();
								Toast.makeText(RegisterActivity.this, userName+userPwd+userEmail, 2).show();;
								Looper.loop();*/
								
								HttpResponse response = httpClient
									.execute(post);  //��
								// ����������ɹ��ط�����Ӧ
								
								if (response.getStatusLine()
									.getStatusCode() == 200)
								{
									msg = EntityUtils
										.toString(response.getEntity()).trim();
									//msg = response.getEntity().toString();
									// ��ʾ��¼�ɹ�
									Looper.prepare();
									if(msg.equals("success")){
										Toast.makeText(UserInfo.this, "�޸ĳɹ��������µ�¼", 2).show();
										
										Intent in = getIntent();
									    setResult(1,in);
									    finish();
									}
									else
										Toast.makeText(UserInfo.this, msg, 2).show();
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
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				/*Intent in = getIntent();
			    setResult(1,in);*/
			    finish();
			}
		});
	}
	
	
	
	String[] info;
	private void showInfo(){
		if(MainActivity.islogin){
			//��õ�¼����û���
			
			username_1.setText(name);
			new Thread()
			{
				String msg;
				@SuppressWarnings("deprecation")
				@Override
				public void run()
				{
					
					try
					{
						HttpPost post = new HttpPost("http://thisisanickname."
								+ "xicp.net:14098/webbb/showinfo.jsp");//��
						// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
						
						List<NameValuePair> params = new
							ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair
							("name",name));
						
						// �����������
						post.setEntity(new UrlEncodedFormEntity(
							params, HTTP.UTF_8));
						// ����POST����
						
						HttpResponse response = httpClient
							.execute(post);  //��
						// ����������ɹ��ط�����Ӧ
						
						if (response.getStatusLine()
							.getStatusCode() == 200)
						{
							msg = EntityUtils
								.toString(response.getEntity()).trim();
							info = msg.split("\n");
							
							// 
							
							Message msg = new Message();
							msg.what = 0x123;
							Bundle data = new Bundle();
							data.putStringArray("info", info);
							msg.setData(data);
							handler.sendMessage(msg);
							
							
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					String ffile;
						try
						{
							HttpPost post1 = new HttpPost("http://thisisanickname."
									+ "xicp.net:14098/webbb/showhead.jsp");//��
							// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
							
							List<NameValuePair> params = new
								ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair
								("name",name));
							
							// �����������
							post1.setEntity(new UrlEncodedFormEntity(
								params, HTTP.UTF_8));
							// ����POST����
							
							HttpResponse response = httpClient
								.execute(post1);  //��
							// ����������ɹ��ط�����Ӧ
							
							if (response.getStatusLine()
								.getStatusCode() == 200)
							{
								ffile = EntityUtils
									.toString(response.getEntity());
								//info = msg.split("\n");
								// 
								
								Message msg = new Message();
								msg.what = 0x12;
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
					
				}
			}.start();
			
		}
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
	
	public void ChangePortrait(View v){
	
		openIamge();
		
		
	}
	
	public void openIamge(){
		Intent intent = new Intent();  
        /* ����Pictures����Type�趨Ϊimage */  
        intent.setType("image/*");  
        /* ʹ��Intent.ACTION_GET_CONTENT���Action */  
        intent.setAction(Intent.ACTION_GET_CONTENT);   
        /* ȡ����Ƭ�󷵻ر����� */  
        startActivityForResult(intent, 1); 
	}
	
	ByteArrayOutputStream stream;
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (resultCode == RESULT_OK) {  
            Uri uri = data.getData();  
            Log.e("uri", uri.toString());  
            ContentResolver cr = this.getContentResolver();  
            try {  
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
                
                upbitmap=BitmapFactory.decodeFile(getAbsoluteImagePath(data.getData()));
                //ImageView imageView = (ImageView) findViewById(R.id.iv01);  
                /* ��Bitmap�趨��ImageView */  
                portrait.setImageBitmap(upbitmap);  
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        } 
        
        
		stream = new ByteArrayOutputStream();
		upbitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        //uploadPortrait();
        
        super.onActivityResult(requestCode, resultCode, data);  
        uploadPortrait();
    }  
	@SuppressWarnings("deprecation")
	public void uploadPortrait(){
		final HttpPost post = new HttpPost("http://thisisanickname."
				+ "xicp.net:14098/webbb/upload.jsp");
		
		byte[] b = stream.toByteArray();
		
		// ��ͼƬ�����ַ�����ʽ�洢����
		String file = new String(Base64Coder.encodeLines(b));
		
		//Toast.makeText(UserInfo.this, file, 2).show();
		final HttpClient client = new DefaultHttpClient();
		// �����ϴ�����
		final List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		
		formparams.add(new BasicNameValuePair("name", name));
		formparams.add(new BasicNameValuePair("portrait", file));
		
		
		new Thread(){
			UrlEncodedFormEntity entity;
			public void run() {
				try {
					post.setEntity(new UrlEncodedFormEntity(
							formparams, HTTP.UTF_8));
					
					/*entity = new UrlEncodedFormEntity(formparams, "UTF-8");
					post.addHeader("Accept",
							"text/javascript, text/html, application/xml, text/xml");
					post.addHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
					post.addHeader("Accept-Encoding", "gzip,deflate,sdch");
					post.addHeader("Connection", "Keep-Alive");
					post.addHeader("Cache-Control", "no-cache");
					post.addHeader("Content-Type", "application/x-www-form-urlencoded");
					post.setEntity(entity);*/
					HttpResponse response = client.execute(post);							
					//System.out.println(response.getStatusLine().getStatusCode());
					HttpEntity e = response.getEntity();
					//System.out.println(EntityUtils.toString(e));
					if (200 == response.getStatusLine().getStatusCode()) {
						//System.out.println("�ϴ����");
						Looper.prepare();
						Toast.makeText(UserInfo.this, EntityUtils.toString(e).trim(), 2).show();
						Looper.loop();
					} else {
						//System.out.println("�ϴ�ʧ��");
						Looper.prepare();
						Toast.makeText(UserInfo.this, "�ϴ�ʧ��", 2).show();
						Looper.loop();
					}
					client.getConnectionManager().shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	@SuppressWarnings("deprecation")
	protected String getAbsoluteImagePath(Uri uri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
