package com.example.photobombproject.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.multidex.BuildConfig;

import com.example.photobombproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    public static String JWT = "";
    public static int Count = 0;
    public static int Version_hit = 0;
    public static String name = "";


    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    //methods to compress image starts//
    public static byte[] FileToByteArray(String file) {
        File fil = new File(file);

        byte[] b = new byte[(int) fil.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(fil);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char) b[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return b;
    }


    public static boolean isEmailValid(String mail) {
        String exp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public static void closeKeyboard(Activity cnx) {

        InputMethodManager imm = (InputMethodManager) cnx.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            if (imm.isAcceptingText() || imm.isActive())
                imm.hideSoftInputFromWindow(cnx.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "QuestionImage", null);
        return Uri.parse(path);
    }


    public static void SHARE_APP(Context context) {
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download  "+context.getString(R.string.app_name)+" App " + playStoreLink);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }



    public static void makeTextViewResizable(final TextView tv,
                                             final int maxLine, final String expandText, final boolean viewMore) {
        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0,
                            lineEndIndex - expandText.length() + 1)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0,
                            lineEndIndex - expandText.length() + 1)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex)
                            + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText()
                                            .toString(), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }


    private static SpannableStringBuilder addClickablePartTextViewResizable(
            final String strSpanned, final TextView tv, final int maxLine,
            final String spanableText, final boolean viewMore) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.RED);

        if (strSpanned.contains(spanableText)) {
            ssb.setSpan(
                    new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {

                            if (viewMore) {
                                tv.setLayoutParams(tv.getLayoutParams());
                                tv.setText(tv.getTag().
                                        toString(), TextView.BufferType.SPANNABLE);
                                tv.invalidate();
                                makeTextViewResizable(tv, -4, "...Read Less", false);
                                //tv.setTextColor(Color.BLACK);
                            } else {
                                tv.setLayoutParams(tv.getLayoutParams());
                                tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                                tv.invalidate();
                                makeTextViewResizable(tv, 4, "...Read More", true);
                                //tv.setTextColor(Color.BLACK);
                            }

                        }
                    }, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            ssb.setSpan(fcs, strSpanned.indexOf(spanableText),
                    strSpanned.indexOf(spanableText) + spanableText.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }
        return ssb;

    }

    public static String formattedTime(long millis) {
        if (millis < 1) {
            return "00:00:00";
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static String convertTimeToMinutes(String time) {
        String[] separated = time.split(":");
        long hours = Long.parseLong(separated[0]);
        long minute = Long.parseLong(separated[1]);
        long second = Long.parseLong(separated[2]);
        return String.valueOf(((hours*3600) + (minute*60) + (second))/60);
    }
    public static String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
      /*  if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";*/
        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }






    public static boolean DataNotValid(EditText view) {
        view.setError("This field is required");
        view.requestFocus();
        return false;
    }
    public static boolean DataNotValid(EditText view, int type) {
        if (type == 1) view.setError("This Email Id is invalid");
        else if (type == 2) view.setError("This Phone is invalid");
        else if (type == 3) view.setError("Password must contain at least 8 characters");
        else if (type == 4) view.setError("Password must be as per  given below instruction");
        else if (type == 5) view.setError("Enter a valid mobile number");
        view.requestFocus();
        return false;
    }


    public static void shareContentExternally(final Activity activity, String typeID, String videoUrl, String id, String mainID, String videoName, String imageUrl) {



        String playStoreLink = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download  "+activity.getString(R.string.app_name)+" App " + playStoreLink);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }

    public static void shareApp(String uri, Activity activity){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,uri);
        intent.setType("text/plain");
        activity.startActivity(intent);
    }

    public static void sendLink(Activity activity, String subject, String msg, String msgHtml) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        ///  sharingIntent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
        if (sharingIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(sharingIntent);
        }
    }

    public static String convertMillieToHMmSs(long millie) {
        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        String result = "";
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            return String.format("%02d:%02d", minute, second);
        }

    }

/*

    public static void openLoginLogoutAlertDialog(final Context context, Progress progress, String alertType) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme);
        dialog.setContentView(R.layout.common_dialogue);

        AppCompatButton yesBtn = dialog.findViewById(R.id.yes_ad_text);
        AppCompatButton cancelBtn = dialog.findViewById(R.id.cancel_ad_text);

        ImageView logoIV = dialog.findViewById(R.id.alert_dialoge_iv);
        TextView titleTV = dialog.findViewById(R.id.ad_title);
        TextView messageTV = dialog.findViewById(R.id.ad_textview);

       */
/* assert logoIV != null;
        logoIV.setImageResource(alertType.equals(Const.LOGIN) ? R.drawable.ic_login_alert : R.drawable.ic_logout_alert);
        Objects.requireNonNull(titleTV).setText(alertType.equals(Const.LOGIN) ? R.string.log_in_alert : R.string.log_Out);
        Objects.requireNonNull(messageTV).setText(alertType.equals(Const.LOGIN) ? R.string.to_watch_this_content_login_now : R.string.log_Out_msg);
        Objects.requireNonNull(btnOk).setText(alertType.equals(Const.LOGIN) ? R.string.login_now : R.string.logout);
*//*
*/
/*
        Objects.requireNonNull(yesBtn).setOnClickListener(v -> {
            dialog.dismiss();
            if (alertType.equals(Const.LOGIN))
                context.startActivity(new Intent(context, LoginSignupActivity.class));
            else {
                if (Functions.isConnectingToInternet(context)) {
                    calLogoutUserFromAppApi(context, progress);
                } else {
                    Toast.makeText(context, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });*//*


        yesBtn.setOnClickListener(view -> logoutUser(context,dialog));

        Objects.requireNonNull(cancelBtn).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
*/


}




