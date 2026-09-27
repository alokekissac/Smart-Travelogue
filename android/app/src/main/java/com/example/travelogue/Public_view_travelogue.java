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

public class Public_view_travelogue extends Activity implements JsonResponse,OnItemClickListener {
	ListView lv1;
	String [] travelogue_id,place_name,title,des,date_tm,val;
	public static String trvl_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_view_travelogue);
		
		lv1=(ListView)findViewById(R.id.lvtravel);
		lv1.setOnItemClickListener(this);
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Public_view_travelogue.this;
        String q = "/public_view_travelogues";
        q=q.replace(" ","%20");
        JR.execute(q);	
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.public_view_travelogue, menu);
//		return true;
//	}

	

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("public_view_travelogues")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				travelogue_id=new String[ja1.length()];
				place_name=new String[ja1.length()];
				 title=new String[ja1.length()];
				 des=new String[ja1.length()];
				 date_tm=new String[ja1.length()];
				 val=new String[ja1.length()];
		      
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					travelogue_id[i]=ja1.getJSONObject(i).getString("travelogue_id");
					place_name[i]=ja1.getJSONObject(i).getString("place_name");
					title[i]=ja1.getJSONObject(i).getString("title");
					des[i]=ja1.getJSONObject(i).getString("description");
					date_tm[i]=ja1.getJSONObject(i).getString("date_time");
					
					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					val[i]="Place Name : "+place_name[i]+" - "+title[i]+"\nDescription : "+des[i]+"\nDATE : "+date_tm[i];
					
				
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
		trvl_id=travelogue_id[arg2];
		
		final CharSequence[] items = {"View Media","View Videos","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Public_view_travelogue.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Media")) 
                {
                	startActivity(new Intent(getApplicationContext(),Public_view_media.class));
                }
                if (items[item].equals("View Videos")) 
                {
                	startActivity(new Intent(getApplicationContext(),Videos.class));
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
		Intent b=new Intent(getApplicationContext(),Login.class);			
		startActivity(b);
	}

		
	

}
