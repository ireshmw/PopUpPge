package com.learntv.iresh.popuppge.animating;

import android.animation.Animator;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;

/**
 * Created by iresh on 3/14/2017.
 */

public class PopUpAnimating extends Thread {
    int sleepTime ;

    @Override
    public void run() {

        System.out.println("sleep time............"+sleepTime);
    }


    public void animateImage(String name, final ImageButton member, final int delay){
//        final boolean[] state = {true};
//        while (state[0]) {
        sleepTime = delay;
            final ViewPropertyAnimator anim = member.animate();
            anim.scaleX(2f).scaleY(2f).setDuration(1000).translationX(0).translationY(400).setStartDelay(1000)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            anim.scaleX(1).scaleY(1).translationX(0).translationY(0).setDuration(1000);
                            //state[0] = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }

                    });
        //}
    }
}


