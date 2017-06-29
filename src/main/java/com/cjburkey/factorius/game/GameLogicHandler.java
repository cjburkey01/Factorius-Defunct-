package com.cjburkey.factorius.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import com.cjburkey.factorius.Logger;

/**
 * Handles game logic.
 * @author cjburkey
 */
public final class GameLogicHandler {
	
	private final List<IGameLogic> gameLogic;
	
	/**
	 * Creates an instance of the handler.
	 */
	public GameLogicHandler() {
		gameLogic = new ArrayList<>();
	}
	
	/**
	 * Loads logic from classpath.
	 */
	public void loadLogic() {
		gameLogic.clear();
		Reflections r = new Reflections("");
		Set<Class<? extends IGameLogic>> logics = r.getSubTypesOf(IGameLogic.class);
		for(Class<? extends IGameLogic> cl : logics) {
			addClass(cl);
		}
		Logger.info("Loaded " + gameLogic.size() + " logic modules.");
	}
	
	/**
	 * Calls runnable for each logic currently loaded.
	 * @param each
	 */
	public void foreach(Call each) {
		for(IGameLogic logic : gameLogic) {
			each.call(logic);
		}
	}
	
	/**
	 * Instantiates and loads a game logic class.
	 * @param cl
	 */
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
	
	/**
	 * Adds instantiated game logic to the list of game logics.
	 * @param logic
	 */
	private void addLogic(IGameLogic logic) {
		gameLogic.add(logic);
	}
	
	/**
	 * Called and supplied with instance of interface.
	 * @author cjburkey
	 */
	@FunctionalInterface
	public static interface Call {
		/**
		 * Called with interface
		 * @param logic Instance of interface
		 */
		void call(IGameLogic logic);
	}
	
}