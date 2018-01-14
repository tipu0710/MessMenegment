package com.example.tsult.messmenegment.Home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tsult.messmenegment.AddBazarPkg.AddBazaarMember;
import com.example.tsult.messmenegment.AddExtraPkg.AddExtra;
import com.example.tsult.messmenegment.AddMealPkg.AddMeal;
import com.example.tsult.messmenegment.AddMember.AddFromPrevious;
import com.example.tsult.messmenegment.AddMember.AddMember;
import com.example.tsult.messmenegment.AddMember.AddMemberDBOperation;
import com.example.tsult.messmenegment.AddMember.ContactActivity;
import com.example.tsult.messmenegment.AddMember.Member;
import com.example.tsult.messmenegment.PreviousDataPkg.PreviousData;
import com.example.tsult.messmenegment.R;
import com.example.tsult.messmenegment.ShowMealRatePkg.MealInfo;
import com.example.tsult.messmenegment.ShowMember.ShowMember;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton addFab, addMealFab, addExtraFab;
    private TextView mealFabText, extraFabText;
    private Animation fabOpen, fabClose, fabForward, fabBackword;
    private boolean isFabOpen = false;
    private RecyclerView memberDetailsList;
    private AddMemberDBOperation addMemberDBOperation;
    private ArrayList<Member> members;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        addFab = (FloatingActionButton) findViewById(R.id.fab);
        addExtraFab = (FloatingActionButton) findViewById(R.id.add_extra_fab);
        addMealFab = (FloatingActionButton) findViewById(R.id.add_meal_fab);
        mealFabText = (TextView) findViewById(R.id.text_fab_meal);
        extraFabText = (TextView) findViewById(R.id.text_fab_extra);
        memberDetailsList = (RecyclerView) findViewById(R.id.member_detail_list_main);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        fabBackword = AnimationUtils.loadAnimation(this, R.anim.rotate_backword);
        fabForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);

        setSupportActionBar(toolbar);

        MealInfo.Preference.SaveInfo(this, MealInfo.getMonthName(MealInfo.getMonth())+" - "+ MealInfo.getYear(), false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimateFab();
            }
        });

        addMealFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, AddMeal.class);
                startActivity(intent);
                AnimateFab();
            }
        });
        addExtraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, AddExtra.class);
                intent.putExtra("status",false);
                startActivity(intent);
                AnimateFab();
            }
        });

        addMemberDBOperation = new AddMemberDBOperation(this);
        members = new ArrayList<>();

        members = addMemberDBOperation.getMemberList(MealInfo.getYear()+" - "+MealInfo.getMonth());
        memberDetailsList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        memberDetailsList.setLayoutManager(mLayoutManager);

        mAdapter = new MemberDetailsListAdapter(members, this);
        memberDetailsList.setAdapter(mAdapter);

    }

    private void AnimateFab(){
        if (isFabOpen) {
            addFab.startAnimation(fabBackword);
            addMealFab.startAnimation(fabClose);
            addExtraFab.startAnimation(fabClose);
            extraFabText.startAnimation(fabClose);
            mealFabText.startAnimation(fabClose);
            addMealFab.setClickable(false);
            addExtraFab.setClickable(false);
            isFabOpen = false;
        } else {
            addFab.startAnimation(fabForward);
            addMealFab.startAnimation(fabOpen);
            addExtraFab.startAnimation(fabOpen);
            extraFabText.startAnimation(fabOpen);
            mealFabText.startAnimation(fabOpen);
            addMealFab.setClickable(true);
            addExtraFab.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            MealInfo.Preference.ClearPreference(this);
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_member_btn) {
            /*Intent intent = new Intent(Main2Activity.this, AddMember.class);
            startActivity(intent);*/

            final Dialog customDialog = new Dialog(this, R.style.TranslucentDialog);
            Window window = customDialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            customDialog.setContentView(R.layout.add_member_menu);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            customDialog.show();

            ImageButton addFromContact = (ImageButton) customDialog.findViewById(R.id.from_contact);
            ImageButton addFromPrevious = (ImageButton) customDialog.findViewById(R.id.from_previous);
            ImageButton addManually = (ImageButton) customDialog.findViewById(R.id.manually);

            addManually.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Main2Activity.this, AddMember.class);
                    startActivity(intent);
                    customDialog.dismiss();
                }
            });

            addFromContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Main2Activity.this, ContactActivity.class);
                    startActivity(intent);
                    customDialog.dismiss();
                }
            });

            addFromPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Main2Activity.this, AddFromPrevious.class);
                    startActivity(intent);
                    customDialog.dismiss();
                }
            });


        } else if (id == R.id.show_member_btn) {
            Intent intent = new Intent(Main2Activity.this, ShowMember.class);
            intent.putExtra("status",false);
            startActivity(intent);
        } else if (id == R.id.add_bazaar_btn) {
            Intent intent = new Intent(Main2Activity.this, AddBazaarMember.class);
            startActivity(intent);
        } else if (id == R.id.add_meal_btn) {
            Intent intent = new Intent(Main2Activity.this, AddMeal.class);
            startActivity(intent);
        } else if (id == R.id.add_deposit_btn) {
            Intent intent = new Intent(Main2Activity.this, ShowMember.class);
            intent.putExtra("status", true);
            startActivity(intent);
        } else if (id == R.id.add_extra_btn) {
            Intent intent = new Intent(Main2Activity.this, AddExtra.class);
            intent.putExtra("status",false);
            startActivity(intent);
        } else if (id == R.id.show_previous_data_btn) {
            Intent intent = new Intent(Main2Activity.this, PreviousData.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
