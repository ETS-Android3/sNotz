package com.sunilpaulmathew.snotz.bridge_implementation;

import android.content.Context;
import android.graphics.Typeface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sunilpaulmathew.snotz.R;
import com.sunilpaulmathew.snotz.adapters.SettingsAdapter;
import com.sunilpaulmathew.snotz.bridge_interface.FontSetting;
import com.sunilpaulmathew.snotz.utils.SettingsItems;
import com.sunilpaulmathew.snotz.utils.Utils;
import com.sunilpaulmathew.snotz.utils.sNotzUtils;

import java.util.List;

public class DefaultFontSetting implements FontSetting {
    public int getFontSizePosition(Context context) {
        String value = String.valueOf(Utils.getInt("font_size", 18, context));
        for (int i = 0; i < getFontSizes().length; i++) {
            if (getFontSizes()[i].contains(value)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getStyle(Context context) {
        String style = Utils.getString("font_style", "bold|italic", context);
        switch (style) {
            case "regular":
                return Typeface.NORMAL;
            case "italics":
                return Typeface.ITALIC;
            case "bold":
                return Typeface.BOLD;
            default:
                return Typeface.BOLD_ITALIC;
        }
    }

    public void setFontSize(int position, List<SettingsItems> items, SettingsAdapter adapter, Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.font_size)
                .setSingleChoiceItems(getFontSizes(), getFontSizePosition(context), (dialog, itemPosition) -> {
                    Utils.saveInt("font_size", Integer.parseInt(getFontSizes()[itemPosition].replace("sp","")), context);
                    items.set(position, new SettingsItems(context.getString(R.string.font_size), context.getString(R.string.font_size_summary,
                            "" + Integer.parseInt(getFontSizes()[itemPosition].replace("sp",""))),
                            sNotzUtils.getDrawable(R.drawable.ic_format_size, context), null));
                    adapter.notifyItemChanged(position);
                    dialog.dismiss();
                }).show();
    }

    public String getFontStyle(Context context) {
        String style = Utils.getString("font_style", "bold|italic", context);
        switch (style) {
            case "regular":
                return context.getString(R.string.text_style_regular);
            case "italics":
                return context.getString(R.string.text_style_italics);
            case "bold":
                return context.getString(R.string.text_style_bold);
            default:
                return context.getString(R.string.text_style_bold_italics);
        }
    }

    public String getFontStyle(int position) {
        switch (position) {
            case 0:
                return "regular";
            case 1:
                return "italics";
            case 2:
                return "bold";
            default:
                return "bold|italic";
        }
    }

    private static int getFontStylePosition(Context context) {
        String style = Utils.getString("font_style", "bold|italic", context);
        switch (style) {
            case "regular":
                return 0;
            case "italics":
                return 1;
            case "bold":
                return 2;
            default:
                return 3;
        }
    }



    public void setFontStyle(int position, List<SettingsItems> items, SettingsAdapter adapter, Context context) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.text_style)
                .setSingleChoiceItems(getFontStyles(context), getFontStylePosition(context), (dialog, itemPosition) -> {
                    Utils.saveString("font_style", getFontStyle(itemPosition), context);
                    items.set(position, new SettingsItems(context.getString(R.string.text_style), getFontStyle(context),
                            sNotzUtils.getDrawable(R.drawable.ic_text_style, context), null));
                    adapter.notifyItemChanged(position);
                    dialog.dismiss();
                }).show();
    }

    private String[] getFontSizes() {
        return new String[]{"10sp", "11sp", "12sp", "13sp", "14sp", "15sp", "16sp", "17sp", "18sp", "19sp", "20sp",
                "21sp", "22sp", "23sp", "24sp", "25sp"};
    }

    public String[] getFontStyles(Context context) {
        return new String[]{context.getString(R.string.text_style_regular), context.getString(R.string.text_style_italics),
                context.getString(R.string.text_style_bold), context.getString(R.string.text_style_bold_italics)};
    }
}
