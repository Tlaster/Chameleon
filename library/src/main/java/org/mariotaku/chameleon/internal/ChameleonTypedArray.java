package org.mariotaku.chameleon.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonUtils;
import org.mariotaku.chameleon.R;

/**
 * Created by mariotaku on 2016/12/18.
 */

public class ChameleonTypedArray {
    private final TypedArray wrapped;
    private final boolean[] hasAttributeStates;
    private final int[] attributeReferences;
    private final Chameleon.Theme theme;

    private ChameleonTypedArray(TypedArray wrapped, boolean[] hasAttributeStates, int[] attributeReferences, Chameleon.Theme theme) {
        this.wrapped = wrapped;
        this.hasAttributeStates = hasAttributeStates;
        this.attributeReferences = attributeReferences;
        this.theme = theme;
    }

    public void recycle() {
        wrapped.recycle();
    }

    public static ChameleonTypedArray obtain(Context context, AttributeSet set, int[] attrs, Chameleon.Theme theme) {
        @SuppressLint("Recycle") TypedArray array = context.obtainStyledAttributes(set, attrs);
        boolean[] hasAttribute = new boolean[attrs.length];
        int[] attributeReferences = new int[attrs.length];
        for (int i = 0; i < attrs.length; i++) {
            final int index = ChameleonUtils.findAttributeIndex(set, attrs[i]);
            if (index != -1) {
                hasAttribute[i] = true;
                String value = set.getAttributeValue(index);
                if (value != null && value.startsWith("?")) {
                    attributeReferences[i] = Integer.parseInt(value.substring(1));
                }
            }
        }
        return new ChameleonTypedArray(array, hasAttribute, attributeReferences, theme);
    }

    public int getColor(int index, int defValue, boolean resolveStyle) {
        final int ref = attributeReferences[index];
        int color = getCommonColorReference(ref);
        if (color != 0) return color;
        if (!resolveStyle && !hasAttributeStates[index]) return defValue;
        return wrapped.getColor(index, defValue);
    }

    public Drawable getDrawable(int index, boolean resolveStyle) {
        final int ref = attributeReferences[index];
        int color = getCommonColorReference(ref);
        if (color != 0) return new ColorDrawable(color);
        if (!resolveStyle && !hasAttributeStates[index]) return null;
        return wrapped.getDrawable(index);
    }

    public int getCommonColorReference(int ref) {
        if (ref == android.support.design.R.attr.colorPrimary) {
            return theme.getColorPrimary();
        } else if (ref == android.support.design.R.attr.colorPrimaryDark) {
            return theme.getColorPrimaryDark();
        } else if (ref == android.support.design.R.attr.colorAccent) {
            return theme.getColorAccent();
        } else if (ref == android.support.design.R.attr.colorControlNormal) {
            return theme.getColorControlNormal();
        } else if (ref == android.support.design.R.attr.colorControlActivated) {
            return theme.getColorControlActivated();
        } else if (ref == android.support.design.R.attr.colorControlHighlight) {
            return theme.getColorControlHighlight();
        } else if (ref == R.attr.colorToolbar) {
            return theme.getColorToolbar();
        }
        return 0;
    }
}
