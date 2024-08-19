package com.example.managersystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserResourcesService {
    @Value("classpath:access_info.dat")
    private File accessFile;

    public void assign(long userId, List<String> endpoints) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fis = new FileInputStream(accessFile);
            Map<Long, List<String>> userResourcesMap;

            if (fis.available() <= 0) {
                userResourcesMap = new HashMap<>();
                userResourcesMap.put(userId, endpoints);
            } else {
                ois = new ObjectInputStream(fis);
                Object object = ois.readObject();
                userResourcesMap = (Map<Long, List<String>>) object;
                userResourcesMap.put(userId, endpoints);
            }
            fos = new FileOutputStream(accessFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(userResourcesMap);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean hasUserResource(long userId, String resourceId) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(accessFile);
            if (fis.available() <= 0) {
                return false;
            }

            ois = new ObjectInputStream(fis);
            Map<Long, List<String>> userResourcesMap = (Map<Long, List<String>>) ois.readObject();
            List<String> resources = userResourcesMap.get(userId);
            return resources.contains(resourceId);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
