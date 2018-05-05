package model.entity.animation;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 * This is a JavaFX extended Event for the Sprite handling Animation classes.
 * There are 3 types of events that are called 1. Every time one full loop of
 * the animation is played 2. Every time an anamation moves 1 frame 3. Every
 * time the animation changes states
 * 
 * @author William Hastings (wbhastings@email.arizona.edu)
 *
 */
public class AnimationEvent extends Event {

	private static final long serialVersionUID = 7552571758406922005L;

	/**
	 * Creating each event type as well an ANY event type which lets you listen for
	 * any EventType
	 */
	public static final EventType<AnimationEvent> ANIMATION = new EventType<AnimationEvent>(Event.ANY, "ANIMATION");
	public static final EventType<AnimationEvent> ANY = ANIMATION;
	public static final EventType<AnimationEvent> LOOP_PASSED = new EventType<AnimationEvent>(AnimationEvent.ANY,
			"LOOP_PASSED");
	public static final EventType<AnimationEvent> FRAME_CHANGED = new EventType<AnimationEvent>(AnimationEvent.ANY,
			"FRAME_CHANGED");
	public static final EventType<AnimationEvent> STATE_CHANGE = new EventType<AnimationEvent>(AnimationEvent.ANY,
			"STATE_CHANGE");

	private int row;
	private int col;
	private int ticTotal;
	private int curTic;
	private AnimationState curState;
	private AnimationState oldState;
	private Animation animation;

	/**
	 * Create an Animation Event
	 * 
	 * @param eventType
	 *            the EventType that is happening
	 * @param anim
	 *            the Animation class
	 * @param row
	 *            the row of the Event was called on
	 * @param col
	 *            the column of the Event was called on
	 * @param curTic
	 *            the currentTic that the animation is on
	 * @param ticTotal
	 *            the total tic count since the animation started at the time of the
	 *            Event
	 * @param state
	 *            the AnimationState the animation was in at the Event
	 */
	public AnimationEvent(EventType<? extends AnimationEvent> eventType, Animation anim, int row, int col, int curTic,
			int ticTotal, AnimationState state) {
		this(null, null, eventType, anim, row, col, curTic, ticTotal, state);
	}

	/**
	 * 
	 * @param source
	 *            used to se the Event source
	 * @param target
	 *            used to set the target of the Event
	 * @param eventType
	 *            the EventType that is happening
	 * @param anim
	 *            the Animation class
	 * @param row
	 *            the row of the Event was called on
	 * @param col
	 *            the column of the Event was called on
	 * @param curTic
	 *            the currentTic that the animation is on
	 * @param ticTotal
	 *            the total tic count since the animation started at the time of the
	 *            Event
	 * @param state
	 *            the AnimationState the animation was in at the Event
	 */
	public AnimationEvent(Object source, EventTarget target, EventType<? extends AnimationEvent> eventType,
			Animation anim, int row, int col, int curTic, int ticTotal, AnimationState state) {
		super(source, target, eventType);
		this.animation = anim;
		this.row = row;
		this.col = col;
		this.ticTotal = ticTotal;
		this.curTic = curTic;
		this.curState = state;
	}

	@Override
	public AnimationEvent copyFor(Object newSource, EventTarget newTarget) {
		return (AnimationEvent) super.copyFor(newSource, newTarget);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EventType<? extends AnimationEvent> getEventType() {
		return (EventType<? extends AnimationEvent>) super.getEventType();
	}

	/**
	 * Set the OldState for a Change of State Event
	 * 
	 * @param oldState
	 *            the Old Event State to set
	 */
	public void setOldState(AnimationState oldState) {
		this.oldState = oldState;
	}

	/**
	 * Get the state of when the Event was triggered
	 * 
	 * @return AnimationState of the Event
	 */
	public AnimationState getCurrentState() {
		return this.curState;
	}

	/**
	 * Get the old state the Animation was in before it was changed
	 * 
	 * @return AnimationState
	 */
	public AnimationState getOldState() {
		return this.oldState;
	}

	/**
	 * Gets the row the Animation was on when the Event was called
	 * 
	 * @return Integer
	 */
	public int getCurrentRow() {
		return this.row;
	}

	/**
	 * Get the column the Animation was on when the Event was called
	 * 
	 * @return Integer
	 */
	public int getCurrentColumn() {
		return this.col;
	}

	/**
	 * Get the total tic count of the animation when the Event was triggered
	 * 
	 * @return Integer
	 */
	public int getTotalTicCount() {
		return this.ticTotal;
	}

	/**
	 * Get the current frame tic change count when the Animation Event was Triggered
	 * 
	 * @return Integer
	 */
	public int getCurrentFrameTic() {
		return this.curTic;
	}

	/**
	 * Get the Animation class triggering the Event
	 * 
	 * @return Animation
	 */
	public Animation getAnimation() {
		return this.animation;
	}
}
