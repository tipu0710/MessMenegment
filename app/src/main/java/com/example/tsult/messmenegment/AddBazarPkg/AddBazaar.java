package com.example.tsult.messmenegment.AddBazarPkg;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsult.messmenegment.BazarList.BazarList;
import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.Home.MainActivity;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMember.ShowMember;
import com.example.tsult.messmenegment.ShowPic.ShowMemo;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

public class AddBazaar extends AppCompatActivity {

    private TextView bazaarDateTv,mNameTv;
    private EditText costEt;
    private Button addBazaarBtn, addMemoBtn;
    private ImageView showPic;

    private int id,bId;
    private String mName, showDate;
    private int year,month,day;
    private boolean condition;
    private byte[] image;
    private String identifier, mPhone, mEmail;
    private boolean status, goBackStatus, check;

    public static final int REQUEST_CAPTURE = 1;

    private AddBazaarDBOperation addBazaarDBOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bazaar);

        bazaarDateTv = (TextView) findViewById(R.id.bazaar_date);
        mNameTv = (TextView) findViewById(R.id.bazaarer_name);
        costEt = (EditText) findViewById(R.id.cost_et);
        addBazaarBtn = (Button) findViewById(R.id.bazaar_add);
        addMemoBtn = (Button) findViewById(R.id.add_memo);
        showPic = (ImageView) findViewById(R.id.show_image);

        final Intent intent = getIntent();
        condition = intent.getBooleanExtra("condition",false);
        id = intent.getIntExtra("id", -1);
        mName = intent.getStringExtra("name");
        mNameTv.setText(mName);
        mPhone = intent.getStringExtra("phone");
        mEmail=intent.getStringExtra("email");
        goBackStatus = intent.getBooleanExtra("status",false);
        status = goBackStatus;
        check = intent.getBooleanExtra("check", false);
        addBazaarDBOperation = new AddBazaarDBOperation(id, this);

        if (!hasCamera()){
            addMemoBtn.setEnabled(false);
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        year = calendar.get(Calendar.YEAR);
        month =calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (condition){
            showDate = intent.getStringExtra("date");
            bId = intent.getIntExtra("bazaarId",-1);
            int c = intent.getIntExtra("cost",0);
            costEt.setText(String.valueOf(c));
            image = intent.getByteArrayExtra("image");
            identifier = intent.getStringExtra("identifier");
            if (image.length>0){
                Bitmap photo = Converter.getImage(image);
                showPic.setImageBitmap(photo);
            }

            addBazaarBtn.setText("UPDATE BAZAAR");
            identifier = intent.getStringExtra("identifier");
        }else {
            showDate = day + "/"+ (month+1) + "/"+year;
            identifier = MealInfo.getYear()+" - "+MealInfo.getMonth();
            Bitmap bitmap = ((BitmapDrawable) showPic.getDrawable()).getBitmap();
            image = Converter.getBytes(bitmap);
        }

        bazaarDateTv.setText("Date: "+showDate);

        bazaarDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddBazaar.this,dateListener,year,month,day);
                dpd.show();
            }
            private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int myear, int mmonth, int dayOfMonth) {
                    showDate = dayOfMonth + "/"+ (mmonth+1) + "/"+myear;
                    bazaarDateTv.setText("Date: "+showDate);
                }
            };
        });

        addBazaarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costString= costEt.getText().toString();
                if (costString.isEmpty()){
                    costEt.setError(getString(R.string.error_msg));
                }else {
                    int cost = Integer.parseInt(costString);

                    if (condition){
                        Bazaar bazaar = new Bazaar(bId, id, mName, showDate, cost, image, identifier);
                        boolean status = addBazaarDBOperation.UpdateBazaarList(bazaar);
                        if (status){
                            Toast.makeText(AddBazaar.this, "Successfully update!", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(AddBazaar.this, BazarList.class);
                            intent1.putExtra("id", id);
                            intent1.putExtra("name", mName);
                            intent1.putExtra("phone", mPhone);
                            intent1.putExtra("email", mEmail);
                            intent1.putExtra("identifier", identifier);
                            intent1.putExtra("check", goBackStatus);
                            startActivity(intent1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        }else {
                            Toast.makeText(AddBazaar.this, "Failed to update!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Bazaar bazaar = new Bazaar(id, mName, showDate, cost, image, identifier);
                        boolean status = addBazaarDBOperation.AddBazaarList(bazaar);
                        if (status){
                            Toast.makeText(AddBazaar.this, "Successfully added!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddBazaar.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //Camera:

        addMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CAPTURE);
            }
        });

        showPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddBazaar.this, ShowMemo.class);
                intent1.putExtra("memo",image);
                startActivity(intent1);
            }
        });

    }



    public static class Converter {

        // convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CAPTURE == requestCode){
            if(resultCode == RESULT_OK){
                Bundle extra = data.getExtras();
                Bitmap photo = (Bitmap) extra.get("data");
                showPic.setImageBitmap(photo);
                image = Converter.getBytes(photo);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (check){
            Intent intent = new Intent(AddBazaar.this, AddBazaarMember.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(AddBazaar.this, BazarList.class);
            intent.putExtra("id", id);
            intent.putExtra("name", mName);
            intent.putExtra("phone", mPhone);
            intent.putExtra("email", mEmail);
            intent.putExtra("identifier", identifier);
            intent.putExtra("check", status);
            startActivity(intent);
        }*/

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
