package com.example.androidjsoup;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String s,title;
	ArrayList<String> list = new ArrayList<String>();
	private Button rellay;
	private EditText listview;
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				Elements e = (Elements) msg.obj;

					Elements elelms = e.get(0).getElementsByTag("a"); //得到标识符为a 的所有元素
					System.out.println(elelms); //打印所有的元素
					for (int j = 0; j < elelms.size(); j++) {
						s = elelms.get(j).attr("href");
						title = elelms.get(j).attr("title");
						System.out.println(s);
						list.add(s+"--");

						// listview.setText(s.toString());
				

				
				}
				for (String element : list) {
					listview.append(element.toString());
				
				}
				break;

			default:
				break;
			}
		};
	};;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		rellay = (Button) findViewById(R.id.button);

		listview = (EditText) findViewById(R.id.listview);

		rellay.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "ds", 1).show();
				new Thread() {
					public void run() {

						try {
							Document doc = Jsoup
									.connect("http://www.tupianzj.com/meinv/")
									.userAgent(
											"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
									.timeout(6000).get();
							Elements ele = doc.select("div.yq_con");
							Message MSG = new Message();
							MSG.obj = ele;
							MSG.what = 100;
							myHandler.sendMessage(MSG);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					};
				}.start();
			}
		});
		
		// listview.setAdapter(new SimpleAdapter(getApplication(), data,
		// resource, from, to))
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
