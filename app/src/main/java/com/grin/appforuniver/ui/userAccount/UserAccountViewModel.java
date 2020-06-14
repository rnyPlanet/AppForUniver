package com.grin.appforuniver.ui.userAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.tools.AuthManager;

public class UserAccountViewModel extends ViewModel {
    private User mUser;

    private MutableLiveData<String> userNameLiveData;
    private MutableLiveData<String> emailLiveData;

    public UserAccountViewModel() {
        mUser = AuthManager.getInstance().getUser();

        userNameLiveData = new MutableLiveData<>();
        userNameLiveData.setValue(mUser.getFullFI());
        emailLiveData = new MutableLiveData<>();
        emailLiveData.setValue(mUser.getEmail());
    }


    public LiveData<String> getUserFI() {
        return userNameLiveData;
    }

    public LiveData<String> getEmail() {
        return emailLiveData;
    }

    public void uploadPhoto(String pathPhoto) {

    }
}
