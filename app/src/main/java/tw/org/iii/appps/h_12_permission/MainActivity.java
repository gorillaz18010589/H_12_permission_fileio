package tw.org.iii.appps.h_12_permission;
//行動資料上的資料存取,這些檔案權限紀錄,是跟著app共存亡的,但照片不是放在data李,他是放在相簿裡
//使用者有時突然打電動,總不能突然有音樂吧,所以要記得使用者權限
//1.取得環境設定物件叫出來
//2.output資料=>data=>這個app資料底下的xml黨
//2_1.到手機=>應用程式=>app=>清除資料發現寫出的xml黨不見了:這就是清楚資料紀錄
//3.資料讀取data檔案夾
//4.io檔案輸出在file底下檔名叫file.txt
//5.iofile資料讀取
//6.外部檔案:不是亂放的,等一下要先找到可以放置的地方,如果容量不夠會加裝sd card這種東西,mnt/sdcard
//6-1.相片通常在dcim底下
//6-2.android底下很特殊
//6-3.照片等因為是私人隱私所以要有權限,這個權限將會在,你寫上去你可以用,而你的使用者要下載app時就會看到這些權限
//<uses-permission android:name="android.permission.INTERNET"/>網際網路權限
// <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>GPS精確位置權限
// <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>Gps粗略位置權限
// <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>藍芽組態設定權限
// <uses-permission android:name="android.permission.BLUETOOTH"/>藍芽功能權限
// <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>讀取外部存取檔案權限(sdcard)
//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>寫出外部存取檔案權限
// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>網路狀態權限
//<uses-feature android:name=""/>宣示你要有那些功能才可以玩這個app
//android material design git hub :可以找到別人設計好的元件
//7.還要加上android developer permission,針對特定的權限,你的app要主動詢問用戶SecurityException危險權限

//要再詢問告知使用者的權限
//<uses-permission android:name="android.permission.READ_CALENDAR"/>讀行事曆權限
// <uses-permission android:name="android.permission.WRITE_CALENDAR"/>寫行事曆權限
//
// <uses-permission android:name="android.permission.CAMERA"/>相機權限

//  <uses-permission android:name="android.permission.READ_CONTACTS"/>讀取聯絡人權限
//  <uses-permission android:name="android.permission.WRITE_CONTACTS"/>寫出聯絡人權限
//  <uses-permission android:name="android.permission.GET_ACCOUNTS"/>取得帳號權限

// <uses-permission android:name="android.permission.RECORD_AUDIO"/>錄音權限

// <uses-permission android:name="android.permission.READ_PHONE_STATE"/>讀取電話狀態權限
// <uses-permission android:name="android.permission.CALL_PHONE"/>打電話權限
// <uses-permission android:name="android.permission.READ_CALL_LOG"/>讀取來電紀錄權限
// <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>寫出來電紀錄權限
// <uses-permission android:name="android.permission.ADD_VOICEMAIL"/>語音訊息權限
// <uses-permission android:name="android.permission.USE_SIP"/>語音權限相關
//  <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>處理外撥權限

//<uses-permission android:name="android.permission.BODY_SENSORS"/>心律身體互動權限

//8.寫出檔案到approot路徑底下,發現sdcard/Android/data,的資料刪除就不見,這是專屬於你內存app的外部空間,app清到連檔案都沒
//9.sdcard的檔案就算清除資料檔案仍在
//1.  compile 'info.hoang8f:fbutton:1.0.5'加進專案導入別人包
//2.在xml加上xmlns:fbutton="http://schemas.android.com/apk/res-auto",讓按鈕可以套用
//3.直接打fb就出現他的view按鈕,套用他的範例
//4.在valuse=>底下的color加上它寫好的顏色

//private SharedPreferences sp ; //分享環境設定物件(寫出)
//private SharedPreferences.Editor editor; //更改環境設定值物件(寫入)

//sp =getSharedPreferences("Config",MODE_PRIVATE);//取得環境參數物件(1.name:"檔案名",2.MODE_PRIVATE"私有模式")
// editor = sp.edit(); //從上面的環境設定物件,取得而來,這個是更改物件值的物件,跟資料有關都用它處理

//SharedPreferences.Editor putString(java.lang.String s, @android.annotation.Nullable java.lang.String s1)://寫出設定姓名(1.name,2.String_value)
//SharedPreferences.getInt(java.lang.String s, int i)://讀取int型態資料設定(1"要讀的檔案名",2.int預設值-1代表沒讀到)(回傳值int)
// openFileOutput(String name, int mode)://打開輸出串流(1."String自訂檔案名稱",2."MODE狀態為私有模式")(回傳值:FileOutputStream )
// openFileInput(String name)://取得io讀取檔案串流("String 檔案名")(回傳值FileInputStream)

//checkSelfPermission(@NonNull Context context, @NonNull String permission)://檢查權限傳回true,flase(1.哪個Context,2.要問的權限)
//requestPermissions(final @NonNull Activity activity,//1.這個this content
//            final @NonNull String[] permissions, final @IntRange(from = 0)//2.要求的權限
//            int requestCode) //3.權限要得如何回應code
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp ; //分享環境設定物件(寫出)
    private SharedPreferences.Editor editor; //更改環境設定值物件(寫入)
    private  File sdcard ,rootapp;//sdcard路徑物件實體,跟rootapp檔案位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //7.如果這個""權限,沒有被授權,那麼去要授權,並且回傳授權參數,給onRequestPermissionsResult處理
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) //這個要求權限
                != PackageManager.PERMISSION_GRANTED) {//PERMISSION_GRANTED:權限授權
            ActivityCompat.requestPermissions(this,//1.這個this content
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},//2.要求的權限擺在陣列可以要求多個權限
                    87);//權限要得如何回應code
        }else{//有授權的話正常處理
            init();//初始化設定
        }
    }

    //8.權限授權回呼結果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //如果權限的會應有一質問,而且權限的第0個權限有等於授權,那就執行
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            Log.v("brad","有取得授權初始化成功" + permissions.toString());
        } else {//如果沒得到授權
            Log.v("brad","沒取得授權關閉中");
          finish();//結束關閉
        }
    }

    //6.初始化取得外部檔環境
       private  void init(){
//        Environment.getExternalStorageState();//從環境.取得外部儲存狀態(sdcard)
//        Environment.getDataDirectory();//取得data資料夾
      //1.取得環境設定物件叫出來
           sp =getSharedPreferences("Config",MODE_PRIVATE);//取得環境參數物件(1.name:"檔案名",2.MODE_PRIVATE"私有模式")
           editor = sp.edit(); //從上面的環境設定物件,取得而來,這個是更改物件值的物件,跟資料有關都用它處理
            sdcard = Environment.getExternalStorageDirectory();//從環境底下.取得外部儲存資料夾路徑(回傳到file)
           Log.v("brad","SdCrad:" + sdcard); //顯示目錄為:/storage/emulated/0

      //8.故意創一個檔名跟android/data底下一樣的目錄
            rootapp = new File(sdcard,"Android/data/"+ getPackageName());
           if(!rootapp.exists()){
               rootapp.mkdirs();
               Log.v("brad","成功創建rootapp在Android/data底下檔案名跟他一樣");
           }
       }

    //2.output資料=>data=>這個app資料底下的xml黨,寫出app預設資料
    public void test1(View view) {
        editor.putString("name","brad"); //寫出設定姓名(1.name,2.String_value)
        editor.putInt("stage", 4); //寫出設定關卡(1.name 2,int value )
        editor.putBoolean("Sound",true);//寫出設定音效(1.name,2.bollean值(開/關))
        editor.commit(); //commit把資料抒發出去
        Toast.makeText(this,"edtorOK",Toast.LENGTH_SHORT).show();
    }

    //3.data資料讀取
    public void test2(View view) {
       int stage = sp.getInt("stage",-1);//讀取int型態資料設定(1"要讀的檔案名",2.int預設值-1代表沒讀到)
       String name = sp.getString("name","nobody");//讀取String型態資料設定(1"要讀的檔案名",2.String型別沒讀到的預設名字)
       boolean Sound = sp.getBoolean("Sound",false);//讀取bollean型態資料設定(1"要讀的檔案名",2.false沒讀到的話出現false預設)
       Log.v("brad","stage:" + stage + "name:" + name +"Sound:" +Sound);//回傳讀入預設資料的狀態
    }
    //4.io檔案輸出在file底下檔名叫file.txt
    // openFileOutput(String name, int mode)://打開輸出串流(1."String自訂檔案名稱",2."MODE狀態為私有模式")(回傳值:FileOutputStream )
    public void test3(View view) {
        try {
            FileOutputStream fout = openFileOutput("file.txt",MODE_PRIVATE);//打開輸出串流(1."String自訂檔案名稱",2."MODE狀態為私有模式"),在傳回 FileOutputStream
            fout.write("hello world".getBytes()); //寫出資料,轉成byte資料
            fout.flush();
            fout.close();
            Toast.makeText(this,"openFileOutput資料寫出成功",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.v("brad","io輸出到file檔錯誤" +e.toString());
        }

    }

    //5.io資料讀取
    // openFileInput(String name)://取得io讀取檔案串流("String 檔案名")(回傳值FileInputStream)
    public void test4(View view) {
        try {
            FileInputStream fin = openFileInput("file.txt");//取得io讀取檔案串流
            int len;  byte[]buf =new byte[4096];
            while((len = fin.read(buf)) != -1){
                Log.v("brad",new String(buf,0,len));
            }
            fin.close();
        }catch (Exception e){
            Log.v("brad","io讀取file檔錯誤" +e.toString());
        }

    }
    //8.寫出檔案到approot路徑底下,發現sdcard/Android/data,的資料刪除就不見,這是專屬於你內存app的外部空間,app清到連檔案都沒
    public void test5(View view) {
        File file1 = new File(rootapp,"abc.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("hello world".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"輸出到rootapp成功",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.v("brad","檔案輸出串rootapp流失敗" + e.toString());
        }



    }
 //測試創建file回顧
//        File dir1 = new File(sdcard,"dir1");
//        if(!dir1.exists()){
//          if(dir1.mkdirs()){
//              Log.v("brad","檔案創建成功");
//          } else{
//              Log.v("brad","檔案創建失敗");
//          }
//        }else{
//            Log.v("brad","這個sdcard路徑已經存在");
//        }
//    }
    //9.寫出檔案到sdcard路徑底下,刪除紀錄資訊了但檔案還在
    public void test6(View view) {
        File file1 = new File(sdcard,"file1.txt");
        try {
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("hello world".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this,"輸出到sdcard成功",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.v("brad","檔案輸出到sdcard串流失敗" + e.toString());
        }
    }
}
