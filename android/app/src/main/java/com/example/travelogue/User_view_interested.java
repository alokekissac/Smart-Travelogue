package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
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

public class User_view_interested extends Activity implements JsonResponse,OnItemClickListener {
	ListView lv1;
	String [] lg_id,type_id,type_name,title,lati,longi,des,val;
	public static String type_ids,lts,lgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_interested);
		
		lv1=(ListView)findViewById(R.id.lvint);
		lv1.setOnItemClickListener(this);
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_interested.this;
        String q = "/user_view_interest?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);	
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.user_view_interested, menu);
//		return true;
//	}

	

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("user_view_interest")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				 type_id=new String[ja1.length()];
				 title=new String[ja1.length()];
				 type_name=new String[ja1.length()];
				 lati=new String[ja1.length()];
				 longi=new String[ja1.length()];
				 des=new String[ja1.length()];
				 
				 val=new String[ja1.length()];
	
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					type_id[i]=ja1.getJSONObject(i).getString("type_id");
					type_name[i]=ja1.getJSONObject(i).getString("type_name");
					title[i]=ja1.getJSONObject(i).getString("title");
					lati[i]=ja1.getJSONObject(i).getString("latitude");
					longi[i]=ja1.getJSONObject(i).getString("longitude");
					des[i]=ja1.getJSONObject(i).getString("description");
					
				
					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					val[i]="Place Name : "+type_name[i]+" - "+title[i]+"\nLatitude:  "+lati[i]+"\nLongitude: "+longi[i]+"\nDescription:  "+des[i];
					
				
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
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		type_ids=type_id[arg2];
		lts=lati[arg2];
		lgs=longi[arg2];
		
		final CharSequence[] items = {"View Map","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_interested.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Map")) 
                {
                	String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+User_view_interested.lts+","+User_view_interested.lgs;
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                }
                   
              else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        
    });
    builder.show();
//	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	//startActivityForResult(i, GALLERY_CODE);
    }
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),User_add_interests.class);			
		startActivity(b);
	}

	

}
