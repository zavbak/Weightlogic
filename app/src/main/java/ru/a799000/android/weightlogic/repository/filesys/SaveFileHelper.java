package ru.a799000.android.weightlogic.repository.filesys;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import rx.Observable;

/**
 * Created by user on 20.06.2017.
 */

public class SaveFileHelper {
    String mFileName;
    String mBody;

    public SaveFileHelper(String fileName, String body) {
        mFileName = fileName;
        mBody = body;
    }

    public Boolean readStringObservable() {


        try {

            String state = Environment.getExternalStorageState();
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return false;
            }


            File sdPath = Environment.getExternalStorageDirectory();

            sdPath = new File(sdPath.getAbsolutePath());
            File sdFile = new File(sdPath, mFileName);

            //Если нет директорий в пути, то они будут созданы:
            if (!sdFile.getParentFile().exists())
                sdFile.getParentFile().mkdirs();
            //Если файл существует, то он будет перезаписан:
            sdFile.createNewFile();

            FileOutputStream fOut = new FileOutputStream(sdFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(mBody);
            myOutWriter.close();
            fOut.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
