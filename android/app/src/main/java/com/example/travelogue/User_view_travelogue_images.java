package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_view_travelogue_images extends Activity implements JsonResponse, OnItemClickListener {
	ListView lv1;
	String[] photo,upload_id;
	SharedPreferences sh;
	public static String hot_id ,upload_ids;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_travelogue_images);
		lv1 = (ListView) findViewById(R.id.lvtrav);
		lv1.setOnItemClickListener(this);

		JsonReq JR = new JsonReq();
		JR.json_response = (JsonResponse) User_view_travelogue_images.this;
		String q = "/user_view_trav_images?trav_id=" + User_manage_travelogue.trv_id;
		q = q.replace(" ", "%20");
		JR.execute(q);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.user_view_travelogue_images, menu);
//		return true;
//	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {

			String method = jo.getString("method");
			if (method.equalsIgnoreCase("user_view_trav_images")) {
				String status = jo.getString("status");
				Log.d("pearl", status);
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
				if (status.equalsIgnoreCase("success")) {

					JSONArray ja1 = (JSONArray) jo.getJSONArray("data");


					photo = new String[ja1.length()];
					upload_id = new String[ja1.length()];



					for (int i = 0; i < ja1.length(); i++) {


						photo[i] = ja1.getJSONObject(i).getString("file_path");
						upload_id[i] = ja1.getJSONObject(i).getString("upload_id");



					}
					Custimage clist = new Custimage(this, photo);
					lv1.setAdapter(clist);


				} else {
					Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

				}
			}
//			if(method.equalsIgnoreCase("buyprod"))
//			{
//				String status=jo.getString("status");
//				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
//				if(status.equalsIgnoreCase("success"))
//				{
//					Toast.makeText(getApplicationContext(),"Your order is submitted!", Toast.LENGTH_LONG).show();
//				}
//				else{
//					Toast.makeText(getApplicationContext(),"Your order is not submitted", Toast.LENGTH_LONG).show();
//				}
//			}
		} catch (Exception e) {
			// TODO: handle exception

			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}


	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		//complaint_ids = complaint_id[i];
		//	SharedPreferences.Editor e=sh.edit();
//		e.putString("complaint_ids",complaint_ids);
		//e.commit();

		// complaint_ids=complaint_id;
		upload_ids = upload_id[i];

		final CharSequence[] items = {"update" ,"close"};

		AlertDialog.Builder builder = new AlertDialog.Builder(User_view_travelogue_images.this);
		builder.setTitle("Select Option!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {


				if (items[item].equals("update"))
				{
//					JsonReq JR=new JsonReq();
//					JR.json_response=(JsonResponse)User_view_travelogue_images.this;
//					String q = "/update";
//					q=q.replace(" ","%20");
//					JR.execute(q);
					startActivity(new Intent(getApplicationContext(),User_update_images_files.class));

				}
				if (items[item].equals("close")) {
					dialog.dismiss();
				}
			}

		});
		builder.show();
	}


	@Override

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b = new Intent(getApplicationContext(), User_manage_travelogue.class);
		startActivity(b);
	}

}
