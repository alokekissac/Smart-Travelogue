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
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class User_add_interests extends Activity implements OnItemClickListener,JsonResponse {
	ListView lv1;
	Button b1;
	String [] type_id,type_name,title,lati,longi,des,val;
	public static String type_ids,lts,lgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_add_interests);
		lv1=(ListView)findViewById(R.id.lvhot);
		lv1.setOnItemClickListener(this);
		b1=(Button)findViewById(R.id.btint);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_view_interested.class));
			}
		});
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_add_interests.this;
        String q = "/view_places";
        q=q.replace(" ","%20");
        JR.execute(q);	
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.User_add_interests, menu);
//		return true;
//	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("view_places")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				
				 type_id=new String[ja1.length()];
				 type_name=new String[ja1.length()];
				 title=new String[ja1.length()];
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
			if(method.equalsIgnoreCase("user_add_interest"))
			{
				String status=jo.getString("status");
				Toast.makeText(getApplicationContext(),status, Toast.LENGTH_LONG).show();
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(),"Interested Place Added Successfully!", Toast.LENGTH_LONG).show();

					startActivity(new Intent(getApplicationContext(), User_add_interests.class));
				}
				else{
//					Toast.makeText(getApplicationContext(),"Interested Place Added Failed", Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "Already Added....",
							Toast.LENGTH_LONG).show();
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
		
		final CharSequence[] items = {"View Map","Add Interest","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_add_interests.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
            	
            	
            	  if (items[item].equals("View Map")) 
                  {
            		  String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+User_add_interests.lts+","+User_add_interests.lgs;
                      Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                      startActivity(in);
                  }

                if (items[item].equals("Add Interest")) 
                {
                	JsonReq JR=new JsonReq();
    		        JR.json_response=(JsonResponse) User_add_interests.this;
    		        String q = "/user_add_interest?loginid="+Login.logid+"&type_id="+User_add_interests.type_ids;
    		        q=q.replace(" ","%20");
    		        JR.execute(q);
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
		Intent b=new Intent(getApplicationContext(),Users_home.class);			
		startActivity(b);
	}

		
	

}
