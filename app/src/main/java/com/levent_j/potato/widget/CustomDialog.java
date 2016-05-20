package com.levent_j.potato.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.levent_j.potato.R;

/**
 * Created by levent_j on 16-5-20.
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context,int theme){
        super(context,theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setMessage(String message){
            this.message = message;
            return this;
        }
        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }
        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public CustomDialog create(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);


            View layout = inflater.inflate(R.layout.custom_dialog,null);
            dialog.addContentView(layout, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            ((TextView)layout.findViewById(R.id.tv_dialog_title)).setText(title);

            if (positiveButtonText != null){
                ((Button)layout.findViewById(R.id.btn_dialog_comfirm)).setText(positiveButtonText);
                if (positiveButtonClickListener!=null){
                    layout.findViewById(R.id.btn_dialog_comfirm).setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            }else {
                layout.findViewById(R.id.btn_dialog_comfirm).setVisibility(View.GONE);
            }

            if (negativeButtonText != null){
                ((Button)layout.findViewById(R.id.btn_dialog_cancel)).setText(negativeButtonText);
                if (negativeButtonClickListener!=null){
                    layout.findViewById(R.id.btn_dialog_cancel)
                            .setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            }else {
                layout.findViewById(R.id.btn_dialog_cancel).setVisibility(View.GONE);
            }

            ((TextView) layout.findViewById(R.id.tv_dialog_message)).setText(message);

            dialog.setContentView(layout);
            return dialog;

        }
    }

}
