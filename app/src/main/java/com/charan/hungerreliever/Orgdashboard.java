package com.charan.hungerreliever;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Orgdashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout orgDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgdashboard);

        Toolbar orgToolbar = findViewById(R.id.org_toolbar);
        setSupportActionBar(orgToolbar);

        orgDrawer = findViewById(R.id.org_drawer_layout);

        NavigationView navigationView = findViewById(R.id.org_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, orgDrawer, orgToolbar, R.string.org_navigation_drawer_open, R.string.org_navigation_drawer_close);
        orgDrawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.org_fragment_container, new HomeOrgFragment()).commit();
            navigationView.setCheckedItem(R.id.org_nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.org_nav_view_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.org_fragment_container, new ProfileOrgFragment()).commit();
                break;
            case R.id.org_nav_donations_received:
                getSupportFragmentManager().beginTransaction().replace(R.id.org_fragment_container, new DonationsFragment()).commit();
                break;
            case R.id.org_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.org_fragment_container, new HomeOrgFragment()).commit();
                break;
        }
        orgDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}