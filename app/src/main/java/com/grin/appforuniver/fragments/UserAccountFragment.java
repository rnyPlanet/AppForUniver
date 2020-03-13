package com.grin.appforuniver.fragments;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.ImagePickerActivity;
import com.grin.appforuniver.activities.LoginActivity;
import com.grin.appforuniver.data.model.schedule.Professors;
import com.grin.appforuniver.data.model.user.User;
import com.grin.appforuniver.data.service.UserService;
import com.grin.appforuniver.data.tools.AuthInterceptor;
import com.grin.appforuniver.data.tools.AuthManager;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    @BindView(R.id.user_account_detail_progress)
    ProgressBar detail_progress;

    @BindView(R.id.user_account_userinfo_rl)
    ConstraintLayout userinfo_rl;

    @BindView(R.id.user_account_header)
    View user_account_header;

    @BindView(R.id.user_account_username_ll)
    LinearLayout username_ll;
    @BindView(R.id.user_account_username_tv)
    TextView username_tv;

    @BindView(R.id.user_account_email_ll)
    LinearLayout email_ll;
    @BindView(R.id.user_account_email_tv)
    TextView email_tv;

    @BindView(R.id.user_account_department_ll)
    LinearLayout department_ll;
    @BindView(R.id.user_account_department_tv)
    TextView department_tv;

    @BindView(R.id.user_account_posada_ll)
    LinearLayout posada_ll;
    @BindView(R.id.user_account_posada_tv)
    TextView posada_tv;

    @BindView(R.id.user_account_phone1_ll)
    LinearLayout telefon1_ll;
    @BindView(R.id.user_account_phone1_tv)
    TextView telefon1_tv;

    @BindView(R.id.avatar_image_view)
    ImageView avatarImageView;
    @BindView(R.id.add_avatar)
    ImageView addAvatarImageView;

    @BindView(R.id.user_account_detail_log_out_button)
    Button logout_btn;

    private View mView;
    private Unbinder mUnbinder;
    private User mUser;
    private Professors mProfessor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUserService = UserService.getService();
        mView = inflater.inflate(R.layout.fragment_user_account, container, false);

        Objects.requireNonNull(getActivity()).setTitle(R.string.menu_user_account);

        mUnbinder = ButterKnife.bind(this, mView);

        getMyAccount(mView.getContext());

        return mView;
    }

    private void getMyAccount(Context context) {
        mUser = AuthManager.getInstance().getUser();

        if (mUser.getFirstName() != null) {
            username_ll.setVisibility(View.VISIBLE);
            username_tv.setText(mUser.getFullFI());
        }

        if (mUser.getEmail() != null && !mUser.getEmail().isEmpty()) {
            email_ll.setVisibility(View.VISIBLE);
            email_tv.setText(mUser.getEmail());
        }

        if (AuthManager.getInstance().getUserRoles().contains(ROLE_TEACHER.toString())) {
            getMyAccountProfessor(context);
        } else{
            posada_tv.setText(R.string.student);
        }

        if (mUser.getTelefon1() != null && !mUser.getTelefon1().isEmpty()) {
            telefon1_ll.setVisibility(View.VISIBLE);
            telefon1_tv.setText(mUser.getTelefon1());
        } else {
            telefon1_ll.setVisibility(View.GONE);
        }

        detail_progress.setVisibility(View.GONE);
        userinfo_rl.setVisibility(View.VISIBLE);
        logout_btn.setVisibility(View.VISIBLE);
        user_account_header.setVisibility(View.VISIBLE);
        email_ll.setVisibility(View.VISIBLE);
        telefon1_ll.setVisibility(View.VISIBLE);
        if (mUser.getPhoto() == null) {
            Picasso.get()
                    .load(R.drawable.account_circle_outline)
                    .into(avatarImageView);
        } else {
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
                    .into(avatarImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private void getMyAccountProfessor(Context context) {
        mUserService.requestCurrentUserProfessorProfile(new UserService.OnRequestCurrentUserProfessorProfileListener() {
            @Override
            public void onRequestCurrentUserProfessorProfileSuccess(Call<Professors> call, Response<Professors> response) {
                if (response.body() != null) {
                    mProfessor = response.body();
                    if (mProfessor.getPosada() != null) {
                        posada_tv.setText(mProfessor.getPosada().getFullPostProfessor());
                    }
                    if (mProfessor.getDepartment() != null) {
                        department_ll.setVisibility(View.VISIBLE);
                        department_tv.setText(mProfessor.getDepartment().getName());
                    } else {
                        department_ll.setVisibility(View.GONE);
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

    @OnClick(R.id.user_account_detail_log_out_button)
    void onClickLogout() {
        AuthManager.getInstance().logout();

        Objects.requireNonNull(getActivity()).startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }

    @OnClick({R.id.avatar_image_view, R.id.add_avatar})
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
        ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
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
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                        .into(avatarImageView, new Callback() {
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
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
