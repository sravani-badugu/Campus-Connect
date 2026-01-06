package com.example.campusconnect;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.Map;

public class CloudinaryConfig {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map config = ObjectUtils.asMap(
                    "cloud_name", "drh3awyrj",
                    "api_key", "243277976284668",
                    "api_secret", "cw_UhQsWjKG8pcPsvzzaE2Fw_vM"
            );
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }
}
