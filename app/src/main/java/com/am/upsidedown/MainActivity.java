package com.am.upsidedown;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.am.upsidedown.chat.ChatFragment;
import com.am.upsidedown.feed.FeedFragment;
import com.am.upsidedown.review.ReviewFragment;
import com.am.upsidedown.utils.CustomDialog;
import com.am.upsidedown.utils.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager viewPager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                getSelectedPage(item.getItemId());
                return true;
            }
        });

    }

    /**
     * This method displays tabs' fragments on ViewPager using ViewPagerAdapter
     * and provides the methods used to perform Fragment Transactions (add, remove, replace fragments) through Fragment Manager.
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FeedFragment().newInstance(), "Feed");
        adapter.addFragment(new ReviewFragment().newInstance(), "Review");
        adapter.addFragment(new ChatFragment().newInstance(), "Chats");
        viewPager.setAdapter(adapter);
    }

    /**
     * This method detects user interaction with the side menu,
     * pops up the preferences dialog
     * and provides logout action handler.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();
        switch (id) {
            case R.id.side_menu_settings:
                CustomDialog preferencesDialog = (CustomDialog) new CustomDialog(this)
                        .setLayout(R.layout.dialog_preference);
                preferencesDialog.show();
                return true;
            case R.id.side_menu_logout:
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method inflates side_menu resource.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }

    /**
     * This method is called if any navigation item from navigation menu is selected
     * and it performs the corresponding action (redirects to another activity or changes fragment).
     * @param id
     * @return
     */
    private boolean getSelectedPage(int id) {
        switch (id) {
            case R.id.nav_item1:
                break;
            case R.id.nav_item2:
                break;
            case R.id.nav_item3:
                break;
            case R.id.nav_item4:
                break;
            case R.id.nav_item5:
                break;
            case R.id.nav_item6:
                break;
            default:
                return true;
        }
        return true;
    }

    /**
     * This method handles back button press action,
     * if navigation menu is opened only the drawer layout closes,
     * else the hole app closes.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }
}