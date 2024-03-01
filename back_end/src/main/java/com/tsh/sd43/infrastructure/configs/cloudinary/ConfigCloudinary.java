package com.tsh.sd43.infrastructure.configs.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigCloudinary {

    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dlietlq1n",
                "api_key", "895638132269786",
                "api_secret", "zBSyKs8tHmJpOzG6mnH4xwrpRkc",
                "secure", true));
    }
}
