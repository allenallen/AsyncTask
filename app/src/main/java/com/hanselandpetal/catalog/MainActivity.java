package com.hanselandpetal.catalog;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

	TextView output;
	ProgressBar pb;
	List<MyTask> taskList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Initialize the TextView for vertical scrolling
		output = (TextView) findViewById(R.id.textView);
		output.setMovementMethod(new ScrollingMovementMethod());

		pb = (ProgressBar) findViewById(R.id.progressBar);
		pb.setVisibility(View.INVISIBLE);

		taskList = new ArrayList<MyTask>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_do_task) {
			if(isOnline()) {
				requestData("http://wsj.com/mdc/public/page/9_3024-AsianStocks_MANILA.html");
			}
			else{
				Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
			}
		}

		if(item.getItemId() == R.id.action_clear){
			output.setText("");
		}
		return false;
	}

	private void requestData(String uri) {
		MyTask task = new MyTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
	}

	protected void updateDisplay(String message) {
		output.append(message + "\n");
	}

	protected boolean isOnline(){

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if(netInfo != null && netInfo.isConnectedOrConnecting()){
			return true;
		}

		return false;
	}

	private class MyTask extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			updateDisplay("Getting Closing Prices\n\n\n");

			if(taskList.size() == 0) {
				pb.setVisibility(View.VISIBLE);
			}
			taskList.add(this);
		}

		@Override
		protected String doInBackground(String... params) {
			ArrayList<StockModel> list = HttpManager.getData(params[0]);

			StringBuilder sb = new StringBuilder();

			for(StockModel item : list){
				if(item.getName() != null)
				sb.append(item.getName());
				sb.append(" -- ");
				sb.append(item.getClose());
				sb.append("\n");
			}

			return sb.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			updateDisplay(result);

			taskList.remove(this);
			if(taskList.size() == 0) {
				pb.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			updateDisplay(values[0]);
		}
	}
}