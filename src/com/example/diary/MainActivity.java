package com.example.diary;

import java.io.File;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
	static boolean islogin=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.activity_main);
		
		File destDir = new File(Environment.getExternalStorageDirectory().toString()+"/Diarytemp");
		if (!destDir.exists()) {
			  destDir.mkdirs();
			  Toast.makeText(this, "success", 2).show();
		}else{
				
		}
		
		calendar = (ImageButton)findViewById(R.id.calendar);
		photo = (ImageButton)findViewById(R.id.photo);
		note = (ImageButton)findViewById(R.id.note);
		
		login = (Button)findViewById(R.id.login);
		register = (Button)findViewById(R.id.register);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
			}
		});
		calendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
			}
		});
		photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
			}
		});
		note.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(MainActivity.this,Note.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflator = new MenuInflater(this);
		// 状态R.menu.context对应的菜单，并添加到menu中
		inflator.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	// 选项菜单的菜单项被单击后的回调方法
	public boolean onOptionsItemSelected(MenuItem mi){
		if(mi.isCheckable()){
			mi.setChecked(true);  //②
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
			Toast.makeText(this, "您尚未登录", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		
		
	}
}
