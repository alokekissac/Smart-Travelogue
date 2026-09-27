package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;
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

public class User_view_hotels extends Activity implements OnItemClickListener,JsonResponse {
	ListView lv1;
	String [] hotel_id,hotel_name,about,lati,longi,phone,email,photo,place,val;
	public static String hot_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_view_hotels);
		lv1=(ListView)findViewById(R.id.lvhot);
		lv1.setOnItemClickListener(this);
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_view_hotels.this;
        String q = "/view_hotels";
        q=q.replace(" ","%20");
        JR.execute(q);	
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.user_view_hotels, menu);
//		return true;
//	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("view_hotels")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				
				 hotel_id=new String[ja1.length()];
				 hotel_name=new String[ja1.length()];
				 about=new String[ja1.length()];
				 lati=new String[ja1.length()];
				 longi=new String[ja1.length()];
				 phone=new String[ja1.length()];
				 email=new String[ja1.length()];
				 photo=new String[ja1.length()];
				 place=new String[ja1.length()];

				
				 val=new String[ja1.length()];
		     
		    
			     
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					
					hotel_id[i]=ja1.getJSONObject(i).getString("hotel_id");
					hotel_name[i]=ja1.getJSONObject(i).getString("hotel_name");
					about[i]=ja1.getJSONObject(i).getString("about");
					lati[i]=ja1.getJSONObject(i).getString("latitude");
					longi[i]=ja1.getJSONObject(i).getString("longitude");
					phone[i]=ja1.getJSONObject(i).getString("phone");
					email[i]=ja1.getJSONObject(i).getString("email");
					photo[i]=ja1.getJSONObject(i).getString("photo");
					place[i]=ja1.getJSONObject(i).getString("place");
					
				
					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					val[i]="Hotel Name: "+hotel_name[i]+"\nAbout:  "+about[i]+"\nLatitude:  "+lati[i]+"\nLongitude:  "+longi[i]+"\nPhone:  "+phone[i]+"\nEmail:  "+email[i]+"\nPhoto:  "+photo[i]+"\nPlace:  "+place[i];
					
				
				}
				ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		hot_id=hotel_id[arg2];
		
		final CharSequence[] items = {"View Images","View Packages","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_view_hotels.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
            	if (items[item].equals("View Images")) 
                {
                	startActivity(new Intent(getApplicationContext(),User_view_hotel_images.class));
                }
            	else if (items[item].equals("View Packages")) 
                {
                	startActivity(new Intent(getApplicationContext(),User_view_packages.class));
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
