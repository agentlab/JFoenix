/*
 * Copyright (c) 2015, CCT and/or its affiliates. All rights reserved.
 * CCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.cctintl.jfx.transitions.hamburger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import com.cctintl.jfx.controls.JFXHamburger;
import com.cctintl.jfx.jidefx.CachedTimelineTransition;

public class HamburgerBasicCloseTransition extends CachedTimelineTransition implements HamburgerTransition {
	
	public HamburgerBasicCloseTransition(){
		super(null,null);
	}
	
	public HamburgerBasicCloseTransition(JFXHamburger burger) {
		super(burger, createTimeline(burger));
		timeline.bind(Bindings.createObjectBinding(()->createTimeline(burger),
				burger.widthProperty(), burger.heightProperty(),
				((Region) burger.getChildren().get(0)).widthProperty(), ((Region) burger.getChildren().get(0)).heightProperty() ));							
		// reduce the number to increase the shifting , increase number to reduce shifting
		setCycleDuration(Duration.seconds(0.3));
		setDelay(Duration.seconds(0));
	}
	
	private static Timeline createTimeline(JFXHamburger burger){
		double burgerWidth = burger.getChildren().get(0).getLayoutBounds().getWidth();
		double burgerHeight = burger.getChildren().get(2).getBoundsInParent().getMaxY() - burger.getChildren().get(0).getBoundsInParent().getMinY();
		
		double hypotenuse = Math.sqrt(Math.pow(burgerHeight, 2) + Math.pow(burgerWidth, 2));
		double angle = (Math.toDegrees(Math.asin(burgerWidth/hypotenuse)) - 90) * -1;
		return new Timeline(
				new KeyFrame(
						Duration.ZERO,       
						new KeyValue(burger.rotateProperty(), 0,Interpolator.EASE_BOTH),
						new KeyValue(burger.getChildren().get(0).rotateProperty(), 0,Interpolator.EASE_BOTH),
						new KeyValue(burger.getChildren().get(0).translateYProperty(), 0,Interpolator.EASE_BOTH),
						new KeyValue(burger.getChildren().get(2).rotateProperty(), 0,Interpolator.EASE_BOTH),
						new KeyValue(burger.getChildren().get(2).translateYProperty(), 0,Interpolator.EASE_BOTH),
						new KeyValue(burger.getChildren().get(1).opacityProperty(), 1,Interpolator.EASE_BOTH)
						),
						new KeyFrame(Duration.millis(1000),
								new KeyValue(burger.rotateProperty(), 0,Interpolator.EASE_BOTH),
								new KeyValue(burger.getChildren().get(0).rotateProperty(), angle,Interpolator.EASE_BOTH),
								new KeyValue(burger.getChildren().get(0).translateYProperty(), (burgerHeight/2)-burger.getChildren().get(0).getBoundsInLocal().getHeight()/2 ,Interpolator.EASE_BOTH),
								new KeyValue(burger.getChildren().get(2).rotateProperty(), -angle,Interpolator.EASE_BOTH),
								new KeyValue(burger.getChildren().get(2).translateYProperty(), - ((burgerHeight/2)-burger.getChildren().get(0).getBoundsInLocal().getHeight()/2),Interpolator.EASE_BOTH),
								new KeyValue(burger.getChildren().get(1).opacityProperty(), 0,Interpolator.EASE_BOTH)
								)
				);
	}
	
	public Transition getAnimation(JFXHamburger burger){
		return new HamburgerBasicCloseTransition(burger);
	}
	
}