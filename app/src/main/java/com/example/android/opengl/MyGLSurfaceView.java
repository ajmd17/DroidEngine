/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import ApexEngine.Input.InputManager.MouseButton;
import ApexEngine.Math.Vector3f;
import ApexEngine.Rendering.Cameras.DefaultCamera;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

/**
 * A view container where OpenGL ES graphics can be drawn on screen. This view
 * can also be used to capture touch events, such as a user interacting with
 * drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

	private final MyGLRenderer mRenderer;

	public MyGLRenderer getRenderer() {
		return mRenderer;
	}

	public MyGLSurfaceView(Context context) {
		super(context);

		setEGLContextClientVersion(2);
		super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);

		mRenderer = new MyGLRenderer();
		setRenderer(mRenderer);

		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

		this.setOnTouchListener(new OnTouchListener() {
			

			float prevX = 0f, prevY = 0f;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					getRenderer().getGame().getInputManager().mouseButtonDown(MouseButton.Left);
				}
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					getRenderer().getGame().getInputManager().mouseButtonUp(MouseButton.Left);
				}
				
				
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					getRenderer().getGame().getInputManager().MOUSE_X = (int) x
							- (getRenderer().getGame().getInputManager().SCREEN_WIDTH / 2);
					getRenderer().getGame().getInputManager().MOUSE_Y = (int)y
							- (getRenderer().getGame().getInputManager().SCREEN_HEIGHT / 2);
					if (getRenderer().getGame().getCamera() instanceof DefaultCamera) {
						float dx = x - prevX;
						float dy = y - prevY;
						getRenderer().getGame().getCamera().rotate(Vector3f.UnitY, dx * 0.08f);
						getRenderer().getGame().getCamera().rotate(getRenderer().getGame().getCamera().getDirection().cross(Vector3f.UnitY), dy * 0.08f);
					}
				}
				prevX = x;
				prevY = y;
				return true;
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {

		return false;
	}

}
