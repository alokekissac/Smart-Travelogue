package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_view_packages extends Activity implements JsonResponse {
	ListView lv1;
	String [] package_id,title,des,val,amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_packages);
		
		lv1=(ListView)findViewById(R.id.lvpack);
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_packages.this;
        String q = "/user_view_packages?hot_id="+User_view_hotels.hot_id;
        q=q.replace(" ","%20");
        JR.execute(q);	
	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.user_view_packages, menu);
//		return true;
//	}

	

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("user_view_packages")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				 package_id=new String[ja1.length()];
				 title=new String[ja1.length()];
				 des=new String[ja1.length()];
				 amount=new String[ja1.length()];

				
				 val=new String[ja1.length()];
		     
		    
			     
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					package_id[i]=ja1.getJSONObject(i).getString("package_id");
					title[i]=ja1.getJSONObject(i).getString("title");
					des[i]=ja1.getJSONObject(i).getString("description");
					amount[i]=ja1.getJSONObject(i).getString("amount");
					
				
					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					val[i]="Package"+title[i]+"\nDescription"+des[i]+"\nAmount"+amount[i];
					
				
				}
				ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
				lv1.setAdapter(ar);
			
		      
		       
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
