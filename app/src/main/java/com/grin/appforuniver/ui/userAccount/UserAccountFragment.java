package com.grin.appforuniver.ui.userAccount;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.ImagePickerActivity;
import com.grin.appforuniver.activities.LoginActivity;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.service.UserService;
import com.grin.appforuniver.data.tools.AuthInterceptor;
import com.grin.appforuniver.data.tools.AuthManager;
import com.grin.appforuniver.databinding.FragmentUserAccountBinding;
import com.grin.appforuniver.utils.CircularTransformation;
import com.grin.appforuniver.utils.Constants;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static com.grin.appforuniver.utils.Constants.Roles.ROLE_TEACHER;

public class UserAccountFragment extends Fragment {

    public final String TAG = UserAccountFragment.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private UserService mUserService;
    private UserAccountViewModel userAccountViewModel;
    private FragmentUserAccountBinding binding;

    private User mUser;
    private Professors mProfessor;

    public UserAccountFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mUserService = UserService.getService();
        userAccountViewModel = ViewModelProviders.of(this).get(UserAccountViewModel.class);
        binding = FragmentUserAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userAccountViewModel.getUserFI().observe(getViewLifecycleOwner(), s -> {
            binding.userAccountUsernameLl.setVisibility(View.VISIBLE);
            binding.userAccountUsernameTv.setText(s);
        });
        userAccountViewModel.getEmail().observe(getViewLifecycleOwner(), s -> {
            binding.userAccountEmailLl.setVisibility(View.VISIBLE);
            binding.userAccountEmailTv.setText(s);
        });
        binding.avatarImageView.setOnClickListener(view1 -> changeAvatar());
        binding.addAvatar.setOnClickListener(view2 -> changeAvatar());
        getMyAccount(binding.getRoot().getContext());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.account_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                onClickLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getMyAccount(Context context) {
        mUser = AuthManager.getInstance().getUser();

        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            getMyAccountProfessor(context);
        } else {
            binding.userAccountPosadaTv.setText(R.string.student);
        }

        if (mUser.getTelefon1() != null && !mUser.getTelefon1().isEmpty()) {
            binding.userAccountPhone1Ll.setVisibility(View.VISIBLE);
            binding.userAccountPhone1Tv.setText(mUser.getTelefon1());
        } else {
            binding.userAccountPhone1Ll.setVisibility(View.GONE);
        }

        binding.userAccountDetailProgress.setVisibility(View.GONE);
        binding.userAccountUserinfoRl.setVisibility(View.VISIBLE);
        binding.userAccountHeader.setVisibility(View.VISIBLE);
        binding.userAccountEmailLl.setVisibility(View.VISIBLE);
        binding.userAccountPhone1Ll.setVisibility(View.VISIBLE);
        String urlPhoto = Constants.API_BASE_URL + "photo/current_user/get_avatar";

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor.getInstance())
                .build();

        Uri photoUri = Uri.parse(urlPhoto);
        Picasso picasso = new Picasso.Builder(getContext())
                .downloader(new OkHttp3Downloader(client))
                .build();
        picasso
                .load(photoUri)
                .placeholder(R.drawable.account_circle_outline)
                .error(R.drawable.ic_warning)
                .transform(new CircularTransformation(0))
                .into(binding.avatarImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void getMyAccountProfessor(Context context) {
        mUserService.requestCurrentUserProfessorProfile(new UserService.OnRequestCurrentUserProfessorProfileListener() {
            @Override
            public void onRequestCurrentUserProfessorProfileSuccess(Call<Professors> call, Response<Professors> response) {
                if (response.body() != null) {
                    mProfessor = response.body();
                    if (mProfessor.getPosada() != null) {
                        binding.userAccountPosadaTv.setText(mProfessor.getPosada().getFullPostProfessor());
                    }
                    if (mProfessor.getDepartment() != null) {
                        binding.userAccountDepartmentLl.setVisibility(View.VISIBLE);
                        binding.userAccountDepartmentTv.setText(mProfessor.getDepartment().getName());
                    } else {
                        binding.userAccountDepartmentLl.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onRequestCurrentUserProfessorProfileFailed(Call<Professors> call, Throwable t) {
                Toasty.error(context, Objects.requireNonNull(t.getMessage()), Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    private void getMyAccountStudent(Context context) {
    }

    void onClickLogout() {
        AuthManager.getInstance().logout();

        requireActivity().startActivity(new Intent(getContext(), LoginActivity.class));
        requireActivity().finish();
    }

    void changeAvatar() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(requireContext(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = ImagePickerActivity.getCameraIntent(requireActivity());
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = ImagePickerActivity.getGalleryIntent(requireActivity());
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

                    File finalFile = new File(uri.getPath());
                    uploadPhoto(finalFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadPhoto(File finalFile) {
        MultipartBody.Part body;
        if (finalFile != null) {
            long fileSize = finalFile.length();
            if (fileSize >= ImagePickerActivity.MAX_FILE_SIZE_FOR_UPLOAD) {
                Toast.makeText(getContext(), "File so much. Please crop image", Toast.LENGTH_LONG).show();
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
                String urlPhoto = Constants.API_BASE_URL + "photo/current_user/get_avatar";
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(AuthInterceptor.getInstance())
                        .build();

                Uri photoUri = Uri.parse(urlPhoto);
                Picasso picasso = new Picasso.Builder(getContext())
                        .downloader(new OkHttp3Downloader(client))
                        .build();
                picasso
                        .load(photoUri)
                        .placeholder(R.drawable.account_circle_outline)
                        .error(R.drawable.ic_warning)
                        .transform(new CircularTransformation(0))
                        .into(binding.avatarImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        });
            }

            @Override
            public void onRequestAvatarProfileFailed(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}
