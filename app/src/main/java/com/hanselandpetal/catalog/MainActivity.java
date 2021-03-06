package com.hanselandpetal.catalog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ListActivity {

	ProgressBar pb;
	List<MyTask> taskList;
	private List<StockModel> stockList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Initialize the TextView for vertical scrolling
		//output.setMovementMethod(new ScrollingMovementMethod());

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
				requestData("http://phisix-api4.appspot.com/stocks.json");
			}
			else{
				Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
			}
		}

		if(item.getItemId() == R.id.action_clear){
			//output.setText("");
		}


		return false;
	}

	private void requestData(String uri) {
		MyTask task = new MyTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
	}

	protected void updateDisplay() {
		StockAdapter adapter = new StockAdapter(this,R.layout.list_layout,stockList);

		setListAdapter(adapter);
		//output.append(message + "\n");
	}

	protected boolean isOnline(){

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if(netInfo != null && netInfo.isConnectedOrConnecting()){
			return true;
		}

		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		StockModel t = (StockModel) this.getListAdapter().getItem(position);
//		Toast.makeText(this,t.getName(),Toast.LENGTH_SHORT).show();

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(t.getName());
		alert.setMessage("Current price: " + t.getPrice() + "\n"
						+ "Percent Change: " + t.getPctChange());
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		alert.create().show();
	}

	private class MyTask extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			//updateDisplay();

			if(taskList.size() == 0) {
				pb.setVisibility(View.VISIBLE);
			}
			taskList.add(this);
		}

		@Override
		protected String doInBackground(String... params) {
			String toParse = HttpManager.getData(params[0]);



			return toParse;
		}

		@Override
		protected void onPostExecute(String result) {
			taskList.remove(this);
			if(taskList.size() == 0) {
				pb.setVisibility(View.INVISIBLE);
			}
			stockList = Parser.parseHtmlString(result);
			updateDisplay();


		}

	}
}