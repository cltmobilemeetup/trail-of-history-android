package org.charmeck.trailofhistory.ranger.manager;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Trey Robinson on 7/9/16.
 */
public class AuthenticationManager {

    private static AuthenticationManager mInstance;

    private FirebaseAuth mAuth;

    public static AuthenticationManager getInstance(){

        if(mInstance == null){
            mInstance = new AuthenticationManager();
        }

        return mInstance;
    }

    private AuthenticationManager(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener listener){
        mAuth.addAuthStateListener(listener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener listener){
        if(mAuth != null){
            mAuth.removeAuthStateListener(listener);
        }
    }

}
