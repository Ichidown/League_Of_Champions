package com.ichidown.loc.postprocessing.effects;


import com.ichidown.loc.postprocessing.PostProcessorEffect;

public abstract class Antialiasing extends PostProcessorEffect {

	public abstract void setViewportSize (int width, int height);
}
