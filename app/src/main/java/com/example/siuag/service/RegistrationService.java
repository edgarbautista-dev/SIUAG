package com.example.siuag.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.siuag.dao.DocumentDAO;
import com.example.siuag.dao.UserDAO;
import com.example.siuag.entity.Document;
import com.example.siuag.entity.User;
import com.example.siuag.repository.SIUAGDatabase;
import com.example.siuag.util.StringUtils;

import java.io.ByteArrayOutputStream;

public class RegistrationService {

    private static Context context;

    public static void createUser(User user, Bitmap profileImage, Context context){
        RegistrationService.context = context;
        if(!StringUtils.isEmpty(profileImage)){
            user.setProfileImage(uploadProfileImage(profileImage));
        }

        SIUAGDatabase database = SIUAGDatabase.getInstance(RegistrationService.context);
        UserDAO userDAO = database.userDAO();
        userDAO.insertAll(user);
    }

    private static int uploadProfileImage(Bitmap image){
        byte[] data = getBitmapAsByteArray(image);

        SIUAGDatabase database = SIUAGDatabase.getInstance(RegistrationService.context);
        DocumentDAO documentDAO = database.documentDAO();
        Document document = new Document();
        document.setFile(data);
        return (int) documentDAO.insertDocument(document);
    }

    private static byte[] getBitmapAsByteArray(Bitmap image){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
