package com.example.androidportfolio.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.androidportfolio.R;
import com.example.androidportfolio.ui.favoritetoys.view.FavoriteToysFragment;
import com.example.androidportfolio.ui.gardenmanager.view.GardenManagerFragment;
import com.example.androidportfolio.ui.githubsearch.view.GitHubSearchFragment;
import com.example.androidportfolio.ui.intents.view.IntentsFragment;
import com.example.androidportfolio.ui.movies.view.MoviesFragment;
import com.example.androidportfolio.ui.networkmanager.view.NetworkManagerFragment;
import com.example.androidportfolio.ui.sandwich.view.SandwichFragment;
import com.example.androidportfolio.ui.sunshine.view.SunshineFragment;
import com.example.androidportfolio.ui.versus.view.VersusFragment;
import com.google.android.material.navigation.NavigationView;

import static androidx.core.view.GravityCompat.START;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final int FIRST_MENU_ITEM = 0;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = mNavigationView.getMenu().getItem(FIRST_MENU_ITEM);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(START)) mDrawerLayout.closeDrawer(START);
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_favorite_toys:
                fragment = new FavoriteToysFragment();
                break;
            case R.id.nav_github_search:
                fragment = new GitHubSearchFragment();
                break;
            case R.id.nav_sunshine:
                fragment = new SunshineFragment();
                break;
            case R.id.nav_intents:
                fragment = new IntentsFragment();
                break;
            case R.id.nav_sandwich:
                fragment = new SandwichFragment();
                break;
            case R.id.nav_movies:
                fragment = new MoviesFragment();
                break;
            case R.id.nav_versus: // TODO - Create an API that is nurtured by WebScrapping from DeviceSpecifications.
                fragment = new VersusFragment();
                break;
            case R.id.nav_network_manager:
                fragment = new NetworkManagerFragment();
                break;
            case R.id.nav_garden_manager:
                fragment = new GardenManagerFragment();
                break;
            default:
                throw new IllegalArgumentException(getString(R.string.menu_option_not_implemented));
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_linear_layout, fragment).commit();

        mDrawerLayout.closeDrawer(START);

        return true;
    }
}
