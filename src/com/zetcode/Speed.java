package com.zetcode;

import javax.swing.Timer;

public class Speed {
	public int setDelay(Timer timer, int DELAY, int timerDecrement) {
		DELAY = DELAY - timerDecrement;
		timer.setInitialDelay(DELAY);
		return DELAY;
	}

	public void updateTimer(Timer timer, int DELAY) {
		timer.setDelay(DELAY);
		timer.start();
	}
}
