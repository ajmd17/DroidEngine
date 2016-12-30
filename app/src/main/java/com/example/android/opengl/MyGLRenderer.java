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

import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ApexEngine.AndroidRenderer.GLESRenderer;
import ApexEngine.Game;
import ApexEngine.Rendering.RenderManager;
import ApexEngine.TestGame;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Game game;
    private long deltaTime, lastFrameTime;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public MyGLRenderer() {
        game = new TestGame(new GLESRenderer());
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        game.run();
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        deltaTime = SystemClock.elapsedRealtime() - lastFrameTime;

        game.updateInternal();
        game.renderInternal();


        lastFrameTime = SystemClock.elapsedRealtime();

        game.getEnvironment().setTimePerFrame((float) deltaTime);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        RenderManager.getRenderer().viewport(0, 0, width, height);
        game.getInputManager().SCREEN_HEIGHT = height;
        game.getInputManager().SCREEN_WIDTH = width;
    }
}