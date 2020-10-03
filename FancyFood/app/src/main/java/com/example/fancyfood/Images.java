package com.example.fancyfood;

import android.net.Uri;

public class Images {
    public Uri imageUri;
    public String imageName;

    public Images(Uri imageUri, String imageName) {
        this.imageUri = imageUri;
        this.imageName = imageName;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
