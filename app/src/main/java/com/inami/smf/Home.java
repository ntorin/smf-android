package com.inami.smf;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.inami.smf.account.AccountSettingsFragment;
import com.inami.smf.bbs.BBSFragment;
import com.inami.smf.bbs.ThreadFragment;
import com.inami.smf.notifications.NotificationsFragment;
import com.inami.smf.personal.FeedFragment;
import com.inami.smf.personal.GroupsFragment;
import com.inami.smf.personal.MessagesFragment;
import com.inami.smf.personal.PersonalFragment;
import com.inami.smf.personal.ProfileFragment;
import com.inami.smf.personal.SingleGroupFragment;
import com.inami.smf.usersearch.UserSearchFragment;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        PersonalFragment.OnFragmentInteractionListener,
        FeedFragment.OnFragmentInteractionListener,
        GroupsFragment.OnFragmentInteractionListener,
        MessagesFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        BBSFragment.OnFragmentInteractionListener,
        NotificationsFragment.OnFragmentInteractionListener,
        AccountSettingsFragment.OnFragmentInteractionListener,
        SingleGroupFragment.OnFragmentInteractionListener,
        UserSearchFragment.OnFragmentInteractionListener,
        ThreadFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Fragment personalFragment = PersonalFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, personalFragment, getString(R.string.fragment_personal)).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String tag = "";
        switch (id) {
            case R.id.nav_bbs:
                tag = getString(R.string.fragment_bbs);
                fragment = BBSFragment.newInstance();
                break;
            case R.id.nav_personal:
                tag = getString(R.string.fragment_personal);
                fragment = PersonalFragment.newInstance();
                break;
            case R.id.nav_notifications:
                tag = getString(R.string.fragment_notifications);
                fragment = NotificationsFragment.newInstance();
                break;
            case R.id.nav_account_settings:
                tag = getString(R.string.fragment_account_settings);
                fragment = AccountSettingsFragment.newInstance();
                break;
            case R.id.nav_user_search:
                tag = getString(R.string.fragment_user_search);
                fragment = UserSearchFragment.newInstance();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, tag).commit();
        Log.d("#fragments", " " + getSupportFragmentManager().getFragments().size());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onThreadFocus() {
        Fragment f = ThreadFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, f).commit();
    }

    @Override
    public void onSingleGroupFocus() {
        Fragment f = SingleGroupFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, f).commit();
    }
}
