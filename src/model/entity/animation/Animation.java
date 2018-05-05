package model.entity.animation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import model.entity.Moveable;
import model.entity.enemy.EnemyType;
import model.sprite.SpriteSheet;

/**
 * Create Animation from a SpriteSheet stored in EnemyType
 * 
 * @author William Hastings
 *
 */
public class Animation extends Moveable implements EventTarget {

	public static final int DEFAULT_CHANGE_ON = 4;
	public static final AnimationState DEFAULT_STATE = AnimationState.LEFT;

	private AnimationState aState;
	private SpriteSheet ss;
	private int ticCnt;
	private int changeOn;
	private int col;

	/**
	 * Creates an Animation from a Type and a SpriteSheet
	 * 
	 * @param ss
	 *            A SpriteSheet
	 * @param x
	 *            the x position for Moveable Class
	 * @param y
	 *            the y position for Moveable Class
	 * @param width
	 *            the width of the enemy
	 * @param height
	 *            the height of the enemy
	 */
	public Animation(SpriteSheet ss, double x, double y, double width, double height) {
		super(x, y, width, height);
		this.ss = ss;
		this.ticCnt = 0;
		this.changeOn = DEFAULT_CHANGE_ON;
		this.aState = DEFAULT_STATE;
	}

	/**
	 * Change how fast the animation changes between sheet columns
	 * 
	 * @param changeOn
	 *            integer 0 - MAX_INT
	 */
	public void setTicPerChange(int changeOn) {
		this.changeOn = changeOn;
	}

	/**
	 * Get the row of the SpriteSheet is animating on
	 * 
	 * @return the row animating over based on the AnimationState
	 */
	public int getRow() {
		return this.aState.getRow();
	}

	/**
	 * Get the current columns the SpriteSheet is animating on
	 * 
	 * @return the colum the current animation is at
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * Get the current AnimationState of the Animation
	 * 
	 * @return the current AnimationState
	 */
	public AnimationState getAnimationState() {
		return this.aState;
	}

	/**
	 * Set the Animation State to animate over a new State
	 * 
	 * @param state
	 *            the AnimationState to animate over
	 */
	public void setAnimationState(AnimationState state) {
		Event event = new AnimationEvent(AnimationEvent.STATE_CHANGE, this, this.getRow(), this.getCol(),
				this.ticCnt % this.changeOn, this.ticCnt, state);
		((AnimationEvent) event).setOldState(this.aState);
		this.fireEvent(event);
		this.aState = state;
	}

	/**
	 * Set the animation to restart at the 0 column for an animation row.
	 */
	public void reset() {
		this.col = 0;
		this.ticCnt = 0;
	}

	/**
	 * Used to update the animation on the main Game tic. Allows the animation to
	 * speed up and slow down by the main game speed (tic rate)
	 */
	public void update() {
		if(this.ss.getColumns(this.getRow()) == 0) return;
		
		this.ticCnt++;
		if (this.ticCnt % this.changeOn == 0) {
			Event event = new AnimationEvent(AnimationEvent.FRAME_CHANGED, this, this.getRow(), this.getCol(),
					this.ticCnt % this.changeOn, this.ticCnt, this.aState);
			this.fireEvent(event);

			if ((this.col = (this.col + 1) % (this.ss.getColumns(this.aState.getRow()))) == 0) {
				Event loopedEvent = new AnimationEvent(AnimationEvent.LOOP_PASSED, this, this.getRow(), this.getCol(),
						this.ticCnt % this.changeOn, this.ticCnt, this.aState);
				this.fireEvent(loopedEvent);
			}
		}
	}

	////////////////////// EVENT HANDLING /////////////////////////////

	@SuppressWarnings("rawtypes")
	private final Map<EventType, Collection<EventHandler>> handlers = new HashMap<>();

	/**
	 * Add an Event Listener to listen for the LOOP_PASSED AnimationEvent EventType
	 * 
	 * @param eventHandler
	 */
	public final <T extends Event> void setOnLoop(EventHandler<? super T> eventHandler) {
		this.handlers.computeIfAbsent(AnimationEvent.LOOP_PASSED, (k) -> new ArrayList<>()).add(eventHandler);
	}

	/**
	 * Add an Event Listener to listen for the STATE_CHANGE AnimationEvent EventType
	 * 
	 * @param eventHandler
	 */
	public final <T extends Event> void setOnStateChange(EventHandler<? super T> eventHandler) {
		this.handlers.computeIfAbsent(AnimationEvent.STATE_CHANGE, (k) -> new ArrayList<>()).add(eventHandler);
	}

	/**
	 * Add a listner to listen for the FRAME_CHANGE AnimationEvent EventType
	 * 
	 * @param eventHandler
	 */
	public final <T extends Event> void setOnFrameChange(EventHandler<? super T> eventHandler) {
		this.handlers.computeIfAbsent(AnimationEvent.FRAME_CHANGED, (k) -> new ArrayList<>()).add(eventHandler);
	}

	/**
	 * Used by the JavaFX EventDispatcher to dispatch events to the listeners
	 */
	@Override
	public final EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
		return tail.prepend(this::dispatchEvent);
	}

	/**
	 * Used to handle events internally
	 * 
	 * @param event
	 *            The JavaFX Event to send out to the listeners
	 * @param handlers
	 *            the Map of Listeners(handlers)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleEvent(Event event, Collection<EventHandler> handlers) {
		if (handlers != null) {
			handlers.forEach(handler -> handler.handle(event));
		}
	}

	private Event dispatchEvent(Event event, EventDispatchChain tail) {
		@SuppressWarnings("rawtypes")
		EventType type = event.getEventType();
		while (type != Event.ANY) {
			handleEvent(event, handlers.get(type));
			type = type.getSuperType();
		}
		handleEvent(event, handlers.get(Event.ANY));
		return event;
	}

	/**
	 * Used to fire an Event in JavaFX
	 * 
	 * @param event
	 *            the JavaFX Event to fire
	 */
	public void fireEvent(Event event) {
		Event.fireEvent(this, event);
	}

}
