package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.example.travelegg.R.id;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Videos extends Activity implements OnItemClickListener,JsonResponse {

	ListView lv1;
	String [] photo;
	public static String hot_id;
	String[] videoid,childnamee,videonamee,detail;

	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videos);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	
		
		lv1=(ListView)findViewById(R.id.ltvideo);
		lv1.setOnItemClickListener(this);
		
		if(sh.getString("val", "").equalsIgnoreCase("public"))
		{
		// TODO Auto-generated method stub
			JsonReq JR=new JsonReq();
	        JR.json_response=(JsonResponse) Videos.this;
	        String q = "/public_view_videos?trav_id="+Public_view_travelogue.trvl_id;
	        q=q.replace(" ","%20");
	        JR.execute(q);	
		}
		else if(sh.getString("val", "").equalsIgnoreCase("user"))
		{
			JsonReq JR=new JsonReq();
	        JR.json_response=(JsonResponse) Videos.this;
	        String q = "/public_view_videos?trav_id="+User_manage_travelogue.trv_id;
	        q=q.replace(" ","%20");
	        JR.execute(q);	
		}
		
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent in = new Intent(getApplicationContext(), VideoPlay.class);
		in.putExtra("vdo", videonamee[arg2]);
		startActivity(in);
	}
	

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("public_view_videos")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				
			
				videonamee=new String[ja1.length()];

				
			     
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					
					
					videonamee[i]=ja1.getJSONObject(i).getString("file_path");
					
					
				
				}
//				Custimage clist=new Custimage(this,photo);
//				 lv1.setAdapter(clist);
				
				ArrayAdapter<String> ar= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,videonamee);
				lv1.setAdapter(ar);
		      
		       
			}
			
			else {
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
			}catch (Exception e)
			{
			// TODO: handle exception

			  Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
			}
		
		
		
	}

	@Override

	public void onBackPressed() 
	{
		
		if(sh.getString("val", "").equalsIgnoreCase("public"))
		{
		// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),Public_view_travelogue.class);			
			startActivity(b);
		}
		else if(sh.getString("val", "").equalsIgnoreCase("user"))
		{
		// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),User_manage_travelogue.class);			
			startActivity(b);
		}
	}

}
