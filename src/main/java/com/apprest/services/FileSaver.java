package com.apprest.services;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobTargetOption;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileSaver {

    private static Storage storage = StorageOptions.getDefaultInstance().getService();


    public String write(MultipartFile file) {

        try {
            BlobInfo blobInfo = storage.create(BlobInfo.newBuilder("micro-agency-364420.appspot.com",
                    file.getOriginalFilename()).build(),
                    file.getBytes(),
                    BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ));
            return blobInfo.getMediaLink();
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}