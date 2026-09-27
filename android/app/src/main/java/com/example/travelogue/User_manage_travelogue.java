package com.example.travelogue;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class User_manage_travelogue extends Activity implements JsonResponse,OnItemClickListener
{
	Button b1,b2;
	EditText e1,e2,e3;
	ListView l1;
	public static String place_name,title,des,trv_id;
	public static String[] travelogue_id,pl_name,titl,desc,date_tm,value;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_manage_travelogue);
		
		e1=(EditText)findViewById(R.id.etplace);
		e2=(EditText)findViewById(R.id.ettitle);
		e3=(EditText)findViewById(R.id.etdes);
		
		l1=(ListView)findViewById(R.id.lvtravel);
		l1.setOnItemClickListener(this);
		
		b1=(Button)findViewById(R.id.bttravel);
		b2=(Button)findViewById(R.id.btviewtravel);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),User_view_suggestions.class));
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				place_name=e1.getText().toString();
				title=e2.getText().toString();
				des=e3.getText().toString();
				if(place_name.equalsIgnoreCase(""))
				{
				  e1.setError("No value for place name");
				  e1.setFocusable(true);
				}
				else if(title.equalsIgnoreCase(""))
				{
				  e2.setError("No value for title");
				  e2.setFocusable(true);
				}
				else if(des.equalsIgnoreCase(""))
				{
				  e3.setError("No value for description");
				  e3.setFocusable(true);
				}
				else{
				JsonReq JR=new JsonReq();
		        JR.json_response=(JsonResponse) User_manage_travelogue.this;
		        String q = "/user_add_travelogue?loginid="+Login.logid+"&p_name="+place_name+"&title="+title+"&des="+des;
		        q=q.replace(" ","%20");
		        JR.execute(q);
				}
			}
		});
		JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse) User_manage_travelogue.this;
        String q = "/user_view_travelogue?loginid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("user_add_travelogue")){
				String status=jo.getString("status");
				Log.d("pearl",status);
				//Toast.makeText(getApplicationContext(),status, Toast.LENGTH_SHORT).show();
				if(status.equalsIgnoreCase("success")){
				
					Toast.makeText(getApplicationContext(), " ADD", Toast.LENGTH_LONG).show();
				    startActivity(new Intent(getApplicationContext(),User_manage_travelogue.class));
			}
				else
				{
					Toast.makeText(getApplicationContext(), "Something went wrong!Try Again.", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),Users_home.class));
				}
			}
			if(method.equalsIgnoreCase("user_view_travelogue")){
				String status=jo.getString("status");
				Log.d("pearl",status);
				
				
				if(status.equalsIgnoreCase("success")){
					JSONArray ja1=(JSONArray)jo.getJSONArray("data");
					//feedback_id=new String[ja1.length()];
					travelogue_id=new String[ja1.length()];
					 pl_name=new String[ja1.length()];
					 titl=new String[ja1.length()];
					 desc=new String[ja1.length()];
					 date_tm=new String[ja1.length()];
					 value=new String[ja1.length()];
					  
						for(int i = 0;i<ja1.length();i++)
						{ 
							//feedback_id[i]=ja1.getJSONObject(i).getString("feedback_id");
							travelogue_id[i]=ja1.getJSONObject(i).getString("travelogue_id");
							pl_name[i]=ja1.getJSONObject(i).getString("place_name");
							titl[i]=ja1.getJSONObject(i).getString("title");
							desc[i]=ja1.getJSONObject(i).getString("description");
							date_tm[i]=ja1.getJSONObject(i).getString("date_time");
							value[i]="Place Name:  "+pl_name[i]+"\nTitle:  "+titl[i]+"\nDescription:  "+desc[i]+"\nDate:  "+date_tm[i];
							
						
						}
						ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
						l1.setAdapter(ar);
						//startActivity(new Intent(getApplicationContext(),User_Post_Disease.class));	 
				}
				
				else
					
				{    				 
					Toast.makeText(getApplicationContext(), "No Travelogue!!", Toast.LENGTH_LONG).show();
					
				}
			}
				
		}catch(Exception e)
		{  
		   Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		trv_id=travelogue_id[arg2];
		Toast.makeText(getApplicationContext(), trv_id, Toast.LENGTH_LONG).show();
		
		final CharSequence[] items = {"Add File","View Images","View Videos","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(User_manage_travelogue.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Add File")) 
                {
                	startActivity(new Intent(getApplicationContext(),User_upload_files.class));
                }
                else if (items[item].equals("View Images")) 
                {
                	startActivity(new Intent(getApplicationContext(),User_view_travelogue_images.class));
                }
                else if (items[item].equals("View Videos")) 
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
		Intent b=new Intent(getApplicationContext(),Users_home.class);			
		startActivity(b);
	}



}
