package com.erichcli.testplayer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MyActivity extends Activity {

    private static final String TAG = MyActivity.class.getName();

    private static Uri mVideoUri;

    private final static int CHOOSE_VIDEO_FILE  = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }

    public void onClickChooseFile(View view){
        // 建立 "選擇檔案 Action" 的 Intent
        Intent intent = new Intent( Intent.ACTION_PICK );

        // 過濾檔案格式
        intent.setType( "video/*" );

        // 建立 "檔案選擇器" 的 Intent  (第二個參數: 選擇器的標題)
        Intent destIntent = Intent.createChooser( intent, "Select Video " );

        // 切換到檔案選擇器 (它的處理結果, 會觸發 onActivityResult 事件)
        startActivityForResult(destIntent, CHOOSE_VIDEO_FILE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_VIDEO_FILE && resultCode == RESULT_OK){
                Uri uri = data.getData();
                //Toast.makeText(MyActivity.this, "Video location:\n" + data.getData(), Toast.LENGTH_LONG).show();
                if( uri != null ){
                    mVideoUri = uri;

                    String name = String.valueOf(mVideoUri);

                    Intent intent = new Intent();
                    intent.setClass(MyActivity.this,VideoDisplay.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("video_address", String.valueOf(mVideoUri));

                    //Toast.makeText(MyActivity.this, name,Toast.LENGTH_SHORT).show();

                    // 把bundle物件指派給Intent
                    intent.putExtras(bundle);

                    // Activity (ActivityMenu)
                    startActivity(intent);

                }else{
                    setTitle("Error Address");
                }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onPause();
    }

}
