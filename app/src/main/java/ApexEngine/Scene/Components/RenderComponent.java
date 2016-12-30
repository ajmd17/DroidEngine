//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:51 PM
//

package ApexEngine.Scene.Components;

import ApexEngine.Rendering.RenderManager;
import ApexEngine.Scene.Components.EngineComponent;

public abstract class RenderComponent   implements EngineComponent
{
    public RenderManager renderManager;
    public abstract void init()  ;

    public abstract void render()  ;

    public abstract void update()  ;

}


