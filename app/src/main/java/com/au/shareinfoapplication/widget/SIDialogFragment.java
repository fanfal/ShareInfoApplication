package com.au.shareinfoapplication.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;


public class SIDialogFragment extends android.support.v4.app.DialogFragment {
    public static final String TAG = "SIDialogFragment";

    private String title;
    private CharSequence message;
    private String positive;
    private String negative;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;
    private boolean cancelable;
    private DialogInterface.OnCancelListener cancelListener;
    private DialogInterface.OnDismissListener dismissListener;

    public static class Builder {
        private Context context;
        private String title = null;
        private CharSequence message = null;
        private String positive = null;
        private DialogInterface.OnClickListener positiveListener = null;
        private String negative = null;
        private DialogInterface.OnClickListener negativeListener = null;
        private boolean cancelable = true;
        private DialogInterface.OnCancelListener cancelListener = null;
        private DialogInterface.OnDismissListener dismissListener = null;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(int resId) {
            return setTitle(context.getString(resId));
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(int resId) {
            return setMessage(context.getString(resId));
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(int resId, DialogInterface.OnClickListener listener) {
            return setPositiveButton(context.getString(resId), listener);
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            this.positive = text;
            this.positiveListener = listener;
            return this;
        }

        public Builder setNegativeButton(int resId, DialogInterface.OnClickListener listener) {
            return setNegativeButton(context.getString(resId), listener);
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            this.negative = text;
            this.negativeListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener listener) {
            this.cancelListener = listener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public void show(FragmentManager fragmentManager) {
            show(fragmentManager, SIDialogFragment.TAG);
        }

        public void show(FragmentManager fragmentManager, String tag) {
            SIDialogFragment fragment = new SIDialogFragment();
            fragment.title = title;
            fragment.message = message;
            fragment.positive = positive;
            fragment.negative = negative;
            fragment.positiveListener = positiveListener;
            fragment.negativeListener = negativeListener;
            fragment.cancelable = cancelable;
            fragment.cancelListener = cancelListener;
            fragment.dismissListener = dismissListener;
            fragmentManager.beginTransaction()
                    .add(fragment, tag)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message);
        if (negative != null) {
            builder.setNegativeButton(negative, negativeListener);
        }
        if (positive != null) {
            builder.setPositiveButton(positive, positiveListener);
        }
        setCancelable(cancelable);
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null)
            cancelListener.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
}
