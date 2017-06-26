package com.cjburkey.factorius.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import com.cjburkey.factorius.Logger;

public final class GameLogicHandler {
	
	private final List<IGameLogic> gameLogic;
	
	public GameLogicHandler() {
		gameLogic = new ArrayList<>();
	}
	
	public void loadLogic() {
		Reflections r = new Reflections("");
		Set<Class<? extends IGameLogic>> logics = r.getSubTypesOf(IGameLogic.class);
		for(Class<? extends IGameLogic> cl : logics) {
			addClass(cl);
		}
		Logger.info("Loaded " + gameLogic.size() + " logic modules.");
	}
	
	public void foreach(Call each) {
		for(IGameLogic logic : gameLogic) {
			each.call(logic);
		}
	}
	
	private void addClass(Class<? extends IGameLogic> cl) {
		try {
			IGameLogic init = cl.newInstance();
			if(init != null && init instanceof IGameLogic) {
				addLogic((IGameLogic) init);
				return;
			}
			Logger.info("Couldn't instantiate GameLogic: " + cl.getName());
		} catch(Exception e) {
			Logger.info("Couldn't add GameLogic: " + cl.getName());
		}
	}
	
	private void addLogic(IGameLogic logic) {
		gameLogic.add(logic);
	}
	
	@FunctionalInterface
	public static interface Call {
		void call(IGameLogic logic);
	}
	
}