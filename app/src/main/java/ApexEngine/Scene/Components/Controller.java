//
// Translated by CS2J (http://www.cs2j.com): 2015-12-12 7:53:51 PM
//

package ApexEngine.Scene.Components;

import ApexEngine.Scene.GameObject;

public abstract class Controller {
    private GameObject gameObject;

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject value) {
        gameObject = value;
    }

    public abstract void init();
    public abstract void update();
}


