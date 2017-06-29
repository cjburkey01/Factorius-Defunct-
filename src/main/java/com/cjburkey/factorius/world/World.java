package com.cjburkey.factorius.world;

import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;
import com.cjburkey.factorius.object.GameObject;

/**
 * Handles objects in world.
 * @author cjburkey
 */
public final class World {
	
	private final SetUniqueList<GameObject> objs;
	
	/**
	 * Instantiate the world.
	 */
	public World() {
		objs = SetUniqueList.setUniqueList(new ArrayList<GameObject>());
	}
	
	/**
	 * Add a game object to the scene.
	 * @param obj The object to add.
	 */
	public void addObjectToWorld(GameObject obj) {
		objs.add(obj);
	}
	
	/**
	 * Remove a game object from the scene.
	 * @param obj The object to remove.
	 */
	public void removeObjectFromWorld(GameObject obj) {
		objs.remove(obj);
	}
	
	/**
	 * Get a list of the objects in the world.
	 * @return Array of objects.
	 */
	public GameObject[] getObjectsInWorld() {
		return objs.toArray(new GameObject[objs.size()]);
	}
	
}