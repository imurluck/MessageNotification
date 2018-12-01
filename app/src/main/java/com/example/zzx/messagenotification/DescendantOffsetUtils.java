package com.example.zzx.messagenotification;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class DescendantOffsetUtils {
    private static final ThreadLocal<Matrix> matrix = new ThreadLocal();
    private static final ThreadLocal<RectF> rectFLocal = new ThreadLocal();

    public DescendantOffsetUtils() {
    }

    public static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
        Matrix m = (Matrix)matrix.get();
        if (m == null) {
            m = new Matrix();
            matrix.set(m);
        } else {
            m.reset();
        }

        offsetDescendantMatrix(parent, descendant, m);
        RectF rectF = (RectF) rectFLocal.get();
        if (rectF == null) {
            rectF = new RectF();
            rectF.set(rectF);
        }
        rectF.set(rect);
        m.mapRect(rectF);
        rect.set((int)(rectF.left + 0.5F), (int)(rectF.top + 0.5F), (int)(rectF.right + 0.5F), (int)(rectF.bottom + 0.5F));
    }

    public static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }

    private static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
        ViewParent parent = view.getParent();
        if (parent instanceof View && parent != target) {
            View vp = (View)parent;
            offsetDescendantMatrix(target, vp, m);
            m.preTranslate((float)(-vp.getScrollX()), (float)(-vp.getScrollY()));
        }

        m.preTranslate((float)view.getLeft(), (float)view.getTop());
        if (!view.getMatrix().isIdentity()) {
            m.preConcat(view.getMatrix());
        }

    }
}
