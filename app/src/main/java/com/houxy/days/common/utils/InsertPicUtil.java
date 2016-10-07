package com.houxy.days.common.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.houxy.days.R;
import com.houxy.days.base.StringHolder;
import com.houxy.days.common.ACache;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Houxy on 2016/9/5.
 */
public class InsertPicUtil {

    private static Pattern IMAGE_TAG_PATTERN = Pattern.compile("<img(.*?)>");
    private static Pattern IMAGE_SRC_PATTERN = Pattern.compile("src=\"(.*?)\"");

    public static void upDateEditText(Uri uri,String imageUrl, EditText editText) {
        int index = editText.getSelectionStart();
        Editable editable = editText.getEditableText();
        if (index < 0 || index >= editable.length()) {
            editable.append("\n");
            editable.append(getBitmapMime(uri, imageUrl, editText));
            editable.append("\n\n");
        } else {
            editable.insert(index, "\n");
            editable.insert(index, getBitmapMime(uri, imageUrl, editText));
        }
    }

    private static SpannableString getBitmapMime(Uri uri, String url, EditText editText) {

        Bitmap pic = BitmapUtils.compressImage(uri, editText.getContext(), getRealWidth(editText));
        url = "<img src=\"" + url + "\" />";
        SpannableString ss = new SpannableString(url);
        ImageSpan span = new ImageSpan(editText.getContext(), pic);
        ss.setSpan(span, 0, url.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private static int getRealWidth(EditText editText) {
        return editText.getWidth() - editText.getPaddingRight() - editText.getPaddingLeft();
    }

    /**
     * 从双引号之间取出字符串
     */
    @Nullable
    private static String getTextBetweenQuotation(String text) {
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


    public static void setRichText1NetWork(String text, final EditText editText) {
        ArrayList<StringHolder> imageHolders = new ArrayList<>();
        editText.setText("");
        editText.getText().append(text);
        //遍历查找
        Matcher imageMatcher, srcMatcher;
        imageMatcher = IMAGE_TAG_PATTERN.matcher(text);
        while (imageMatcher.find()) {
            String image = imageMatcher.group().trim();
            final int matchStringStartIndex = text.indexOf(image);
            final int matchStringEndIndex = matchStringStartIndex + image.length();
            srcMatcher = IMAGE_SRC_PATTERN.matcher(image);
            String src = null;
            if (srcMatcher.find()) {
                src = getTextBetweenQuotation(srcMatcher.group().trim().substring(4));
            }
            if (TextUtils.isEmpty(src)) {
                continue;
            }
            StringHolder imageHolder = new StringHolder(src, matchStringStartIndex, matchStringEndIndex);
            imageHolders.add(imageHolder);
        }


        for (int i = 0; i < imageHolders.size(); i++) {
            final StringHolder imageHolder = imageHolders.get(i);
            final String url = "<img src=\"" + imageHolder.getString() + "\" />";
            final SpannableString ss = new SpannableString(url);

            final SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>(360, 480) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ImageSpan span = new ImageSpan(editText.getContext(), resource);
                    ss.setSpan(span, 0, url.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    int index = imageHolder.getStartIndex();
                    editText.getText().delete(index, imageHolder.getEndIndex());
                    editText.getText().insert(index, ss);
                    Log.d("TAG", imageHolder.toString());
                }
            };

            Glide.with(editText.getContext()).load(imageHolder.getString())
                    .asBitmap()
                    .placeholder(R.mipmap.default_profile)
                    .into(simpleTarget);
        }
    }

    public static void setRichTextLocal(String text, EditText editText) {
        ArrayList<StringHolder> imageHolders = new ArrayList<>();
        editText.setText(text);
        //遍历查找
        Matcher imageMatcher;
        imageMatcher = IMAGE_TAG_PATTERN.matcher(text);
        while (imageMatcher.find()) {
            String image = imageMatcher.group().trim();
            int matchStringStartIndex = text.indexOf(image);
            int matchStringEndIndex = matchStringStartIndex + image.length();
            StringHolder imageHolder = new StringHolder(image, matchStringStartIndex, matchStringEndIndex);
            imageHolders.add(imageHolder);
        }

        for (int i = 0; i < imageHolders.size(); i++) {
            StringHolder imageHolder = imageHolders.get(i);
            String url = imageHolder.getString();
            SpannableString picSs = new SpannableString(url);
            Bitmap pic = ACache.getDefault().getAsBitmap(url);
            ImageSpan span = new ImageSpan(editText.getContext(), pic);
            picSs.setSpan(span, 0, url.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            int index = imageHolder.getStartIndex();
            editText.getText().delete(index, imageHolder.getEndIndex());
            editText.getText().insert(index, picSs);
        }
    }

}
