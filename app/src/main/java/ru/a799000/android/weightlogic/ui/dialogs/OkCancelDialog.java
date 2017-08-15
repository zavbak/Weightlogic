package ru.a799000.android.weightlogic.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ru.a799000.android.weightlogic.R;
import ru.a799000.android.weightlogic.ui.fragments.BarcodesListScreenFr;

import android.content.DialogInterface.OnClickListener;

/**
 * Created by Alex on 15.08.2017.
 */

public class OkCancelDialog extends DialogFragment implements OnClickListener {

    public static final String TAG = "OkCancelDialog";

    String mTitle;
    String mMessage;
    int mId;
    CallBackOkCancelDialog mCallBackOkCancelDialog;

    BuilderInterface mBuilderInterface;


    public interface BuilderInterface{
        int getId();
        String getTitle();
        String getMessage();
    }

    public interface CallBackOkCancelDialog {
        void chouiceDialog(int id, Boolean positive);
    }


    public static OkCancelDialog getInstance(BuilderInterface builderInterface, CallBackOkCancelDialog callBackOkCancelDialog ) {
        OkCancelDialog fragment = new OkCancelDialog();
        fragment.setMessage(builderInterface.getMessage());
        fragment.setTitle(builderInterface.getTitle());
        fragment.setId(builderInterface.getId());
        fragment.setCallBackOkCancelDialog(callBackOkCancelDialog);
        return fragment;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setPositiveButton("Да", this)
                .setNegativeButton("Отмена", this)
                .setMessage(mMessage);

        return adb.create();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setCallBackOkCancelDialog(CallBackOkCancelDialog callBackOkCancelDialog) {
        mCallBackOkCancelDialog = callBackOkCancelDialog;
    }


    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mCallBackOkCancelDialog != null) {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    mCallBackOkCancelDialog.chouiceDialog(mId,true);
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    mCallBackOkCancelDialog.chouiceDialog(mId,false);
                    break;
            }
        }


    }
}
