package org.mariotaku.chameleon.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;

import org.mariotaku.chameleon.Chameleon;
import org.mariotaku.chameleon.ChameleonView;

/**
 * Created by mariotaku on 2016/12/18.
 */

public class ChameleonAutoCompleteTextView extends AppCompatAutoCompleteTextView implements ChameleonView {
    public ChameleonAutoCompleteTextView(Context context) {
        super(context);
    }

    public ChameleonAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChameleonAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isPostApplyTheme() {
        return false;
    }

    @Nullable
    @Override
    public ChameleonEditText.Appearance createAppearance(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull Chameleon.Theme theme) {
        return ChameleonTextView.Appearance.create(this, context, attributeSet, theme);
    }


    @Override
    public void applyAppearance(@NonNull Appearance appearance) {
        final ChameleonTextView.Appearance a = (ChameleonTextView.Appearance) appearance;
        ChameleonTextView.Appearance.apply(this, a);
    }

}
