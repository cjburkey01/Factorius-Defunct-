package com.cjburkey.factorius.world;

import java.util.ArrayList;
import org.apache.commons.collections4.list.SetUniqueList;
import com.cjburkey.factorius.object.GameObject;

public class World {
	
	private final SetUniqueList<GameObject> objs;
	
	public World() {
		objs = SetUniqueList.setUniqueList(new ArrayList<GameObject>());
	}
	
	public void addObjectToWorld(GameObject obj) {
		objs.add(obj);
	}
	
	public void removeObjectFromWorld(GameObject obj) {
		objs.remove(obj);
	}
	
	public GameObject[] getObjectsInWorld() {
		return objs.toArray(new GameObject[objs.size()]);
	}
	
}