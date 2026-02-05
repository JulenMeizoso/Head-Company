package com.example.headcompany.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimUtils {

    public static void jiggle(View v) {
        ObjectAnimator squashX = ObjectAnimator.ofFloat(v, "scaleX", 1.6f);
        ObjectAnimator squashY = ObjectAnimator.ofFloat(v, "scaleY", 0.6f);
        squashX.setDuration(25);
        squashY.setDuration(25);
        squashX.setInterpolator(new AccelerateDecelerateInterpolator());
        squashY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator jiggleBackX = ObjectAnimator.ofFloat(v, "scaleX", 0.90f);
        ObjectAnimator jiggleBackY = ObjectAnimator.ofFloat(v, "scaleY", 1.1f);
        jiggleBackX.setDuration(50);
        jiggleBackY.setDuration(50);
        jiggleBackX.setInterpolator(new AccelerateDecelerateInterpolator());
        jiggleBackY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator stretchX = ObjectAnimator.ofFloat(v, "scaleX", 0.6f);
        ObjectAnimator stretchY = ObjectAnimator.ofFloat(v, "scaleY", 1.6f);
        stretchX.setDuration(50);
        stretchY.setDuration(50);
        stretchX.setInterpolator(new AccelerateDecelerateInterpolator());
        stretchY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX.setDuration(60);
        normalY.setDuration(60);
        normalX.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator squashX2 = ObjectAnimator.ofFloat(v, "scaleX", 1.2f);
        ObjectAnimator squashY2 = ObjectAnimator.ofFloat(v, "scaleY", 0.8f);
        squashX2.setDuration(75);
        squashY2.setDuration(75);
        squashX2.setInterpolator(new AccelerateDecelerateInterpolator());
        squashY2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX2 = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY2 = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX2.setDuration(75);
        normalY2.setDuration(75);
        normalX2.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY2.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(squashX).with(squashY);
        animatorSet.play(jiggleBackX).with(jiggleBackY).after(squashX);
        animatorSet.play(stretchX).with(stretchY).after(jiggleBackX);
        animatorSet.play(normalX).with(normalY).after(stretchX);
        animatorSet.play(squashX2).with(squashY2).after(normalX);
        animatorSet.play(normalX2).with(normalY2).after(squashX2);
        animatorSet.start();
    }

    public static void jiggleDown(View v) {
        ObjectAnimator squashX = ObjectAnimator.ofFloat(v, "scaleX", 1.6f);
        ObjectAnimator squashY = ObjectAnimator.ofFloat(v, "scaleY", 0.6f);
        squashX.setDuration(25);
        squashY.setDuration(25);
        squashX.setInterpolator(new AccelerateDecelerateInterpolator());
        squashY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(squashX).with(squashY);
        animatorSet.start();
    }

    public static void pressDown(View v) {
        ObjectAnimator pressX = ObjectAnimator.ofFloat(v, "scaleX", 0.8f);
        ObjectAnimator pressY = ObjectAnimator.ofFloat(v, "scaleY", 0.8f);
        pressX.setDuration(25);
        pressY.setDuration(25);
        pressX.setInterpolator(new AccelerateDecelerateInterpolator());
        pressY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(pressX).with(pressY);
        animatorSet.start();
    }

    public static void jiggleUp(View v) {
        ObjectAnimator jiggleBackX = ObjectAnimator.ofFloat(v, "scaleX", 0.90f);
        ObjectAnimator jiggleBackY = ObjectAnimator.ofFloat(v, "scaleY", 1.1f);
        jiggleBackX.setDuration(50);
        jiggleBackY.setDuration(50);
        jiggleBackX.setInterpolator(new AccelerateDecelerateInterpolator());
        jiggleBackY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator stretchX = ObjectAnimator.ofFloat(v, "scaleX", 0.6f);
        ObjectAnimator stretchY = ObjectAnimator.ofFloat(v, "scaleY", 1.6f);
        stretchX.setDuration(50);
        stretchY.setDuration(50);
        stretchX.setInterpolator(new AccelerateDecelerateInterpolator());
        stretchY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX.setDuration(60);
        normalY.setDuration(60);
        normalX.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator squashX2 = ObjectAnimator.ofFloat(v, "scaleX", 1.2f);
        ObjectAnimator squashY2 = ObjectAnimator.ofFloat(v, "scaleY", 0.8f);
        squashX2.setDuration(75);
        squashY2.setDuration(75);
        squashX2.setInterpolator(new AccelerateDecelerateInterpolator());
        squashY2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX2 = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY2 = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX2.setDuration(75);
        normalY2.setDuration(75);
        normalX2.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY2.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(jiggleBackX).with(jiggleBackY);
        animatorSet.play(stretchX).with(stretchY).after(jiggleBackX);
        animatorSet.play(normalX).with(normalY).after(stretchX);
        animatorSet.play(squashX2).with(squashY2).after(normalX);
        animatorSet.play(normalX2).with(normalY2).after(squashX2);
        animatorSet.start();
    }

    public static void bruhsoff(View v) {
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(v, "scaleX", 1.2f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(v, "scaleY", 1.2f);
        scaleUpX.setDuration(50);
        scaleUpY.setDuration(50);
        scaleUpX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleUpY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator rotate1 = ObjectAnimator.ofFloat(v, "rotation", 5f);
        rotate1.setDuration(50);
        rotate1.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator rotate2 = ObjectAnimator.ofFloat(v, "rotation", -5f);
        rotate2.setDuration(50);
        rotate2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator rotate3 = ObjectAnimator.ofFloat(v, "rotation", 5f);
        rotate3.setDuration(50);
        rotate3.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator rotate4 = ObjectAnimator.ofFloat(v, "rotation", 0f);
        rotate4.setDuration(50);
        rotate4.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX.setDuration(60);
        normalY.setDuration(60);
        normalX.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleUpX).with(scaleUpY);
        animatorSet.play(rotate1).after(scaleUpX);
        animatorSet.play(rotate2).after(rotate1);
        animatorSet.play(rotate3).after(rotate2);
        animatorSet.play(rotate4).after(rotate3);
        animatorSet.play(normalX).with(normalY).after(rotate4);
        animatorSet.start();
    }

    public static void blocknod(View v) {
        ObjectAnimator nod = ObjectAnimator.ofFloat(v, "translationX", 0f, 25f, -25f, 25f, -25f, 25f ,0f);
        nod.setDuration(400);
        nod.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(nod);
        animatorSet.start();
    }

    public static void slideRight(View v) {
        if (v == null) return;

        v.setVisibility(View.VISIBLE);

        ObjectAnimator slideIn = ObjectAnimator.ofFloat(v, "translationX", -v.getWidth(), 0f);
        slideIn.setDuration(250);
        slideIn.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        fadeIn.setDuration(400);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(slideIn, fadeIn);
        animatorSet.start();
    }

    public static void slideUp(View v) {
        if (v == null) return;

        v.setVisibility(View.VISIBLE);

        ObjectAnimator slideUp = ObjectAnimator.ofFloat(v, "translationY", v.getHeight(), 0f);
        slideUp.setDuration(150);
        slideUp.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        fadeIn.setDuration(400);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator stretchX = ObjectAnimator.ofFloat(v, "scaleX", 0.6f);
        ObjectAnimator stretchY = ObjectAnimator.ofFloat(v, "scaleY", 1.6f);
        stretchX.setDuration(50);
        stretchY.setDuration(50);
        stretchX.setInterpolator(new AccelerateDecelerateInterpolator());
        stretchY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX.setDuration(60);
        normalY.setDuration(60);
        normalX.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator squashX2 = ObjectAnimator.ofFloat(v, "scaleX", 1.2f);
        ObjectAnimator squashY2 = ObjectAnimator.ofFloat(v, "scaleY", 0.8f);
        squashX2.setDuration(75);
        squashY2.setDuration(75);
        squashX2.setInterpolator(new AccelerateDecelerateInterpolator());
        squashY2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator normalX2 = ObjectAnimator.ofFloat(v, "scaleX", 1f);
        ObjectAnimator normalY2 = ObjectAnimator.ofFloat(v, "scaleY", 1f);
        normalX2.setDuration(75);
        normalY2.setDuration(75);
        normalX2.setInterpolator(new AccelerateDecelerateInterpolator());
        normalY2.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(stretchX).with(stretchY).with(fadeIn).with(slideUp);
        animatorSet.play(normalX).with(normalY).after(stretchX);
        animatorSet.play(squashX2).with(squashY2).after(normalX);
        animatorSet.play(normalX2).with(normalY2).after(squashX2);
        animatorSet.playTogether(slideUp, fadeIn);
        animatorSet.start();
    }

    public static void slideDown(View v) {
        if (v == null) return;

        v.setVisibility(View.VISIBLE);

        ObjectAnimator slideDown = ObjectAnimator.ofFloat(v, "translationY", 0f, 1000f);
        slideDown.setDuration(200);
        slideDown.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f);
        fadeOut.setDuration(400);
        fadeOut.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator squashX = ObjectAnimator.ofFloat(v, "scaleX", 1.6f);
        ObjectAnimator squashY = ObjectAnimator.ofFloat(v, "scaleY", 0.6f);
        squashX.setDuration(100);
        squashY.setDuration(100);
        squashX.setInterpolator(new AccelerateDecelerateInterpolator());
        squashY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideDown).with(fadeOut).with(squashX).with(squashY);
        animatorSet.start();
    }


}
