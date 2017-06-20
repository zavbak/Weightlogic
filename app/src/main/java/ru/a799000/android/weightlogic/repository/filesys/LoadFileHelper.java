package ru.a799000.android.weightlogic.repository.filesys;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


import rx.Observable;

/**
 * Created by Alex on 30.05.2017.
 */

public class LoadFileHelper {

    String mFileName;

    public LoadFileHelper(String fileName) {
        mFileName = fileName;
    }

    public Observable<String> readStringObservable() {

        String fullString = "";

        try {


            String state = Environment.getExternalStorageState();
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                return Observable.error(new Throwable("Нет доступа для чтения и записи"));
            }

            File sdPath = Environment.getExternalStorageDirectory();


            sdPath = new File(sdPath.getAbsolutePath());


            // открываем поток для чтения
            File sdFile = new File(sdPath, mFileName);
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";


            // читаем содержимое
            while ((str = br.readLine()) != null) {
                fullString = fullString + "\n" +str;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Observable.error(e);
        } catch (IOException e) {

        } catch (Exception e) {

            e.printStackTrace();
            return Observable.error(e);
        }

        return Observable.just(fullString);

    }


}
