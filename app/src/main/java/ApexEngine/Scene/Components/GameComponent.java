//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:51 PM
//

package ApexEngine.Scene.Components;

import ApexEngine.Rendering.Camera;
import ApexEngine.Scene.Node;

public abstract class GameComponent implements EngineComponent {
    public Node rootNode = new Node("GameComponent Node");
    public Camera cam;

    public abstract void update();
    public abstract void init();
}


