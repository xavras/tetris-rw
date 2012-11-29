package com.example.game.model;

import android.graphics.Rect;

public final class ColisionDetector {

	public static boolean isCollision(Rect one, Rect two) {
		//TO DO
		if(one.bottom == two.top)
			return true;
		return false;
	}
}