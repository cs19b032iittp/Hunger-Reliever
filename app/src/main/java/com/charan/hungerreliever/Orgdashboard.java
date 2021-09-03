package com.charan.hungerreliever;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Orgdashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout orgDrawer;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//
//            db.collection("verifyOrganisations")
//                    .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Map<String, Object> profile = document.getData();
//                                    if(String.valueOf(profile.get("formSubmitted")).equals("false")){
//                                        startActivity(new Intent(new Intent(getApplicationContext(),OrganisationDetailsForm.class)));
//                                        finish();
//                                    }
//                                    else {
//                                        startActivity(new Intent(new Intent(getApplicationContext(),VerificationStatus.class)));
//                                        finish();
//                                    }
//                                }
//                            } else {
//                                Log.d("MainActivity", "Error getting documents: ", task.getException());
//                                Toast.makeText(Orgdashboard.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Orgdashboard.this,Login.class);
//                                startActivity(intent);
//                                finish();
//
//                            }
//                        }
//                    });
//        }
//        else {
//            Intent intent = new Intent(Orgdashboard.this,Login.class);
//            startActivity(intent);
//            finish();
//        }
//    }

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
            case R.id.org_nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Orgdashboard.this,Login.class);
                startActivity(intent);
                finish();
                break;
        }
        orgDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}