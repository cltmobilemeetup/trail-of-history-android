package org.charmeck.trailofhistory.ranger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.charmeck.trailofhistory.ranger.manager.AuthenticationManager;

/**
 * Created by Trey Robinson on 7/9/16.
 */
public class AuthenticatedActivity extends BaseActivity implements  FirebaseAuth.AuthStateListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AuthenticationManager.getInstance().addAuthStateListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        AuthenticationManager.getInstance().removeAuthStateListener(this);
    }

    private void goToSignIn(){
        startActivity(EmailPasswordActivity.newInstance(this));
        finish();
    }

    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            goToSignIn();
        }
    }

}