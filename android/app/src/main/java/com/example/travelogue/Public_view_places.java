package com.example.travelogue;

import org.json.JSONArray;
import org.json.JSONObject;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Public_view_places extends Activity implements JsonResponse,OnItemClickListener {
	ListView lv1;
	String [] place_id,type_name,title,des,lt,lg,val;
	public static String pl_id,lts,lgs;
	EditText e1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_view_places);
		
		lv1=(ListView)findViewById(R.id.lvplace);
		lv1.setOnItemClickListener(this);
		e1=(EditText)findViewById(R.id.editText1);
		
		startService(new Intent(getApplicationContext(),LocationService.class));
		
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) Public_view_places.this;
        String q = "/public_view_place";
        q=q.replace(" ","%20");
        JR.execute(q);	
        
        e1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String s=e1.getText().toString();
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) Public_view_places.this;
		        String q = "/public_view_places?val="+s;
		        q=q.replace(" ","%20");
		        JR.execute(q);
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.public_view_places, menu);
//		return true;
//	}

	

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
try {
			
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("public_view_place")){
			String status=jo.getString("status");
			Log.d("pearl",status);
			Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
			if(status.equalsIgnoreCase("success")){
			
				JSONArray ja1=(JSONArray)jo.getJSONArray("data");
				place_id=new String[ja1.length()];
				type_name=new String[ja1.length()];
				 title=new String[ja1.length()];
				 des=new String[ja1.length()];
				 lt=new String[ja1.length()];
				 lg=new String[ja1.length()];

				 val=new String[ja1.length()];
		      
				for(int i = 0;i<ja1.length();i++)
				{ 
					
					place_id[i]=ja1.getJSONObject(i).getString("place_id");
					type_name[i]=ja1.getJSONObject(i).getString("type_name");
					title[i]=ja1.getJSONObject(i).getString("title");
					des[i]=ja1.getJSONObject(i).getString("description");
					lt[i]=ja1.getJSONObject(i).getString("latitude");
					lg[i]=ja1.getJSONObject(i).getString("longitude");
				
					Toast.makeText(getApplicationContext(),val[i], Toast.LENGTH_SHORT).show();
					val[i]="Place Name : "+type_name[i]+" - "+title[i]+"\nDescription : "+des[i];
					
				
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
		pl_id=place_id[arg2];
		lts=lt[arg2];
		lgs=lg[arg2];
		
		final CharSequence[] items = {"View Map","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Public_view_places.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("View Map")) 
                {
                	String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+Public_view_places.lts+","+Public_view_places.lgs;
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
		Intent b=new Intent(getApplicationContext(),Login.class);			
		startActivity(b);
	}

		
	

}
