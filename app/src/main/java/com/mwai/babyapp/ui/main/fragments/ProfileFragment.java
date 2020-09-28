package com.mwai.babyapp.ui.main.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mwai.babyapp.BuildConfig;
import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseFragment;
import com.mwai.babyapp.databinding.DialogCameraBinding;
import com.mwai.babyapp.databinding.FragmentProfileBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment {
    private FragmentProfileBinding binding;
    private static final String TAG = "ProfileFragment";
    private int PICK_IMAGE_REQUEST = 2;
    static final int CAMERA_PIC_REQUEST = 1;
    private static Uri outputFileUri;

    public FirebaseAuth mAuth;
    public FirebaseUser mFirebaseUser;
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        userProfileData();

        binding.profileImage.setOnClickListener(v -> selectImage());
        return binding.getRoot();
    }

    private void userProfileData() {
        if(mFirebaseUser != null) {
            binding.userName.setText(mFirebaseUser.getDisplayName());
            binding.email.setText(mFirebaseUser.getEmail());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void selectImage() {
        try {
            if (checkForPermission()) {
                if (ContextCompat.checkSelfPermission(activityContext, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            CAMERA_PIC_REQUEST);
                } else {

                    PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
                    int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
                    if (hasPerm == PackageManager.PERMISSION_GRANTED) {

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activityContext);
                        DialogCameraBinding dialogCameraBinding = DataBindingUtil.inflate(LayoutInflater.from(activityContext),
                                R.layout.dialog_camera, null, false);
                        dialogBuilder.setView(dialogCameraBinding.getRoot());
                        AlertDialog alertDialog = dialogBuilder.create();
                        alertDialog.show();
                        dialogCameraBinding.btnCamera.setOnClickListener(v -> {
                            alertDialog.dismiss();
                            outputFileUri = getImageUri(getActivity().getApplicationContext());
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                                cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                        });

                        dialogCameraBinding.btnClose.setOnClickListener(v -> alertDialog.dismiss());

                        dialogCameraBinding.btnGallery.setOnClickListener(v -> {

                            alertDialog.dismiss();
                            //create a file and pass the Uri of the file to the camera intent so that once the photo
                            //is taken it is stored in that file
                            outputFileUri = getImageUri(getActivity().getApplication().getApplicationContext());

                            Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                                startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
                            }
                        });

                    }
                }
            } else {
                Toast.makeText(activityContext,"Please allow camera permissions",Toast.LENGTH_SHORT ).show();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            Toast.makeText(activityContext, "Camera Permission error", Toast.LENGTH_SHORT).show();
        }
    }

    public static Uri getImageUri(Context mContext){
        Uri m_imgUri = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                m_imgUri = FileProvider.getUriForFile(mContext,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile(mContext));
            } else {
                m_imgUri = Uri.fromFile(createImageFile(mContext));
            }
        } catch (Exception p_e) {

        }
        return m_imgUri;
    }

    public static File createImageFile(Context mContext) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d("log_tag","image path: "+image.getAbsolutePath());

        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK ) {
            try {
                Uri extras = outputFileUri;
                Bitmap bmp = BitmapFactory.decodeFile(extras.getPath());

                binding.profileImage.setImageBitmap(bmp);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);

            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK ) {
            try {
                Uri extras = outputFileUri;
                Bitmap bmp = BitmapFactory.decodeFile(extras.getPath());

                binding.profileImage.setImageBitmap(bmp);
            } catch (Exception e) {
                Log.e(TAG, "exception: ", e);
            }
        }
    }
    }