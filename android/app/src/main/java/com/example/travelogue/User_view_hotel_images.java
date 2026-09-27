package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class User_view_hotel_images extends Activity implements JsonResponse {
	
	ImageView imv;
	String wpic;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_hotel_images);
		
		
		imv=(ImageView)findViewById(R.id.imgv1);
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_hotel_images.this;
        String q = "/user_view_hotel_image?hot_id="+User_view_hotels.hot_id;
        q=q.replace(" ","%20");
        JR.execute(q);	
		
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.user_view_hotel_images, menu);
//		return true;
//	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try{
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("user_view_hotel_image")){
		String status=jo.getString("status");
		Log.d("pearl",status);
		Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
		if(status.equalsIgnoreCase("success")){
		
			JSONArray ja1=(JSONArray)jo.getJSONArray("data");
			
			
			 
			wpic=ja1.getJSONObject(0).getString("photo");
			
			String pth = "http://"+IPSetting.ip+"/"+wpic;
		       pth = pth.replace("~", "");
		        
		        Log.d("-------------", pth);
		        Picasso.with(getApplicationContext())
		                .load(pth)
		                .placeholder(R.drawable.ic_launcher_background)
		                .error(R.drawable.ic_launcher_background).into(imv);
		
	       
		}
		
		else {
			Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_LONG).show();

			} 
		}
		
		
		}catch (Exception e)
		{
		// TODO: handle exception

		  Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
		}
	
	
		
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),User_view_hotels.class);			
		startActivity(b);
	}

}
