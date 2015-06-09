package com.example.diary;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
public class Note extends Activity {
	
	EditText note;
	Button save,cancel;
	final String path = Environment.getExternalStorageDirectory().toString()+"/Diarytemp/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.note);
		note = (EditText)findViewById(R.id.editText);
		save = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.cancel);
		
		save.setOnClickListener(new OnClickListener() {
			final View saveDialog = getLayoutInflater().inflate(R.layout.save, null);
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if(TextUtils.isEmpty(note.getText())){
					Toast.makeText(Note.this, "��ûд������", 2).show();
				}else{
						final String content = note.getText().toString();
						// ʹ�öԻ����û���¼ϵͳ
						new AlertDialog.Builder(Note.this)
							.setTitle("��д����")
							.setView(saveDialog)
							.setPositiveButton("����",
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
										 writer.close();//�ǵùر�
	
										 outStream.close();
										 Toast.makeText(Note.this, "����ɹ�", 2).show();
									  } 
									  catch (Exception e)
									  {
									   Toast.makeText(Note.this, e.getMessage(), 2).show();
									  } 
									
								}
							}).setNegativeButton("ȡ��", null).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				finish();
			}
		});
	}
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

}