package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.adapter.ViewPagerAdapter;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.AllDonarFragment;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.BloodRequestFragment;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.DepartmentFragment;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.ReadyDonarFragment;
import com.eudev.bloodbank.bloodbankeu.activity.login.LoginActivity;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference bloodCountTable;

    //Header Title
    private TextView headerName,headerBloodGroup,headerPhoneNum;

    //This is our tablayout
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    AllDonarFragment allDonarFragment;
    ReadyDonarFragment readyDonarFragment;
    BloodRequestFragment bloodRequestFragment;
    DepartmentFragment departmentFragment;

    String[] tabTitle={"All Donar","Ready Donar","Blood Request","Department"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Blood Bank EU");
        setSupportActionBar(toolbar);




        //database init

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User");
        bloodCountTable = database.getReference("Blood_Request");

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


        //for getting alldonars size
        totalSizeOfData();


        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        headerName= (TextView) headerView.findViewById(R.id.header_name);
        headerBloodGroup= (TextView) headerView.findViewById(R.id.header_blood_group);
        headerPhoneNum= (TextView) headerView.findViewById(R.id.header_phone_num);
        headerName.setText(Common.currentUser.getName());
        headerBloodGroup.setText(Common.currentUser.getBlood_group());
        headerPhoneNum.setText(Common.currentUser.getPhone());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
        }else if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_new_member) {

        } else if (id == R.id.edit_profile) {

        } else if (id == R.id.log_out) {
            Intent signIn = new Intent(getApplicationContext(), LoginActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //--------------Tablayout method works------------





    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        allDonarFragment =new AllDonarFragment();
        readyDonarFragment = new ReadyDonarFragment();
        bloodRequestFragment = new BloodRequestFragment();
        departmentFragment = new DepartmentFragment();
        adapter.addFragment(allDonarFragment,"All Donar");
        adapter.addFragment(readyDonarFragment,"Ready Donar");
        adapter.addFragment(bloodRequestFragment,"Blood Request");
        adapter.addFragment(departmentFragment,"Department");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(final int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        final TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);


        final long[] unreadCount = new long[4];



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               long size = dataSnapshot.getChildrenCount();
                unreadCount[0] = size;
//                unreadCount[1] = 4;
//                unreadCount[2] = 5;
//                unreadCount[3] = 8;
                tv_count.setText(""+unreadCount[pos]);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bloodCountTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long size = dataSnapshot.getChildrenCount();
                unreadCount[1] = 0;
                unreadCount[2] = size;
                unreadCount[3] = 0;
                tv_count.setText(""+unreadCount[pos]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





//        if(unreadCount[pos]>0)
//        {
//            tv_count.setVisibility(View.VISIBLE);
//            tv_count.setText(""+unreadCount[pos]);
//        }
//        else
//            tv_count.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons()
    {

        for(int i=0;i<tabTitle.length;i++)
        {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }

    //-----------------Total Size oF FireBase Data------------------
    private void totalSizeOfData() {



    }


}
