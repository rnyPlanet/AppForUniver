package com.grin.appforuniver.ui.userAccount;

import android.Manifest;
import android.app.Activity;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.grin.appforuniver.R;
import com.grin.appforuniver.activities.ImagePickerActivity;
import com.grin.appforuniver.activities.LoginActivity;
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

import java.io.IOException;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;

public class UserAccountFragment extends Fragment {

    public final String TAG = UserAccountFragment.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private UserAccountViewModel userAccountViewModel;
    private FragmentUserAccountBinding binding;

    public UserAccountFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        userAccountViewModel = ViewModelProviders.of(this).get(UserAccountViewModel.class);
        binding = FragmentUserAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userAccountViewModel.getUserFI().observe(getViewLifecycleOwner(), s -> binding.userAccountUsernameTv.setText(s));
        userAccountViewModel.getEmail().observe(getViewLifecycleOwner(), s -> {
            binding.userAccountEmailLl.setVisibility(View.VISIBLE);
            binding.userAccountEmailTv.setText(s);
        });
        userAccountViewModel.getPosada().observe(getViewLifecycleOwner(), s -> binding.userAccountPosadaTv.setText(s));
        userAccountViewModel.getTelephone1().observe(getViewLifecycleOwner(), s -> {
            binding.userAccountPhone1Ll.setVisibility(View.VISIBLE);
            binding.userAccountPhone1Tv.setText(s);
        });
        userAccountViewModel.getDepartment().observe(getViewLifecycleOwner(), s -> {
            binding.userAccountDepartmentLl.setVisibility(View.VISIBLE);
            binding.userAccountDepartmentTv.setText(s);
        });
        userAccountViewModel.getUpdateAvatar().observe(getViewLifecycleOwner(), aBoolean -> requestAvatar());
        userAccountViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage ->
                Toasty.error(requireContext(), errorMessage, Toast.LENGTH_SHORT, true).show());
        binding.avatarImageView.setOnClickListener(view1 -> changeAvatar());
        binding.addAvatar.setOnClickListener(view2 -> changeAvatar());
        binding.userAccountDetailProgress.setVisibility(View.GONE);
        binding.userAccountEmailLl.setVisibility(View.VISIBLE);
        binding.userAccountPhone1Ll.setVisibility(View.VISIBLE);
        requestAvatar();
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

    private void requestAvatar() {
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

    void onClickLogout() {
        AuthManager.getInstance().logout();

        requireActivity().startActivity(new Intent(getContext(), LoginActivity.class));
        requireActivity().finish();
    }

    void changeAvatar() {
        Dexter.withActivity(requireActivity())
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
                    userAccountViewModel.uploadPhoto(uri.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}
