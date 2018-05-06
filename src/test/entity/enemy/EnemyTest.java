package test.entity.enemy;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import model.entity.enemy.Enemy;
import model.entity.enemy.EnemyType;
import model.entity.enemy.TestEnemy;
import model.entity.enemy.boss.BossMario;
import model.entity.enemy.boss.EnemyBoss;
import model.entity.enemy.damageresistant.DamageResistant;
import model.entity.enemy.damageresistant.EasyToad;
import model.entity.enemy.damageresistant.HardToad;
import model.entity.enemy.damageresistant.MediumToad;
import model.entity.enemy.fast.FastEnemy;
import model.entity.enemy.fast.Luigi;
import model.entity.enemy.fast.MediumPrincess;
import model.entity.enemy.simple.EasyPrincess;
import model.entity.enemy.simple.HardPrincess;
import model.entity.enemy.simple.SimpleEnemy;
import model.entity.enemy.simple.Wario;
import model.entity.tower.projectiles.Damage;
import model.sound.soundeffect.EnemySoundEffects;
import model.sound.soundeffect.ProjectileSoundEffects;

class EnemyTest {

	@Test
	void testSimpleEnemy() {
		new JFXPanel();
		Enemy enemy= new EasyPrincess(1.3, 1.3);
		enemy.getHeight();
		enemy.getWidth();
		enemy.getHP();
		enemy.getMoneyForKill();
		enemy.getEnemyType();
		enemy.getHitBoxWidth();
		enemy.getHitBoxHeight();
		enemy.getStats().getHp();
		enemy.getStats().getSpeed();
		enemy.getStats().getType();
		double oldHp= enemy.getHP();
		Damage damage= new Damage(10);
		enemy.takeDamage(damage);
		assertEquals(enemy.getHP(),oldHp-10);
		Damage damage2= new Damage(490);
		enemy.takeDamage(damage2);
		assertFalse(enemy.isAlive());
	}
	@Test
	void testFastEnemy() {
		new JFXPanel();
		Enemy enemy= new MediumPrincess(1.3, 1.3);
		enemy.getHeight();
		enemy.getWidth();
		enemy.getHP();
		enemy.getMoneyForKill();
		enemy.getEnemyType();
		enemy.getHitBoxWidth();
		enemy.getHitBoxHeight();
		enemy.getStats().getHp();
		enemy.getStats().getSpeed();
		enemy.getStats().getType();
		enemy.getStats().getKillValue();
		double oldHp= enemy.getHP();
		Damage damage= new Damage(10);
		enemy.takeDamage(damage);
		assertEquals(enemy.getHP(),oldHp-10);
		Damage damage2= new Damage(400);
		enemy.takeDamage(damage2);
		assertFalse(enemy.isAlive());
	}
	@Test
	void testDamageResistantEnemy() {
		new JFXPanel();
		Enemy enemy= new EasyToad(1.3, 1.3);
		enemy.getHeight();
		enemy.getWidth();
		enemy.getHP();
		enemy.getMoneyForKill();
		enemy.getEnemyType();
		enemy.getHitBoxWidth();
		enemy.getHitBoxHeight();
		enemy.getStats().getHp();
		enemy.getStats().getSpeed();
		enemy.getStats().getType();
		enemy.getStats().getKillValue();
		double oldHp= enemy.getHP();
		Damage damage= new Damage(20);
		enemy.takeDamage(damage);
		assertEquals(enemy.getHP(),oldHp-20);
		Damage damage2= new Damage(Integer.MAX_VALUE);
		enemy.takeDamage(damage2);
		assertFalse(enemy.isAlive());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testEnemyBoss() {
		new JFXPanel();
		Enemy enemy= new BossMario(1.3, 1.3);
		enemy.getHeight();
		enemy.getWidth();
		enemy.getHP();
		enemy.getMoneyForKill();
		enemy.getEnemyType();
		enemy.getHitBoxWidth();
		enemy.getHitBoxHeight();
		enemy.getStats().getHp();
		enemy.getStats().getSpeed();
		enemy.getStats().getType();
		enemy.getStats().getKillValue();
		double oldHp= enemy.getHP();
		Damage damage= new Damage(60);
		enemy.takeDamage(damage);
		assertEquals(enemy.getHP(),oldHp-60);
		Damage damage2= new Damage(Integer.MAX_VALUE);
		enemy.takeDamage(damage2);
		assertFalse(enemy.isAlive());
	}

	@Test
	void testTestEnemy() {
		new JFXPanel();
		Enemy enemy= new TestEnemy(1.3, 1.3,0.03,0.03);
		assertEquals(enemy.getHeight(),0.03);
		assertEquals(enemy.getWidth(),0.03);
		assertEquals(enemy.getHP(),500);
		assertEquals(enemy.getMoneyForKill(),20);
		assertEquals(enemy.getEnemyType(),EnemyType.SIMPLE_ENEMY);
		assertEquals(enemy.getHitBoxWidth(),0.03);
		assertEquals(enemy.getHitBoxHeight(),0.03);
		assertEquals(enemy.getStats().getHp(),enemy.getHP());
		assertEquals(enemy.getStats().getSpeed(),enemy.getSpeed());
		assertEquals(enemy.getStats().getType(),enemy.getEnemyType());
		assertEquals(enemy.getStats().getKillValue(),enemy.getMoneyForKill());
		double oldHp= enemy.getHP();
		Damage damage= new Damage(10);
		enemy.takeDamage(damage);
		assertEquals(enemy.getHP(),oldHp-10);
		Damage damage2= new Damage(490);
		enemy.takeDamage(damage2);
		assertFalse(enemy.isAlive());
	}
	
	@Test
	void testMoneyForKill() {
		new JFXPanel();
		Enemy enemy= new EasyPrincess(1.3, 1.3);
		enemy.setMoneyForKill(200);
		assertEquals(enemy.getMoneyForKill(),200);
	}
	
	@Test
	void testDrawEnemy() {
		new JFXPanel();
		Canvas c = new Canvas();
		Enemy enemy= new EasyPrincess(1.3, 1.3);
		enemy.draw(c.getGraphicsContext2D(), 10.0);
	}
	
	@Test
	void playEnemySounds() {
		new JFXPanel();
		BossMario mario= new BossMario(1.3,1.3);
		mario.playDeathSound();
		EasyPrincess princess1= new EasyPrincess(1.4,1.4);
		princess1.playDeathSound();
		EasyToad toad1= new EasyToad(1.5,1.5);
		toad1.playDeathSound();
		HardPrincess princess2= new HardPrincess(1.2,1.2);
		princess2.playDeathSound();
		HardToad toad2= new HardToad(1.1,1.1);
		toad2.playDeathSound();
		Luigi luigi= new Luigi(0.5,0.5);
		luigi.playDeathSound();
		MediumPrincess princess3= new MediumPrincess(0.7,0.7);
		princess3.playDeathSound();
		Wario warrio= new Wario(0.9,0.9);
		warrio.playDeathSound();
		MediumToad toad3= new MediumToad(0.3,0.3);
		toad3.playDeathSound();
		EnemySoundEffects.daisy_dies();
		EnemySoundEffects.luigi_dies();
		EnemySoundEffects.mario_dies();
		EnemySoundEffects.mario_summond();
		EnemySoundEffects.old_toad_dies();
		EnemySoundEffects.peach_dies();
		EnemySoundEffects.rosalina_dies();
		EnemySoundEffects.toad_dies();
		EnemySoundEffects.toadette_dies();
		EnemySoundEffects.waluigi_dies();
		EnemySoundEffects.wario_dies();
	}
	
	@Test
	void testProjectileSounds() {
		ProjectileSoundEffects.bomb_hit();
		ProjectileSoundEffects.bomb_shoot();
		ProjectileSoundEffects.bounce_hit();
		ProjectileSoundEffects.bounce_shoot();
		ProjectileSoundEffects.fireball_shoot();
		ProjectileSoundEffects.tower_build();
		ProjectileSoundEffects.tower_upgrade();
		ProjectileSoundEffects.whomp_hit();
	}
}
