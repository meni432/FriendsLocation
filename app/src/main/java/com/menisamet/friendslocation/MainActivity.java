package com.menisamet.friendslocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showUserDetail();
    }

    public void loadSignInAcrivity(View view){
        Utility.checkAuthAndGoToActivity(this, FindMyLocationActivity.class);
    }
    void showUserDetail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView username = (TextView) findViewById(R.id.usernameMainView);
        if (user != null) {
            username.setText(user.getDisplayName());
            ImageView avatarImageview = (ImageView) findViewById(R.id.userAvatarImageView);
            Picasso.with(this).load(user.getPhotoUrl()).into(avatarImageview);
        } else {
            username.setText("Guest");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Utility.logOutUser(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case R.id.action_logout:
//                Utility.logOutUser(this);
//                Utility.showToast(this, "logout click");
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }
}
