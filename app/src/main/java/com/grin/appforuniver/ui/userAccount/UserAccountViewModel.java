package com.grin.appforuniver.ui.userAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grin.appforuniver.App;
import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.ImagePickerActivity;
import com.grin.appforuniver.data.models.Professors;
import com.grin.appforuniver.data.models.User;
import com.grin.appforuniver.data.service.UserService;
import com.grin.appforuniver.data.tools.AuthManager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public class UserAccountViewModel extends ViewModel {
    private UserService mUserService;
    private User user;
    private Professors professor;
    private MutableLiveData<String> userNameLiveData;
    private MutableLiveData<String> emailLiveData;
    private MutableLiveData<String> telephone1LiveData;
    private MutableLiveData<String> posadaLiveData;
    private MutableLiveData<String> departmentLiveData;
    private MutableLiveData<Boolean> updateAvatarLiveData;

    private MutableLiveData<String> errorMessageLiveData;

    public UserAccountViewModel() {
        mUserService = UserService.getService();
        user = AuthManager.getInstance().getUser();

        userNameLiveData = new MutableLiveData<>();
        emailLiveData = new MutableLiveData<>();
        telephone1LiveData = new MutableLiveData<>();
        posadaLiveData = new MutableLiveData<>();
        departmentLiveData = new MutableLiveData<>();
        updateAvatarLiveData = new MutableLiveData<>();

        errorMessageLiveData = new MutableLiveData<>();

        userNameLiveData.setValue(user.getFullFI());
        emailLiveData.setValue(user.getEmail());
        if (user.getTelefon1() != null && !user.getTelefon1().isEmpty()) {
            telephone1LiveData.setValue(user.getTelefon1());
        }
        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            getMyAccountProfessor();
        } else {
            posadaLiveData.setValue(App.getInstance().getApplicationContext().getResources().getString(R.string.student));
        }
    }

    private void getMyAccountProfessor() {
        mUserService.requestCurrentUserProfessorProfile(new UserService.OnRequestCurrentUserProfessorProfileListener() {
            @Override
            public void onRequestCurrentUserProfessorProfileSuccess(Call<Professors> call, Response<Professors> response) {
                if (response.body() != null) {
                    professor = response.body();
                    if (professor.getPosada() != null) {
                        posadaLiveData.setValue(professor.getPosada().getFullPostProfessor());
                    }
                    if (professor.getDepartment() != null) {
                        departmentLiveData.setValue(professor.getDepartment().getName());
                    }
                }
            }

            @Override
            public void onRequestCurrentUserProfessorProfileFailed(Call<Professors> call, Throwable t) {
                errorMessageLiveData.setValue(t.getMessage());
            }
        });
    }

    public LiveData<String> getUserFI() {
        return userNameLiveData;
    }

    public LiveData<String> getEmail() {
        return emailLiveData;
    }

    public LiveData<String> getTelephone1() {
        return telephone1LiveData;
    }

    public LiveData<String> getPosada() {
        return posadaLiveData;
    }

    public LiveData<String> getDepartment() {
        return departmentLiveData;
    }

    public LiveData<Boolean> getUpdateAvatar() {
        return updateAvatarLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessageLiveData;
    }

    public void uploadPhoto(String pathPhoto) {
        File finalFile = new File(pathPhoto);

        MultipartBody.Part body;
        if (finalFile != null) {
            long fileSize = finalFile.length();
            if (fileSize >= ImagePickerActivity.MAX_FILE_SIZE_FOR_UPLOAD) {
                errorMessageLiveData.setValue("File so much. Please crop image");
                return;
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), finalFile);
            body = MultipartBody.Part.createFormData("image", finalFile.getName(), requestBody);
        } else {
            RequestBody requestBody = RequestBody.create(MultipartBody.FORM, "");
            body = MultipartBody.Part.createFormData("image", "", requestBody);
        }
        mUserService.setAvatarProfile(body, new UserService.OnRequestAvatarProfileListener() {
            @Override
            public void onRequestAvatarProfileSuccess(Call<Void> call, Response<Void> response) {
                updateAvatarLiveData.setValue(true);
            }

            @Override
            public void onRequestAvatarProfileFailed(Call<Void> call, Throwable t) {
                errorMessageLiveData.setValue("Failed upload avatar image");
            }
        });
    }
}
