package com.example.diary;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.sql.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
public class Note extends Activity {
	
	EditText note;
	Button save,cancel;
	final String path = Environment.getExternalStorageDirectory().toString()+"/Diarytemp/";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		
		final RelativeLayout parent = (RelativeLayout) inflater.inflate(R.layout.note, null);  
		
		setContentView(parent);
		
		note = (EditText)findViewById(R.id.editText);
		save = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.cancel);
		
		
		
		save.setOnClickListener(new OnClickListener() {
			final View saveDialog = getLayoutInflater().inflate(R.layout.save, null);
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				if(TextUtils.isEmpty(note.getText())){
					Toast.makeText(Note.this, "还没写东西呢", 2).show();
				}else{
						final String content = note.getText().toString();
						
						// 使用对话框供用户登录系统
						AlertDialog.Builder dialog = new AlertDialog.Builder(Note.this);
						dialog.setTitle("填写名称");
						//parent.removeViewInLayout(saveDialog);
						
						dialog.setView(saveDialog)
							.setPositiveButton("保存",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
									int which)
								{
									final String name = ((EditText)saveDialog
											.findViewById(R.id.notename)).getText()
											.toString();
									 try 
									  {
										 FileOutputStream outStream = new FileOutputStream(path+name+".txt",true);
										 OutputStreamWriter writer = new OutputStreamWriter(outStream,Charset.forName("unicode"));
										 writer.write(content);
										 writer.write("\n");
										 writer.flush();
										 writer.close();//记得关闭
	
										 outStream.close();
										 Toast.makeText(Note.this, "保存成功", 2).show();
										 
									  } 
									  catch (Exception e)
									  {
									   Toast.makeText(Note.this, e.getMessage(), 2).show();
									  } 
									
								}
							})
					.setNegativeButton("取消", null).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {
        	final View saveDialog = getLayoutInflater().inflate(R.layout.save, null);
        	
        	final String content = note.getText().toString();
			// 使用对话框供用户登录系统
        	if(!TextUtils.isEmpty(content)){
        		new AlertDialog.Builder(Note.this)
				.setTitle("是否保存？")
				.setView(saveDialog)
				.setPositiveButton("保存",
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog,
						int which)
					{
						final String name = ((EditText)saveDialog
								.findViewById(R.id.notename)).getText()
								.toString();
						 try 
						  {
							 FileOutputStream outStream = new FileOutputStream(path+name+".txt",true);
							 OutputStreamWriter writer = new OutputStreamWriter(outStream,Charset.forName("unicode"));
							 writer.write(content);
							 writer.write("\n");
							 writer.flush();
							 writer.close();//记得关闭

							 outStream.close();
							 Toast.makeText(Note.this, "保存成功", 2).show();
							 
						  } 
						  catch (Exception e)
						  {
						   Toast.makeText(Note.this, e.getMessage(), 2).show();
						  } 
						
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						finish();
					}
				}).show();
        	}else{
        		finish();
        	}
        }
        return false;
    }
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

}