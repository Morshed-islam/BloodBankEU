package com.eudev.bloodbank.bloodbankeu.activity.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.eudev.bloodbank.bloodbankeu.R;
import com.eudev.bloodbank.bloodbankeu.activity.adapter.ViewPagerAdapter;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.AllDonarFragment;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.BloodRequestFragment;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.DepartmentFragment;
import com.eudev.bloodbank.bloodbankeu.activity.fragment.ReadyDonarFragment;
import com.eudev.bloodbank.bloodbankeu.activity.login.LoginActivity;
import com.eudev.bloodbank.bloodbankeu.activity.model.BloodRequest;
import com.eudev.bloodbank.bloodbankeu.activity.model.Common;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private InterstitialAd mInterstitialAd;

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference bloodCountTable;
    private DatabaseReference readyDonorCount;

    //Header Title
    private TextView headerName, headerBloodGroup, headerPhoneNum;
    private CircleImageView headerImg;

    //This is our tablayout
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    AllDonarFragment allDonarFragment;
    ReadyDonarFragment readyDonarFragment;
    BloodRequestFragment bloodRequestFragment;
    DepartmentFragment departmentFragment;

    String[] tabTitle = {"All Donor", "Ready Donor", "Blood Request", "Department"};


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
        readyDonorCount = database.getReference("User");

        //phone call permission
        isPermissionGranted();

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


        //for getting alldonars size
        totalSizeOfData();


        //ad load
        //ad View
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-1851961673513824/3488626679");
//        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
//
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
//                mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
//            }
//        });


        try {
            setupTabIcons();
        } catch (Exception e) {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position, false);

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

        headerName = (TextView) headerView.findViewById(R.id.header_name);
        headerImg = (CircleImageView) headerView.findViewById(R.id.nav_profile_image);
        headerBloodGroup = (TextView) headerView.findViewById(R.id.header_blood_group);
        headerPhoneNum = (TextView) headerView.findViewById(R.id.header_phone_num);
        headerName.setText(Common.currentUser.getName());
        headerBloodGroup.setText(Common.currentUser.getBlood_group());
        headerPhoneNum.setText(Common.currentUser.getPhone());
        headerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Coming Soon!! ", Toast.LENGTH_SHORT).show();
            }
        });

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
        if (id == R.id.action_search) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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

            startActivity(new Intent(getApplicationContext(), AddNewMemberActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        } else if (id == R.id.edit_profile) {


            startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

//            if (mInterstitialAd.isLoaded()) {
//
//                mInterstitialAd.show();
//            } else {
////                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
////                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//            }


        } else if (id == R.id.blood_request) {
            startActivity(new Intent(getApplicationContext(), BloodRequestActivity.class));
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        } else if (id == R.id.developer) {
            Intent dev = new Intent(getApplicationContext(), DeveloperActivity.class);
            startActivity(dev);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }else if(id == R.id.special_thanks){
            Intent thanks = new Intent(getApplicationContext(), SpecialThanksActivity.class);
            startActivity(thanks);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        } else if (id == R.id.log_out) {
            Intent signIn = new Intent(getApplicationContext(), LoginActivity.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signIn);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //--------------Tablayout method works------------


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        allDonarFragment = new AllDonarFragment();
        readyDonarFragment = new ReadyDonarFragment();
        bloodRequestFragment = new BloodRequestFragment();
        departmentFragment = new DepartmentFragment();
        adapter.addFragment(allDonarFragment, "All Donor");
        adapter.addFragment(readyDonarFragment, "Ready Donor");
        adapter.addFragment(bloodRequestFragment, "Blood Request");
        adapter.addFragment(departmentFragment, "Department");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(final int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
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
                tv_count.setText("" + unreadCount[pos]);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bloodCountTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long size = dataSnapshot.getChildrenCount();
                // unreadCount[1] = 0;
                unreadCount[2] = size;
                // unreadCount[3] = 0;
                tv_count.setText("" + unreadCount[pos]);

                Log.i("morshed", "blood size: " + size);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = mFirebaseDatabaseReference.child("User").orderByChild("ready").equalTo("Yes");


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long size = dataSnapshot.getChildrenCount();

                Log.i("morshed", "size is: " + size);

                unreadCount[1] = size;
                //unreadCount[2] = size;
                // unreadCount[3] = 0;
                tv_count.setText("" + unreadCount[pos]);

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

    private void setupTabIcons() {

        for (int i = 0; i < tabTitle.length; i++) {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }

    //-----------------Total Size oF FireBase Data------------------
    private void totalSizeOfData() {


    }


    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
